package theinvestinator.com.dataprocessing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import theinvestinator.com.dataprocessing.Service.ExchangeRateService;
import theinvestinator.com.dataprocessing.Service.OECDAPIService;
import theinvestinator.com.dataprocessing.Service.WorldBankAPIService;

@Controller
public class DataProcessingController {

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private OECDAPIService oecdAPIService;

    @Autowired
    private WorldBankAPIService worldBankAPIService;

    @GetMapping(value = {"/", "/index"})
    public void getAll() {
        //ModelAndView model = new ModelAndView("index");
        //List<ExchangeRate> exchangeRatesList = exchangeRatesDAO.listAllExchangeRates("Exchange_Rate");
        //model.addObject("exchangeRatesList", exchangeRatesList);
        //exchangeRateService.saveExchangeRate();
        oecdAPIService.saveOECDData();
        worldBankAPIService.saveWorldBankData();

        //return model;
    }
}
