package cz.cuni.mff.java.kurinna.microservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SpringDataJpaService {
    private final RestTemplate restTemplate;
    private final String springdataJpaApi = System.getenv("SPRINGDATA_JPA_API");

    public SpringDataJpaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // health check
    public String healthCheck() {
        String url = springdataJpaApi + "/health";
        return restTemplate.getForObject(url, String.class);
    }

    // get pricing summary
    public String getPricingSummary() {
        String url = springdataJpaApi + "/q1";
        return restTemplate.getForObject(url, String.class);
    }

    // get minimum cost supplier
    public String getMinimumCostSupplier() {
        String url = springdataJpaApi + "/q2";
        return restTemplate.getForObject(url, String.class);
    }

    // get shipping priority
    public String getShippingPriority() {
        String url = springdataJpaApi + "/q3";
        return restTemplate.getForObject(url, String.class);
    }

    // get order priority checking
    public String getOrderPriorityChecking() {
        String url = springdataJpaApi + "/q4";
        return restTemplate.getForObject(url, String.class);
    }

    // get local supplier volume
    public String getLocalSupplierVolume() {
        String url = springdataJpaApi + "/q5";
        return restTemplate.getForObject(url, String.class);
    }

    // A) Selection, Projection, Source (of data)
    public String executeQueryA1() {
        String url = springdataJpaApi + "/a1";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryA2() {
        String url = springdataJpaApi + "/a2";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryA3() {
        String url = springdataJpaApi + "/a3";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryA4() {
        String url = springdataJpaApi + "/a4";
        return restTemplate.getForObject(url, String.class);
    }

    // B) Aggregation
    public String executeQueryB1() {
        String url = springdataJpaApi + "/b1";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryB2() {
        String url = springdataJpaApi + "/b2";
        return restTemplate.getForObject(url, String.class);
    }

    // C) Joins
    public String executeQueryC1() {
        String url = springdataJpaApi + "/c1";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryC2() {
        String url = springdataJpaApi + "/c2";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryC3() {
        String url = springdataJpaApi + "/c3";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryC4() {
        String url = springdataJpaApi + "/c4";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryC5() {
        String url = springdataJpaApi + "/c5";
        return restTemplate.getForObject(url, String.class);
    }

    // D) Set operations
    public String executeQueryD1() {
        String url = springdataJpaApi + "/d1";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryD2() {
        String url = springdataJpaApi + "/d2";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryD3() {
        String url = springdataJpaApi + "/d3";
        return restTemplate.getForObject(url, String.class);
    }

    // E) Result Modification
    public String executeQueryE1() {
        String url = springdataJpaApi + "/e1";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryE2() {
        String url = springdataJpaApi + "/e2";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryE3() {
        String url = springdataJpaApi + "/e3";
        return restTemplate.getForObject(url, String.class);
    }

    // F) Advanced Queries
    public String executeQueryF1() {
        String url = springdataJpaApi + "/f1";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryF2() {
        String url = springdataJpaApi + "/f2";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryF3() {
        String url = springdataJpaApi + "/f3";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryF4() {
        String url = springdataJpaApi + "/f4";
        return restTemplate.getForObject(url, String.class);
    }
}
