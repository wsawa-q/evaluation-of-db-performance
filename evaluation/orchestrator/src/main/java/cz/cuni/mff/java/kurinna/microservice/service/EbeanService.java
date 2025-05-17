package cz.cuni.mff.java.kurinna.microservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EbeanService {
    private final RestTemplate restTemplate;
    private final String ebeanApi = System.getenv("EBEAN_API");

    public EbeanService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String healthCheck() {
        return restTemplate.getForObject(ebeanApi + "/health", String.class);
    }

    public String getPricingSummary() {
        return restTemplate.getForObject(ebeanApi + "/q1", String.class);
    }

    public String getMinimumCostSupplier() {
        return restTemplate.getForObject(ebeanApi + "/q2", String.class);
    }

    public String getShippingPriority() {
        return restTemplate.getForObject(ebeanApi + "/q3", String.class);
    }

    public String getOrderPriorityChecking() {
        return restTemplate.getForObject(ebeanApi + "/q4", String.class);
    }

    public String getLocalSupplierVolume() {
        return restTemplate.getForObject(ebeanApi + "/q5", String.class);
    }

    // A) Selection, Projection, Source (of data)
    public String executeQueryA1() {
        return restTemplate.getForObject(ebeanApi + "/a1", String.class);
    }

    public String executeQueryA2() {
        return restTemplate.getForObject(ebeanApi + "/a2", String.class);
    }

    public String executeQueryA3() {
        return restTemplate.getForObject(ebeanApi + "/a3", String.class);
    }

    public String executeQueryA4() {
        return restTemplate.getForObject(ebeanApi + "/a4", String.class);
    }

    // B) Aggregation
    public String executeQueryB1() {
        return restTemplate.getForObject(ebeanApi + "/b1", String.class);
    }

    public String executeQueryB2() {
        return restTemplate.getForObject(ebeanApi + "/b2", String.class);
    }

    // C) Joins
    public String executeQueryC1() {
        return restTemplate.getForObject(ebeanApi + "/c1", String.class);
    }

    public String executeQueryC2() {
        return restTemplate.getForObject(ebeanApi + "/c2", String.class);
    }

    public String executeQueryC3() {
        return restTemplate.getForObject(ebeanApi + "/c3", String.class);
    }

    public String executeQueryC4() {
        return restTemplate.getForObject(ebeanApi + "/c4", String.class);
    }

    public String executeQueryC5() {
        return restTemplate.getForObject(ebeanApi + "/c5", String.class);
    }

    // D) Set operations
    public String executeQueryD1() {
        return restTemplate.getForObject(ebeanApi + "/d1", String.class);
    }

    public String executeQueryD2() {
        return restTemplate.getForObject(ebeanApi + "/d2", String.class);
    }

    public String executeQueryD3() {
        return restTemplate.getForObject(ebeanApi + "/d3", String.class);
    }

    // E) Result Modification
    public String executeQueryE1() {
        return restTemplate.getForObject(ebeanApi + "/e1", String.class);
    }

    public String executeQueryE2() {
        return restTemplate.getForObject(ebeanApi + "/e2", String.class);
    }

    public String executeQueryE3() {
        return restTemplate.getForObject(ebeanApi + "/e3", String.class);
    }

    // F) Advanced Queries
    public String executeQueryF1() {
        return restTemplate.getForObject(ebeanApi + "/f1", String.class);
    }

    public String executeQueryF2() {
        return restTemplate.getForObject(ebeanApi + "/f2", String.class);
    }

    public String executeQueryF3() {
        return restTemplate.getForObject(ebeanApi + "/f3", String.class);
    }

    public String executeQueryF4() {
        return restTemplate.getForObject(ebeanApi + "/f4", String.class);
    }
}
