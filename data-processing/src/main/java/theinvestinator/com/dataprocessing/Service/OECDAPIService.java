package theinvestinator.com.dataprocessing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import theinvestinator.com.dataprocessing.Model.*;
import theinvestinator.com.dataprocessing.Repository.*;

import java.time.LocalDate;

@Service
public class OECDAPIService {
    @Autowired
    private XMLParserService xmlParserService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private RealGDPRateRepository gdpRateRepository;

    @Autowired
    private RealGDPRepository gdpRepository;

    @Autowired
    private InflationRateRepository inflationRateRepository;

    @Autowired
    private UnemploymentRateRepository unemploymentRateRepository;

    @Autowired
    private LaborCostIndexRepository laborCostIndexRepository;

    //store data in database
    public void saveOECDData() {
        int currentYear = LocalDate.now().getYear();
        countryRepository.findAll().forEach(country -> {
            String countryAbbreviation = country.getAbbr().trim();
            int countryID = country.getCountryID();
            String url = "";

            //save GDP Rate
            System.out.println("GDP rate " + country.getName() + ":");
            url = "https://stats.oecd.org/restsdmx/sdmx.ashx/GetData/QNA/" + countryAbbreviation + ".B1_GE.GPSA.Q/all?endTime=" + currentYear;
            getRealGDPRateData(countryID, url);

            //save GDP
            System.out.println("GDP ppp " + country.getName() + ":");
            url = "https://stats.oecd.org/restsdmx/sdmx.ashx/GetData/QNA/" + countryAbbreviation + ".B1_GE.CPCARSA.Q/all?endTime=" + currentYear;
            getRealGDPData(countryID, url);

            //save inflation rate
            System.out.println("Inflation rate " + country.getName() + ":");
            url = "https://stats.oecd.org/restsdmx/sdmx.ashx/GetData/KEI/CPALTT01." + countryAbbreviation + ".GP.M/all?endTime=" + currentYear;
            getInflationRateData(countryID, url);

            //save unemployment rate
            System.out.println("Unemployment rate " + country.getName() + ": ");
            url = "https://stats.oecd.org/restsdmx/sdmx.ashx/GetData/MEI_ARCHIVE/" + countryAbbreviation + ".501.201811.M/all?endTime=" + currentYear;
            getUnemploymentRateData(countryID, url);

            //save labor cost index
            System.out.println("Labor cost index " + country.getName() + ": ");
            url = "https://stats.oecd.org/restsdmx/sdmx.ashx/GetData/ULC_EEQ/" + countryAbbreviation + ".ULQEUL01.IXOBSA.Q/all?endTime=" + currentYear;
            getLaborCostIndexData(countryID, url);
        });
    }

    //fetch real GDP rate from API, process and store in database
    private void getRealGDPRateData(int countryID, String url) {

        //access to OCED API
        Document xmlDocument = xmlParserService.xmlParser(url);

        //get time series data
        NodeList observations = xmlDocument.getElementsByTagName("Obs");
        for (int i = observations.getLength() - 1; i > 0; i--) {
            Element observation = (Element) observations.item(i);

            //save API data in database
            @SuppressWarnings("DuplicatesGDPRate")
            LocalDate date = convertStringToLocalDate(observation.getElementsByTagName("Time").item(0).getTextContent());
            double value = Double.valueOf(((Element) observation.getElementsByTagName("ObsValue").item(0)).getAttribute("value"));
            if (gdpRateRepository.existsByDateAndCountryID(date, countryID))
                break;
            else {
                System.out.println(date + ": " + value + " added!");
                gdpRateRepository.save(new RealGDPRate(countryID, date, value));
            }
        }
    }

    //fetch real GDP ppp from API, process and store in database
    private void getRealGDPData(int countryID, String url) {

        //access to OCED API
        Document xmlDocument = xmlParserService.xmlParser(url);

        //get time series data
        NodeList observations = xmlDocument.getElementsByTagName("Obs");
        for (int i = observations.getLength() - 1; i > 0; i--) {
            Element observation = (Element) observations.item(i);

            //save API data in database
            @SuppressWarnings(value = "DuplicatesGDP")
            LocalDate date = convertStringToLocalDate(observation.getElementsByTagName("Time").item(0).getTextContent());
            double value = Double.valueOf(((Element) observation.getElementsByTagName("ObsValue").item(0)).getAttribute("value"));
            if (gdpRepository.existsByDateAndCountryID(date, countryID))
                break;
            else {
                System.out.println(date + ": " + value + " added!");
                gdpRepository.save(new RealGDP(countryID, date, value));
            }
        }
    }

