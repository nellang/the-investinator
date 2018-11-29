package theinvestinator.com.dataprocessing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import theinvestinator.com.dataprocessing.Service.ExchangeRateService;

import java.io.IOException;
import java.text.ParseException;

@Controller
public class DataProcessingController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @GetMapping(value = {"/", "/index"})
    public ModelAndView listAllCompanies() throws IOException, ParseException {
        ModelAndView model = new ModelAndView("index");
        //List<ExchangeRate> exchangeRatesList = exchangeRatesDAO.listAllExchangeRates("Exchange_Rate");
        //model.addObject("exchangeRatesList", exchangeRatesList);

        exchangeRateService.getDataFromAPI(1, "FX_INTRADAY", "Time Series FX (1min)");
        exchangeRateService.getDataFromAPI(1, "FX_DAILY", "Time Series FX (Daily)");
        exchangeRateService.getDataFromAPI(1, "FX_MONTHLY", "Time Series FX (Monthly)");
        return model;
    }
}
