package cz.cuni.mff.java.kurinna.microservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JooqService {
    private final RestTemplate restTemplate;
    private final String jooqApi = System.getenv("JOOQ_API");

    public JooqService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String healthCheck() {
        return restTemplate.getForObject(jooqApi + "/health", String.class);
    }

    public String getPricingSummary() {
        return restTemplate.getForObject(jooqApi + "/q1", String.class);
    }

    public String getMinimumCostSupplier() {
        return restTemplate.getForObject(jooqApi + "/q2", String.class);
    }

    public String getShippingPriority() {
        return restTemplate.getForObject(jooqApi + "/q3", String.class);
    }

    public String getOrderPriorityChecking() {
        return restTemplate.getForObject(jooqApi + "/q4", String.class);
    }

    public String getLocalSupplierVolume() {
        return restTemplate.getForObject(jooqApi + "/q5", String.class);
    }

    // A) Selection, Projection, Source (of data)
    public String executeQueryA1() {
        return restTemplate.getForObject(jooqApi + "/a1", String.class);
    }

    public String executeQueryA2() {
        return restTemplate.getForObject(jooqApi + "/a2", String.class);
    }

    public String executeQueryA3() {
        return restTemplate.getForObject(jooqApi + "/a3", String.class);
    }

    public String executeQueryA4() {
        return restTemplate.getForObject(jooqApi + "/a4", String.class);
    }

    // B) Aggregation
    public String executeQueryB1() {
        return restTemplate.getForObject(jooqApi + "/b1", String.class);
    }

    public String executeQueryB2() {
        return restTemplate.getForObject(jooqApi + "/b2", String.class);
    }

    // C) Joins
    public String executeQueryC1() {
        return restTemplate.getForObject(jooqApi + "/c1", String.class);
    }

    public String executeQueryC2() {
        return restTemplate.getForObject(jooqApi + "/c2", String.class);
    }

    public String executeQueryC3() {
        return restTemplate.getForObject(jooqApi + "/c3", String.class);
    }

    public String executeQueryC4() {
        return restTemplate.getForObject(jooqApi + "/c4", String.class);
    }

    public String executeQueryC5() {
        return restTemplate.getForObject(jooqApi + "/c5", String.class);
    }

    // D) Set operations
    public String executeQueryD1() {
        return restTemplate.getForObject(jooqApi + "/d1", String.class);
    }

    public String executeQueryD2() {
        return restTemplate.getForObject(jooqApi + "/d2", String.class);
    }

    public String executeQueryD3() {
        return restTemplate.getForObject(jooqApi + "/d3", String.class);
    }

    // E) Result Modification
    public String executeQueryE1() {
        return restTemplate.getForObject(jooqApi + "/e1", String.class);
    }

    public String executeQueryE2() {
        return restTemplate.getForObject(jooqApi + "/e2", String.class);
    }

    public String executeQueryE3() {
        return restTemplate.getForObject(jooqApi + "/e3", String.class);
    }

    // F) Advanced Queries
    public String executeQueryF1() {
        return restTemplate.getForObject(jooqApi + "/f1", String.class);
    }

    public String executeQueryF2() {
        return restTemplate.getForObject(jooqApi + "/f2", String.class);
    }

    public String executeQueryF3() {
        return restTemplate.getForObject(jooqApi + "/f3", String.class);
    }

    public String executeQueryF4() {
        return restTemplate.getForObject(jooqApi + "/f4", String.class);
    }
}
