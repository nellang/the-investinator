package theinvestinator.com.dataprocessing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import theinvestinator.com.dataprocessing.Service.ExchangeRateService;

@Controller
public class DataProcessingController {

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }
}
