package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.JooqService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public Map<String, Object> getPricingSummary() {
        return jooqService.getPricingSummary();
    }

    @GetMapping(value = "/q2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getMinimumCostSupplier() {
        return jooqService.getMinimumCostSupplier();
    }

    @GetMapping(value = "/q3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getShippingPriority() {
        return jooqService.getShippingPriority();
    }

    @GetMapping(value = "/q4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getOrderPriorityChecking() {
        return jooqService.getOrderPriorityChecking();
    }

    @GetMapping(value = "/q5", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getLocalSupplierVolume() {
        return jooqService.getLocalSupplierVolume();
    }

    @GetMapping(value = "/a1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA1() {
        return jooqService.executeQueryA1();
    }

    @GetMapping(value = "/a2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA2() {
        return jooqService.executeQueryA2();
    }

    @GetMapping(value = "/a3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA3() {
        return jooqService.executeQueryA3();
    }

    @GetMapping(value = "/a4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA4() {
        return jooqService.executeQueryA4();
    }

    @GetMapping(value = "/b1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryB1() {
        return jooqService.executeQueryB1();
    }

    @GetMapping(value = "/b2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryB2() {
        return jooqService.executeQueryB2();
    }

    @GetMapping(value = "/c1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC1() {
        return jooqService.executeQueryC1();
    }

    @GetMapping(value = "/c2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC2() {
        return jooqService.executeQueryC2();
    }

    @GetMapping(value = "/c3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC3() {
        return jooqService.executeQueryC3();
    }

    @GetMapping(value = "/c4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC4() {
        return jooqService.executeQueryC4();
    }

    @GetMapping(value = "/c5", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC5() {
        return jooqService.executeQueryC5();
    }

    @GetMapping(value = "/d1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD1() {
        return jooqService.executeQueryD1();
    }

    @GetMapping(value = "/d2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD2() {
        return jooqService.executeQueryD2();
    }

    @GetMapping(value = "/d3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD3() {
        return jooqService.executeQueryD3();
    }

    @GetMapping(value = "/e1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE1() {
        return jooqService.executeQueryE1();
    }

    @GetMapping(value = "/e2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE2() {
        return jooqService.executeQueryE2();
    }

    @GetMapping(value = "/e3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE3() {
        return jooqService.executeQueryE3();
    }
}

