package theinvestinator.com.dataprocessing.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import theinvestinator.com.dataprocessing.Service.OECDAPIService;

@Controller
public class DataProcessingController {

    @Autowired
    OECDAPIService service;

    @RequestMapping(value = "/")
    public String index() {
        service.saveOECDMonthlyData();
        service.saveOECDQuarterlyData();
        return "index";
    }
}
