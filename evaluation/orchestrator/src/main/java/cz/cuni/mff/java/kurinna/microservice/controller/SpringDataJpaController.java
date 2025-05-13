package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.SpringDataJpaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/springdatajpa")
public class SpringDataJpaController {
    private final SpringDataJpaService springDataJpaService;

    public SpringDataJpaController(SpringDataJpaService springDataJpaService) {
        this.springDataJpaService = springDataJpaService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return springDataJpaService.healthCheck();
    }

    @GetMapping(value = "/q1", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPricingSummary() {
        int randomNumber = (int) (Math.random() * 60) + 60;
        return springDataJpaService.getPricingSummary(randomNumber);
    }
}
