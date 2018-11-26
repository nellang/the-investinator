package theinvestinator.com.dataprocessing.Mapper;

import org.springframework.jdbc.core.RowMapper;
import theinvestinator.com.dataprocessing.Model.ExchangeRateModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ExchangeRateMapper implements RowMapper<ExchangeRateModel> {
    @Override
    public ExchangeRateModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        ExchangeRateModel exchangeRateModel = new ExchangeRateModel();
        exchangeRateModel.setLocalDateTime(rs.getDate("exchange_rate_id"));
        exchangeRateModel.setCurrencyID(rs.getInt("currency_id"));
        exchangeRateModel.setValue(rs.getDouble("value"));
        return exchangeRateModel;
    }
}
