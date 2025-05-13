package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.config.CayenneConfig;
import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.repository.UniversalRepository;
import org.apache.cayenne.DataRow;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.datasource.DataSourceBuilder;
import org.apache.cayenne.query.SQLSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

import java.time.LocalDate;
import java.util.Map;

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
}
