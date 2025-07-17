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

import java.util.*;
import java.util.stream.Collectors;

import static cz.cuni.mff.java.kurinna.microservice.utils.Utils.ALL_SERVICES;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorController {
    private final MyBatisService myBatisService;
    private final SpringDataJpaService springDataJpaService;
    private final CayenneService cayenneService;
    private final EbeanService ebeanService;
    private final JdbcService jdbcService;
    private final JooqService jooqService;

    public OrchestratorController(MyBatisService myBatisService, SpringDataJpaService springDataJpaService,
            CayenneService cayenneService, EbeanService ebeanService, JdbcService jdbcService,
            JooqService jooqService) {
        this.myBatisService = myBatisService;
        this.springDataJpaService = springDataJpaService;
        this.cayenneService = cayenneService;
        this.ebeanService = ebeanService;
        this.jdbcService = jdbcService;
        this.jooqService = jooqService;
    }

    private Set<String> parseServices(Optional<String> servicesOpt) {
        List<String> allServicesList = Arrays.asList(ALL_SERVICES);
        return servicesOpt
                .map(s -> Arrays.stream(s.split(","))
                        .map(String::trim)
                        .filter(allServicesList::contains)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .orElse(new LinkedHashSet<>(allServicesList));
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }

    @GetMapping(value = "/q1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getPricingSummary(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "Q1) Pricing Summary Report Query",
                "TPC-H Q1 query that reports pricing summary for all items shipped before a given date.");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::getPricingSummary,
                springDataJpaService::getPricingSummary,
                cayenneService::getPricingSummary,
                ebeanService::getPricingSummary,
                jdbcService::getPricingSummary,
                jooqService::getPricingSummary);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/q2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getMinimumCostSupplier(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "Q2) Minimum Cost Supplier Query",
                "TPC-H Q2 query that finds suppliers who can supply parts of a given type and size at minimum cost.");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::getMinimumCostSupplier,
                springDataJpaService::getMinimumCostSupplier,
                cayenneService::getMinimumCostSupplier,
                ebeanService::getMinimumCostSupplier,
                jdbcService::getMinimumCostSupplier,
                jooqService::getMinimumCostSupplier);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/q3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getShippingPriority(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "Q3) Shipping Priority Query",
                "TPC-H Q3 query that retrieves the shipping priority and potential revenue of orders.");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::getShippingPriority,
                springDataJpaService::getShippingPriority,
                cayenneService::getShippingPriority,
                ebeanService::getShippingPriority,
                jdbcService::getShippingPriority,
                jooqService::getShippingPriority);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/q4", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getOrderPriorityChecking(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "Q4) Order Priority Checking Query",
                "TPC-H Q4 query that counts orders with at least one lineitem that was received later than committed.");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::getOrderPriorityChecking,
                springDataJpaService::getOrderPriorityChecking,
                cayenneService::getOrderPriorityChecking,
                ebeanService::getOrderPriorityChecking,
                jdbcService::getOrderPriorityChecking,
                jooqService::getOrderPriorityChecking);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/q5", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getLocalSupplierVolume(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "Q5) Local Supplier Volume Query",
                "TPC-H Q5 query that lists the revenue volume for each nation in a region where suppliers and customers are from the same nation.");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::getLocalSupplierVolume,
                springDataJpaService::getLocalSupplierVolume,
                cayenneService::getLocalSupplierVolume,
                ebeanService::getLocalSupplierVolume,
                jdbcService::getLocalSupplierVolume,
                jooqService::getLocalSupplierVolume);

        return ResponseEntity.ok(results);
    }

    // A) Selection, Projection, Source (of data)

    @GetMapping(value = "/a1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryA1(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "A1) Non-Indexed Columns",
                "SELECT * FROM lineitem");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryA1,
                springDataJpaService::executeQueryA1,
                cayenneService::executeQueryA1,
                ebeanService::executeQueryA1,
                jdbcService::executeQueryA1,
                jooqService::executeQueryA1);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/a2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryA2(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "A2) Non-Indexed Columns — Range Query",
                "SELECT * FROM orders WHERE o_orderdate BETWEEN '1996-01-01' AND '1996-12-31';");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryA2,
                springDataJpaService::executeQueryA2,
                cayenneService::executeQueryA2,
                ebeanService::executeQueryA2,
                jdbcService::executeQueryA2,
                jooqService::executeQueryA2);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/a3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryA3(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "A3) Indexed Columns",
                "SELECT * FROM customer");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryA3,
                springDataJpaService::executeQueryA3,
                cayenneService::executeQueryA3,
                ebeanService::executeQueryA3,
                jdbcService::executeQueryA3,
                jooqService::executeQueryA3);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/a4", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryA4(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "A4) Indexed Columns — Range Query",
                "SELECT * FROM orders WHERE o_orderkey BETWEEN 1000 AND 2000;");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryA4,
                springDataJpaService::executeQueryA4,
                cayenneService::executeQueryA4,
                ebeanService::executeQueryA4,
                jdbcService::executeQueryA4,
                jooqService::executeQueryA4);

        return ResponseEntity.ok(results);
    }

    // B) Aggregation

    @GetMapping(value = "/b1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryB1(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "B1) COUNT",
                "SELECT COUNT(*) AS order_count FROM orders WHERE o_orderdate BETWEEN '1996-01-01' AND '1996-12-31';");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryB1,
                springDataJpaService::executeQueryB1,
                cayenneService::executeQueryB1,
                ebeanService::executeQueryB1,
                jdbcService::executeQueryB1,
                jooqService::executeQueryB1);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/b2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryB2(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "B2) MAX",
                "SELECT MAX(l_extendedprice) AS max_price FROM lineitem;");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryB2,
                springDataJpaService::executeQueryB2,
                cayenneService::executeQueryB2,
                ebeanService::executeQueryB2,
                jdbcService::executeQueryB2,
                jooqService::executeQueryB2);

        return ResponseEntity.ok(results);
    }

    // C) Joins

    @GetMapping(value = "/c1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryC1(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "C1) Non-Indexed Columns",
                "SELECT c.c_name, o.o_orderdate, o.o_totalprice FROM customer c, orders o WHERE c.c_mktsegment = 'BUILDING' AND c.c_custkey = o.o_custkey;");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryC1,
                springDataJpaService::executeQueryC1,
                cayenneService::executeQueryC1,
                ebeanService::executeQueryC1,
                jdbcService::executeQueryC1,
                jooqService::executeQueryC1);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/c2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryC2(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "C2) Indexed Columns",
                "SELECT c.c_name, o.o_orderdate, o.o_totalprice FROM customer c JOIN orders o ON c.c_custkey = o.o_custkey WHERE o.o_orderkey BETWEEN 1000 AND 2000;");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryC2,
                springDataJpaService::executeQueryC2,
                cayenneService::executeQueryC2,
                ebeanService::executeQueryC2,
                jdbcService::executeQueryC2,
                jooqService::executeQueryC2);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/c3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryC3(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "C3) Complex Join 1",
                "SELECT c.c_name, n.n_name, o.o_orderdate, o.o_totalprice FROM customer c JOIN nation n ON c.c_nationkey = n.n_nationkey JOIN orders o ON c.c_custkey = o.o_custkey WHERE n.n_name = 'GERMANY' AND o.o_orderdate BETWEEN '1996-01-01' AND '1996-12-31';");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryC3,
                springDataJpaService::executeQueryC3,
                cayenneService::executeQueryC3,
                ebeanService::executeQueryC3,
                jdbcService::executeQueryC3,
                jooqService::executeQueryC3);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/c4", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryC4(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "C4) Complex Join 2",
                "SELECT c.c_name, n.n_name, r.r_name, o.o_orderdate, o.o_totalprice FROM customer c JOIN nation n ON c.c_nationkey = n.n_nationkey JOIN region r ON n.n_regionkey = r.r_regionkey JOIN orders o ON c.c_custkey = o.o_custkey WHERE r.r_name = 'EUROPE' AND o.o_orderdate BETWEEN '1996-01-01' AND '1996-12-31';");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryC4,
                springDataJpaService::executeQueryC4,
                cayenneService::executeQueryC4,
                ebeanService::executeQueryC4,
                jdbcService::executeQueryC4,
                jooqService::executeQueryC4);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/c5", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryC5(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "C5) Left Outer Join",
                "SELECT c.c_custkey, c.c_name, o.o_orderkey, o.o_orderdate FROM customer c LEFT OUTER JOIN orders o ON c.c_custkey = o.o_custkey WHERE c.c_nationkey = 3;");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryC5,
                springDataJpaService::executeQueryC5,
                cayenneService::executeQueryC5,
                ebeanService::executeQueryC5,
                jdbcService::executeQueryC5,
                jooqService::executeQueryC5);

        return ResponseEntity.ok(results);
    }

    // D) Set operations

    @GetMapping(value = "/d1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryD1(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "D1) UNION",
                "(SELECT c_nationkey FROM customer WHERE c_acctbal > 9000) UNION (SELECT s_nationkey FROM supplier;");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryD1,
                springDataJpaService::executeQueryD1,
                cayenneService::executeQueryD1,
                ebeanService::executeQueryD1,
                jdbcService::executeQueryD1,
                jooqService::executeQueryD1);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/d2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryD2(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "D2) INTERSECT",
                "SELECT DISTINCT c_nationkey FROM customer WHERE c_acctbal > 9000 AND c_nationkey IN (SELECT s_nationkey FROM supplier;");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryD2,
                springDataJpaService::executeQueryD2,
                cayenneService::executeQueryD2,
                ebeanService::executeQueryD2,
                jdbcService::executeQueryD2,
                jooqService::executeQueryD2);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/d3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryD3(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "D3) DIFFERENCE",
                "SELECT DISTINCT c_nationkey FROM customer WHERE c_acctbal > 9000 AND c_nationkey NOT IN (SELECT s_nationkey FROM supplier;");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryD3,
                springDataJpaService::executeQueryD3,
                cayenneService::executeQueryD3,
                ebeanService::executeQueryD3,
                jdbcService::executeQueryD3,
                jooqService::executeQueryD3);

        return ResponseEntity.ok(results);
    }

    // E) Result Modification
    @GetMapping(value = "/e1", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryE1(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "E1) Non-Indexed Columns Sorting",
                "SELECT c_name, c_address, c_acctbal FROM customer ORDER BY c_acctbal DESC;");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryE1,
                springDataJpaService::executeQueryE1,
                cayenneService::executeQueryE1,
                ebeanService::executeQueryE1,
                jdbcService::executeQueryE1,
                jooqService::executeQueryE1);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/e2", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryE2(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "E2) Indexed Columns Sorting",
                "SELECT o_orderkey, o_custkey, o_orderdate, o_totalprice FROM orders ORDER BY o_orderkey;");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryE2,
                springDataJpaService::executeQueryE2,
                cayenneService::executeQueryE2,
                ebeanService::executeQueryE2,
                jdbcService::executeQueryE2,
                jooqService::executeQueryE2);

        return ResponseEntity.ok(results);
    }

    @GetMapping(value = "/e3", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> executeQueryE3(
            @RequestParam Optional<String> repetitions,
            @RequestParam Optional<String> services) {
        Set<String> selected = parseServices(services);
        int rep = parseRepetitions(repetitions);
        Map<String, Object> results = createResultsMap(
                "E3) Distinct",
                "SELECT DISTINCT c_nationkey, c_mktsegment FROM customer;");

        executeQueriesAcrossAllServices(
                results,
                rep,
                selected,
                myBatisService::executeQueryE3,
                springDataJpaService::executeQueryE3,
                cayenneService::executeQueryE3,
                ebeanService::executeQueryE3,
                jdbcService::executeQueryE3,
                jooqService::executeQueryE3);

        return ResponseEntity.ok(results);
    }

    /**
     * Helper method to parse the repetitions parameter
     *
     * @param repetitions Optional parameter for number of repetitions
     * @return The parsed number of repetitions, defaulting to 1 if not provided or
     *         invalid
     */
    private int parseRepetitions(Optional<String> repetitions) {
        int rep = 1;
        try {
            rep = Integer.parseInt(repetitions.orElse("1"));
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }
        return rep;
    }

    /**
     * Helper method to create a results map with query info
     *
     * @param queryName   The name of the query
     * @param description The description of the query
     * @return A map containing the query info
     */
    private Map<String, Object> createResultsMap(String queryName, String description) {
        Map<String, Object> results = new LinkedHashMap<>();
        results.put("query", queryName);
        results.put("description", description);
        return results;
    }

    /**
     * Helper method to execute queries across all services
     *
     * @param results            The map to store the results
     * @param repetitions        The number of times to repeat the query execution
     * @param myBatisQuery       The MyBatis query executor
     * @param springDataJpaQuery The SpringDataJpa query executor
     * @param cayenneQuery       The Cayenne query executor
     * @param ebeanQuery         The Ebean query executor
     * @param jdbcQuery          The JDBC query executor
     * @param jooqQuery          The JOOQ query executor
     */
    private void executeQueriesAcrossAllServices(
            Map<String, Object> results,
            int repetitions,
            Set<String> services,
            QueryExecutor myBatisQuery,
            QueryExecutor springDataJpaQuery,
            QueryExecutor cayenneQuery,
            QueryExecutor ebeanQuery,
            QueryExecutor jdbcQuery,
            QueryExecutor jooqQuery) {

        if (services.contains("myBatis")) {
            executeQueryWithTiming("myBatis", myBatisQuery, results, repetitions);
        }
        if (services.contains("springDataJpa")) {
            executeQueryWithTiming("springDataJpa", springDataJpaQuery, results, repetitions);
        }
        if (services.contains("cayenne")) {
            executeQueryWithTiming("cayenne", cayenneQuery, results, repetitions);
        }
        if (services.contains("ebean")) {
            executeQueryWithTiming("ebean", ebeanQuery, results, repetitions);
        }
        if (services.contains("jdbc")) {
            executeQueryWithTiming("jdbc", jdbcQuery, results, repetitions);
        }
        if (services.contains("jooq")) {
            executeQueryWithTiming("jooq", jooqQuery, results, repetitions);
        }
    }

    /**
     * Helper method to execute a query and extract its execution time and memory
     * usage from the response
     *
     * @param serviceName   The name of the service executing the query
     * @param queryExecutor A lambda that executes the query
     * @param results       The map to store the results
     * @param repetitions   The number of times to repeat the query execution
     */
    private void executeQueryWithTiming(String serviceName, QueryExecutor queryExecutor, Map<String, Object> results,
            int repetitions) {
        Map<String, Object> serviceResults = new LinkedHashMap<>();
        double totalTime = 0.0;
        double totalMemory = 0.0;
        double maxMemory = 0.0;
        double minMemory = 0.0;
        double maxTime = 0.0;
        double minTime = 0.0;
        List<Map<String, Object>> iterationResultsList = new ArrayList<>();

        for (int i = 0; i < repetitions; i++) {
            try {
                Map<String, Object> response = queryExecutor.execute();
                try {
                    if (response.containsKey("elapsed")) {
                        String elapsedStr = response.get("elapsed").toString();
                        double executionTime = Double.parseDouble(elapsedStr.trim());
                        totalTime += executionTime;

                        if (executionTime > maxTime) {
                            maxTime = executionTime;
                        }
                        if (executionTime < minTime || minTime == 0.0) {
                            minTime = executionTime;
                        }
                    }

                    if (response.containsKey("delta")) {
                        String memoryUsageStr = response.get("delta").toString();
                        double memoryUsage = Double.parseDouble(memoryUsageStr.trim());
                        totalMemory += memoryUsage;

                        if (memoryUsage > maxMemory) {
                            maxMemory = memoryUsage;
                        }
                        if (memoryUsage < minMemory || minMemory == 0.0) {
                            minMemory = memoryUsage;
                        }
                    } else {
                        serviceResults.put("delta", 0.0);
                    }

                    serviceResults.put("status", response.getOrDefault("status", "success"));
                    iterationResultsList.add(response);
                } catch (Exception e) {
                    serviceResults.put("response", response);
                    serviceResults.put("status", "success");
                }
            } catch (Exception e) {
                serviceResults.put("status", "error");
                serviceResults.put("error", e.getMessage());
            }
        }

        serviceResults.put("repetition", repetitions);

        double averageTime = totalTime / repetitions;
        serviceResults.put("averageExecutionTime", averageTime);

        double averageMemory = totalMemory / repetitions;
        serviceResults.put("averageMemoryUsage", averageMemory);

        serviceResults.put("maxExecutionTime", maxTime);
        serviceResults.put("minExecutionTime", minTime);
        serviceResults.put("maxMemoryUsage", maxMemory);
        serviceResults.put("minMemoryUsage", minMemory);
        serviceResults.put("iterationResults", iterationResultsList);

        results.put(serviceName, serviceResults);
    }

    /**
     * Functional interface for executing a query
     */
    @FunctionalInterface
    private interface QueryExecutor {
        Map<String, Object> execute() throws Exception;
    }
}
