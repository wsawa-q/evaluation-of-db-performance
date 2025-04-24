package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.repository.UniversalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QueryService {
    private final UniversalRepository universalRepository;

    public QueryService(UniversalRepository universalRepository) {
        this.universalRepository = universalRepository;
    }

    public List<PricingSummary> getPricingSummary(int days) {
        return universalRepository.fetchPricingSummary(days);
    }
}
