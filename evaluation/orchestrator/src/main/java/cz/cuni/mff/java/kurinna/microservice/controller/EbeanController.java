package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.EbeanService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
    public Map<String, Object> getPricingSummary() { return ebeanService.getPricingSummary(); }

    @GetMapping(value = "/q2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getMinimumCostSupplier() {
        return ebeanService.getMinimumCostSupplier();
    }

    @GetMapping(value = "/q3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getShippingPriority() {
        return ebeanService.getShippingPriority();
    }

    @GetMapping(value = "/q4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getOrderPriorityChecking() {
        return ebeanService.getOrderPriorityChecking();
    }

    @GetMapping(value = "/q5", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getLocalSupplierVolume() {
        return ebeanService.getLocalSupplierVolume();
    }

    @GetMapping(value = "/a1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA1() {
        return ebeanService.executeQueryA1();
    }

    @GetMapping(value = "/a2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA2() {
        return ebeanService.executeQueryA2();
    }

    @GetMapping(value = "/a3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA3() {
        return ebeanService.executeQueryA3();
    }

    @GetMapping(value = "/a4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA4() {
        return ebeanService.executeQueryA4();
    }

    @GetMapping(value = "/b1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryB1() {
        return ebeanService.executeQueryB1();
    }

    @GetMapping(value = "/b2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryB2() {
        return ebeanService.executeQueryB2();
    }

    @GetMapping(value = "/c1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC1() {
        return ebeanService.executeQueryC1();
    }

    @GetMapping(value = "/c2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC2() {
        return ebeanService.executeQueryC2();
    }

    @GetMapping(value = "/c3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC3() {
        return ebeanService.executeQueryC3();
    }

    @GetMapping(value = "/c4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC4() {
        return ebeanService.executeQueryC4();
    }

    @GetMapping(value = "/c5", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC5() {
        return ebeanService.executeQueryC5();
    }

    @GetMapping(value = "/d1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD1() {
        return ebeanService.executeQueryD1();
    }

    @GetMapping(value = "/d2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD2() {
        return ebeanService.executeQueryD2();
    }

    @GetMapping(value = "/d3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD3() {
        return ebeanService.executeQueryD3();
    }

    @GetMapping(value = "/e1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE1() {
        return ebeanService.executeQueryE1();
    }

    @GetMapping(value = "/e2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE2() {
        return ebeanService.executeQueryE2();
    }

    @GetMapping(value = "/e3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE3() {
        return ebeanService.executeQueryE3();
    }
}
