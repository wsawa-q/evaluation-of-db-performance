package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.common.service.IQueryService;
import org.apache.cayenne.DataRow;
import cz.cuni.mff.java.kurinna.microservice.repository.UniversalRepository;
import org.apache.cayenne.ObjectContext;
import org.springframework.stereotype.Service;

import java.util.List;

import java.time.LocalDate;

@Service
public class QueryService implements IQueryService<DataRow> {
    private final UniversalRepository universalRepository;
    private final ObjectContext objectContext;

    public QueryService(UniversalRepository universalRepository, ObjectContext objectContext) {
        this.universalRepository = universalRepository;
        this.objectContext = objectContext;
    }

    // Basic queries
    public List<DataRow> a1() {
        return universalRepository.a1(objectContext);
    }

    public List<DataRow> a2(LocalDate startDate, LocalDate endDate) {
        return universalRepository.a2(objectContext, startDate, endDate);
    }

    public List<DataRow> a3() {
        return universalRepository.a3(objectContext);
    }

    public List<DataRow> a4(int minOrderKey, int maxOrderKey) {
        return universalRepository.a4(objectContext, minOrderKey, maxOrderKey);
    }

    public List<DataRow> b1() {
        return universalRepository.b1(objectContext);
    }

    public List<DataRow> b2() {
        return universalRepository.b2(objectContext);
    }

    public List<DataRow> c1() {
        return universalRepository.c1(objectContext);
    }

    public List<DataRow> c2() {
        return universalRepository.c2(objectContext);
    }

    public List<DataRow> c3() {
        return universalRepository.c3(objectContext);
    }

    public List<DataRow> c4() {
        return universalRepository.c4(objectContext);
    }

    public List<DataRow> c5() {
        return universalRepository.c5(objectContext);
    }

    public List<DataRow> d1() {
        return universalRepository.d1(objectContext);
    }

    public List<DataRow> d2() {
        return universalRepository.d2(objectContext);
    }

    public List<DataRow> d3() {
        return universalRepository.d3(objectContext);
    }

    public List<DataRow> e1() {
        return universalRepository.e1(objectContext);
    }

    public List<DataRow> e2() {
        return universalRepository.e2(objectContext);
    }

    public List<DataRow> e3() {
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
