package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.repository.UniversalMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    private final UniversalMapper universalMapper;

    public QueryService(UniversalMapper universalMapper) {
        this.universalMapper = universalMapper;
    }

    public List<PricingSummary> getPricingSummary(int days) {
        return universalMapper.fetchPricingSummary(days);
    }
}
