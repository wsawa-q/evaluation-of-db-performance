package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.dto.QueryResult;
import org.apache.cayenne.DataRow;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SQLSelect;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UniversalRepository {
    // Helper method to convert DataRow to QueryResult
    private List<QueryResult> convertToQueryResults(List<DataRow> dataRows) {
        List<QueryResult> queryResults = new ArrayList<>();
        for (DataRow dataRow : dataRows) {
            Map<String, Object> data = new HashMap<>(dataRow);
            queryResults.add(new QueryResult(data));
        }
        return queryResults;
    }

    // A1) Non-Indexed Columns
    public List<QueryResult> a1(ObjectContext context) {
        String sql = "SELECT * FROM lineitem";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // A2) Non-Indexed Columns — Range Query
    public List<QueryResult> a2(ObjectContext context, LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM orders WHERE o_orderdate BETWEEN #bind($startDate) AND #bind($endDate)";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("startDate", Date.valueOf(startDate));
        parameters.put("endDate", Date.valueOf(endDate));
        query.params(parameters);

        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // A3) Indexed Columns
    public List<QueryResult> a3(ObjectContext context) {
        String sql = "SELECT * FROM customer";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // A4) Indexed Columns — Range Query
    public List<QueryResult> a4(ObjectContext context, int minOrderKey, int maxOrderKey) {
        String sql = "SELECT * FROM orders WHERE o_orderkey BETWEEN #bind($minOrderKey) AND #bind($maxOrderKey)";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("minOrderKey", minOrderKey);
        parameters.put("maxOrderKey", maxOrderKey);
        query.params(parameters);

        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // B1) COUNT
    public List<QueryResult> b1(ObjectContext context) {
        String sql = "SELECT COUNT(o.o_orderkey) AS order_count, " +
                "DATE_FORMAT(o.o_orderdate, '%Y-%m') AS order_month " +
                "FROM orders o " +
                "GROUP BY order_month";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // B2) MAX
    public List<QueryResult> b2(ObjectContext context) {
        String sql = "SELECT DATE_FORMAT(l.l_shipdate, '%Y-%m') AS ship_month, " +
                "MAX(l.l_extendedprice) AS max_price " +
                "FROM lineitem l " +
                "GROUP BY ship_month";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // C1) Non-Indexed Columns
    public List<QueryResult> c1(ObjectContext context) {
        String sql = "SELECT c.c_name, o.o_orderdate, o.o_totalprice " +
                "FROM customer c, orders o";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // C2) Indexed Columns
    public List<QueryResult> c2(ObjectContext context) {
        String sql = "SELECT c.c_name, o.o_orderdate, o.o_totalprice " +
                "FROM customer c " +
                "JOIN orders o ON c.c_custkey = o.o_custkey";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // C3) Complex Join 1
    public List<QueryResult> c3(ObjectContext context) {
        String sql = "SELECT c.c_name, n.n_name, o.o_orderdate, o.o_totalprice " +
                "FROM customer c " +
                "JOIN nation n ON c.c_nationkey = n.n_nationkey " +
                "JOIN orders o ON c.c_custkey = o.o_custkey";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // C4) Complex Join 2
    public List<QueryResult> c4(ObjectContext context) {
        String sql = "SELECT c.c_name, n.n_name, r.r_name, o.o_orderdate, o.o_totalprice " +
                "FROM customer c " +
                "JOIN nation n ON c.c_nationkey = n.n_nationkey " +
                "JOIN region r ON n.n_regionkey = r.r_regionkey " +
                "JOIN orders o ON c.c_custkey = o.o_custkey";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // C5) Left Outer Join
    public List<QueryResult> c5(ObjectContext context) {
        String sql = "SELECT c.c_custkey, c.c_name, o.o_orderkey, o.o_orderdate " +
                "FROM customer c " +
                "LEFT OUTER JOIN orders o ON c.c_custkey = o.o_custkey";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // D1) UNION
    public List<QueryResult> d1(ObjectContext context) {
        String sql = "(SELECT c_nationkey AS nationkey FROM customer) " +
                "UNION " +
                "(SELECT s_nationkey AS nationkey FROM supplier)";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // D2) INTERSECT
    public List<QueryResult> d2(ObjectContext context) {
        String sql = "SELECT DISTINCT c.c_custkey AS custkey " +
                "FROM customer c " +
                "WHERE c.c_custkey IN (SELECT s.s_suppkey FROM supplier s)";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // D3) DIFFERENCE
    public List<QueryResult> d3(ObjectContext context) {
        String sql = "SELECT DISTINCT c.c_custkey AS custkey " +
                "FROM customer c " +
                "WHERE c.c_custkey NOT IN (SELECT DISTINCT s.s_suppkey FROM supplier s)";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // E1) Non-Indexed Columns Sorting
    public List<QueryResult> e1(ObjectContext context) {
        String sql = "SELECT c_name, c_address, c_acctbal " +
                "FROM customer " +
                "ORDER BY c_acctbal DESC";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // E2) Indexed Columns Sorting
    public List<QueryResult> e2(ObjectContext context) {
        String sql = "SELECT o_orderkey, o_custkey, o_orderdate, o_totalprice " +
                "FROM orders " +
                "ORDER BY o_orderkey";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }

    // E3) Distinct
    public List<QueryResult> e3(ObjectContext context) {
        String sql = "SELECT DISTINCT c_nationkey, c_mktsegment " +
                "FROM customer";
        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);
        List<DataRow> results = query.select(context);
        return convertToQueryResults(results);
    }
    public List<DataRow> q1(ObjectContext context, int deltaDays) {
        LocalDate baseDate = LocalDate.of(1998, 12, 1);
        LocalDate shipDateThreshold = baseDate.minusDays(deltaDays);

        String sql = "SELECT " +
                "    l_returnflag, " +
                "    l_linestatus, " +
                "    SUM(l_quantity) AS sum_qty, " +
                "    SUM(l_extendedprice) AS sum_base_price, " +
                "    SUM(l_extendedprice * (1 - l_discount)) AS sum_disc_price, " +
                "    SUM(l_extendedprice * (1 - l_discount) * (1 + l_tax)) AS sum_charge, " +
                "    AVG(l_quantity) AS avg_qty, " +
                "    AVG(l_extendedprice) AS avg_price, " +
                "    AVG(l_discount) AS avg_disc, " +
                "    COUNT(*) AS count_order " +
                "FROM " +
                "    lineitem " +
                "WHERE " +
                "    l_shipdate <= #bind($ship_date_threshold) " +
                "GROUP BY " +
                "    l_returnflag, " +
                "    l_linestatus " +
                "ORDER BY " +
                "    l_returnflag, " +
                "    l_linestatus";

        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ship_date_threshold", Date.valueOf(shipDateThreshold));
        query.params(parameters);

        return query.select(context);
    }

    public List<DataRow> q2(ObjectContext context, int size, String type, String region) {
        String sql = "SELECT " +
                "    s.s_acctbal, " +
                "    s.s_name, " +
                "    n.n_name, " +
                "    p.p_partkey, " +
                "    p.p_mfgr, " +
                "    s.s_address, " +
                "    s.s_phone, " +
                "    s.s_comment " +
                "FROM " +
                "    part p, " +
                "    supplier s, " +
                "    partsupp ps, " +
                "    nation n, " +
                "    region r " +
                "WHERE " +
                "    p.p_partkey = ps.ps_partkey " +
                "    AND s.s_suppkey = ps.ps_suppkey " +
                "    AND p.p_size = #bind($size) " +
                "    AND p.p_type LIKE #bind($type) " +
                "    AND s.s_nationkey = n.n_nationkey " +
                "    AND n.n_regionkey = r.r_regionkey " +
                "    AND r.r_name = #bind($region) " +
                "    AND ps.ps_supplycost = ( " +
                "        SELECT MIN(ps.ps_supplycost) " +
                "        FROM " +
                "            partsupp ps, " +
                "            supplier s, " +
                "            nation n, " +
                "            region r " +
                "        WHERE " +
                "            p.p_partkey = ps.ps_partkey " +
                "            AND s.s_suppkey = ps.ps_suppkey " +
                "            AND s.s_nationkey = n.n_nationkey " +
                "            AND n.n_regionkey = r.r_regionkey " +
                "            AND r.r_name = #bind($region) " +
                "    ) " +
                "ORDER BY " +
                "    s.s_acctbal DESC, " +
                "    n.n_name, " +
                "    s.s_name, " +
                "    p.p_partkey ";

        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("size", size);
        parameters.put("type", type);
        parameters.put("region", region);
        query.params(parameters);

        return query.select(context);
    }

    public List<DataRow> q3(ObjectContext context, String segment, LocalDate orderDate, LocalDate shipDate) {
        String sql = "SELECT " +
                "    l.l_orderkey, " +
                "    SUM(l.l_extendedprice * (1 - l.l_discount)) AS revenue, " +
                "    o.o_orderdate, " +
                "    o.o_shippriority " +
                "FROM " +
                "    customer c, " +
                "    orders o, " +
                "    lineitem l " +
                "WHERE " +
                "    c.c_mktsegment = #bind($segment) " +
                "    AND c.c_custkey = o.o_custkey " +
                "    AND l.l_orderkey = o.o_orderkey " +
                "    AND o.o_orderdate < #bind($order_date) " +
                "    AND l.l_shipdate > #bind($ship_date) " +
                "GROUP BY " +
                "    l.l_orderkey, " +
                "    o.o_orderdate, " +
                "    o.o_shippriority " +
                "ORDER BY " +
                "    revenue DESC, " +
                "    o.o_orderdate ";

        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("segment", segment);
        parameters.put("order_date", Date.valueOf(orderDate));
        parameters.put("ship_date", Date.valueOf(shipDate));
        query.params(parameters);

        return query.select(context);
    }

    public List<DataRow> q4(ObjectContext context, LocalDate orderDate) {
        LocalDate endDate = orderDate.plusMonths(3);

        String sql = "SELECT " +
                "    o_orderpriority, " +
                "    COUNT(*) AS order_count " +
                "FROM " +
                "    orders " +
                "WHERE " +
                "    o_orderdate >= #bind($order_date) " +
                "    AND o_orderdate < #bind($end_date) " +
                "    AND EXISTS ( " +
                "        SELECT * " +
                "        FROM " +
                "            lineitem " +
                "        WHERE " +
                "            l_orderkey = o_orderkey " +
                "            AND l_commitdate < l_receiptdate " +
                "    ) " +
                "GROUP BY " +
                "    o_orderpriority " +
                "ORDER BY " +
                "    o_orderpriority";

        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("order_date", Date.valueOf(orderDate));
        parameters.put("end_date", Date.valueOf(endDate));
        query.params(parameters);

        return query.select(context);
    }

    public List<DataRow> q5(ObjectContext context, String region, LocalDate orderDate) {
        LocalDate endDate = orderDate.plusYears(1);

        String sql = "SELECT " +
                "    n.n_name, " +
                "    SUM(l.l_extendedprice * (1 - l.l_discount)) AS revenue " +
                "FROM " +
                "    customer c, " +
                "    orders o, " +
                "    lineitem l, " +
                "    supplier s, " +
                "    nation n, " +
                "    region r " +
                "WHERE " +
                "    c.c_custkey = o.o_custkey " +
                "    AND l.l_orderkey = o.o_orderkey " +
                "    AND l.l_suppkey = s.s_suppkey " +
                "    AND c.c_nationkey = s.s_nationkey " +
                "    AND s.s_nationkey = n.n_nationkey " +
                "    AND n.n_regionkey = r.r_regionkey " +
                "    AND r.r_name = #bind($region) " +
                "    AND o.o_orderdate >= #bind($order_date) " +
                "    AND o.o_orderdate < #bind($end_date) " +
                "GROUP BY " +
                "    n.n_name " +
                "ORDER BY " +
                "    revenue DESC";

        SQLSelect<DataRow> query = SQLSelect.dataRowQuery(sql);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("region", region);
        parameters.put("order_date", Date.valueOf(orderDate));
        parameters.put("end_date", Date.valueOf(endDate));
        query.params(parameters);

        return query.select(context);
    }
}