    //fetch inflation rate from API, process and store in database
    private void getInflationRateData(int countryID, String url) {

        //access to OCED API
        Document xmlDocument = xmlParserService.xmlParser(url);

        //get time series data
        NodeList observations = xmlDocument.getElementsByTagName("Obs");
        for (int i = observations.getLength() - 1; i > 0; i--) {
            Element observation = (Element) observations.item(i);

            //save API data in database
            @SuppressWarnings("DuplicatesInflationRate")
            LocalDate date = convertStringToLocalDate(observation.getElementsByTagName("Time").item(0).getTextContent());
            double value = Double.valueOf(((Element) observation.getElementsByTagName("ObsValue").item(0)).getAttribute("value"));
            if (inflationRateRepository.existsByDateAndCountryID(date, countryID))
                break;
            else {
                System.out.println(date + ": " + value + " added!");
                inflationRateRepository.save(new InflationRate(countryID, date, value));
            }
        }
    }

    //fetch unemployment rate from API, process and store in database
    private void getUnemploymentRateData(int countryID, String url) {

        //access to OCED API
        Document xmlDocument = xmlParserService.xmlParser(url);

        //get time series data
        NodeList observations = xmlDocument.getElementsByTagName("Obs");
        for (int i = observations.getLength() - 1; i > 0; i--) {
            Element observation = (Element) observations.item(i);

            //save API data in database
            @SuppressWarnings(value = "DuplicatesUnemploymentRate")
            LocalDate date = convertStringToLocalDate(observation.getElementsByTagName("Time").item(0).getTextContent());
            double value = Double.valueOf(((Element) observation.getElementsByTagName("ObsValue").item(0)).getAttribute("value"));
            if (unemploymentRateRepository.existsByDateAndCountryID(date, countryID))
                break;
            else {
                System.out.println(date + ": " + value + " added!");
                unemploymentRateRepository.save(new UnemploymentRate(countryID, date, value));
            }
        }
    }

    //fetch labor cost index from API, process and store in database
    private void getLaborCostIndexData(int countryID, String url) {

        //access to OCED API
        Document xmlDocument = xmlParserService.xmlParser(url);

        //get time series data
        NodeList observations = xmlDocument.getElementsByTagName("Obs");
        for (int i = observations.getLength() - 1; i > 0; i--) {
            Element observation = (Element) observations.item(i);

            //save API data in database
            @SuppressWarnings(value = "DuplicatesLaborCostIndex")
            LocalDate date = convertStringToLocalDate(observation.getElementsByTagName("Time").item(0).getTextContent());
            double value = Double.valueOf(((Element) observation.getElementsByTagName("ObsValue").item(0)).getAttribute("value"));
            if (laborCostIndexRepository.existsByDateAndCountryID(date, countryID))
                break;
            else {
                System.out.println(date + ": " + value + " added!");
                laborCostIndexRepository.save(new LaborCostIndex(countryID, date, value));
            }
        }
    }

    //convert time value in String type to LocalDate type
    private LocalDate convertStringToLocalDate(String time) {
        LocalDate date = LocalDate.now();

        //quarterly data
        if (time.contains("Q")) {
            if (time.contains("Q1"))
                date = LocalDate.parse(time.substring(0, 4) + "-03-31");
            if (time.contains("Q2"))
                date = LocalDate.parse(time.substring(0, 4) + "-06-30");
            if (time.contains("Q3"))
                date = LocalDate.parse(time.substring(0, 4) + "-09-30");
            if (time.contains("Q4"))
                date = LocalDate.parse(time.substring(0, 4) + "-12-31");
        }

        //monthly data
        else if (time.lastIndexOf('-') == 4)
            date = LocalDate.parse(time + "-01");

        return date;
    }
}
