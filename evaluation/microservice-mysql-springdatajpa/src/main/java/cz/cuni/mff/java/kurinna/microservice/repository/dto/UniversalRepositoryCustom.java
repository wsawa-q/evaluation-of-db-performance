package cz.cuni.mff.java.kurinna.microservice.repository.dto;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.dto.MinimumCostSupplier;
import cz.cuni.mff.java.kurinna.microservice.dto.ShippingPriority;
import cz.cuni.mff.java.kurinna.microservice.dto.OrderPriorityChecking;
import cz.cuni.mff.java.kurinna.microservice.dto.LocalSupplierVolume;

import java.time.LocalDate;
import java.util.List;

public interface UniversalRepositoryCustom {
    List<PricingSummary> findPricingSummaryReport(int days);
    List<MinimumCostSupplier> findMinimumCostSupplier(int size, String type, String region);
    List<ShippingPriority> findShippingPriority(String segment, LocalDate orderDate, LocalDate shipDate);
    List<OrderPriorityChecking> findOrderPriorityChecking(LocalDate orderDate);
    List<LocalSupplierVolume> findLocalSupplierVolume(String region, LocalDate orderDate);
}
