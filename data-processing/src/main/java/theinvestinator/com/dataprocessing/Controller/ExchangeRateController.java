package theinvestinator.com.dataprocessing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import theinvestinator.com.dataprocessing.Service.ExchangeRateService;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class ExchangeRateController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping(value = {"/", "/index"})
    public ModelAndView listAllCompanies() throws IOException, ParseException {
        ModelAndView model = new ModelAndView("index");
        //List<ExchangeRateModel> exchangeRatesList = exchangeRatesDAO.listAllExchangeRates("Exchange_Rate");
        //model.addObject("exchangeRatesList", exchangeRatesList);

        exchangeRateService.getDataFromAPI("https://www.alphavantage.co/query?function=FX_INTRADAY&from_symbol=TRY&to_symbol=USD&interval=1min&apikey=XWBDXMKNOIU6106B", "Time Series FX (1min)", "Exchange_Rate");
        exchangeRateService.getDataFromAPI("https://www.alphavantage.co/query?function=FX_DAILY&from_symbol=TRY&to_symbol=USD&apikey=XWBDXMKNOIU6106B", "Time Series FX (Daily)", "Exchange_Rate");
        exchangeRateService.getDataFromAPI("https://www.alphavantage.co/query?function=FX_MONTHLY&from_symbol=TRY&to_symbol=USD&apikey=XWBDXMKNOIU6106B", "Time Series FX (Monthly)", "Exchange_Rate");
        return model;
    }
}
