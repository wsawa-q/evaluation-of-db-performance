package cz.cuni.mff.java.kurinna.microservice.repository.dto;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.dto.MinimumCostSupplier;
import cz.cuni.mff.java.kurinna.microservice.dto.ShippingPriority;
import cz.cuni.mff.java.kurinna.microservice.dto.OrderPriorityChecking;
import cz.cuni.mff.java.kurinna.microservice.dto.LocalSupplierVolume;
import cz.cuni.mff.java.kurinna.microservice.dto.QueryResult;

import java.time.LocalDate;
import java.util.List;

public interface UniversalRepositoryCustom {
    // Basic queries
    List<QueryResult> a1();
    List<QueryResult> a2(LocalDate startDate, LocalDate endDate);
    List<QueryResult> a3();
    List<QueryResult> a4(int minOrderKey, int maxOrderKey);
    List<QueryResult> b1();
    List<QueryResult> b2();
    List<QueryResult> c1();
    List<QueryResult> c2();
    List<QueryResult> c3();
    List<QueryResult> c4();
    List<QueryResult> c5();
    List<QueryResult> d1();
    List<QueryResult> d2();
    List<QueryResult> d3();
    List<QueryResult> e1();
    List<QueryResult> e2();
    List<QueryResult> e3();

    // Advanced queries
    List<PricingSummary> q1(int days);
    List<MinimumCostSupplier> q2(int size, String type, String region);
    List<ShippingPriority> q3(String segment, LocalDate orderDate, LocalDate shipDate);
    List<OrderPriorityChecking> q4(LocalDate orderDate);
    List<LocalSupplierVolume> q5(String region, LocalDate orderDate);
}
