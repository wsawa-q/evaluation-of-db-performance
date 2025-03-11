package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.common.controller.ServiceController;
import cz.cuni.mff.java.kurinna.microservice.model.User;
import cz.cuni.mff.java.kurinna.microservice.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping("/results")
    public ResponseEntity<List<User>> getResults() {
        return ResponseEntity.ok(queryService.getResults());
    }
}
