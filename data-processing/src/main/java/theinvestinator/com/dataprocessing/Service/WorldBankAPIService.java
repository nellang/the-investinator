package theinvestinator.com.dataprocessing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import theinvestinator.com.dataprocessing.Model.BusinessRegistrationProcedures;
import theinvestinator.com.dataprocessing.Model.Exports;
import theinvestinator.com.dataprocessing.Model.Imports;
import theinvestinator.com.dataprocessing.Repository.BusinessRegistrationProceduresRepository;
import theinvestinator.com.dataprocessing.Repository.CountryRepository;
import theinvestinator.com.dataprocessing.Repository.ExportsRepository;
import theinvestinator.com.dataprocessing.Repository.ImportsRepository;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class WorldBankAPIService {

    @Autowired
    private XMLParserService xmlParserService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private BusinessRegistrationProceduresRepository registrationProceduresRepository;

    @Autowired
    private ImportsRepository importsRepository;

    @Autowired
    private ExportsRepository exportsRepository;

    //store data in database
    public void saveWorldBankData() {
        countryRepository.findAll().forEach(country -> {
            int countryID = country.getCountryID();

            //save business registration procedures
            System.out.println("Business registration procedures " + country.getName() + ": ");
            Map<LocalDate, Double> businessRegistrationProceduresTimeSeriesMap = getWorldBankData("IC.REG.PROC", countryID);
            businessRegistrationProceduresTimeSeriesMap.keySet().forEach(date -> {
                if(!registrationProceduresRepository.existsByDateAndCountryID(date, countryID)) {
                    double value = businessRegistrationProceduresTimeSeriesMap.get(date);
                    System.out.println(date + ": " + value + " added!");
                    registrationProceduresRepository.save(new BusinessRegistrationProcedures(countryID, date, value));
                }
            });

            //save imports of goods and services
            System.out.println("Imports " + country.getName() + ": ");
            Map<LocalDate, Double> importsTimeSeriesMap = getWorldBankData("NE.IMP.GNFS.ZS", countryID);
            importsTimeSeriesMap.keySet().forEach(date -> {
                if (!importsRepository.existsByDateAndCountryID(date, countryID)) {
                    double value = importsTimeSeriesMap.get(date);
                    System.out.println(date + ": " + value + " added!");
                    importsRepository.save(new Imports(countryID, date, value));
                }
            });

            //save exports of goods and services
            System.out.println("Exports " + country.getName() + ": ");
            Map<LocalDate, Double> exportsTimeSeriesMap = getWorldBankData("NE.EXP.GNFS.KD.ZG", countryID);
            exportsTimeSeriesMap.keySet().forEach(date -> {
                if (!exportsRepository.existsByDateAndCountryID(date, countryID)) {
                    double value = exportsTimeSeriesMap.get(date);
                    System.out.println(date + ": " + value + " added!");
                    exportsRepository.save(new Exports(countryID, date, value));
                }
            });
        });

        File folder = new File("src/main/resources/static/files");
        for (File file : folder.listFiles())
            if (file.isFile() && file.getName().endsWith(".xml"))
                file.delete();
    }

    //fetch data from API, process and save in database
    private Map<LocalDate, Double> getWorldBankData(String indicator, int countryID) {

        //access to World Bank API
        String country = countryRepository.findById(countryID).getName();
        String url = "http://api.worldbank.org/v2/en/indicator/" + indicator + "?downloadformat=xml";
        String path = "";
        try {
            path = downloadAndUnzipFile(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Document xmlDocument = xmlParserService.xmlParser(path);

        //get time series data
        Map<LocalDate, Double> timeSeriesMap = new LinkedHashMap<>();
        NodeList records = xmlDocument.getElementsByTagName("record");
        outerloop:
        for (int i = 0; i < records.getLength(); i++) {
            Node record = records.item(i);
            if (record.getTextContent().contains(country)) {
                LocalDate date = LocalDate.now();
                double value = 0;
                NodeList fields = ((Element) record).getElementsByTagName("field");
                for (int j = 0; j < fields.getLength(); j++) {
                    Element field = (Element) fields.item(j);

                    //get value
                    if (field.getAttribute("name").equalsIgnoreCase("value")) {
                        if (field.getTextContent().isEmpty())
                            continue outerloop;
                        else
                            value = Double.valueOf(field.getTextContent());
                    }

                    //get time then convert to Date type -> yearly data
                    else if (field.getAttribute("name").equalsIgnoreCase("year"))
                        date = LocalDate.parse(field.getTextContent() + "-12-31");
                }

                //save API data in map
                timeSeriesMap.put(date, value);
            }
        }
        return timeSeriesMap;
    }

    private String downloadAndUnzipFile(URL url) throws IOException {
        ZipInputStream zipIn = new ZipInputStream(url.openStream());
        ZipEntry entry = zipIn.getNextEntry();
        String path = "src/main/resources/static/files/" + entry.getName();
        if (new File(path).exists())
            return path;

        //extract the zip file
        if (!entry.isDirectory()) {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
            byte[] bytesIn = new byte[4096];
            int read = 0;
            while ((read = zipIn.read(bytesIn)) != -1)
                bufferedOutputStream.write(bytesIn, 0, read);
            bufferedOutputStream.close();
        }
        zipIn.close();
        return path;
    }
}
