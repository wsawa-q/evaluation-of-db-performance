package cz.cuni.mff.java.kurinna.microservice.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public class UniversalRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;

    public UniversalRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.jdbcTemplate = jdbcTemplate;

    }

    public List<Map<String, Object>> q1(int days) {
        String sql = """
            SELECT
              l_returnflag,
              l_linestatus,
              SUM(l_quantity)                     AS sum_qty,
              SUM(l_extendedprice)                AS sum_base_price,
              SUM(l_extendedprice * (1 - l_discount))             AS sum_disc_price,
              SUM(l_extendedprice * (1 - l_discount) * (1 + l_tax)) AS sum_charge,
              AVG(l_quantity)                     AS avg_qty,
              AVG(l_extendedprice)                AS avg_price,
              AVG(l_discount)                     AS avg_disc,
              COUNT(*)                            AS count_order
            FROM lineitem
            WHERE l_shipdate <=
              DATE_SUB('1998-12-01', INTERVAL :days DAY)
            GROUP BY l_returnflag, l_linestatus
            ORDER BY l_returnflag, l_linestatus
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("days", days);

        return namedParameterJdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> q2(int size, String type, String region) {
        String sql = """
            SELECT
              s.s_acctbal,
              s.s_name,
              n.n_name,
              p.p_partkey,
              p.p_mfgr,
              s.s_address,
              s.s_phone,
              s.s_comment
            FROM
              part p,
              supplier s,
              partsupp ps,
              nation n,
              region r
            WHERE
              p.p_partkey = ps.ps_partkey
              AND s.s_suppkey = ps.ps_suppkey
              AND p.p_size = :size
              AND p.p_type LIKE :type
              AND s.s_nationkey = n.n_nationkey
              AND n.n_regionkey = r.r_regionkey
              AND r.r_name = :region
              AND ps.ps_supplycost = (
                SELECT MIN(ps.ps_supplycost)
                FROM
                  partsupp ps,
                  supplier s,
                  nation n,
                  region r
                WHERE
                  p.p_partkey = ps.ps_partkey
                  AND s.s_suppkey = ps.ps_suppkey
                  AND s.s_nationkey = n.n_nationkey
                  AND n.n_regionkey = r.r_regionkey
                  AND r.r_name = :region
              )
            ORDER BY
              s.s_acctbal DESC,
              n.n_name,
              s.s_name,
              p.p_partkey
            LIMIT 100
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("size", size);
        params.addValue("type", type);
        params.addValue("region", region);

        return namedParameterJdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> q3(String segment, LocalDate orderDate, LocalDate shipDate) {
        String sql = """
            SELECT
              l.l_orderkey,
              SUM(l.l_extendedprice * (1 - l.l_discount)) AS revenue,
              o.o_orderdate,
              o.o_shippriority
            FROM
              customer c,
              orders o,
              lineitem l
            WHERE
              c.c_mktsegment = :segment
              AND c.c_custkey = o.o_custkey
              AND l.l_orderkey = o.o_orderkey
              AND o.o_orderdate < :orderDate
              AND l.l_shipdate > :shipDate
            GROUP BY
              l.l_orderkey,
              o.o_orderdate,
              o.o_shippriority
            ORDER BY
              revenue DESC,
              o.o_orderdate
            LIMIT 10
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("segment", segment);
        params.addValue("orderDate", java.sql.Date.valueOf(orderDate));
        params.addValue("shipDate", java.sql.Date.valueOf(shipDate));

        return namedParameterJdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> q4(LocalDate orderDate) {
        LocalDate endDate = orderDate.plusMonths(3);

        String sql = """
            SELECT
              o_orderpriority,
              COUNT(*) AS order_count
            FROM
              orders
            WHERE
              o_orderdate >= :orderDate
              AND o_orderdate < :endDate
              AND EXISTS (
                SELECT *
                FROM
                  lineitem
                WHERE
                  l_orderkey = o_orderkey
                  AND l_commitdate < l_receiptdate
              )
            GROUP BY
              o_orderpriority
            ORDER BY
              o_orderpriority
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("orderDate", java.sql.Date.valueOf(orderDate));
        params.addValue("endDate", java.sql.Date.valueOf(endDate));

        return namedParameterJdbcTemplate.queryForList(sql, params);
    }

    public List<Map<String, Object>> q5(String region, LocalDate orderDate) {
        LocalDate endDate = orderDate.plusYears(1);

        String sql = """
            SELECT
              n.n_name,
              SUM(l.l_extendedprice * (1 - l.l_discount)) AS revenue
            FROM
              customer c,
              orders o,
              lineitem l,
              supplier s,
              nation n,
              region r
            WHERE
              c.c_custkey = o.o_custkey
              AND l.l_orderkey = o.o_orderkey
              AND l.l_suppkey = s.s_suppkey
              AND c.c_nationkey = s.s_nationkey
              AND s.s_nationkey = n.n_nationkey
              AND n.n_regionkey = r.r_regionkey
              AND r.r_name = :region
              AND o.o_orderdate >= :orderDate
              AND o.o_orderdate < :endDate
            GROUP BY
              n.n_name
            ORDER BY
              revenue DESC
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("region", region);
        params.addValue("orderDate", java.sql.Date.valueOf(orderDate));
        params.addValue("endDate", java.sql.Date.valueOf(endDate));

        return namedParameterJdbcTemplate.queryForList(sql, params);
    }

    // A1) Non-Indexed Columns
    public List<Map<String, Object>> a1() {
        String sql = """
            SELECT * FROM lineitem
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // A2) Non-Indexed Columns — Range Query
    public List<Map<String, Object>> a2(LocalDate startDate, LocalDate endDate) {
        String sql = """
            SELECT * FROM orders
            WHERE o_orderdate
                BETWEEN :startDate AND :endDate
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", java.sql.Date.valueOf(startDate));
        params.addValue("endDate", java.sql.Date.valueOf(endDate));

        return namedParameterJdbcTemplate.queryForList(sql, params);
    }

    // A3) Indexed Columns
    public List<Map<String, Object>> a3() {
        String sql = """
            SELECT * FROM customer
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // A4) Indexed Columns — Range Query
    public List<Map<String, Object>> a4(int startKey, int endKey) {
        String sql = """
            SELECT * FROM orders
            WHERE o_orderkey BETWEEN :startKey AND :endKey
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startKey", startKey);
        params.addValue("endKey", endKey);

        return namedParameterJdbcTemplate.queryForList(sql, params);
    }

    // B1) COUNT
    public List<Map<String, Object>> b1() {
        String sql = """
            SELECT COUNT(o.o_orderkey) AS order_count,
                   DATE_FORMAT(o.o_orderdate, '%Y-%m') AS order_month
            FROM orders o
            GROUP BY order_month
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // B2) MAX
    public List<Map<String, Object>> b2() {
        String sql = """
            SELECT DATE_FORMAT(l.l_shipdate, '%Y-%m') AS ship_month,
                   MAX(l.l_extendedprice) AS max_price
            FROM lineitem l
            GROUP BY ship_month
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // C1) Non-Indexed Columns
    public List<Map<String, Object>> c1() {
        String sql = """
            SELECT c.c_name, o.o_orderdate, o.o_totalprice
            FROM customer c, orders o
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // C2) Indexed Columns
    public List<Map<String, Object>> c2() {
        String sql = """
            SELECT c.c_name, o.o_orderdate, o.o_totalprice
            FROM customer c
            JOIN orders o ON c.c_custkey = o.o_custkey
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // C3) Complex Join 1
    public List<Map<String, Object>> c3() {
        String sql = """
            SELECT c.c_name, n.n_name, o.o_orderdate, o.o_totalprice
            FROM customer c
            JOIN nation n ON c.c_nationkey = n.n_nationkey
            JOIN orders o ON c.c_custkey = o.o_custkey
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // C4) Complex Join 2
    public List<Map<String, Object>> c4() {
        String sql = """
            SELECT c.c_name, n.n_name, r.r_name, o.o_orderdate, o.o_totalprice
            FROM customer c
            JOIN nation n ON c.c_nationkey = n.n_nationkey
            JOIN region r ON n.n_regionkey = r.r_regionkey
            JOIN orders o ON c.c_custkey = o.o_custkey
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // C5) Left Outer Join
    public List<Map<String, Object>> c5() {
        String sql = """
            SELECT c.c_custkey, c.c_name, o.o_orderkey, o.o_orderdate
            FROM customer c
            LEFT OUTER JOIN orders o ON c.c_custkey = o.o_custkey
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // D1) UNION
    public List<Map<String, Object>> d1() {
        String sql = """
            (SELECT c_nationkey AS nation_key FROM customer)
            UNION
            (SELECT s_nationkey AS nation_key FROM supplier)
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // D2) INTERSECT
    public List<Map<String, Object>> d2() {
        String sql = """
            SELECT DISTINCT c.c_custkey AS cust_key
            FROM customer c
            WHERE c.c_custkey IN (
                SELECT s.s_suppkey
                FROM supplier s
            )
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // D3) DIFFERENCE
    public List<Map<String, Object>> d3() {
        String sql = """
            SELECT DISTINCT c.c_custkey AS cust_key
            FROM customer c
            WHERE c.c_custkey NOT IN (
                SELECT DISTINCT s.s_suppkey
                FROM supplier s
            )
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // E1) Non-Indexed Columns Sorting
    public List<Map<String, Object>> e1() {
        String sql = """
            SELECT c_name, c_address, c_acctbal
            FROM customer
            ORDER BY c_acctbal DESC
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // E2) Indexed Columns Sorting
    public List<Map<String, Object>> e2() {
        String sql = """
            SELECT o_orderkey, o_custkey, o_orderdate, o_totalprice
            FROM orders
            ORDER BY o_orderkey
            """;

        return jdbcTemplate.queryForList(sql);
    }

    // E3) Distinct
    public List<Map<String, Object>> e3() {
        String sql = """
            SELECT DISTINCT c_nationkey, c_mktsegment
            FROM customer
            """;

        return jdbcTemplate.queryForList(sql);
    }
}
