package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.dto.MinimumCostSupplier;
import cz.cuni.mff.java.kurinna.microservice.dto.ShippingPriority;
import cz.cuni.mff.java.kurinna.microservice.dto.OrderPriorityChecking;
import cz.cuni.mff.java.kurinna.microservice.dto.LocalSupplierVolume;
import cz.cuni.mff.java.kurinna.microservice.repository.UniversalRepository;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public List<MinimumCostSupplier> q2(int size, String type, String region) {
        return universalRepository.q2(size, type, region);
    }

    public List<ShippingPriority> q3(String segment, LocalDate orderDate, LocalDate shipDate) {
        return universalRepository.q3(segment, orderDate, shipDate);
    }

    public List<OrderPriorityChecking> q4(LocalDate orderDate) {
        return universalRepository.q4(orderDate);
    }

    public List<LocalSupplierVolume> q5(String region, LocalDate orderDate) {
        return universalRepository.q5(region, orderDate);
    }
}
