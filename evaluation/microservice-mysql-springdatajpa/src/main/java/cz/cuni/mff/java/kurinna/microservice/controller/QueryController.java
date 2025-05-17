package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.dto.MinimumCostSupplier;
import cz.cuni.mff.java.kurinna.microservice.dto.ShippingPriority;
import cz.cuni.mff.java.kurinna.microservice.dto.OrderPriorityChecking;
import cz.cuni.mff.java.kurinna.microservice.dto.LocalSupplierVolume;
import cz.cuni.mff.java.kurinna.microservice.model.Customer;
import cz.cuni.mff.java.kurinna.microservice.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@RestController
public class QueryController {
    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/q1")
    public ResponseEntity<Map<String, Object>> getPricingSummary() {
        Map<String, Object> response = executeWithMeasurement(() -> {
            List<PricingSummary> result = queryService.getPricingSummary(90);
            Map<String, Object> data = new HashMap<>();
            data.put("pricingSummary", result);
            return data;
        });
        return ResponseEntity.ok(response);
    }

    @GetMapping("/q2")
    public ResponseEntity<Map<String, Object>> getMinimumCostSupplier() {
        Map<String, Object> response = executeWithMeasurement(() -> {
            int size = 15;
            String type = "%BRASS";
            String region = "EUROPE";

            List<MinimumCostSupplier> result = queryService.getMinimumCostSupplier(size, type, region);
            Map<String, Object> data = new HashMap<>();
            data.put("minimumCostSupplier", result);
            return data;
        });
        return ResponseEntity.ok(response);
    }

    @GetMapping("/q3")
    public ResponseEntity<Map<String, Object>> getShippingPriority() {
        Map<String, Object> response = executeWithMeasurement(() -> {
            String segment = "BUILDING";
            LocalDate orderDate = LocalDate.of(1995, 3, 15);
            LocalDate shipDate = LocalDate.of(1995, 3, 15);

            List<ShippingPriority> result = queryService.getShippingPriority(segment, orderDate, shipDate);
            Map<String, Object> data = new HashMap<>();
            data.put("shippingPriority", result);
            return data;
        });
        return ResponseEntity.ok(response);
    }

    @GetMapping("/q4")
    public ResponseEntity<Map<String, Object>> getOrderPriorityChecking() {
        Map<String, Object> response = executeWithMeasurement(() -> {
            LocalDate orderDate = LocalDate.of(1993, 7, 1);

            List<OrderPriorityChecking> result = queryService.getOrderPriorityChecking(orderDate);
            Map<String, Object> data = new HashMap<>();
            data.put("orderPriorityChecking", result);
            return data;
        });
        return ResponseEntity.ok(response);
    }

    @GetMapping("/q5")
    public ResponseEntity<Map<String, Object>> getLocalSupplierVolume() {
        Map<String, Object> response = executeWithMeasurement(() -> {
            String region = "ASIA";
            LocalDate orderDate = LocalDate.of(1994, 1, 1);

            List<LocalSupplierVolume> result = queryService.getLocalSupplierVolume(region, orderDate);
            Map<String, Object> data = new HashMap<>();
            data.put("localSupplierVolume", result);
            return data;
        });
        return ResponseEntity.ok(response);
    }

    /**
     * Helper method to execute a query and measure its execution time and memory usage
     * 
     * @param supplier A lambda that executes the query and returns the result
     * @return A map containing the result, execution time, and memory usage
     */
    private <T> Map<String, Object> executeWithMeasurement(Supplier<T> supplier) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Force garbage collection before measuring memory
            // System.gc();

            // Measure memory before execution
            // Runtime runtime = Runtime.getRuntime();
            // long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

            // Measure execution time
            long startTime = System.currentTimeMillis();
            T result = supplier.get();
            long endTime = System.currentTimeMillis();

            // Force garbage collection again
            // System.gc();

            // Measure memory after execution
            // long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

            // Calculate memory used
            // long memoryUsed = memoryAfter - memoryBefore;

            // Add the result to the response
            if (result instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> resultMap = (Map<String, Object>) result;
                response.putAll(resultMap);
            } else {
                response.put("result", result);
            }

            // Add execution time and memory usage to the response
            response.put("executionTime", (endTime - startTime) + " ms");
            // response.put("memoryUsage", memoryUsed + " bytes");
            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("error", e.getMessage());
        }

        return response;
    }
}
