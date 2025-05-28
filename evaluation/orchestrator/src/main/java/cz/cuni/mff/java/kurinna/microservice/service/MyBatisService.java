package cz.cuni.mff.java.kurinna.microservice.service;

import cz.cuni.mff.java.kurinna.microservice.client.MicroserviceMysqlMyBatisClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class MyBatisService {
    private final MicroserviceMysqlMyBatisClient myBatisClient;

    public MyBatisService(MicroserviceMysqlMyBatisClient myBatisClient) {
        this.myBatisClient = myBatisClient;
    }

    // health check
    public String healthCheck() {
        return myBatisClient.health().getBody();
    }

    // get pricing summary
    public Map<String, Object> getPricingSummary() {
        Map<String, Object> response = myBatisClient.getPricingSummary().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // get minimum cost supplier
    public Map<String, Object> getMinimumCostSupplier() {
        Map<String, Object> response = myBatisClient.getMinimumCostSupplier().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // get shipping priority
    public Map<String, Object> getShippingPriority() {
        Map<String, Object> response = myBatisClient.getShippingPriority().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // get order priority checking
    public Map<String, Object> getOrderPriorityChecking() {
        Map<String, Object> response = myBatisClient.getOrderPriorityChecking().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // get local supplier volume
    public Map<String, Object> getLocalSupplierVolume() {
        Map<String, Object> response = myBatisClient.getLocalSupplierVolume().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // A) Selection, Projection, Source (of data)
    public Map<String, Object> executeQueryA1() {
        Map<String, Object> response = myBatisClient.getNonIndexedColumns().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryA2() {
        Map<String, Object> response = myBatisClient.getNonIndexedColumnsRangeQuery("1996-01-01", "1996-12-31").getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryA3() {
        Map<String, Object> response = myBatisClient.getIndexedColumns().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryA4() {
        Map<String, Object> response = myBatisClient.getIndexedColumnsRangeQuery(1000, 50000).getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // B) Aggregation
    public Map<String, Object> executeQueryB1() {
        Map<String, Object> response = myBatisClient.getCount().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryB2() {
        Map<String, Object> response = myBatisClient.getMax().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // C) Joins
    public Map<String, Object> executeQueryC1() {
        Map<String, Object> response = myBatisClient.getJoinNonIndexedColumns().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryC2() {
        Map<String, Object> response = myBatisClient.getJoinIndexedColumns().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryC3() {
        Map<String, Object> response = myBatisClient.getComplexJoin1().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryC4() {
        Map<String, Object> response = myBatisClient.getComplexJoin2().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryC5() {
        Map<String, Object> response = myBatisClient.getLeftOuterJoin().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // D) Set operations
    public Map<String, Object> executeQueryD1() {
        Map<String, Object> response = myBatisClient.getUnion().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryD2() {
        Map<String, Object> response = myBatisClient.getIntersect().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryD3() {
        Map<String, Object> response = myBatisClient.getDifference().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    // E) Result Modification
    public Map<String, Object> executeQueryE1() {
        Map<String, Object> response = myBatisClient.getNonIndexedColumnsSorting().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryE2() {
        Map<String, Object> response = myBatisClient.getIndexedColumnsSorting().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }

    public Map<String, Object> executeQueryE3() {
        Map<String, Object> response = myBatisClient.getDistinct().getBody();
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("No data found");
        }
        return response;
    }
}
