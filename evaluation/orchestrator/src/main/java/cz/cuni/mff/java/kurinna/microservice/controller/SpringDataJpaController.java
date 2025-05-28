package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.SpringDataJpaService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public Map<String, Object> getPricingSummary() {
        return springDataJpaService.getPricingSummary();
    }

    @GetMapping(value = "/q2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getMinimumCostSupplier() {
        return springDataJpaService.getMinimumCostSupplier();
    }

    @GetMapping(value = "/q3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getShippingPriority() {
        return springDataJpaService.getShippingPriority();
    }

    @GetMapping(value = "/q4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getOrderPriorityChecking() {
        return springDataJpaService.getOrderPriorityChecking();
    }

    @GetMapping(value = "/q5", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getLocalSupplierVolume() {
        return springDataJpaService.getLocalSupplierVolume();
    }

    @GetMapping(value = "/a1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA1() {
        return springDataJpaService.executeQueryA1();
    }

    @GetMapping(value = "/a2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA2() {
        return springDataJpaService.executeQueryA2();
    }

    @GetMapping(value = "/a3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA3() {
        return springDataJpaService.executeQueryA3();
    }

    @GetMapping(value = "/a4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA4() {
        return springDataJpaService.executeQueryA4();
    }

    @GetMapping(value = "/b1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryB1() {
        return springDataJpaService.executeQueryB1();
    }

    @GetMapping(value = "/b2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryB2() {
        return springDataJpaService.executeQueryB2();
    }

    @GetMapping(value = "/c1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC1() {
        return springDataJpaService.executeQueryC1();
    }

    @GetMapping(value = "/c2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC2() {
        return springDataJpaService.executeQueryC2();
    }

    @GetMapping(value = "/c3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC3() {
        return springDataJpaService.executeQueryC3();
    }

    @GetMapping(value = "/c4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC4() {
        return springDataJpaService.executeQueryC4();
    }

    @GetMapping(value = "/c5", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC5() {
        return springDataJpaService.executeQueryC5();
    }

    @GetMapping(value = "/d1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD1() {
        return springDataJpaService.executeQueryD1();
    }

    @GetMapping(value = "/d2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD2() {
        return springDataJpaService.executeQueryD2();
    }

    @GetMapping(value = "/d3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD3() {
        return springDataJpaService.executeQueryD3();
    }

    @GetMapping(value = "/e1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE1() {
        return springDataJpaService.executeQueryE1();
    }

    @GetMapping(value = "/e2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE2() {
        return springDataJpaService.executeQueryE2();
    }

    @GetMapping(value = "/e3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE3() {
        return springDataJpaService.executeQueryE3();
    }
}
