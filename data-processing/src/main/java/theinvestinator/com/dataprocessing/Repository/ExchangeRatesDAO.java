package theinvestinator.com.dataprocessing.Repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Mapper.ExchangeRatesMapper;
import theinvestinator.com.dataprocessing.Model.ExchangeRatesModel;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class ExchangeRatesDAO {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    // Connect to database
    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //fetch data from API, process and save them in array list
    public void getDataFromAPI(String api, String path, String tableName) throws IOException, ParseException {
        URL url = new URL(api);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(url);

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
            if (date.contains(":"))
                dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            else
                dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //change date time to local date time
            dateFormat.setTimeZone(timeZone);
            Date localDateTime = dateFormat.parse(date);

            addExchangeRate(tableName, new ExchangeRatesModel(localDateTime, rate.getValue().get("4. close").asDouble()));
        }

    }

    public List<ExchangeRatesModel> listAllExchangeRates(String tableName) {
        String sql = "SELECT * FROM " + tableName;
        return jdbcTemplate.query(sql, new ExchangeRatesMapper());
    }

    private void addExchangeRate(String tableName, ExchangeRatesModel exchangeRate) {
        String sql = "INSERT INTO " + tableName + " VALUES (?, ?)";
        try {
            if (!checkExistExchangeRate(tableName, "LocalDateTime", exchangeRate))
                jdbcTemplate.update(sql, exchangeRate);
            else
                System.out.println("Date time existed");
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        }
    }

    private boolean checkExistExchangeRate(String tableName, String param, ExchangeRatesModel exchangeRate) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + param + " = ?";
        Integer countRate = jdbcTemplate.queryForObject(sql, new Object[]{exchangeRate.getLocalDateTime()}, Integer.class);
        return (countRate != null);
    }

}