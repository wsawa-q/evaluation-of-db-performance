package cz.cuni.mff.java.kurinna.microservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class RootController {
    @GetMapping("/getQueryEndpoints")
    public ResponseEntity<String[]> getQueryRoutes() {
        return ResponseEntity.ok(new String[]{
                "q1", "q2", "q3", "q4", "q5",
                "a1", "a2", "a3", "a4",
                "b1", "b2",
                "c1", "c2", "c3", "c4", "c5",
                "d1", "d2", "d3",
                "e1", "e2", "e3"
        });
    }

    @GetMapping("/getMicroserviceEndpoints")
    public ResponseEntity<String[]> getMicroserviceRoutes() {
        return ResponseEntity.ok(new String[]{
                "ebean", "cayenne", "jdbc", "jooq", "mybatis", "springdatajpa"
        });
    }
}
