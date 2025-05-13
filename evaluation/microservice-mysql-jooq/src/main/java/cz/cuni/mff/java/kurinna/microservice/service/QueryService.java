package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.repository.UniversalRepository;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {
    private final UniversalRepository universalRepository;

    public QueryService(UniversalRepository universalRepository) {
        this.universalRepository = universalRepository;
    }

    public List<PricingSummary> q1(int days) {
        return universalRepository.q1(days);
    }
}
