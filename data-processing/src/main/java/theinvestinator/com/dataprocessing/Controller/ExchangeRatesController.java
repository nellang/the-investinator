package theinvestinator.com.dataprocessing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import theinvestinator.com.dataprocessing.Model.ExchangeRatesModel;
import theinvestinator.com.dataprocessing.Repository.ExchangeRatesDAO;

import java.sql.SQLException;
import java.util.List;

@Controller
public class ExchangeRatesController {

    /*@Autowired
    private ExchangeRatesDAO exchangeRatesDAO;

    @GetMapping(value = {"/", "/index"})
    public ModelAndView listAllCompanies() throws SQLException {
        ModelAndView model = new ModelAndView("index");
        List<ExchangeRatesModel> exchangeRatesList = exchangeRatesDAO.listAllExchangeRates("Exchange_Rates");
        model.addObject("exchangeRatesList", exchangeRatesList);
        return model;
    }*/
}
