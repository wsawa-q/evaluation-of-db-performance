package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.common.controller.ServiceController;
import cz.cuni.mff.java.kurinna.microservice.model.User;
import cz.cuni.mff.java.kurinna.microservice.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class QueryController implements ServiceController<User> {
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
    public ResponseEntity<Long> save(@RequestBody User user) {
        User savedUser = queryService.save(user);
        return ResponseEntity.ok(savedUser.getId());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> findById(Long id) {
        return ResponseEntity.ok(queryService.findById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok(queryService.getResults());
    }
}
