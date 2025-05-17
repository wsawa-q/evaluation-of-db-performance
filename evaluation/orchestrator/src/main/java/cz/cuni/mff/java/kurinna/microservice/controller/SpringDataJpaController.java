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
        return springDataJpaService.getPricingSummary();
    }

    @GetMapping(value = "/q2", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMinimumCostSupplier() {
        return springDataJpaService.getMinimumCostSupplier();
    }

    @GetMapping(value = "/q3", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getShippingPriority() {
        return springDataJpaService.getShippingPriority();
    }

    @GetMapping(value = "/q4", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getOrderPriorityChecking() {
        return springDataJpaService.getOrderPriorityChecking();
    }

    @GetMapping(value = "/q5", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLocalSupplierVolume() {
        return springDataJpaService.getLocalSupplierVolume();
    }
}
