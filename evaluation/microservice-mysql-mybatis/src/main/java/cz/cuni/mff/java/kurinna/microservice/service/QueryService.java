package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.dto.MinimumCostSupplier;
import cz.cuni.mff.java.kurinna.microservice.dto.ShippingPriority;
import cz.cuni.mff.java.kurinna.microservice.dto.OrderPriorityChecking;
import cz.cuni.mff.java.kurinna.microservice.dto.LocalSupplierVolume;
import cz.cuni.mff.java.kurinna.microservice.repository.UniversalMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
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

    public List<MinimumCostSupplier> getMinimumCostSupplier(int size, String type, String region) {
        return universalMapper.fetchMinimumCostSupplier(size, type, region);
    }

    public List<ShippingPriority> getShippingPriority(String segment, LocalDate orderDate, LocalDate shipDate) {
        return universalMapper.fetchShippingPriority(segment, orderDate, shipDate);
    }

    public List<OrderPriorityChecking> getOrderPriorityChecking(LocalDate orderDate) {
        return universalMapper.fetchOrderPriorityChecking(orderDate);
    }

    public List<LocalSupplierVolume> getLocalSupplierVolume(String region, LocalDate orderDate) {
        return universalMapper.fetchLocalSupplierVolume(region, orderDate);
    }
}
