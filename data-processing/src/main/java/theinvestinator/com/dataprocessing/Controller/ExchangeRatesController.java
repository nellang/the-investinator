package theinvestinator.com.dataprocessing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import theinvestinator.com.dataprocessing.Model.ExchangeRatesModel;
import theinvestinator.com.dataprocessing.Repository.ExchangeRatesDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

@Controller
public class ExchangeRatesController {

    @Autowired
    private ExchangeRatesDAO exchangeRatesDAO;

    @GetMapping(value = {"/", "/index"})
    public ModelAndView listAllCompanies() throws IOException, ParseException {
        ModelAndView model = new ModelAndView("index");
        List<ExchangeRatesModel> exchangeRatesList = exchangeRatesDAO.listAllExchangeRates("Exchange_Rates");
        model.addObject("exchangeRatesList", exchangeRatesList);

        exchangeRatesDAO.getDataFromAPI("https://www.alphavantage.co/query?function=FX_INTRADAY&from_symbol=TRY&to_symbol=USD&interval=1min&apikey=XWBDXMKNOIU6106B", "Time Series FX (1min)", "Exchange_Rates");
        exchangeRatesDAO.getDataFromAPI("https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=TRY&to_symbol=USD&apikey=XWBDXMKNOIU6106B", "Time Series FX (Daily)", "Exchange_Rates");
        exchangeRatesDAO.getDataFromAPI("https://www.alphavantage.co/query?function=FX_MONTHLY&from_symbol=TRY&to_symbol=USD&apikey=XWBDXMKNOIU6106B", "Time Series FX (Monthly)", "Exchange_Rates");

        return model;
    }
}
