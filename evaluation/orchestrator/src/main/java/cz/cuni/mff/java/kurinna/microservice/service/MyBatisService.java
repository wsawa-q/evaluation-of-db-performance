package cz.cuni.mff.java.kurinna.microservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyBatisService {
    private final RestTemplate restTemplate;
    private final String myBatisEndpoint = System.getenv("MYBATIS_API");

    public MyBatisService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String healthCheck() {
        String url = myBatisEndpoint + "/health";
        return restTemplate.getForObject(url, String.class);
    }

    public String getPricingSummary(int randomNumber) {
        String url = myBatisEndpoint + "/q1/" + randomNumber;
        return restTemplate.getForObject(url, String.class);
    }
}
