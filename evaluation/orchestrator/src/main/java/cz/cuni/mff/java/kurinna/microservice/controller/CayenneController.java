package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.CayenneService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cayenne")
public class CayenneController {
    private final CayenneService cayenneService;

    public CayenneController(CayenneService cayenneService) {
        this.cayenneService = cayenneService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return cayenneService.healthCheck();
    }

    @GetMapping(value = "/q1", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPricingSummary() {
        return cayenneService.getPricingSummary();
    }

    @GetMapping(value = "/q2", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMinimumCostSupplier() {
        return cayenneService.getMinimumCostSupplier();
    }

    @GetMapping(value = "/q3", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getShippingPriority() {
        return cayenneService.getShippingPriority();
    }

    @GetMapping(value = "/q4", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getOrderPriorityChecking() {
        return cayenneService.getOrderPriorityChecking();
    }

    @GetMapping(value = "/q5", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLocalSupplierVolume() {
        return cayenneService.getLocalSupplierVolume();
    }
}
