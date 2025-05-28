package cz.cuni.mff.java.kurinna.microservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "microservice-mysql-cayenne")
public interface MicroserviceMysqlCayenneClient {

    @GetMapping("/health")
    ResponseEntity<String> health();

    // A-series queries
    @GetMapping("/a1")
    ResponseEntity<Map<String, Object>> getNonIndexedColumns();

    @GetMapping("/a2")
    ResponseEntity<Map<String, Object>> getNonIndexedColumnsRangeQuery(
            @RequestParam(defaultValue = "1996-01-01") String startDate,
            @RequestParam(defaultValue = "1996-12-31") String endDate);

    @GetMapping("/a3")
    ResponseEntity<Map<String, Object>> getIndexedColumns();

    @GetMapping("/a4")
    ResponseEntity<Map<String, Object>> getIndexedColumnsRangeQuery(
            @RequestParam(defaultValue = "1000") int minOrderKey,
            @RequestParam(defaultValue = "50000") int maxOrderKey);

    // B-series queries
    @GetMapping("/b1")
    ResponseEntity<Map<String, Object>> getCount();

    @GetMapping("/b2")
    ResponseEntity<Map<String, Object>> getMax();

    @GetMapping("/c1")
    ResponseEntity<Map<String, Object>> getJoinNonIndexedColumns();

    @GetMapping("/c2")
    ResponseEntity<Map<String, Object>> getJoinIndexedColumns();

    @GetMapping("/c3")
    ResponseEntity<Map<String, Object>> getComplexJoin1();

    @GetMapping("/c4")
    ResponseEntity<Map<String, Object>> getComplexJoin2();

    @GetMapping("/c5")
    ResponseEntity<Map<String, Object>> getLeftOuterJoin();

    // D-series queries
    @GetMapping("/d1")
    ResponseEntity<Map<String, Object>> getUnion();

    @GetMapping("/d2")
    ResponseEntity<Map<String, Object>> getIntersect();

    @GetMapping("/d3")
    ResponseEntity<Map<String, Object>> getDifference();

    // E-series queries
    @GetMapping("/e1")
    ResponseEntity<Map<String, Object>> getNonIndexedColumnsSorting();

    @GetMapping("/e2")
    ResponseEntity<Map<String, Object>> getIndexedColumnsSorting();

    @GetMapping("/e3")
    ResponseEntity<Map<String, Object>> getDistinct();

    // Q-series queries
    @GetMapping("/q1")
    ResponseEntity<Map<String, Object>> getPricingSummary();

    @GetMapping("/q2")
    ResponseEntity<Map<String, Object>> getMinimumCostSupplier();

    @GetMapping("/q3")
    ResponseEntity<Map<String, Object>> getShippingPriority();

    @GetMapping("/q4")
    ResponseEntity<Map<String, Object>> getOrderPriorityChecking();

    @GetMapping("/q5")
    ResponseEntity<Map<String, Object>> getLocalSupplierVolume();
}
