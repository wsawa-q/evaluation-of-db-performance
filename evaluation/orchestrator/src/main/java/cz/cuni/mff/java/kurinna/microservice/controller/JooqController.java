package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.JooqService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jooq")
public class JooqController {
    private final JooqService jooqService;

    public JooqController(JooqService jooqService) {
        this.jooqService = jooqService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return jooqService.healthCheck();
    }

    @GetMapping(value = "/q1", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPricingSummary() {
        return jooqService.getPricingSummary();
    }

    @GetMapping(value = "/q2", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMinimumCostSupplier() {
        return jooqService.getMinimumCostSupplier();
    }

    @GetMapping(value = "/q3", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getShippingPriority() {
        return jooqService.getShippingPriority();
    }

    @GetMapping(value = "/q4", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getOrderPriorityChecking() {
        return jooqService.getOrderPriorityChecking();
    }

    @GetMapping(value = "/q5", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLocalSupplierVolume() {
        return jooqService.getLocalSupplierVolume();
    }
}

