package cz.cuni.mff.java.kurinna.microservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CayenneService {
    private final RestTemplate restTemplate;
    private final String cayenneEndpoint = System.getenv("CAYENNE_API");

    public CayenneService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String healthCheck() {
        String url = cayenneEndpoint + "/health";
        return restTemplate.getForObject(url, String.class);
    }

    public String getPricingSummary() {
        String url = cayenneEndpoint + "/q1";
        return restTemplate.getForObject(url, String.class);
    }

    public String getMinimumCostSupplier() {
        String url = cayenneEndpoint + "/q2";
        return restTemplate.getForObject(url, String.class);
    }

    public String getShippingPriority() {
        String url = cayenneEndpoint + "/q3";
        return restTemplate.getForObject(url, String.class);
    }

    public String getOrderPriorityChecking() {
        String url = cayenneEndpoint + "/q4";
        return restTemplate.getForObject(url, String.class);
    }

    public String getLocalSupplierVolume() {
        String url = cayenneEndpoint + "/q5";
        return restTemplate.getForObject(url, String.class);
    }

    // A) Selection, Projection, Source (of data)
    public String executeQueryA1() {
        String url = cayenneEndpoint + "/a1";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryA2() {
        String url = cayenneEndpoint + "/a2";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryA3() {
        String url = cayenneEndpoint + "/a3";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryA4() {
        String url = cayenneEndpoint + "/a4";
        return restTemplate.getForObject(url, String.class);
    }

    // B) Aggregation
    public String executeQueryB1() {
        String url = cayenneEndpoint + "/b1";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryB2() {
        String url = cayenneEndpoint + "/b2";
        return restTemplate.getForObject(url, String.class);
    }

    // C) Joins
    public String executeQueryC1() {
        String url = cayenneEndpoint + "/c1";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryC2() {
        String url = cayenneEndpoint + "/c2";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryC3() {
        String url = cayenneEndpoint + "/c3";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryC4() {
        String url = cayenneEndpoint + "/c4";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryC5() {
        String url = cayenneEndpoint + "/c5";
        return restTemplate.getForObject(url, String.class);
    }

    // D) Set operations
    public String executeQueryD1() {
        String url = cayenneEndpoint + "/d1";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryD2() {
        String url = cayenneEndpoint + "/d2";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryD3() {
        String url = cayenneEndpoint + "/d3";
        return restTemplate.getForObject(url, String.class);
    }

    // E) Result Modification
    public String executeQueryE1() {
        String url = cayenneEndpoint + "/e1";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryE2() {
        String url = cayenneEndpoint + "/e2";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryE3() {
        String url = cayenneEndpoint + "/e3";
        return restTemplate.getForObject(url, String.class);
    }

    // F) Advanced Queries
    public String executeQueryF1() {
        String url = cayenneEndpoint + "/f1";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryF2() {
        String url = cayenneEndpoint + "/f2";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryF3() {
        String url = cayenneEndpoint + "/f3";
        return restTemplate.getForObject(url, String.class);
    }

    public String executeQueryF4() {
        String url = cayenneEndpoint + "/f4";
        return restTemplate.getForObject(url, String.class);
    }
}
