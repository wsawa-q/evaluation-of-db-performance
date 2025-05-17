package cz.cuni.mff.java.kurinna.microservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JdbcService {
    private final RestTemplate restTemplate;
    private final String jdbcApi = System.getenv("JDBC_API");

    public JdbcService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String healthCheck() {
        return restTemplate.getForObject(jdbcApi + "/health", String.class);
    }

    public String getPricingSummary() {
        return restTemplate.getForObject(jdbcApi + "/q1", String.class);
    }

    public String getMinimumCostSupplier() {
        return restTemplate.getForObject(jdbcApi + "/q2", String.class);
    }

    public String getShippingPriority() {
        return restTemplate.getForObject(jdbcApi + "/q3", String.class);
    }

    public String getOrderPriorityChecking() {
        return restTemplate.getForObject(jdbcApi + "/q4", String.class);
    }

    public String getLocalSupplierVolume() {
        return restTemplate.getForObject(jdbcApi + "/q5", String.class);
    }

    // A) Selection, Projection, Source (of data)
    public String executeQueryA1() {
        return restTemplate.getForObject(jdbcApi + "/a1", String.class);
    }

    public String executeQueryA2() {
        return restTemplate.getForObject(jdbcApi + "/a2", String.class);
    }

    public String executeQueryA3() {
        return restTemplate.getForObject(jdbcApi + "/a3", String.class);
    }

    public String executeQueryA4() {
        return restTemplate.getForObject(jdbcApi + "/a4", String.class);
    }

    // B) Aggregation
    public String executeQueryB1() {
        return restTemplate.getForObject(jdbcApi + "/b1", String.class);
    }

    public String executeQueryB2() {
        return restTemplate.getForObject(jdbcApi + "/b2", String.class);
    }

    // C) Joins
    public String executeQueryC1() {
        return restTemplate.getForObject(jdbcApi + "/c1", String.class);
    }

    public String executeQueryC2() {
        return restTemplate.getForObject(jdbcApi + "/c2", String.class);
    }

    public String executeQueryC3() {
        return restTemplate.getForObject(jdbcApi + "/c3", String.class);
    }

    public String executeQueryC4() {
        return restTemplate.getForObject(jdbcApi + "/c4", String.class);
    }

    public String executeQueryC5() {
        return restTemplate.getForObject(jdbcApi + "/c5", String.class);
    }

    // D) Set operations
    public String executeQueryD1() {
        return restTemplate.getForObject(jdbcApi + "/d1", String.class);
    }

    public String executeQueryD2() {
        return restTemplate.getForObject(jdbcApi + "/d2", String.class);
    }

    public String executeQueryD3() {
        return restTemplate.getForObject(jdbcApi + "/d3", String.class);
    }

    // E) Result Modification
    public String executeQueryE1() {
        return restTemplate.getForObject(jdbcApi + "/e1", String.class);
    }

    public String executeQueryE2() {
        return restTemplate.getForObject(jdbcApi + "/e2", String.class);
    }

    public String executeQueryE3() {
        return restTemplate.getForObject(jdbcApi + "/e3", String.class);
    }

    // F) Advanced Queries
    public String executeQueryF1() {
        return restTemplate.getForObject(jdbcApi + "/f1", String.class);
    }

    public String executeQueryF2() {
        return restTemplate.getForObject(jdbcApi + "/f2", String.class);
    }

    public String executeQueryF3() {
        return restTemplate.getForObject(jdbcApi + "/f3", String.class);
    }

    public String executeQueryF4() {
        return restTemplate.getForObject(jdbcApi + "/f4", String.class);
    }
}
