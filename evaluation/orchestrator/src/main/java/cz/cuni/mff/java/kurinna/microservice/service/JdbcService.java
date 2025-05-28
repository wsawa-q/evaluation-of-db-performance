package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.client.MicroserviceMysqlJdbcClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class JdbcService {
    private final MicroserviceMysqlJdbcClient jdbcClient;

    public JdbcService(MicroserviceMysqlJdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // health check
    public String healthCheck() {
        return jdbcClient.health().getBody();
    }

    // get pricing summary
    public Map<String, Object> getPricingSummary() {
        Map<String, Object> response = jdbcClient.getPricingSummary().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // get minimum cost supplier
    public Map<String, Object> getMinimumCostSupplier() {
        Map<String, Object> response = jdbcClient.getMinimumCostSupplier().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // get shipping priority
    public Map<String, Object> getShippingPriority() {
        Map<String, Object> response = jdbcClient.getShippingPriority().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // get order priority checking
    public Map<String, Object> getOrderPriorityChecking() {
        Map<String, Object> response = jdbcClient.getOrderPriorityChecking().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // get local supplier volume
    public Map<String, Object> getLocalSupplierVolume() {
        Map<String, Object> response = jdbcClient.getLocalSupplierVolume().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // A) Selection, Projection, Source (of data)
    public Map<String, Object> executeQueryA1() {
        Map<String, Object> response = jdbcClient.getNonIndexedColumns().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryA2() {
        Map<String, Object> response = jdbcClient.getNonIndexedColumnsRangeQuery("1996-01-01", "1996-12-31").getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryA3() {
        Map<String, Object> response = jdbcClient.getIndexedColumns().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryA4() {
        Map<String, Object> response = jdbcClient.getIndexedColumnsRangeQuery(1000, 50000).getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // B) Aggregation
    public Map<String, Object> executeQueryB1() {
        Map<String, Object> response = jdbcClient.getCount().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryB2() {
        Map<String, Object> response = jdbcClient.getMax().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // C) Joins
    public Map<String, Object> executeQueryC1() {
        Map<String, Object> response = jdbcClient.getJoinNonIndexedColumns().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryC2() {
        Map<String, Object> response = jdbcClient.getJoinIndexedColumns().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryC3() {
        Map<String, Object> response = jdbcClient.getComplexJoin1().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryC4() {
        Map<String, Object> response = jdbcClient.getComplexJoin2().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryC5() {
        Map<String, Object> response = jdbcClient.getLeftOuterJoin().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // D) Set operations
    public Map<String, Object> executeQueryD1() {
        Map<String, Object> response = jdbcClient.getUnion().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryD2() {
        Map<String, Object> response = jdbcClient.getIntersect().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryD3() {
        Map<String, Object> response = jdbcClient.getDifference().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // E) Result Modification
    public Map<String, Object> executeQueryE1() {
        Map<String, Object> response = jdbcClient.getNonIndexedColumnsSorting().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryE2() {
        Map<String, Object> response = jdbcClient.getIndexedColumnsSorting().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryE3() {
        Map<String, Object> response = jdbcClient.getDistinct().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }
}
