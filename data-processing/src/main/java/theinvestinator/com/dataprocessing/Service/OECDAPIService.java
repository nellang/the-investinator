package theinvestinator.com.dataprocessing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import theinvestinator.com.dataprocessing.Repository.CountryRepository;
import theinvestinator.com.dataprocessing.Repository.RealGDPRateRepository;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;

@Service
public class OECDAPIService {

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RealGDPRateRepository gdpRateRepository;

    //fetch data from API, process and save them in database
    public void getOECDData(String part1, int countryID, String part2) {
        //access to OECD API in XML format
        int currentYear = Year.now().getValue();
        String country = countryRepository.findById(countryID).getAbbr().trim();
        String url = part1 + country + part2 + currentYear;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
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
        NodeList timeSeries = doc.getElementsByTagName("Obs");
        for (int i = 0; i < timeSeries.getLength(); i++) {
            Element observation = (Element) timeSeries.item(i);

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
            System.out.println(value);

            /*if (gdpRateRepository.findByDate(localDateTime) == 0) {
            System.out.println(localDateTime + ": GDP rate " + country + " = " + value + " added!");
                gdpRateRepository.save(new RealGDPRate(countryID, localDateTime, value));
                }*/
        }
    }
}
