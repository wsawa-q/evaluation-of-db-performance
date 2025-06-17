package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.repository.UniversalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class QueryService {
    private final UniversalRepository universalRepository;

    public QueryService(UniversalRepository universalRepository) {
        this.universalRepository = universalRepository;
    }

    // Basic queries
    public List<?> a1() {
        return universalRepository.a1();
    }

    public List<?> a2(LocalDate startDate, LocalDate endDate) {
        return universalRepository.a2(startDate, endDate);
    }

    public List<?> a3() {
        return universalRepository.a3();
    }

    public List<?> a4(int minOrderKey, int maxOrderKey) {
        return universalRepository.a4(minOrderKey, maxOrderKey);
    }

    public List<?> b1() {
        return universalRepository.b1();
    }

    public List<?> b2() {
        return universalRepository.b2();
    }

    public List<?> c1() {
        return universalRepository.c1();
    }

    public List<?> c2() {
        return universalRepository.c2();
    }

    public List<?> c3() {
        return universalRepository.c3();
    }

    public List<?> c4() {
        return universalRepository.c4();
    }

    public List<?> c5() {
        return universalRepository.c5();
    }

    public List<?> d1() {
        return universalRepository.d1();
    }

    public List<?> d2() {
        return universalRepository.d2();
    }

    public List<?> d3() {
        return universalRepository.d3();
    }

    public List<?> e1() {
        return universalRepository.e1();
    }

    public List<?> e2() {
        return universalRepository.e2();
    }

    public List<?> e3() {
        return universalRepository.e3();
    }

    // Advanced queries
    public List<?> q1(int days) {
        return universalRepository.q1(days);
    }

    public List<?> q2(int size, String type, String region) {
        return universalRepository.q2(size, type, region);
    }

    public List<?> q3(String segment, LocalDate orderDate, LocalDate shipDate) {
        return universalRepository.q3(segment, orderDate, shipDate);
    }

    public List<?> q4(LocalDate orderDate) {
        return universalRepository.q4(orderDate);
    }

    public List<?> q5(String region, LocalDate orderDate) {
        return universalRepository.q5(region, orderDate);
    }
}
