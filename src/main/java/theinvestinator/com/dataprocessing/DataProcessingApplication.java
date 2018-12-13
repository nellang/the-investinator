package theinvestinator.com.dataprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
public class DataProcessingApplication {

    // Set Spring Boot time zone to Istanbul
    @PostConstruct
    public void init(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Istanbul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DataProcessingApplication.class, args);
    }
}
