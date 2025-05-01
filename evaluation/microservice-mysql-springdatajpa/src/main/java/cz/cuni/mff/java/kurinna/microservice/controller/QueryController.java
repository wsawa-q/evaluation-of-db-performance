package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.model.Customer;
import cz.cuni.mff.java.kurinna.microservice.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAll() {
        queryService.deleteAll();
        return ResponseEntity.ok("All users deleted");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteById(Long id) {
        queryService.deleteById(id);
        return ResponseEntity.ok("User with id " + id + " deleted");
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Long> save(@RequestBody Customer user) {
        Customer savedUser = queryService.save(user);
        return ResponseEntity.ok(savedUser.getC_custkey());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Customer> findById(@PathVariable Long id) {
        return ResponseEntity.ok(queryService.findById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok(queryService.findAll());
    }

    @GetMapping("/q1")
    public ResponseEntity<Map<String, Object>> getPricingSummary() {
        int randomNumber = (int) (Math.random() * 60) + 60;
        // time the query
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
