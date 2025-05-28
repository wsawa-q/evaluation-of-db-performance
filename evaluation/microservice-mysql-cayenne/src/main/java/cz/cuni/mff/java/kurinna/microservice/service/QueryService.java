package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.dto.QueryResult;
import cz.cuni.mff.java.kurinna.microservice.repository.UniversalRepository;
import org.apache.cayenne.DataRow;
import org.apache.cayenne.ObjectContext;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDate;

@Service
public class QueryService {
    private final UniversalRepository universalRepository;
    private final ObjectContext objectContext;

    public QueryService(UniversalRepository universalRepository, ObjectContext objectContext) {
        this.universalRepository = universalRepository;
        this.objectContext = objectContext;
    }

    // Basic queries
    public List<QueryResult> a1() {
        return universalRepository.a1(objectContext);
    }

    public List<QueryResult> a2(LocalDate startDate, LocalDate endDate) {
        return universalRepository.a2(objectContext, startDate, endDate);
    }

    public List<QueryResult> a3() {
        return universalRepository.a3(objectContext);
    }

    public List<QueryResult> a4(int minOrderKey, int maxOrderKey) {
        return universalRepository.a4(objectContext, minOrderKey, maxOrderKey);
    }

    public List<QueryResult> b1() {
        return universalRepository.b1(objectContext);
    }

    public List<QueryResult> b2() {
        return universalRepository.b2(objectContext);
    }

    public List<QueryResult> c1() {
        return universalRepository.c1(objectContext);
    }

    public List<QueryResult> c2() {
        return universalRepository.c2(objectContext);
    }

    public List<QueryResult> c3() {
        return universalRepository.c3(objectContext);
    }

    public List<QueryResult> c4() {
        return universalRepository.c4(objectContext);
    }

    public List<QueryResult> c5() {
        return universalRepository.c5(objectContext);
    }

    public List<QueryResult> d1() {
        return universalRepository.d1(objectContext);
    }

    public List<QueryResult> d2() {
        return universalRepository.d2(objectContext);
    }

    public List<QueryResult> d3() {
        return universalRepository.d3(objectContext);
    }

    public List<QueryResult> e1() {
        return universalRepository.e1(objectContext);
    }

    public List<QueryResult> e2() {
        return universalRepository.e2(objectContext);
    }

    public List<QueryResult> e3() {
        return universalRepository.e3(objectContext);
    }

    // Advanced queries
    public List<DataRow> q1(int deltaDays) {
        return universalRepository.q1(objectContext, deltaDays);
    }

    public List<DataRow> q2(int size, String type, String region) {
        return universalRepository.q2(objectContext, size, type, region);
    }

    public List<DataRow> q3(String segment, LocalDate orderDate, LocalDate shipDate) {
        return universalRepository.q3(objectContext, segment, orderDate, shipDate);
    }

    public List<DataRow> q4(LocalDate orderDate) {
        return universalRepository.q4(objectContext, orderDate);
    }

    public List<DataRow> q5(String region, LocalDate orderDate) {
        return universalRepository.q5(objectContext, region, orderDate);
    }
}
