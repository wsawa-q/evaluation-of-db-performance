package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.client.MicroserviceMysqlEbeanClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EbeanService {
    private final MicroserviceMysqlEbeanClient ebeanClient;

    public EbeanService(MicroserviceMysqlEbeanClient ebeanClient) {
        this.ebeanClient = ebeanClient;
    }

    // health check
    public String healthCheck() {
        return ebeanClient.health().getBody();
    }

    // get pricing summary
    public Map<String, Object> getPricingSummary() {
        Map<String, Object> response = ebeanClient.getPricingSummary().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // get minimum cost supplier
    public Map<String, Object> getMinimumCostSupplier() {
        Map<String, Object> response = ebeanClient.getMinimumCostSupplier().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // get shipping priority
    public Map<String, Object> getShippingPriority() {
        Map<String, Object> response = ebeanClient.getShippingPriority().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // get order priority checking
    public Map<String, Object> getOrderPriorityChecking() {
        Map<String, Object> response = ebeanClient.getOrderPriorityChecking().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // get local supplier volume
    public Map<String, Object> getLocalSupplierVolume() {
        Map<String, Object> response = ebeanClient.getLocalSupplierVolume().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // A) Selection, Projection, Source (of data)
    public Map<String, Object> executeQueryA1() {
        Map<String, Object> response = ebeanClient.getNonIndexedColumns().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryA2() {
        Map<String, Object> response = ebeanClient.getNonIndexedColumnsRangeQuery("1996-01-01", "1996-12-31").getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryA3() {
        Map<String, Object> response = ebeanClient.getIndexedColumns().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryA4() {
        Map<String, Object> response = ebeanClient.getIndexedColumnsRangeQuery(1000, 50000).getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // B) Aggregation
    public Map<String, Object> executeQueryB1() {
        Map<String, Object> response = ebeanClient.getCount().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryB2() {
        Map<String, Object> response = ebeanClient.getMax().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // C) Joins
    public Map<String, Object> executeQueryC1() {
        Map<String, Object> response = ebeanClient.getJoinNonIndexedColumns().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryC2() {
        Map<String, Object> response = ebeanClient.getJoinIndexedColumns().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryC3() {
        Map<String, Object> response = ebeanClient.getComplexJoin1().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryC4() {
        Map<String, Object> response = ebeanClient.getComplexJoin2().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryC5() {
        Map<String, Object> response = ebeanClient.getLeftOuterJoin().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // D) Set operations
    public Map<String, Object> executeQueryD1() {
        Map<String, Object> response = ebeanClient.getUnion().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryD2() {
        Map<String, Object> response = ebeanClient.getIntersect().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryD3() {
        Map<String, Object> response = ebeanClient.getDifference().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // E) Result Modification
    public Map<String, Object> executeQueryE1() {
        Map<String, Object> response = ebeanClient.getNonIndexedColumnsSorting().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryE2() {
        Map<String, Object> response = ebeanClient.getIndexedColumnsSorting().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryE3() {
        Map<String, Object> response = ebeanClient.getDistinct().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }
}
