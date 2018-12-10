package theinvestinator.com.dataprocessing.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import theinvestinator.com.dataprocessing.Model.ExchangeRate;
import theinvestinator.com.dataprocessing.Repository.CurrencyRepository;
import theinvestinator.com.dataprocessing.Repository.ExchangeRateRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

@Service
public class ExchangeRateService {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Scheduled(cron = "0 0 0/3 1/1 * ?")
    public void saveIntradayExchangeRate() {
        currencyRepository.findAll().forEach(currency -> {
            int currencyID = currency.getCurrencyID();
            logger.info("Intraday Exchange Rate " + currency.getName() + ":");
            getExchangeRateData(currencyID, "FX_INTRADAY", "Time Series FX (1min)");
        });
        entityManager.createNativeQuery("EXEC sp_add_exchange_rate");
    }

    @Scheduled(cron = "0 15 8 1/1 * ?", zone = "GMT+8")
    public void saveDailyExchangeRate() {
        currencyRepository.findAll().forEach(currency -> {
            int currencyID = currency.getCurrencyID();
            logger.info("Daily Exchange Rate " + currency.getName() + ":");
            getExchangeRateData(currencyID, "FX_DAILY", "Time Series FX (Daily)");
        });
        entityManager.createNativeQuery("EXEC sp_add_exchange_rate");
    }

    @Scheduled(cron = "0 0 0 1 1/1 ?")
    public void saveMonthlyExchangeRate() {
        currencyRepository.findAll().forEach(currency -> {
            int currencyID = currency.getCurrencyID();
            logger.info("Monthly Exchange Rate " + currency.getName() + ":");
            getExchangeRateData(currencyID, "FX_MONTHLY", "Time Series FX (Monthly)");
        });
        entityManager.createNativeQuery("EXEC sp_add_exchange_rate");
    }

    //fetch data from API, process and store in database
    private void getExchangeRateData(int currencyID, String apiType, String path) {
        String currency = currencyRepository.findById(currencyID).getName();
        String url = "https://www.alphavantage.co/query?function=" + apiType + "&from_symbol=" + currency;
        url += currency.equals("USD") ? "&to_symbol=EUR" : "&to_symbol=USD";
        if (apiType.contains("INTRADAY"))
            url += "&interval=1min";
        JsonNode rootNode = accessExchangeRateAPI(url + "&apikey=XWBDXMKNOIU6106B", path);

        //get time zone of API data
        TimeZone timeZone = TimeZone.getDefault();
        Iterator<Map.Entry<String, JsonNode>> elements = rootNode.path("Meta Data").fields();
        while (elements.hasNext()) {
            Map.Entry<String, JsonNode> metadata = elements.next();
            if (metadata.getKey().contains("Time Zone")) {
                timeZone = TimeZone.getTimeZone(metadata.getValue().asText());
                break;
            }
        }

        //save API data in database
        elements = rootNode.path(path).fields();
        outerLoop:
        while (elements.hasNext()) {
            Map.Entry<String, JsonNode> rate = elements.next();

            if (apiType.contains("MONTHLY")) {
                int currentYear = LocalDate.now().getYear();
                for (int i = 0; i < 5; i++) {
                    int month = LocalDate.now().getMonth().minus(i).getValue();
                    if (rate.getKey().substring(0, 7).equals(currentYear + (month < 10 ? "-0" : "-") + month)) {
                        continue outerLoop;
                    }
                }
            }

            Date date = convertStringToDate(rate.getKey(), timeZone);
            double value = rate.getValue().get("4. close").asDouble();

            //save in database
            if (exchangeRateRepository.existsByDateAndCurrencyID(date, currencyID)) {
                break;
            } else {
                logger.info(date + ": 1 " + currency + " = " + value + (currency.equals("USD") ? " EUR added!" : " USD added!"));
                exchangeRateRepository.save(new ExchangeRate(date, currencyID, value));
            }
        }
    }

    //access to exchange rate API from alphavantage in JSON format
    private JsonNode accessExchangeRateAPI(String url, String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;

        try {
            rootNode = objectMapper.readTree(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (rootNode == null)
            return accessExchangeRateAPI(url, path);
        else if (rootNode.path(path).isNull())
            return accessExchangeRateAPI(url, path);
        else return rootNode;
    }

    //convert time value from String type to Date type
    private Date convertStringToDate(String time, TimeZone timeZone) {
        SimpleDateFormat dateFormat = time.endsWith(":00") ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") : new SimpleDateFormat("yyyy-MM-dd");

        //change date time to local date time
        dateFormat.setTimeZone(timeZone);
        Date date = new Date();
        try {
            date = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}