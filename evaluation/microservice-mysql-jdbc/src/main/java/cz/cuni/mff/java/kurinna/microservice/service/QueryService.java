package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.repository.UniversalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class QueryService {
    private final UniversalRepository universalRepository;

    public QueryService(UniversalRepository universalRepository) {
        this.universalRepository = universalRepository;
    }

    public List<Map<String, Object>> q1(int days) {
        return universalRepository.q1(days);
    }

    public List<Map<String, Object>> q2(int size, String type, String region) {
        return universalRepository.q2(size, type, region);
    }

    public List<Map<String, Object>> q3(String segment, LocalDate orderDate, LocalDate shipDate) {
        return universalRepository.q3(segment, orderDate, shipDate);
    }

    public List<Map<String, Object>> q4(LocalDate orderDate) {
        return universalRepository.q4(orderDate);
    }

    public List<Map<String, Object>> q5(String region, LocalDate orderDate) {
        return universalRepository.q5(region, orderDate);
    }

    // A1) Non-Indexed Columns
    public List<Map<String, Object>> a1() {
        return universalRepository.a1();
    }

    // A2) Non-Indexed Columns — Range Query
    public List<Map<String, Object>> a2(LocalDate startDate, LocalDate endDate) {
        return universalRepository.a2(startDate, endDate);
    }

    // A3) Indexed Columns
    public List<Map<String, Object>> a3() {
        return universalRepository.a3();
    }

    // A4) Indexed Columns — Range Query
    public List<Map<String, Object>> a4(int startKey, int endKey) {
        return universalRepository.a4(startKey, endKey);
    }

    // B1) COUNT
    public List<Map<String, Object>> b1() {
        return universalRepository.b1();
    }

    // B2) MAX
    public List<Map<String, Object>> b2() {
        return universalRepository.b2();
    }

    // C1) Non-Indexed Columns
    public List<Map<String, Object>> c1() {
        return universalRepository.c1();
    }

    // C2) Indexed Columns
    public List<Map<String, Object>> c2() {
        return universalRepository.c2();
    }

    // C3) Complex Join 1
    public List<Map<String, Object>> c3() {
        return universalRepository.c3();
    }

    // C4) Complex Join 2
    public List<Map<String, Object>> c4() {
        return universalRepository.c4();
    }

    // C5) Left Outer Join
    public List<Map<String, Object>> c5() {
        return universalRepository.c5();
    }

    // D1) UNION
    public List<Map<String, Object>> d1() {
        return universalRepository.d1();
    }

    // D2) INTERSECT
    public List<Map<String, Object>> d2() {
        return universalRepository.d2();
    }

    // D3) DIFFERENCE
    public List<Map<String, Object>> d3() {
        return universalRepository.d3();
    }

    // E1) Non-Indexed Columns Sorting
    public List<Map<String, Object>> e1() {
        return universalRepository.e1();
    }

    // E2) Indexed Columns Sorting
    public List<Map<String, Object>> e2() {
        return universalRepository.e2();
    }

    // E3) Distinct
    public List<Map<String, Object>> e3() {
        return universalRepository.e3();
    }
}
