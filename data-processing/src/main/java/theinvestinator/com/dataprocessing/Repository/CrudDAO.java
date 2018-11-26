package theinvestinator.com.dataprocessing.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import theinvestinator.com.dataprocessing.Mapper.CurrencyMapper;
import theinvestinator.com.dataprocessing.Mapper.ExchangeRateMapper;
import theinvestinator.com.dataprocessing.Model.ExchangeRateModel;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class CrudDAO {

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    // Connect to database
    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Object add(String tableName, Object t) {
        String sql = "INSERT INTO Exchange_Rate VALUES (?, ?)";
        try {
            if (!exist(tableName, "LocalDateTime", exchangeRate))
                jdbcTemplate.update(sql, exchangeRate);
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
        }
    }

    public List findWithNamedQuery(String tableName, String namedQuery, Map parameters) {
        String sql = "SELECT * FROM " + tableName + " WHERE " + param + " = ?";
    }

    public boolean exist(String tableName, String param, Object object) {
        String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + param + " = ?";
        Integer countRate = jdbcTemplate.queryForObject(sql, new Object[]{object.}, Integer.class);
        return (countRate != null);
    }

    public List<ExchangeRateModel> listAll() {
        String sql = "SELECT * FROM Exchange_Rate";
        return jdbcTemplate.query(sql, new ExchangeRateMapper());
    }

    public String findCurrency(int currencyID) {
        String sql = "SELECT * FROM Currency WHERE currency_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{currencyID}, new CurrencyMapper()).getName();
    }

}
