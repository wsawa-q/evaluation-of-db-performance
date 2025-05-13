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
    public String getPricingSummary(int randomNumber) {
        String url = springdataJpaApi + "/q1/" + randomNumber;
        return restTemplate.getForObject(url, String.class);
    }
}
