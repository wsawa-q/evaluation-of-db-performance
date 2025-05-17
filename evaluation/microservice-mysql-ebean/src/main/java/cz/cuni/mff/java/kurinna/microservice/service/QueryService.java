package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.repository.UniversalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class QueryService {
    private final UniversalRepository universalRepository;

    public QueryService(UniversalRepository universalRepository) {
        this.universalRepository = universalRepository;
    }

    public List<Map<String, Object>> getPricingSummary(int days) {
        return universalRepository.fetchPricingSummary(days);
    }

    public List<Map<String, Object>> getMinimumCostSupplier(int size, String type, String region) {
        return universalRepository.fetchMinimumCostSupplier(size, type, region);
    }

    public List<Map<String, Object>> getShippingPriority(String segment, LocalDate orderDate, LocalDate shipDate) {
        return universalRepository.fetchShippingPriority(segment, orderDate, shipDate);
    }

    public List<Map<String, Object>> getOrderPriorityChecking(LocalDate orderDate) {
        return universalRepository.fetchOrderPriorityChecking(orderDate);
    }

    public List<Map<String, Object>> getLocalSupplierVolume(String region, LocalDate orderDate) {
        return universalRepository.fetchLocalSupplierVolume(region, orderDate);
    }
}
