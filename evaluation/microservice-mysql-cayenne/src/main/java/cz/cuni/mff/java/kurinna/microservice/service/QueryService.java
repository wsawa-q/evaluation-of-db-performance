package cz.cuni.mff.java.kurinna.microservice.service;

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
