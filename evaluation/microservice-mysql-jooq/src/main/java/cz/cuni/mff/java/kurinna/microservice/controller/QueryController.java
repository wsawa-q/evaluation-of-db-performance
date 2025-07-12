package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.common.controller.IQueryController;
import cz.cuni.mff.java.kurinna.microservice.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

import static cz.cuni.mff.java.kurinna.common.utils.QueryExecutor.executeWithMeasurement;

@RestController
public class QueryController implements IQueryController {
    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OK");
    }

    // A1) Non-Indexed Columns
    @GetMapping("/a1")
    public ResponseEntity<Map<String, Object>> a1() {
        Map<String, Object> response = executeWithMeasurement(queryService::a1);
        return ResponseEntity.ok(response);
    }

    // A2) Non-Indexed Columns — Range Query
    @GetMapping("/a2")
    public ResponseEntity<Map<String, Object>> a2(
            @RequestParam(defaultValue = "1996-01-01") String startDate,
            @RequestParam(defaultValue = "1996-12-31") String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        Map<String, Object> response = executeWithMeasurement(() -> queryService.a2(start, end));
        return ResponseEntity.ok(response);
    }

    // A3) Indexed Columns
    @GetMapping("/a3")
    public ResponseEntity<Map<String, Object>> a3() {
        Map<String, Object> response = executeWithMeasurement(queryService::a3);
        return ResponseEntity.ok(response);
    }

    // A4) Indexed Columns — Range Query
    @GetMapping("/a4")
    public ResponseEntity<Map<String, Object>> a4(
            @RequestParam(defaultValue = "1000") int minOrderKey,
            @RequestParam(defaultValue = "50000") int maxOrderKey) {
        Map<String, Object> response = executeWithMeasurement(() -> queryService.a4(minOrderKey, maxOrderKey));
        return ResponseEntity.ok(response);
    }

    // B1) COUNT
    @GetMapping("/b1")
    public ResponseEntity<Map<String, Object>> b1() {
        Map<String, Object> response = executeWithMeasurement(queryService::b1);
        return ResponseEntity.ok(response);
    }

    // B2) MAX
    @GetMapping("/b2")
    public ResponseEntity<Map<String, Object>> b2() {
        Map<String, Object> response = executeWithMeasurement(queryService::b2);
        return ResponseEntity.ok(response);
    }

    // C1) Non-Indexed Columns
    @GetMapping("/c1")
    public ResponseEntity<Map<String, Object>> c1() {
        Map<String, Object> response = executeWithMeasurement(queryService::c1);
        return ResponseEntity.ok(response);
    }

    // C2) Indexed Columns
    @GetMapping("/c2")
    public ResponseEntity<Map<String, Object>> c2() {
        Map<String, Object> response = executeWithMeasurement(queryService::c2);
        return ResponseEntity.ok(response);
    }

    // C3) Complex Join 1
    @GetMapping("/c3")
    public ResponseEntity<Map<String, Object>> c3() {
        Map<String, Object> response = executeWithMeasurement(queryService::c3);
        return ResponseEntity.ok(response);
    }

    // C4) Complex Join 2
    @GetMapping("/c4")
    public ResponseEntity<Map<String, Object>> c4() {
        Map<String, Object> response = executeWithMeasurement(queryService::c4);
        return ResponseEntity.ok(response);
    }

    // C5) Left Outer Join
    @GetMapping("/c5")
    public ResponseEntity<Map<String, Object>> c5() {
        Map<String, Object> response = executeWithMeasurement(queryService::c5);
        return ResponseEntity.ok(response);
    }

    // D1) UNION
    @GetMapping("/d1")
    public ResponseEntity<Map<String, Object>> d1() {
        Map<String, Object> response = executeWithMeasurement(queryService::d1);
        return ResponseEntity.ok(response);
    }

    // D2) INTERSECT
    @GetMapping("/d2")
    public ResponseEntity<Map<String, Object>> d2() {
        Map<String, Object> response = executeWithMeasurement(queryService::d2);
        return ResponseEntity.ok(response);
    }

    // D3) DIFFERENCE
    @GetMapping("/d3")
    public ResponseEntity<Map<String, Object>> d3() {
        Map<String, Object> response = executeWithMeasurement(queryService::d3);
        return ResponseEntity.ok(response);
    }

    // E1) Non-Indexed Columns Sorting
    @GetMapping("/e1")
    public ResponseEntity<Map<String, Object>> e1() {
        Map<String, Object> response = executeWithMeasurement(queryService::e1);
        return ResponseEntity.ok(response);
    }

    // E2) Indexed Columns Sorting
    @GetMapping("/e2")
    public ResponseEntity<Map<String, Object>> e2() {
        Map<String, Object> response = executeWithMeasurement(queryService::e2);
        return ResponseEntity.ok(response);
    }

    // E3) Distinct
    @GetMapping("/e3")
    public ResponseEntity<Map<String, Object>> e3() {
        Map<String, Object> response = executeWithMeasurement(queryService::e3);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/q1")
    public ResponseEntity<Map<String, Object>> q1() {
        Map<String, Object> response = executeWithMeasurement(() -> queryService.q1(90));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/q2")
    public ResponseEntity<Map<String, Object>> q2() {
        int size = 15;
        String type = "%BRASS";
        String region = "EUROPE";
        Map<String, Object> response = executeWithMeasurement(() -> queryService.q2(size, type, region));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/q3")
    public ResponseEntity<Map<String, Object>> q3() {
        String segment = "BUILDING";
        LocalDate orderDate = LocalDate.of(1995, 3, 15);
        LocalDate shipDate = LocalDate.of(1995, 3, 15);
        Map<String, Object> response = executeWithMeasurement(() -> queryService.q3(segment, orderDate, shipDate));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/q4")
    public ResponseEntity<Map<String, Object>> q4() {
        LocalDate orderDate = LocalDate.of(1993, 7, 1);
        Map<String, Object> response = executeWithMeasurement(() -> queryService.q4(orderDate));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/q5")
    public ResponseEntity<Map<String, Object>> q5() {
        String region = "ASIA";
        LocalDate orderDate = LocalDate.of(1994, 1, 1);
        Map<String, Object> response = executeWithMeasurement(() -> queryService.q5(region, orderDate));
        return ResponseEntity.ok(response);
    }
}
