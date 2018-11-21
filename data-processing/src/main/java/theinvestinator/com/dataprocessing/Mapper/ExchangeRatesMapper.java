package theinvestinator.com.dataprocessing.Mapper;

import org.springframework.jdbc.core.RowMapper;
import theinvestinator.com.dataprocessing.Model.ExchangeRatesModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRatesMapper implements RowMapper<ExchangeRatesModel> {
    @Override
    public ExchangeRatesModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        ExchangeRatesModel exchangeRatesModel = new ExchangeRatesModel();
        exchangeRatesModel.setLocalDateTime(rs.getDate("LocalDateTime"));
        exchangeRatesModel.setTRYValue(rs.getDouble("TRY"));
        return exchangeRatesModel;
    }
}
