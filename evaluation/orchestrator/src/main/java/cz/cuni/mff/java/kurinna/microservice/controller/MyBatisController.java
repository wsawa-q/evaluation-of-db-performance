package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.MyBatisService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mybatis")
public class MyBatisController {
    private final MyBatisService myBatisService;

    public MyBatisController(MyBatisService myBatisService) {
        this.myBatisService = myBatisService;
    }

    // health check
    @GetMapping("/health")
    public String healthCheck() {
        return myBatisService.healthCheck();
    }

    // get pricing summary
    @GetMapping(value = "/q1", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPricingSummary() {
        int randomNumber = (int) (Math.random() * 60) + 60;
        return myBatisService.getPricingSummary(randomNumber);
    }
}
