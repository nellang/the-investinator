package theinvestinator.com.dataprocessing.Mapper;

import org.springframework.jdbc.core.RowMapper;
import theinvestinator.com.dataprocessing.Model.CountryModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryMapper implements RowMapper<CountryModel> {
    @Override
    public CountryModel mapRow(ResultSet rs, int rowNum) throws SQLException {
        CountryModel countryModel = new CountryModel();
        countryModel.setCountryID(rs.getInt("country_id"));
        countryModel.setCurrencyID(rs.getInt("currency_id"));
        countryModel.setName(rs.getString("name"));
        return countryModel;
    }
}
