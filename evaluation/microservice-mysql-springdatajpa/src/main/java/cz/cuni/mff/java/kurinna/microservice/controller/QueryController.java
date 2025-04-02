package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.common.controller.ServiceController;
import cz.cuni.mff.java.kurinna.microservice.model.Customer;
import cz.cuni.mff.java.kurinna.microservice.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QueryController implements ServiceController<Customer> {
    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @Override
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    @Override
    @DeleteMapping("/users")
    public ResponseEntity<String> deleteAll() {
        queryService.deleteAll();
        return ResponseEntity.ok("All users deleted");
    }

    @Override
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
}
