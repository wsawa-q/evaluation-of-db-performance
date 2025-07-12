package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.common.service.IQueryService;
import cz.cuni.mff.java.kurinna.microservice.repository.UniversalMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class QueryService implements IQueryService<Map<String, Object>> {
    private final UniversalMapper universalMapper;

    public QueryService(UniversalMapper universalMapper) {
        this.universalMapper = universalMapper;
    }

    public List<Map<String, Object>> q1(int days) {
        return universalMapper.q1(days);
    }

    public List<Map<String, Object>> q2(int size, String type, String region) {
        return universalMapper.q2(size, type, region);
    }

    public List<Map<String, Object>> q3(String segment, LocalDate orderDate, LocalDate shipDate) {
        return universalMapper.q3(segment, orderDate, shipDate);
    }

    public List<Map<String, Object>> q4(LocalDate orderDate) {
        return universalMapper.q4(orderDate);
    }

    public List<Map<String, Object>> q5(String region, LocalDate orderDate) {
        return universalMapper.q5(region, orderDate);
    }

    // A1) Non-Indexed Columns
    public List<Map<String, Object>> a1() {
        return universalMapper.a1();
    }

    // A2) Non-Indexed Columns — Range Query
    public List<Map<String, Object>> a2(LocalDate startDate, LocalDate endDate) {
        return universalMapper.a2(startDate, endDate);
    }

    // A3) Indexed Columns
    public List<Map<String, Object>> a3() {
        return universalMapper.a3();
    }

    // A4) Indexed Columns — Range Query
    public List<Map<String, Object>> a4(int startKey, int endKey) {
        return universalMapper.a4(startKey, endKey);
    }

    // B1) COUNT
    public List<Map<String, Object>> b1() {
        return universalMapper.b1();
    }

    // B2) MAX
    public List<Map<String, Object>> b2() {
        return universalMapper.b2();
    }

    // C1) Non-Indexed Columns
    public List<Map<String, Object>> c1() {
        return universalMapper.c1();
    }

    // C2) Indexed Columns
    public List<Map<String, Object>> c2() {
        return universalMapper.c2();
    }

    // C3) Complex Join 1
    public List<Map<String, Object>> c3() {
        return universalMapper.c3();
    }

    // C4) Complex Join 2
    public List<Map<String, Object>> c4() {
        return universalMapper.c4();
    }

    // C5) Left Outer Join
    public List<Map<String, Object>> c5() {
        return universalMapper.c5();
    }

    // D1) UNION
    public List<Map<String, Object>> d1() {
        return universalMapper.d1();
    }

    // D2) INTERSECT
    public List<Map<String, Object>> d2() {
        return universalMapper.d2();
    }

    // D3) DIFFERENCE
    public List<Map<String, Object>> d3() {
        return universalMapper.d3();
    }

    // E1) Non-Indexed Columns Sorting
    public List<Map<String, Object>> e1() {
        return universalMapper.e1();
    }

    // E2) Indexed Columns Sorting
    public List<Map<String, Object>> e2() {
        return universalMapper.e2();
    }

    // E3) Distinct
    public List<Map<String, Object>> e3() {
        return universalMapper.e3();
    }
}
