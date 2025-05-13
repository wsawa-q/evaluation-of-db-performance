package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.MyBatisService;
import cz.cuni.mff.java.kurinna.microservice.service.SpringDataJpaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorController {
    private final MyBatisService myBatisService;
    private final SpringDataJpaService springDataJpaService;

    public OrchestratorController(MyBatisService myBatisService, SpringDataJpaService springDataJpaService) {
        this.myBatisService = myBatisService;
        this.springDataJpaService = springDataJpaService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }

    @GetMapping(value = "/q1", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPricingSummary() {
        int randomNumber = (int) (Math.random() * 60) + 60;
        String myBatisResponse = myBatisService.getPricingSummary(randomNumber);
        String springDataJpaResponse = springDataJpaService.getPricingSummary(randomNumber);

        return String.format("{\"myBatisResponse\": %s, \"springDataJpaResponse\": %s}", myBatisResponse, springDataJpaResponse);
    }
}

