package cz.cuni.mff.java.kurinna.microservice.controller;

import cz.cuni.mff.java.kurinna.microservice.service.CayenneService;
import cz.cuni.mff.java.kurinna.microservice.service.EbeanService;
import cz.cuni.mff.java.kurinna.microservice.service.JdbcService;
import cz.cuni.mff.java.kurinna.microservice.service.JooqService;
import cz.cuni.mff.java.kurinna.microservice.service.MyBatisService;
import cz.cuni.mff.java.kurinna.microservice.service.SpringDataJpaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorController {
    private final MyBatisService myBatisService;
    private final SpringDataJpaService springDataJpaService;
    private final CayenneService cayenneService;
    private final EbeanService ebeanService;
    private final JdbcService jdbcService;
    private final JooqService jooqService;


    public OrchestratorController(MyBatisService myBatisService, SpringDataJpaService springDataJpaService, CayenneService cayenneService, EbeanService ebeanService, JdbcService jdbcService, JooqService jooqService) {
        this.myBatisService = myBatisService;
        this.springDataJpaService = springDataJpaService;
        this.cayenneService = cayenneService;
        this.ebeanService = ebeanService;
        this.jdbcService = jdbcService;
        this.jooqService = jooqService;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "OK";
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> index() {
        Map<String, Object> response = new HashMap<>();
        SortedMap<String, String> queries = new TreeMap<>();

        // TPC-H Queries
        queries.put("/q1", "Q1) Pricing Summary Report Query");
        queries.put("/q2", "Q2) Minimum Cost Supplier Query");
        queries.put("/q3", "Q3) Shipping Priority Query");
        queries.put("/q4", "Q4) Order Priority Checking Query");
        queries.put("/q5", "Q5) Local Supplier Volume Query");

        // A) Selection, Projection, Source (of data)
        queries.put("/a1", "A1) Non-Indexed Columns");
        queries.put("/a2", "A2) Non-Indexed Columns — Range Query");
        queries.put("/a3", "A3) Indexed Columns");
        queries.put("/a4", "A4) Indexed Columns — Range Query");

        // B) Aggregation
        queries.put("/b1", "B1) COUNT");
        queries.put("/b2", "B2) MAX");

        // C) Joins
        queries.put("/c1", "C1) Non-Indexed Columns");
        queries.put("/c2", "C2) Indexed Columns");
        queries.put("/c3", "C3) Complex Join 1");
        queries.put("/c4", "C4) Complex Join 2");
        queries.put("/c5", "C5) Left Outer Join");

        // D) Set operations
        queries.put("/d1", "D1) UNION");
        queries.put("/d2", "D2) INTERSECT");
        queries.put("/d3", "D3) DIFFERENCE");

        // E) Result Modification
        queries.put("/e1", "E1) Non-Indexed Columns Sorting");
        queries.put("/e2", "E2) Indexed Columns Sorting");
        queries.put("/e3", "E3) Distinct");

        // F) Advanced Queries
        queries.put("/f1", "F1) Subquery with Aggregation");
        queries.put("/f2", "F2) GROUP BY with HAVING");
        queries.put("/f3", "F3) Window Functions");
        queries.put("/f4", "F4) Complex Business Query");

        response.put("available_queries", queries);
        response.put("description", "This API allows you to execute, time, and measure memory usage of various SQL queries across different database access technologies.");
        response.put("instructions", "Append any of the query endpoints to the base URL to execute that query and see timing and memory usage results.");

        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/q1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getPricingSummary() {
        int days = 90;

        Map<String, Object> results = new HashMap<>();
        results.put("days", days);
        results.put("query", "Q1) Pricing Summary Report Query");
        results.put("description", "TPC-H Q1 query that reports pricing summary for all items shipped before a given date.");

        executeQueryWithTiming("myBatis", myBatisService::getPricingSummary, results);
        executeQueryWithTiming("springDataJpa", springDataJpaService::getPricingSummary, results);
        executeQueryWithTiming("cayenne", cayenneService::getPricingSummary, results);
        executeQueryWithTiming("ebean", ebeanService::getPricingSummary, results);
        executeQueryWithTiming("jdbc", jdbcService::getPricingSummary, results);
        executeQueryWithTiming("jooq", jooqService::getPricingSummary, results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/q2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getMinimumCostSupplier() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "Q2) Minimum Cost Supplier Query");
        results.put("description", "TPC-H Q2 query that finds suppliers who can supply parts of a given type and size at minimum cost.");

        executeQueryWithTiming("myBatis", myBatisService::getMinimumCostSupplier, results);
        executeQueryWithTiming("springDataJpa", springDataJpaService::getMinimumCostSupplier, results);
        executeQueryWithTiming("cayenne", cayenneService::getMinimumCostSupplier, results);
        executeQueryWithTiming("ebean", ebeanService::getMinimumCostSupplier, results);
        executeQueryWithTiming("jdbc", jdbcService::getMinimumCostSupplier, results);
        executeQueryWithTiming("jooq", jooqService::getMinimumCostSupplier, results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/q3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getShippingPriority() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "Q3) Shipping Priority Query");
        results.put("description", "TPC-H Q3 query that retrieves the shipping priority and potential revenue of orders.");

        executeQueryWithTiming("myBatis", myBatisService::getShippingPriority, results);
        executeQueryWithTiming("springDataJpa", springDataJpaService::getShippingPriority, results);
        executeQueryWithTiming("cayenne", cayenneService::getShippingPriority, results);
        executeQueryWithTiming("ebean", ebeanService::getShippingPriority, results);
        executeQueryWithTiming("jdbc", jdbcService::getShippingPriority, results);
        executeQueryWithTiming("jooq", jooqService::getShippingPriority, results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/q4", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getOrderPriorityChecking() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "Q4) Order Priority Checking Query");
        results.put("description", "TPC-H Q4 query that counts orders with at least one lineitem that was received later than committed.");

        executeQueryWithTiming("myBatis", myBatisService::getOrderPriorityChecking, results);
        executeQueryWithTiming("springDataJpa", springDataJpaService::getOrderPriorityChecking, results);
        executeQueryWithTiming("cayenne", cayenneService::getOrderPriorityChecking, results);
        executeQueryWithTiming("ebean", ebeanService::getOrderPriorityChecking, results);
        executeQueryWithTiming("jdbc", jdbcService::getOrderPriorityChecking, results);
        executeQueryWithTiming("jooq", jooqService::getOrderPriorityChecking, results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/q5", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getLocalSupplierVolume() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "Q5) Local Supplier Volume Query");
        results.put("description", "TPC-H Q5 query that lists the revenue volume for each nation in a region where suppliers and customers are from the same nation.");

        executeQueryWithTiming("myBatis", myBatisService::getLocalSupplierVolume, results);
        executeQueryWithTiming("springDataJpa", springDataJpaService::getLocalSupplierVolume, results);
        executeQueryWithTiming("cayenne", cayenneService::getLocalSupplierVolume, results);
        executeQueryWithTiming("ebean", ebeanService::getLocalSupplierVolume, results);
        executeQueryWithTiming("jdbc", jdbcService::getLocalSupplierVolume, results);
        executeQueryWithTiming("jooq", jooqService::getLocalSupplierVolume, results);

        return ResponseEntity.ok(results);
    }

    // A) Selection, Projection, Source (of data)

    @GetMapping(value = "/a1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryA1() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "A1) Non-Indexed Columns");
        results.put("description", "SELECT * FROM lineitem WHERE l_extendedprice < 1000.0;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryA1(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryA1(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryA1(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryA1(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryA1(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryA1(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/a2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryA2() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "A2) Non-Indexed Columns — Range Query");
        results.put("description", "SELECT * FROM orders WHERE o_orderdate BETWEEN '1996-01-01' AND '1996-12-31';");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryA2(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryA2(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryA2(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryA2(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryA2(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryA2(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/a3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryA3() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "A3) Indexed Columns");
        results.put("description", "SELECT * FROM customer WHERE c_custkey = 1000;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryA3(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryA3(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryA3(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryA3(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryA3(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryA3(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/a4", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryA4() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "A4) Indexed Columns — Range Query");
        results.put("description", "SELECT * FROM orders WHERE o_orderkey BETWEEN 1000 AND 2000;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryA4(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryA4(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryA4(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryA4(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryA4(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryA4(), results);

        return ResponseEntity.ok(results);
    }

    // B) Aggregation

    @GetMapping(value = "/b1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryB1() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "B1) COUNT");
        results.put("description", "SELECT COUNT(*) AS order_count FROM orders WHERE o_orderdate BETWEEN '1996-01-01' AND '1996-12-31';");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryB1(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryB1(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryB1(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryB1(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryB1(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryB1(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/b2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryB2() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "B2) MAX");
        results.put("description", "SELECT MAX(l_extendedprice) AS max_price FROM lineitem;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryB2(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryB2(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryB2(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryB2(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryB2(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryB2(), results);

        return ResponseEntity.ok(results);
    }

    // C) Joins

    @GetMapping(value = "/c1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryC1() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "C1) Non-Indexed Columns");
        results.put("description", "SELECT c.c_name, o.o_orderdate, o.o_totalprice FROM customer c, orders o WHERE c.c_mktsegment = 'BUILDING' AND c.c_custkey = o.o_custkey;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryC1(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryC1(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryC1(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryC1(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryC1(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryC1(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/c2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryC2() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "C2) Indexed Columns");
        results.put("description", "SELECT c.c_name, o.o_orderdate, o.o_totalprice FROM customer c JOIN orders o ON c.c_custkey = o.o_custkey WHERE o.o_orderkey BETWEEN 1000 AND 2000;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryC2(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryC2(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryC2(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryC2(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryC2(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryC2(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/c3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryC3() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "C3) Complex Join 1");
        results.put("description", "SELECT c.c_name, n.n_name, o.o_orderdate, o.o_totalprice FROM customer c JOIN nation n ON c.c_nationkey = n.n_nationkey JOIN orders o ON c.c_custkey = o.o_custkey WHERE n.n_name = 'GERMANY' AND o.o_orderdate BETWEEN '1996-01-01' AND '1996-12-31';");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryC3(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryC3(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryC3(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryC3(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryC3(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryC3(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/c4", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryC4() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "C4) Complex Join 2");
        results.put("description", "SELECT c.c_name, n.n_name, r.r_name, o.o_orderdate, o.o_totalprice FROM customer c JOIN nation n ON c.c_nationkey = n.n_nationkey JOIN region r ON n.n_regionkey = r.r_regionkey JOIN orders o ON c.c_custkey = o.o_custkey WHERE r.r_name = 'EUROPE' AND o.o_orderdate BETWEEN '1996-01-01' AND '1996-12-31';");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryC4(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryC4(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryC4(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryC4(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryC4(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryC4(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/c5", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryC5() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "C5) Left Outer Join");
        results.put("description", "SELECT c.c_custkey, c.c_name, o.o_orderkey, o.o_orderdate FROM customer c LEFT OUTER JOIN orders o ON c.c_custkey = o.o_custkey WHERE c.c_nationkey = 3;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryC5(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryC5(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryC5(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryC5(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryC5(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryC5(), results);

        return ResponseEntity.ok(results);
    }

    // D) Set operations

    @GetMapping(value = "/d1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryD1() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "D1) UNION");
        results.put("description", "(SELECT c_nationkey FROM customer WHERE c_acctbal > 9000) UNION (SELECT s_nationkey FROM supplier WHERE s_acctbal > 9000);");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryD1(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryD1(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryD1(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryD1(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryD1(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryD1(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/d2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryD2() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "D2) INTERSECT");
        results.put("description", "SELECT DISTINCT c_nationkey FROM customer WHERE c_acctbal > 9000 AND c_nationkey IN (SELECT s_nationkey FROM supplier WHERE s_acctbal > 9000);");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryD2(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryD2(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryD2(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryD2(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryD2(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryD2(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/d3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryD3() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "D3) DIFFERENCE");
        results.put("description", "SELECT DISTINCT c_nationkey FROM customer WHERE c_acctbal > 9000 AND c_nationkey NOT IN (SELECT s_nationkey FROM supplier WHERE s_acctbal > 9000);");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryD3(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryD3(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryD3(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryD3(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryD3(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryD3(), results);

        return ResponseEntity.ok(results);
    }

    // E) Result Modification

    @GetMapping(value = "/e1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryE1() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "E1) Non-Indexed Columns Sorting");
        results.put("description", "SELECT c_name, c_address, c_acctbal FROM customer ORDER BY c_acctbal DESC LIMIT 10;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryE1(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryE1(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryE1(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryE1(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryE1(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryE1(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/e2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryE2() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "E2) Indexed Columns Sorting");
        results.put("description", "SELECT o_orderkey, o_custkey, o_orderdate, o_totalprice FROM orders ORDER BY o_orderkey LIMIT 20;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryE2(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryE2(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryE2(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryE2(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryE2(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryE2(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/e3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryE3() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "E3) Distinct");
        results.put("description", "SELECT DISTINCT c_nationkey, c_mktsegment FROM customer;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryE3(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryE3(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryE3(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryE3(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryE3(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryE3(), results);

        return ResponseEntity.ok(results);
    }

    // F) Advanced Queries

    @GetMapping(value = "/f1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryF1() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "F1) Subquery with Aggregation");
        results.put("description", "SELECT c.c_custkey, c.c_name, c.c_acctbal, COUNT(o.o_orderkey) as order_count FROM customer c JOIN orders o ON c.c_custkey = o.o_custkey WHERE c.c_acctbal > (SELECT AVG(c_acctbal) FROM customer) GROUP BY c.c_custkey, c.c_name, c.c_acctbal ORDER BY order_count DESC LIMIT 10;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryF1(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryF1(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryF1(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryF1(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryF1(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryF1(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/f2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryF2() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "F2) GROUP BY with HAVING");
        results.put("description", "SELECT n.n_name as nation, COUNT(c.c_custkey) as customer_count, AVG(c.c_acctbal) as avg_balance FROM customer c JOIN nation n ON c.c_nationkey = n.n_nationkey GROUP BY n.n_name HAVING COUNT(c.c_custkey) > 5 ORDER BY customer_count DESC;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryF2(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryF2(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryF2(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryF2(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryF2(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryF2(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/f3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryF3() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "F3) Window Functions");
        results.put("description", "SELECT o.o_orderkey, o.o_custkey, o.o_totalprice, RANK() OVER (PARTITION BY o.o_custkey ORDER BY o.o_totalprice DESC) as price_rank FROM orders o WHERE o.o_orderdate BETWEEN '1996-01-01' AND '1996-12-31' LIMIT 20;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryF3(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryF3(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryF3(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryF3(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryF3(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryF3(), results);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/f4", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryF4() {
        Map<String, Object> results = new HashMap<>();
        results.put("query", "F4) Complex Business Query");
        results.put("description", "SELECT n.n_name as nation, YEAR(o.o_orderdate) as order_year, SUM(l.l_extendedprice * (1 - l.l_discount)) as revenue FROM customer c JOIN orders o ON c.c_custkey = o.o_custkey JOIN lineitem l ON o.o_orderkey = l.l_orderkey JOIN nation n ON c.c_nationkey = n.n_nationkey JOIN region r ON n.n_regionkey = r.r_regionkey WHERE r.r_name = 'EUROPE' AND o.o_orderdate BETWEEN '1995-01-01' AND '1996-12-31' GROUP BY nation, order_year ORDER BY nation, order_year;");

        executeQueryWithTiming("myBatis", () -> myBatisService.executeQueryF4(), results);
        executeQueryWithTiming("springDataJpa", () -> springDataJpaService.executeQueryF4(), results);
        executeQueryWithTiming("cayenne", () -> cayenneService.executeQueryF4(), results);
        executeQueryWithTiming("ebean", () -> ebeanService.executeQueryF4(), results);
        executeQueryWithTiming("jdbc", () -> jdbcService.executeQueryF4(), results);
        executeQueryWithTiming("jooq", () -> jooqService.executeQueryF4(), results);

        return ResponseEntity.ok(results);
    }

    /**
     * Helper method to execute a query and extract its execution time and memory usage from the response
     * 
     * @param serviceName The name of the service executing the query
     * @param queryExecutor A lambda that executes the query
     * @param results The map to store the results
     */
    private void executeQueryWithTiming(String serviceName, QueryExecutor queryExecutor, Map<String, Object> results) {
        Map<String, Object> serviceResults = new HashMap<>();

        try {
            // Execute the query
            String responseStr = queryExecutor.execute();

            // Parse the response
            // If the response is in JSON format and contains executionTime and memoryUsage, use those values
            // Otherwise, use default values
            try {
                // Try to parse the response as JSON
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                Map<String, Object> responseMap = mapper.readValue(responseStr, Map.class);

                // Extract execution time and memory usage if available
                if (responseMap.containsKey("executionTime")) {
                    serviceResults.put("executionTime", responseMap.get("executionTime"));
                }

                if (responseMap.containsKey("memoryUsage")) {
                    serviceResults.put("memoryUsage", responseMap.get("memoryUsage"));
                }

                // Extract the response data
                if (responseMap.containsKey("pricingSummary") || responseMap.containsKey("result")) {
                    Object responseData = responseMap.containsKey("pricingSummary") ? 
                                         responseMap.get("pricingSummary") : responseMap.get("result");
                    serviceResults.put("response", responseData);
                } else {
                    // If no specific data field is found, use the whole response
                    serviceResults.put("response", responseMap);
                }

                serviceResults.put("status", responseMap.containsKey("status") ? 
                                  responseMap.get("status") : "success");
            } catch (Exception e) {
                // If parsing fails, use the raw response
                serviceResults.put("response", responseStr);
                serviceResults.put("status", "success");
            }
        } catch (Exception e) {
            serviceResults.put("status", "error");
            serviceResults.put("error", e.getMessage());
        }

        results.put(serviceName, serviceResults);
    }

    /**
     * Functional interface for executing a query
     */
    @FunctionalInterface
    private interface QueryExecutor {
        String execute() throws Exception;
    }
}
