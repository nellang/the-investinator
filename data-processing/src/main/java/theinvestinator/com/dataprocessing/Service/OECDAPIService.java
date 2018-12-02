package theinvestinator.com.dataprocessing.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import theinvestinator.com.dataprocessing.Model.*;
import theinvestinator.com.dataprocessing.Repository.*;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

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
        countryRepository.findAll().forEach(country -> {
            int countryID = country.getCountryID();

            //save GDP Rate
            System.out.println("GDP rate " + country.getName() + ": ");
            Map<LocalDate, Double> gdpRateTimeSeriesMap = getOECDData("https://stats.oecd.org/restsdmx/sdmx.ashx/GetData/QNA/", countryID, ".B1_GE.GPSA.Q/all?endTime=");
            gdpRateTimeSeriesMap.keySet().forEach(date -> {
                if (!gdpRateRepository.existsByDateAndCountryID(date, countryID)) {
                    double value = gdpRateTimeSeriesMap.get(date);
                    System.out.println(date + ": " + value + " added!");
                    gdpRateRepository.save(new RealGDPRate(countryID, date, value));
                }
            });

            //save GDP
            System.out.println("GDP ppp " + country.getName() + ": ");
            Map<LocalDate, Double> gdpTimeSeriesMap = getOECDData("https://stats.oecd.org/restsdmx/sdmx.ashx/GetData/QNA/", countryID, ".B1_GE.CPCARSA.Q/all?endTime=");
            gdpTimeSeriesMap.keySet().forEach(date -> {
                if (!gdpRepository.existsByDateAndCountryID(date, countryID)) {
                    double value = gdpTimeSeriesMap.get(date);
                    System.out.println(date + ": " + value + " added!");
                    gdpRepository.save(new RealGDP(countryID, date, value));
                }
            });

            //save inflation rate
            System.out.println("Inflation rate " + country.getName() + ": ");
            Map<LocalDate, Double> inflationRateTimeSeriesMap = getOECDData("https://stats.oecd.org/restsdmx/sdmx.ashx/GetData/KEI/CPALTT01.", countryID, ".GP.M/all?endTime=");
            inflationRateTimeSeriesMap.keySet().forEach(date -> {
                if (!inflationRateRepository.existsByDateAndCountryID(date, countryID)) {
                    double value = inflationRateTimeSeriesMap.get(date);
                    System.out.println(date + ": " + value + " added!");
                    inflationRateRepository.save(new InflationRate(countryID, date, value));
                }
            });

            //save unemployment rate
            System.out.println("Unemployment rate " + country.getName() + ": ");
            Map<LocalDate, Double> unemploymentRateTimeSeriesMap = getOECDData("https://stats.oecd.org/restsdmx/sdmx.ashx/GetData/MEI_ARCHIVE/", countryID, ".501.201811.M/all?endTime=");
            unemploymentRateTimeSeriesMap.keySet().forEach(date -> {
                if (!unemploymentRateRepository.existsByDateAndCountryID(date, countryID)) {
                    double value = unemploymentRateTimeSeriesMap.get(date);
                    System.out.println(date + ": " + value + " added!");
                    unemploymentRateRepository.save(new UnemploymentRate(countryID, date, value));
                }
            });

            //save labor cost index
            System.out.println("Labor cost index " + country.getName() + ": ");
            Map<LocalDate, Double> laborCostIndexTimeSeriesMap = getOECDData("https://stats.oecd.org/restsdmx/sdmx.ashx/GetData/ULC_EEQ/", countryID, ".ULQEUL01.IXOBSA.Q/all?endTime=");
            laborCostIndexTimeSeriesMap.keySet().forEach(date -> {
                if (!laborCostIndexRepository.existsByDateAndCountryID(date, countryID)) {
                    double value = laborCostIndexTimeSeriesMap.get(date);
                    System.out.println(date + ": " + value + " added!");
                    laborCostIndexRepository.save(new LaborCostIndex(countryID, date, value));
                }
            });
        });
    }

    //fetch data from API and process
    private Map<LocalDate, Double> getOECDData(String part1, int countryID, String part2) {

        //access to OCED API
        LocalDate date = LocalDate.now();
        int currentYear = date.getYear();
        String country = countryRepository.findById(countryID).getAbbr().trim();
        Document xmlDocument = xmlParserService.xmlParser(part1 + country + part2 + currentYear);

        //get time series data
        Map<LocalDate, Double> timeSeriesMap = new LinkedHashMap<>();
        NodeList observations = xmlDocument.getElementsByTagName("Obs");
        for (int i = 0; i < observations.getLength(); i++) {
            Element observation = (Element) observations.item(i);

            //get time value and convert to Date type
            String time = observation.getElementsByTagName("Time").item(0).getTextContent();

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

            //save API data in map
            double value = Double.valueOf(((Element) observation.getElementsByTagName("ObsValue").item(0)).getAttribute("value"));
            timeSeriesMap.put(date, value);
        }

        return timeSeriesMap;
    }
}
