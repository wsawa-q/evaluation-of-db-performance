package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.dto.QueryResult;
import io.ebean.Database;
import io.ebean.SqlRow;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UniversalRepository {
    private final Database database;

    public UniversalRepository(Database database) {
        this.database = database;
    }

    // A1) Non-Indexed Columns
    public List<SqlRow> a1() {
        String sql = "SELECT * FROM lineitem";
        return database.sqlQuery(sql).findList();
    }

    // A2) Non-Indexed Columns — Range Query
    public List<QueryResult> a2(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM orders WHERE o_orderdate BETWEEN :startDate AND :endDate";
        return database.sqlQuery(sql)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .findList()
                .stream()
                .map(row -> {
                    Map<String, Object> data = new HashMap<>();
                    for (String col : row.keySet()) {
                        data.put(col, row.get(col));
                    }
                    return new QueryResult(data);
                })
                .toList();
    }

    // A3) Indexed Columns
    public List<QueryResult> a3() {
        String sql = "SELECT * FROM customer";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // A4) Indexed Columns — Range Query
    public List<QueryResult> a4(int minOrderKey, int maxOrderKey) {
        String sql = "SELECT * FROM orders WHERE o_orderkey BETWEEN :minOrderKey AND :maxOrderKey";
        return database.sqlQuery(sql)
                .setParameter("minOrderKey", minOrderKey)
                .setParameter("maxOrderKey", maxOrderKey)
                .findList()
                .stream()
                .map(row -> {
                    Map<String, Object> data = new HashMap<>();
                    for (String col : row.keySet()) {
                        data.put(col, row.get(col));
                    }
                    return new QueryResult(data);
                })
                .toList();
    }

    // B1) COUNT
    public List<QueryResult> b1() {
        String sql = "SELECT COUNT(o.o_orderkey) AS order_count, " +
                "DATE_FORMAT(o.o_orderdate, '%Y-%m') AS order_month " +
                "FROM orders o " +
                "GROUP BY order_month";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // B2) MAX
    public List<QueryResult> b2() {
        String sql = "SELECT DATE_FORMAT(l.l_shipdate, '%Y-%m') AS ship_month, " +
                "MAX(l.l_extendedprice) AS max_price " +
                "FROM lineitem l " +
                "GROUP BY ship_month";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // C1) Non-Indexed Columns
    public List<QueryResult> c1() {
        String sql = "SELECT c.c_name, o.o_orderdate, o.o_totalprice " +
                "FROM customer c, orders o";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // C2) Indexed Columns
    public List<QueryResult> c2() {
        String sql = "SELECT c.c_name, o.o_orderdate, o.o_totalprice " +
                "FROM customer c " +
                "JOIN orders o ON c.c_custkey = o.o_custkey";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // C3) Complex Join 1
    public List<QueryResult> c3() {
        String sql = "SELECT c.c_name, n.n_name, o.o_orderdate, o.o_totalprice " +
                "FROM customer c " +
                "JOIN nation n ON c.c_nationkey = n.n_nationkey " +
                "JOIN orders o ON c.c_custkey = o.o_custkey";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // C4) Complex Join 2
    public List<QueryResult> c4() {
        String sql = "SELECT c.c_name, n.n_name, r.r_name, o.o_orderdate, o.o_totalprice " +
                "FROM customer c " +
                "JOIN nation n ON c.c_nationkey = n.n_nationkey " +
                "JOIN region r ON n.n_regionkey = r.r_regionkey " +
                "JOIN orders o ON c.c_custkey = o.o_custkey";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // C5) Left Outer Join
    public List<QueryResult> c5() {
        String sql = "SELECT c.c_custkey, c.c_name, o.o_orderkey, o.o_orderdate " +
                "FROM customer c " +
                "LEFT OUTER JOIN orders o ON c.c_custkey = o.o_custkey";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // D1) UNION
    public List<QueryResult> d1() {
        String sql = "(SELECT c_nationkey AS nationkey FROM customer) " +
                "UNION " +
                "(SELECT s_nationkey AS nationkey FROM supplier)";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // D2) INTERSECT
    public List<QueryResult> d2() {
        String sql = "SELECT DISTINCT c.c_custkey AS custkey " +
                "FROM customer c " +
                "WHERE c.c_custkey IN (SELECT s.s_suppkey FROM supplier s)";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // D3) DIFFERENCE
    public List<QueryResult> d3() {
        String sql = "SELECT DISTINCT c.c_custkey AS custkey " +
                "FROM customer c " +
                "WHERE c.c_custkey NOT IN (SELECT DISTINCT s.s_suppkey FROM supplier s)";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // E1) Non-Indexed Columns Sorting
    public List<QueryResult> e1() {
        String sql = "SELECT c_name, c_address, c_acctbal " +
                "FROM customer " +
                "ORDER BY c_acctbal DESC";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // E2) Indexed Columns Sorting
    public List<QueryResult> e2() {
        String sql = "SELECT o_orderkey, o_custkey, o_orderdate, o_totalprice " +
                "FROM orders " +
                "ORDER BY o_orderkey";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // E3) Distinct
    public List<QueryResult> e3() {
        String sql = "SELECT DISTINCT c_nationkey, c_mktsegment " +
                "FROM customer";
        return executeQueryAndConvertToQueryResults(sql);
    }

    // Helper method to execute a query and convert the results to QueryResult objects
    private List<QueryResult> executeQueryAndConvertToQueryResults(String sql) {
        return database.sqlQuery(sql)
                .findList()
                .stream()
                .map(row -> {
                    Map<String, Object> data = new HashMap<>();
                    for (String col : row.keySet()) {
                        data.put(col, row.get(col));
                    }
                    return new QueryResult(data);
                })
                .toList();
    }

    public List<Map<String, Object>> q1(int days) {
        LocalDate cutoff = LocalDate.of(1998, 12, 1).minusDays(days);

        String sql =
            "SELECT l_returnflag, l_linestatus, " +
                "SUM(l_quantity)    AS sum_qty, " +
                "SUM(l_extendedprice) AS sum_base_price, " +
                "SUM(l_extendedprice * (1 - l_discount)) AS sum_disc_price, " +
                "SUM(l_extendedprice * (1 - l_discount) * (1 + l_tax)) AS sum_charge, " +
                "AVG(l_quantity)    AS avg_qty, " +
                "AVG(l_extendedprice) AS avg_price, " +
                "AVG(l_discount)    AS avg_disc, " +
                "COUNT(*)           AS count_order " +
                "FROM lineitem " +
                "WHERE l_shipdate <= :cutoff " +
                "GROUP BY l_returnflag, l_linestatus " +
                "ORDER BY l_returnflag, l_linestatus";

        return database.sqlQuery(sql)
                .setParameter("cutoff", cutoff)
                .findList()
                .stream()
                .map(row -> Map.of(
                        "l_returnflag", row.get("l_returnflag"),
                        "l_linestatus", row.get("l_linestatus"),
                        "sum_qty", row.get("sum_qty"),
                        "sum_base_price", row.get("sum_base_price"),
                        "sum_disc_price", row.get("sum_disc_price"),
                        "sum_charge", row.get("sum_charge"),
                        "avg_qty", row.get("avg_qty"),
                        "avg_price", row.get("avg_price"),
                        "avg_disc", row.get("avg_disc"),
                        "count_order", row.get("count_order")
                ))
                .toList();
    }

    public List<Map<String, Object>> q2(int size, String type, String region) {
        String sql = "SELECT " +
                "s.s_acctbal, " +
                "s.s_name, " +
                "n.n_name, " +
                "p.p_partkey, " +
                "p.p_mfgr, " +
                "s.s_address, " +
                "s.s_phone, " +
                "s.s_comment " +
                "FROM " +
                "part p, " +
                "supplier s, " +
                "partsupp ps, " +
                "nation n, " +
                "region r " +
                "WHERE " +
                "p.p_partkey = ps.ps_partkey " +
                "AND s.s_suppkey = ps.ps_suppkey " +
                "AND p.p_size = :size " +
                "AND p.p_type LIKE :type " +
                "AND s.s_nationkey = n.n_nationkey " +
                "AND n.n_regionkey = r.r_regionkey " +
                "AND r.r_name = :region " +
                "AND ps.ps_supplycost = ( " +
                "    SELECT MIN(ps.ps_supplycost) " +
                "    FROM " +
                "        partsupp ps, " +
                "        supplier s, " +
                "        nation n, " +
                "        region r " +
                "    WHERE " +
                "        p.p_partkey = ps.ps_partkey " +
                "        AND s.s_suppkey = ps.ps_suppkey " +
                "        AND s.s_nationkey = n.n_nationkey " +
                "        AND n.n_regionkey = r.r_regionkey " +
                "        AND r.r_name = :region " +
                ") " +
                "ORDER BY " +
                "s.s_acctbal DESC, " +
                "n.n_name, " +
                "s.s_name, " +
                "p.p_partkey " +
                "LIMIT 100";

        return database.sqlQuery(sql)
                .setParameter("size", size)
                .setParameter("type", type)
                .setParameter("region", region)
                .findList()
                .stream()
                .map(row -> Map.of(
                        "s_acctbal", row.get("s_acctbal"),
                        "s_name", row.get("s_name"),
                        "n_name", row.get("n_name"),
                        "p_partkey", row.get("p_partkey"),
                        "p_mfgr", row.get("p_mfgr"),
                        "s_address", row.get("s_address"),
                        "s_phone", row.get("s_phone"),
                        "s_comment", row.get("s_comment")
                ))
                .toList();
    }

    public List<Map<String, Object>> q3(String segment, LocalDate orderDate, LocalDate shipDate) {
        String sql = "SELECT " +
                "l.l_orderkey, " +
                "SUM(l.l_extendedprice * (1 - l.l_discount)) AS revenue, " +
                "o.o_orderdate, " +
                "o.o_shippriority " +
                "FROM " +
                "customer c, " +
                "orders o, " +
                "lineitem l " +
                "WHERE " +
                "c.c_mktsegment = :segment " +
                "AND c.c_custkey = o.o_custkey " +
                "AND l.l_orderkey = o.o_orderkey " +
                "AND o.o_orderdate < :orderDate " +
                "AND l.l_shipdate > :shipDate " +
                "GROUP BY " +
                "l.l_orderkey, " +
                "o.o_orderdate, " +
                "o.o_shippriority " +
                "ORDER BY " +
                "revenue DESC, " +
                "o.o_orderdate " +
                "LIMIT 10";

        return database.sqlQuery(sql)
                .setParameter("segment", segment)
                .setParameter("orderDate", orderDate)
                .setParameter("shipDate", shipDate)
                .findList()
                .stream()
                .map(row -> Map.of(
                        "l_orderkey", row.get("l_orderkey"),
                        "revenue", row.get("revenue"),
                        "o_orderdate", row.get("o_orderdate"),
                        "o_shippriority", row.get("o_shippriority")
                ))
                .toList();
    }

    public List<Map<String, Object>> q4(LocalDate orderDate) {
        LocalDate endDate = orderDate.plusMonths(3);

        String sql = "SELECT " +
                "o_orderpriority, " +
                "COUNT(*) AS order_count " +
                "FROM " +
                "orders " +
                "WHERE " +
                "o_orderdate >= :orderDate " +
                "AND o_orderdate < :endDate " +
                "AND EXISTS ( " +
                "    SELECT * " +
                "    FROM " +
                "        lineitem " +
                "    WHERE " +
                "        l_orderkey = o_orderkey " +
                "        AND l_commitdate < l_receiptdate " +
                ") " +
                "GROUP BY " +
                "o_orderpriority " +
                "ORDER BY " +
                "o_orderpriority";

        return database.sqlQuery(sql)
                .setParameter("orderDate", orderDate)
                .setParameter("endDate", endDate)
                .findList()
                .stream()
                .map(row -> Map.of(
                        "o_orderpriority", row.get("o_orderpriority"),
                        "order_count", row.get("order_count")
                ))
                .toList();
    }

    public List<Map<String, Object>> q5(String region, LocalDate orderDate) {
        LocalDate endDate = orderDate.plusYears(1);

        String sql = "SELECT " +
                "n.n_name, " +
                "SUM(l.l_extendedprice * (1 - l.l_discount)) AS revenue " +
                "FROM " +
                "customer c, " +
                "orders o, " +
                "lineitem l, " +
                "supplier s, " +
                "nation n, " +
                "region r " +
                "WHERE " +
                "c.c_custkey = o.o_custkey " +
                "AND l.l_orderkey = o.o_orderkey " +
                "AND l.l_suppkey = s.s_suppkey " +
                "AND c.c_nationkey = s.s_nationkey " +
                "AND s.s_nationkey = n.n_nationkey " +
                "AND n.n_regionkey = r.r_regionkey " +
                "AND r.r_name = :region " +
                "AND o.o_orderdate >= :orderDate " +
                "AND o.o_orderdate < :endDate " +
                "GROUP BY " +
                "n.n_name " +
                "ORDER BY " +
                "revenue DESC";

        return database.sqlQuery(sql)
                .setParameter("region", region)
                .setParameter("orderDate", orderDate)
                .setParameter("endDate", endDate)
                .findList()
                .stream()
                .map(row -> Map.of(
                        "n_name", row.get("n_name"),
                        "revenue", row.get("revenue")
                ))
                .toList();
    }
}
