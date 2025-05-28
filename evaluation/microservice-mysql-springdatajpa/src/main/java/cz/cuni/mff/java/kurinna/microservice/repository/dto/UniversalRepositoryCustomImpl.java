package cz.cuni.mff.java.kurinna.microservice.repository.dto;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.dto.MinimumCostSupplier;
import cz.cuni.mff.java.kurinna.microservice.dto.ShippingPriority;
import cz.cuni.mff.java.kurinna.microservice.dto.OrderPriorityChecking;
import cz.cuni.mff.java.kurinna.microservice.dto.LocalSupplierVolume;
import cz.cuni.mff.java.kurinna.microservice.dto.QueryResult;
import cz.cuni.mff.java.kurinna.microservice.model.LineItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UniversalRepositoryCustomImpl implements UniversalRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    // Helper method to convert query results to QueryResult objects
    private List<QueryResult> convertToQueryResults(List<Object[]> results, String[] columnNames) {
        List<QueryResult> queryResults = new ArrayList<>();
        for (Object[] result : results) {
            Map<String, Object> data = new HashMap<>();
            for (int i = 0; i < result.length; i++) {
                data.put(columnNames[i], result[i]);
            }
            queryResults.add(new QueryResult(data));
        }
        return queryResults;
    }

    // A1) Non-Indexed Columns
    @Override
    public List<QueryResult> a1() {
        String sql = "SELECT * FROM lineitem";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        // Define column names for lineitem table
        String[] columnNames = {
            "l_orderkey", "l_partkey", "l_suppkey", "l_linenumber", "l_quantity", 
            "l_extendedprice", "l_discount", "l_tax", "l_returnflag", "l_linestatus", 
            "l_shipdate", "l_commitdate", "l_receiptdate", "l_shipinstruct", "l_shipmode", "l_comment"
        };

        return convertToQueryResults(results, columnNames);
    }

    // A2) Non-Indexed Columns — Range Query
    @Override
    public List<QueryResult> a2(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM orders WHERE o_orderdate BETWEEN ?1 AND ?2";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, java.sql.Date.valueOf(startDate));
        query.setParameter(2, java.sql.Date.valueOf(endDate));
        List<Object[]> results = query.getResultList();

        // Define column names for orders table
        String[] columnNames = {
            "o_orderkey", "o_custkey", "o_orderstatus", "o_totalprice", "o_orderdate", 
            "o_orderpriority", "o_clerk", "o_shippriority", "o_comment"
        };

        return convertToQueryResults(results, columnNames);
    }

    // A3) Indexed Columns
    @Override
    public List<QueryResult> a3() {
        String sql = "SELECT * FROM customer";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        // Define column names for customer table
        String[] columnNames = {
            "c_custkey", "c_name", "c_address", "c_nationkey", "c_phone", 
            "c_acctbal", "c_mktsegment", "c_comment"
        };

        return convertToQueryResults(results, columnNames);
    }

    // A4) Indexed Columns — Range Query
    @Override
    public List<QueryResult> a4(int minOrderKey, int maxOrderKey) {
        String sql = "SELECT * FROM orders WHERE o_orderkey BETWEEN ?1 AND ?2";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, minOrderKey);
        query.setParameter(2, maxOrderKey);
        List<Object[]> results = query.getResultList();

        // Define column names for orders table
        String[] columnNames = {
            "o_orderkey", "o_custkey", "o_orderstatus", "o_totalprice", "o_orderdate", 
            "o_orderpriority", "o_clerk", "o_shippriority", "o_comment"
        };

        return convertToQueryResults(results, columnNames);
    }

    // B1) COUNT
    @Override
    public List<QueryResult> b1() {
        String sql = "SELECT COUNT(o.o_orderkey) AS order_count, " +
                "DATE_FORMAT(o.o_orderdate, '%Y-%m') AS order_month " +
                "FROM orders o " +
                "GROUP BY order_month";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"order_count", "order_month"};

        return convertToQueryResults(results, columnNames);
    }

    // B2) MAX
    @Override
    public List<QueryResult> b2() {
        String sql = "SELECT DATE_FORMAT(l.l_shipdate, '%Y-%m') AS ship_month, " +
                "MAX(l.l_extendedprice) AS max_price " +
                "FROM lineitem l " +
                "GROUP BY ship_month";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"ship_month", "max_price"};

        return convertToQueryResults(results, columnNames);
    }

    // C1) Non-Indexed Columns
    @Override
    public List<QueryResult> c1() {
        String sql = "SELECT c.c_name, o.o_orderdate, o.o_totalprice " +
                "FROM customer c, orders o";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"c_name", "o_orderdate", "o_totalprice"};

        return convertToQueryResults(results, columnNames);
    }

    // C2) Indexed Columns
    @Override
    public List<QueryResult> c2() {
        String sql = "SELECT c.c_name, o.o_orderdate, o.o_totalprice " +
                "FROM customer c " +
                "JOIN orders o ON c.c_custkey = o.o_custkey";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"c_name", "o_orderdate", "o_totalprice"};

        return convertToQueryResults(results, columnNames);
    }

    // C3) Complex Join 1
    @Override
    public List<QueryResult> c3() {
        String sql = "SELECT c.c_name, n.n_name, o.o_orderdate, o.o_totalprice " +
                "FROM customer c " +
                "JOIN nation n ON c.c_nationkey = n.n_nationkey " +
                "JOIN orders o ON c.c_custkey = o.o_custkey";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"c_name", "n_name", "o_orderdate", "o_totalprice"};

        return convertToQueryResults(results, columnNames);
    }

    // C4) Complex Join 2
    @Override
    public List<QueryResult> c4() {
        String sql = "SELECT c.c_name, n.n_name, r.r_name, o.o_orderdate, o.o_totalprice " +
                "FROM customer c " +
                "JOIN nation n ON c.c_nationkey = n.n_nationkey " +
                "JOIN region r ON n.n_regionkey = r.r_regionkey " +
                "JOIN orders o ON c.c_custkey = o.o_custkey";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"c_name", "n_name", "r_name", "o_orderdate", "o_totalprice"};

        return convertToQueryResults(results, columnNames);
    }

    // C5) Left Outer Join
    @Override
    public List<QueryResult> c5() {
        String sql = "SELECT c.c_custkey, c.c_name, o.o_orderkey, o.o_orderdate " +
                "FROM customer c " +
                "LEFT OUTER JOIN orders o ON c.c_custkey = o.o_custkey";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"c_custkey", "c_name", "o_orderkey", "o_orderdate"};

        return convertToQueryResults(results, columnNames);
    }

    // D1) UNION
    @Override
    public List<QueryResult> d1() {
        String sql = "(SELECT c_nationkey AS nationkey FROM customer) " +
                "UNION " +
                "(SELECT s_nationkey AS nationkey FROM supplier)";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"nationkey"};

        return convertToQueryResults(results, columnNames);
    }

    // D2) INTERSECT
    @Override
    public List<QueryResult> d2() {
        String sql = "SELECT DISTINCT c.c_custkey AS custkey " +
                "FROM customer c " +
                "WHERE c.c_custkey IN (SELECT s.s_suppkey FROM supplier s)";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"custkey"};

        return convertToQueryResults(results, columnNames);
    }

    // D3) DIFFERENCE
    @Override
    public List<QueryResult> d3() {
        String sql = "SELECT DISTINCT c.c_custkey AS custkey " +
                "FROM customer c " +
                "WHERE c.c_custkey NOT IN (SELECT DISTINCT s.s_suppkey FROM supplier s)";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"custkey"};

        return convertToQueryResults(results, columnNames);
    }

    // E1) Non-Indexed Columns Sorting
    @Override
    public List<QueryResult> e1() {
        String sql = "SELECT c_name, c_address, c_acctbal " +
                "FROM customer " +
                "ORDER BY c_acctbal DESC";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"c_name", "c_address", "c_acctbal"};

        return convertToQueryResults(results, columnNames);
    }

    // E2) Indexed Columns Sorting
    @Override
    public List<QueryResult> e2() {
        String sql = "SELECT o_orderkey, o_custkey, o_orderdate, o_totalprice " +
                "FROM orders " +
                "ORDER BY o_orderkey";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"o_orderkey", "o_custkey", "o_orderdate", "o_totalprice"};

        return convertToQueryResults(results, columnNames);
    }

    // E3) Distinct
    @Override
    public List<QueryResult> e3() {
        String sql = "SELECT DISTINCT c_nationkey, c_mktsegment " +
                "FROM customer";
        Query query = entityManager.createNativeQuery(sql);
        List<Object[]> results = query.getResultList();

        String[] columnNames = {"c_nationkey", "c_mktsegment"};

        return convertToQueryResults(results, columnNames);
    }

    @Override
    public List<PricingSummary> q1(int days) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<PricingSummary> cq = cb.createQuery(PricingSummary.class);
        Root<LineItem> root = cq.from(LineItem.class);

        cq.select(cb.construct(
                PricingSummary.class,
                root.get("l_returnflag"),
                root.get("l_linestatus"),
                cb.sum(root.get("l_quantity")),
                cb.sum(root.get("l_extendedprice")),
                cb.sum(cb.prod(root.get("l_extendedprice"), cb.diff(1, root.get("l_discount")))),  // SUM(l_extendedprice * (1 - l_discount))
                cb.sum(cb.prod(cb.prod(root.get("l_extendedprice"), cb.diff(1, root.get("l_discount"))),
                        cb.sum(1, root.get("l_tax")))),  // SUM(l_extendedprice * (1 - l_discount) * (1 + l_tax))
                cb.avg(root.get("l_quantity")),
                cb.avg(root.get("l_extendedprice")),
                cb.avg(root.get("l_discount")),
                cb.count(root)
        ));

        Calendar cal = Calendar.getInstance();
        cal.set(1998, Calendar.DECEMBER, 1);
        Date dateThreshold = cal.getTime();

        Date calculatedDate = new Date(dateThreshold.getTime() - (days * 24 * 60 * 60 * 1000L));

        cq.where(cb.lessThanOrEqualTo(root.get("l_shipdate"), calculatedDate));

        cq.groupBy(root.get("l_returnflag"), root.get("l_linestatus"));

        cq.orderBy(cb.asc(root.get("l_returnflag")), cb.asc(root.get("l_linestatus")));

        TypedQuery<PricingSummary> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

    @Override
    public List<MinimumCostSupplier> q2(int size, String type, String region) {
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
                "AND p.p_size = ?1 " +
                "AND p.p_type LIKE ?2 " +
                "AND s.s_nationkey = n.n_nationkey " +
                "AND n.n_regionkey = r.r_regionkey " +
                "AND r.r_name = ?3 " +
                "AND ps.ps_supplycost = ( " +
                "    SELECT MIN(ps2.ps_supplycost) " +
                "    FROM " +
                "      partsupp ps2, " +
                "      supplier s2, " +
                "      nation n2, " +
                "      region r2 " +
                "    WHERE " +
                "      p.p_partkey = ps2.ps_partkey " +
                "      AND s2.s_suppkey = ps2.ps_suppkey " +
                "      AND s2.s_nationkey = n2.n_nationkey " +
                "      AND n2.n_regionkey = r2.r_regionkey " +
                "      AND r2.r_name = ?3 " +
                ") " +
                "ORDER BY " +
                "s.s_acctbal DESC, " +
                "n.n_name, " +
                "s.s_name, " +
                "p.p_partkey ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, size);
        query.setParameter(2, type);
        query.setParameter(3, region);

        List<Object[]> results = query.getResultList();
        List<MinimumCostSupplier> suppliers = new ArrayList<>();

        for (Object[] result : results) {
            Double acctbal = result[0] != null ? ((Number) result[0]).doubleValue() : null;
            String name = (String) result[1];
            String nationName = (String) result[2];
            Integer partKey = result[3] != null ? ((Number) result[3]).intValue() : null;
            String mfgr = (String) result[4];
            String address = (String) result[5];
            String phone = (String) result[6];
            String comment = (String) result[7];

            suppliers.add(new MinimumCostSupplier(acctbal, name, nationName, partKey, mfgr, address, phone, comment));
        }

        return suppliers;
    }

    @Override
    public List<ShippingPriority> q3(String segment, LocalDate orderDate, LocalDate shipDate) {
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
                "c.c_mktsegment = ?1 " +
                "AND c.c_custkey = o.o_custkey " +
                "AND l.l_orderkey = o.o_orderkey " +
                "AND o.o_orderdate < ?2 " +
                "AND l.l_shipdate > ?3 " +
                "GROUP BY " +
                "l.l_orderkey, " +
                "o.o_orderdate, " +
                "o.o_shippriority " +
                "ORDER BY " +
                "revenue DESC, " +
                "o.o_orderdate ";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, segment);
        query.setParameter(2, java.sql.Date.valueOf(orderDate));
        query.setParameter(3, java.sql.Date.valueOf(shipDate));

        List<Object[]> results = query.getResultList();
        List<ShippingPriority> priorities = new ArrayList<>();

        for (Object[] result : results) {
            Integer orderKey = result[0] != null ? ((Number) result[0]).intValue() : null;
            Double revenue = result[1] != null ? ((Number) result[1]).doubleValue() : null;
            LocalDate date = result[2] != null ? ((java.sql.Date) result[2]).toLocalDate() : null;
            Integer shipPriority = result[3] != null ? ((Number) result[3]).intValue() : null;

            priorities.add(new ShippingPriority(orderKey, revenue, date, shipPriority));
        }

        return priorities;
    }

    @Override
    public List<OrderPriorityChecking> q4(LocalDate orderDate) {
        String sql = "SELECT " +
                "o.o_orderpriority, " +
                "COUNT(*) AS order_count " +
                "FROM " +
                "orders o " +
                "WHERE " +
                "o.o_orderdate >= ?1 " +
                "AND o.o_orderdate < DATE_ADD(?1, INTERVAL 3 MONTH) " +
                "AND EXISTS ( " +
                "    SELECT * " +
                "    FROM " +
                "        lineitem l " +
                "    WHERE " +
                "        l.l_orderkey = o.o_orderkey " +
                "        AND l.l_commitdate < l.l_receiptdate " +
                ") " +
                "GROUP BY " +
                "o.o_orderpriority " +
                "ORDER BY " +
                "o.o_orderpriority";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, java.sql.Date.valueOf(orderDate));

        List<Object[]> results = query.getResultList();
        List<OrderPriorityChecking> priorityCheckings = new ArrayList<>();

        for (Object[] result : results) {
            String orderPriority = (String) result[0];
            Long orderCount = result[1] != null ? ((Number) result[1]).longValue() : null;

            priorityCheckings.add(new OrderPriorityChecking(orderPriority, orderCount));
        }

        return priorityCheckings;
    }

    @Override
    public List<LocalSupplierVolume> q5(String region, LocalDate orderDate) {
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
                "AND r.r_name = ?1 " +
                "AND o.o_orderdate >= ?2 " +
                "AND o.o_orderdate < DATE_ADD(?2, INTERVAL 1 YEAR) " +
                "GROUP BY " +
                "n.n_name " +
                "ORDER BY " +
                "revenue DESC";

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, region);
        query.setParameter(2, java.sql.Date.valueOf(orderDate));

        List<Object[]> results = query.getResultList();
        List<LocalSupplierVolume> volumes = new ArrayList<>();

        for (Object[] result : results) {
            String nationName = (String) result[0];
            Double revenue = result[1] != null ? ((Number) result[1]).doubleValue() : null;

            volumes.add(new LocalSupplierVolume(nationName, revenue));
        }

        return volumes;
    }
}
