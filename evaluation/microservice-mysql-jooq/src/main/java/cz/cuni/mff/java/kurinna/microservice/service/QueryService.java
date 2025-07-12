package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.common.service.IQueryService;
import cz.cuni.mff.java.kurinna.microservice.repository.UniversalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class QueryService implements IQueryService<Map<String, Object>> {
    private final UniversalRepository universalRepository;

    public QueryService(UniversalRepository universalRepository) {
        this.universalRepository = universalRepository;
    }

    public List<Map<String, Object>> a1() {
        return universalRepository.a1();
    }

    public List<Map<String, Object>> a2(LocalDate startDate, LocalDate endDate) {
        return universalRepository.a2(startDate, endDate);
    }

    public List<Map<String, Object>> a3() {
        return universalRepository.a3();
    }

    public List<Map<String, Object>> a4(int minOrderKey, int maxOrderKey) {
        return universalRepository.a4(minOrderKey, maxOrderKey);
    }

    public List<Map<String, Object>> b1() {
        return universalRepository.b1();
    }

    public List<Map<String, Object>> b2() {
        return universalRepository.b2();
    }

    public List<Map<String, Object>> c1() {
        return universalRepository.c1();
    }

    public List<Map<String, Object>> c2() {
        return universalRepository.c2();
    }

    public List<Map<String, Object>> c3() {
        return universalRepository.c3();
    }

    public List<Map<String, Object>> c4() {
        return universalRepository.c4();
    }

    public List<Map<String, Object>> c5() {
        return universalRepository.c5();
    }

    public List<Map<String, Object>> d1() {
        return universalRepository.d1();
    }

    public List<Map<String, Object>> d2() {
        return universalRepository.d2();
    }

    public List<Map<String, Object>> d3() {
        return universalRepository.d3();
    }

    public List<Map<String, Object>> e1() {
        return universalRepository.e1();
    }

    public List<Map<String, Object>> e2() {
        return universalRepository.e2();
    }

    public List<Map<String, Object>> e3() {
        return universalRepository.e3();
    }

    // Advanced queries
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
}
