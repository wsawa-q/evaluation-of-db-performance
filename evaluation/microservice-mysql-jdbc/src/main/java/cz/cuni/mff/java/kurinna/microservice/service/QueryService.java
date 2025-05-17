package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.dto.MinimumCostSupplier;
import cz.cuni.mff.java.kurinna.microservice.dto.ShippingPriority;
import cz.cuni.mff.java.kurinna.microservice.dto.OrderPriorityChecking;
import cz.cuni.mff.java.kurinna.microservice.dto.LocalSupplierVolume;
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

    public List<PricingSummary> getPricingSummary(int days) {
        return universalRepository.fetchPricingSummary(days);
    }

    public List<MinimumCostSupplier> getMinimumCostSupplier(int size, String type, String region) {
        return universalRepository.fetchMinimumCostSupplier(size, type, region);
    }

    public List<ShippingPriority> getShippingPriority(String segment, LocalDate orderDate, LocalDate shipDate) {
        return universalRepository.fetchShippingPriority(segment, orderDate, shipDate);
    }

    public List<OrderPriorityChecking> getOrderPriorityChecking(LocalDate orderDate) {
        return universalRepository.fetchOrderPriorityChecking(orderDate);
    }

    public List<LocalSupplierVolume> getLocalSupplierVolume(String region, LocalDate orderDate) {
        return universalRepository.fetchLocalSupplierVolume(region, orderDate);
    }
}
