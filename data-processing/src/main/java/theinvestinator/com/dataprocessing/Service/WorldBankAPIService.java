package theinvestinator.com.dataprocessing.Service;

import org.jembi.sdmxhd.SDMXHDMessage;
import org.jembi.sdmxhd.parser.SDMXHDParser;
import org.jembi.sdmxhd.parser.exceptions.ExternalRefrenceNotFoundException;
import org.jembi.sdmxhd.parser.exceptions.SchemaValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import theinvestinator.com.dataprocessing.Repository.CountryRepository;
import theinvestinator.com.dataprocessing.Repository.RealGDPRateRepository;

import javax.xml.bind.ValidationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipFile;

@Service
public class WorldBankAPIService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RealGDPRateRepository gdpRateRepository;

    //fetch data from API, process and save them in database
    public void getWorldBankData(String indicator, int countryID) {
        //access to WorldBank API in XML format
        String country = countryRepository.findById(countryID).getName();
        String url = "http://api.worldbank.org/v2/en/indicator/" + indicator + "?downloadformat=xml";
        SDMXHDParser parser = new SDMXHDParser();
        SDMXHDMessage msg = new SDMXHDMessage();

        try {
            URLConnection conn = new URL(url).openConnection();
            InputStream stream = conn.getInputStream();
            Files.copy(stream, Paths.get("test.zip"));
            stream.close();
            msg = parser.parse(new ZipFile("test.zip"));
        } catch (IOException | XMLStreamException | ExternalRefrenceNotFoundException | SchemaValidationException | ValidationException e) {
            e.printStackTrace();
        }

        System.out.println(msg.getCds().getDatasets().size());

        /*DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        Document doc = null;
        try {
            doc = factory.newDocumentBuilder().parse(new URL(url).openStream());
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        if (doc == null) {
            System.out.println("XML not found!");
            return;
        }
        doc.getDocumentElement().normalize();

        //get time series data
        NodeList timeSeries = doc.getElementsByTagName("record");
        for (int i = 0; i < timeSeries.getLength(); i++) {
            Node record = timeSeries.item(i);
            System.out.println(record.getFirstChild().getTextContent());

            //get time period and convert to Date type
            String timePeriod = observation.getElementsByTagName("Time").item(0).getTextContent();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            try {
                //quarterly data
                if (timePeriod.contains("Q")) {
                    if (timePeriod.contains("Q1"))
                        date = dateFormat.parse(timePeriod.substring(0, 4) + "-03-31");
                    if (timePeriod.contains("Q2"))
                        date = dateFormat.parse(timePeriod.substring(0, 4) + "-06-30");
                    if (timePeriod.contains("Q3"))
                        date = dateFormat.parse(timePeriod.substring(0, 4) + "-09-30");
                    if (timePeriod.contains("Q4"))
                        date = dateFormat.parse(timePeriod.substring(0, 4) + "-12-31");
                }
                //monthly data
                else if (timePeriod.lastIndexOf('-') == 4)
                    date = dateFormat.parse(timePeriod + "-01");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(date);
            //save API data in database
            double value = Double.valueOf(((Element) observation.getElementsByTagName("ObsValue").item(0)).getAttribute("value"));
            System.out.println(value);*/

            /*if (gdpRateRepository.findByDate(localDateTime) == 0) {
            System.out.println(localDateTime + ": GDP rate " + country + " = " + value + " added!");
                gdpRateRepository.save(new RealGDPRate(countryID, localDateTime, value));
                }
        }*/
    }
}
