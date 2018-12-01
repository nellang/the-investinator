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
    private OECDAPIService gdpRateService;

    @Autowired
    private WorldBankAPIService worldBankAPIService;

    @GetMapping(value = {"/", "/index"})
    public void listAllCompanies() {
        //ModelAndView model = new ModelAndView("index");
        //List<ExchangeRate> exchangeRatesList = exchangeRatesDAO.listAllExchangeRates("Exchange_Rate");
        //model.addObject("exchangeRatesList", exchangeRatesList);
        gdpRateService.getOECDData("https://stats.oecd.org/restsdmx/sdmx.ashx/GetData/KEI/CPALTT01.",2,".GP.M/all?endTime=");
        worldBankAPIService.getWorldBankData("NE.IMP.GNFS.ZS",2);
        //return model;
    }
}
