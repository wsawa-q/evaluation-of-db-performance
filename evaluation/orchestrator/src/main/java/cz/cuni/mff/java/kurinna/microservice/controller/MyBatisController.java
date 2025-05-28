package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.MyBatisService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/mybatis")
public class MyBatisController {
    private final MyBatisService myBatisService;

    public MyBatisController(MyBatisService myBatisService) {
        this.myBatisService = myBatisService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return myBatisService.healthCheck();
    }

    @GetMapping(value = "/q1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getPricingSummary() {
        return myBatisService.getPricingSummary();
    }

    @GetMapping(value = "/q2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getMinimumCostSupplier() {
        return myBatisService.getMinimumCostSupplier();
    }

    @GetMapping(value = "/q3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getShippingPriority() {
        return myBatisService.getShippingPriority();
    }

    @GetMapping(value = "/q4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getOrderPriorityChecking() {
        return myBatisService.getOrderPriorityChecking();
    }

    @GetMapping(value = "/q5", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getLocalSupplierVolume() {
        return myBatisService.getLocalSupplierVolume();
    }

    @GetMapping(value = "/a1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA1() {
        return myBatisService.executeQueryA1();
    }

    @GetMapping(value = "/a2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA2() {
        return myBatisService.executeQueryA2();
    }

    @GetMapping(value = "/a3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA3() {
        return myBatisService.executeQueryA3();
    }

    @GetMapping(value = "/a4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryA4() {
        return myBatisService.executeQueryA4();
    }

    @GetMapping(value = "/b1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryB1() {
        return myBatisService.executeQueryB1();
    }

    @GetMapping(value = "/b2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryB2() {
        return myBatisService.executeQueryB2();
    }

    @GetMapping(value = "/c1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC1() {
        return myBatisService.executeQueryC1();
    }

    @GetMapping(value = "/c2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC2() {
        return myBatisService.executeQueryC2();
    }

    @GetMapping(value = "/c3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC3() {
        return myBatisService.executeQueryC3();
    }

    @GetMapping(value = "/c4", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC4() {
        return myBatisService.executeQueryC4();
    }

    @GetMapping(value = "/c5", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryC5() {
        return myBatisService.executeQueryC5();
    }

    @GetMapping(value = "/d1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD1() {
        return myBatisService.executeQueryD1();
    }

    @GetMapping(value = "/d2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD2() {
        return myBatisService.executeQueryD2();
    }

    @GetMapping(value = "/d3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryD3() {
        return myBatisService.executeQueryD3();
    }

    @GetMapping(value = "/e1", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE1() {
        return myBatisService.executeQueryE1();
    }

    @GetMapping(value = "/e2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE2() {
        return myBatisService.executeQueryE2();
    }

    @GetMapping(value = "/e3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> executeQueryE3() {
        return myBatisService.executeQueryE3();
    }
}
