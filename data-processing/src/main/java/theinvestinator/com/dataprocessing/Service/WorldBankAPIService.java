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
            System.out.println("Business registration procedures " + country.getName() + ":");
            getBusinessRegistrationProceduresData(countryID);

            //save imports of goods and services
            System.out.println("Imports " + country.getName() + ":");
            getImportsData(countryID);

            //save exports of goods and services
            System.out.println("Exports " + country.getName() + ":");
            getImportsData(countryID);
        });

        File folder = new File("src/main/resources/static/files/");
        for (File file : folder.listFiles())
            if (file.isFile() && file.getName().endsWith(".xml"))
                file.delete();
    }

    //fetch business registration procedures from API, process and save in database
    private void getBusinessRegistrationProceduresData(int countryID) {

        //access to World Bank API
        String url = "http://api.worldbank.org/v2/en/indicator/IC.REG.PROC?downloadformat=xml";
        NodeList records = accessWorldBankAPI(url);

        //get time series data
        String country = countryRepository.findById(countryID).getName();
        for (int i = records.getLength() - 1; i > 0; i--) {
            Node record = records.item(i);
            if (record.getTextContent().contains(country)) {
                NodeList fields = ((Element) record).getElementsByTagName("field");
                LocalDate date = getLocalDateFromXML(fields);
                double value = getDoubleValueFromXML(fields);

                //save API data in database
                if (registrationProceduresRepository.existsByDateAndCountryID(date, countryID))
                    break;
                else if (value != -1) {
                    System.out.println(date + ": " + value + " added!");
                    registrationProceduresRepository.save(new BusinessRegistrationProcedures(countryID, date, value));
                }
            }
        }
    }

    //fetch imports of goods and services from API, process and save in database
    private void getImportsData(int countryID) {

        //access to World Bank API
        String url = "http://api.worldbank.org/v2/en/indicator/NE.IMP.GNFS.ZS?downloadformat=xml";
        NodeList records = accessWorldBankAPI(url);

        //get time series data
        String country = countryRepository.findById(countryID).getName();
        for (int i = records.getLength() - 1; i > 0; i--) {
            Node record = records.item(i);
            if (record.getTextContent().contains(country)) {
                NodeList fields = ((Element) record).getElementsByTagName("field");
                LocalDate date = getLocalDateFromXML(fields);
                double value = getDoubleValueFromXML(fields);

                //save API data in database
                if (importsRepository.existsByDateAndCountryID(date, countryID))
                    break;
                else if (value != -1) {
                    System.out.println(date + ": " + value + " added!");
                    importsRepository.save(new Imports(countryID, date, value));
                }
            }
        }
    }

    //fetch exports of goods and services from API, process and save in database
    private void getExportsData(int countryID) {

        //access to World Bank API
        String url = "http://api.worldbank.org/v2/en/indicator/NE.EXP.GNFS.KD.ZG?downloadformat=xml";
        NodeList records = accessWorldBankAPI(url);

        //get time series data
        String country = countryRepository.findById(countryID).getName();
        for (int i = records.getLength() - 1; i > 0; i--) {
            Node record = records.item(i);
            if (record.getTextContent().contains(country)) {
                NodeList fields = ((Element) record).getElementsByTagName("field");
                LocalDate date = getLocalDateFromXML(fields);
                double value = getDoubleValueFromXML(fields);

                //save API data in database
                if (exportsRepository.existsByDateAndCountryID(date, countryID))
                    break;
                else if (value != -1) {
                    System.out.println(date + ": " + value + " added!");
                    exportsRepository.save(new Exports(countryID, date, value));
                }
            }
        }
    }

    //access to API and get list of time series data
    private NodeList accessWorldBankAPI(String url) {

        //download and unzip file from API
        String path = "src/main/resources/static/files/";
        try {
            ZipInputStream zipIn = new ZipInputStream(new URL(url).openStream());
            ZipEntry entry = zipIn.getNextEntry();
            path += entry.getName();
            if (!new File(path).exists()) {
                //extract the zip file
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(path));
                byte[] bytesIn = new byte[4096];
                int read = 0;
                while ((read = zipIn.read(bytesIn)) != -1)
                    bufferedOutputStream.write(bytesIn, 0, read);
                bufferedOutputStream.close();
            }
            zipIn.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //read XML
        Document xmlDocument = xmlParserService.xmlParser(path);
        return xmlDocument.getElementsByTagName("record");
    }

    private double getDoubleValueFromXML(NodeList fields) {
        for (int i = 0; i < fields.getLength(); i++) {
            Element field = (Element) fields.item(i);
            if (field.getAttribute("name").equalsIgnoreCase("value")) {
                if (!field.getTextContent().isEmpty())
                    return Double.valueOf(field.getTextContent());
            }
        }
        return -1;
    }

    private LocalDate getLocalDateFromXML(NodeList fields) {
        for (int i = 0; i < fields.getLength(); i++) {
            Element field = (Element) fields.item(i);
            if (field.getAttribute("name").equalsIgnoreCase("year"))
                return LocalDate.parse(field.getTextContent() + "-12-31");
        }
        return LocalDate.now();
    }

}
