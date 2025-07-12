package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.common.service.IQueryService;
import cz.cuni.mff.java.kurinna.microservice.repository.UniversalRepository;
import io.ebean.SqlRow;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class QueryService implements IQueryService<SqlRow> {
    private final UniversalRepository universalRepository;

    public QueryService(UniversalRepository universalRepository) {
        this.universalRepository = universalRepository;
    }

    // Basic queries
    public List<SqlRow> a1() {
        return universalRepository.a1();
    }

    public List<SqlRow> a2(LocalDate startDate, LocalDate endDate) {
        return universalRepository.a2(startDate, endDate);
    }

    public List<SqlRow> a3() {
        return universalRepository.a3();
    }

    public List<SqlRow> a4(int minOrderKey, int maxOrderKey) {
        return universalRepository.a4(minOrderKey, maxOrderKey);
    }

    public List<SqlRow> b1() {
        return universalRepository.b1();
    }

    public List<SqlRow> b2() {
        return universalRepository.b2();
    }

    public List<SqlRow> c1() {
        return universalRepository.c1();
    }

    public List<SqlRow> c2() {
        return universalRepository.c2();
    }

    public List<SqlRow> c3() {
        return universalRepository.c3();
    }

    public List<SqlRow> c4() {
        return universalRepository.c4();
    }

    public List<SqlRow> c5() {
        return universalRepository.c5();
    }

    public List<SqlRow> d1() {
        return universalRepository.d1();
    }

    public List<SqlRow> d2() {
        return universalRepository.d2();
    }

    public List<SqlRow> d3() {
        return universalRepository.d3();
    }

    public List<SqlRow> e1() {
        return universalRepository.e1();
    }

    public List<SqlRow> e2() {
        return universalRepository.e2();
    }

    public List<SqlRow> e3() {
        return universalRepository.e3();
    }

    // Advanced queries
    public List<SqlRow> q1(int days) {
        return universalRepository.q1(days);
    }

    public List<SqlRow> q2(int size, String type, String region) {
        return universalRepository.q2(size, type, region);
    }

    public List<SqlRow> q3(String segment, LocalDate orderDate, LocalDate shipDate) {
        return universalRepository.q3(segment, orderDate, shipDate);
    }

    public List<SqlRow> q4(LocalDate orderDate) {
        return universalRepository.q4(orderDate);
    }

    public List<SqlRow> q5(String region, LocalDate orderDate) {
        return universalRepository.q5(region, orderDate);
    }
}
