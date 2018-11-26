package theinvestinator.com.dataprocessing.Mapper;

import org.springframework.jdbc.core.RowMapper;
import theinvestinator.com.dataprocessing.Model.CurrencyModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyMapper implements RowMapper<CurrencyModel> {
    @Override
    public CurrencyModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        CurrencyModel currencyModel = new CurrencyModel();
        currencyModel.setCurrencyID(rs.getInt("currency_id"));
        currencyModel.setName(rs.getString("name"));
        return currencyModel;
    }
}
