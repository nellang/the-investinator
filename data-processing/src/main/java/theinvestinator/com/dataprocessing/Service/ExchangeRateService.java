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

    //fetch data from API, process and save them in array list
    public void getDataFromAPI(int currencyID, String apiType, String path) throws IOException, ParseException {
        String url;
        String currency = currencyRepository.findById(currencyID).getName();
        if (currency.equals("USD"))
            url = "https://www.alphavantage.co/query?function=" + apiType + "&from_symbol=" + currency + "&to_symbol=EUR";
        else
            url = "https://www.alphavantage.co/query?function=" + apiType + "&from_symbol=" + currency + "&to_symbol=USD";
        if (apiType.contains("INTRADAY"))
            url += "&interval=1min";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new URL(url + "&apikey=XWBDXMKNOIU6106B"));

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
            String date = rate.getKey();

            //convert String type from JSON data to Date type
            SimpleDateFormat dateFormat;
            if (apiType.contains("INTRADAY"))
                dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            else
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //change date time to local date time
            dateFormat.setTimeZone(timeZone);
            Date localDateTime = dateFormat.parse(date);

            if (!exchangeRateRepository.existsById(localDateTime))
                exchangeRateRepository.save(new ExchangeRate(localDateTime, currencyID, rate.getValue().get("4. close").asDouble()));
        }

    }
}