package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.EbeanService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ebean")
public class EbeanController {
    private final EbeanService ebeanService;

    public EbeanController(EbeanService ebeanService) {
        this.ebeanService = ebeanService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return ebeanService.healthCheck();
    }

    @GetMapping(value = "/q1", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getPricingSummary() {
        return ebeanService.getPricingSummary();
    }

    @GetMapping(value = "/q2", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMinimumCostSupplier() {
        return ebeanService.getMinimumCostSupplier();
    }

    @GetMapping(value = "/q3", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getShippingPriority() {
        return ebeanService.getShippingPriority();
    }

    @GetMapping(value = "/q4", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getOrderPriorityChecking() {
        return ebeanService.getOrderPriorityChecking();
    }

    @GetMapping(value = "/q5", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getLocalSupplierVolume() {
        return ebeanService.getLocalSupplierVolume();
    }
}
