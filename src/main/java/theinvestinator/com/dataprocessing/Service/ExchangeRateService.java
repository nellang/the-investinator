package theinvestinator.com.dataprocessing.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import theinvestinator.com.dataprocessing.Model.ExchangeRate;
import theinvestinator.com.dataprocessing.Repository.CurrencyRepository;
import theinvestinator.com.dataprocessing.Repository.ExchangeRateRepository;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;

@Service
public class ExchangeRateService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    public void saveExchangeRate() {
        currencyRepository.findAll().forEach(currency -> {
            int currencyID = currency.getCurrencyID();
            getExchangeRateData(currencyID, "FX_INTRADAY", "Time Series FX (1min)");
            getExchangeRateData(currencyID, "FX_DAILY", "Time Series FX (Daily)");
            getExchangeRateData(currencyID, "FX_MONTHLY", "Time Series FX (Monthly)");
        });
    }

    //fetch data from API, process and store in database
    private void getExchangeRateData(int currencyID, String apiType, String path) {

        //access to exchange rate API from alphavantage in JSON format
        String currency = currencyRepository.findById(currencyID).getName();
        String url = "https://www.alphavantage.co/query?function=" + apiType + "&from_symbol=" + currency ;
        if (currency.equals("USD"))
            url += "&to_symbol=EUR";
        else
            url += "&to_symbol=USD";
        if (apiType.contains("INTRADAY"))
            url += "&interval=1min";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;

        do {
            try {
                rootNode = objectMapper.readTree(new URL(url + "&apikey=XWBDXMKNOIU6106B"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (rootNode == null);

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
        while (elements.hasNext()) {
            Map.Entry<String, JsonNode> rate = elements.next();
            Date date = convertStringToDate(rate.getKey(), timeZone);
            double value = rate.getValue().get("4. close").asDouble();

            //save in database
            if (exchangeRateRepository.existsByDateAndCurrencyID(date, currencyID))
                break;
            else {
                System.out.println(date + ": 1 " + currency + " = " + value + (currency.equals("USD") ? " EUR added!" : " USD added!"));
                exchangeRateRepository.save(new ExchangeRate(date, currencyID, value));
            }
        }
    }

    //convert time value from String type to Date type
    private Date convertStringToDate(String time, TimeZone timeZone) {
        SimpleDateFormat dateFormat;
        if (time.endsWith("00"))
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        else
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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