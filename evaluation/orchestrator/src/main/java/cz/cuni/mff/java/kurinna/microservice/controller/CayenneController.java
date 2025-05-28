package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.CayenneService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public Map<String, Object> getPricingSummary() {
        return cayenneService.getPricingSummary();
    }

    @GetMapping(value = "/q2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getMinimumCostSupplier() {
        return cayenneService.getMinimumCostSupplier();
    }

    @GetMapping(value = "/q3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getShippingPriority() {
        return cayenneService.getShippingPriority();
    }

    @GetMapping(value = "/q4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getOrderPriorityChecking() {
        return cayenneService.getOrderPriorityChecking();
    }

    @GetMapping(value = "/q5", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getLocalSupplierVolume() {
        return cayenneService.getLocalSupplierVolume();
    }

    @GetMapping(value = "/a1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA1() {
        return cayenneService.executeQueryA1();
    }

    @GetMapping(value = "/a2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA2() {
        return cayenneService.executeQueryA2();
    }

    @GetMapping(value = "/a3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA3() {
        return cayenneService.executeQueryA3();
    }

    @GetMapping(value = "/a4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA4() {
        return cayenneService.executeQueryA4();
    }

    @GetMapping(value = "/b1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryB1() {
        return cayenneService.executeQueryB1();
    }

    @GetMapping(value = "/b2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryB2() {
        return cayenneService.executeQueryB2();
    }

    @GetMapping(value = "/c1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC1() {
        return cayenneService.executeQueryC1();
    }

    @GetMapping(value = "/c2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC2() {
        return cayenneService.executeQueryC2();
    }

    @GetMapping(value = "/c3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC3() {
        return cayenneService.executeQueryC3();
    }

    @GetMapping(value = "/c4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC4() {
        return cayenneService.executeQueryC4();
    }

    @GetMapping(value = "/c5", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC5() {
        return cayenneService.executeQueryC5();
    }

    @GetMapping(value = "/d1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD1() {
        return cayenneService.executeQueryD1();
    }

    @GetMapping(value = "/d2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD2() {
        return cayenneService.executeQueryD2();
    }

    @GetMapping(value = "/d3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD3() {
        return cayenneService.executeQueryD3();
    }

    @GetMapping(value = "/e1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE1() {
        return cayenneService.executeQueryE1();
    }

    @GetMapping(value = "/e2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE2() {
        return cayenneService.executeQueryE2();
    }

    @GetMapping(value = "/e3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE3() {
        return cayenneService.executeQueryE3();
    }
}
