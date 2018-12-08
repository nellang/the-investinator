package theinvestinator.com.dataprocessing.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DataProcessingController {

    @GetMapping(value = {"/", "/index"})
    public ModelAndView getAll() {
        ModelAndView model = new ModelAndView("index");
        //List<ExchangeRate> exchangeRatesList = exchangeRatesDAO.listAllExchangeRates("Exchange_Rate");
        //model.addObject("exchangeRatesList", exchangeRatesList);
        return model;
    }
}
