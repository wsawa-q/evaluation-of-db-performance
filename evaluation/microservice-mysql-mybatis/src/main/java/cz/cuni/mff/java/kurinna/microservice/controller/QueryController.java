package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/q1/{randomNumber}")
    public ResponseEntity<Map<String, Object>> getPricingSummary(@PathVariable("randomNumber") int randomNumber) {
        long startTime = System.currentTimeMillis();
        List<PricingSummary> pricingSummary = queryService.getPricingSummary(randomNumber);
        long endTime = System.currentTimeMillis();
        Map<String, Object> response = new HashMap<>();
        response.put("executionTime", (endTime - startTime) + " ms");
        response.put("randomNumber", randomNumber);
        response.put("pricingSummary", pricingSummary);
        return ResponseEntity.ok(response);
    }
}
