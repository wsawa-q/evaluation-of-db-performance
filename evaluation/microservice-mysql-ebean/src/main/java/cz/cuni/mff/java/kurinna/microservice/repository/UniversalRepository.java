package cz.cuni.mff.java.kurinna.microservice.repository;

import io.ebean.Database;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class UniversalRepository {
    private final Database database;

    public UniversalRepository(Database database) {
        this.database = database;
    }

    public List<Map<String, Object>> fetchPricingSummary(int days) {
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

    public List<Map<String, Object>> fetchMinimumCostSupplier(int size, String type, String region) {
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

    public List<Map<String, Object>> fetchShippingPriority(String segment, LocalDate orderDate, LocalDate shipDate) {
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

    public List<Map<String, Object>> fetchOrderPriorityChecking(LocalDate orderDate) {
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

    public List<Map<String, Object>> fetchLocalSupplierVolume(String region, LocalDate orderDate) {
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
