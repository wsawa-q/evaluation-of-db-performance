package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.ResultType;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UniversalMapper {
    // A1) Non-Indexed Columns
    @Select("SELECT * FROM lineitem")
    List<LineItem> a1();

    // A2) Non-Indexed Columns — Range Query
    @Select("SELECT * FROM orders WHERE o_orderdate BETWEEN #{startDate} AND #{endDate}")
    List<Order> a2(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // A3) Indexed Columns
    @Select("SELECT * FROM customer")
    List<Customer> a3();

    // A4) Indexed Columns — Range Query
    @Select("SELECT * FROM orders WHERE o_orderkey BETWEEN #{minOrderKey} AND #{maxOrderKey}")
    List<Order> a4(@Param("minOrderKey") int minOrderKey, @Param("maxOrderKey") int maxOrderKey);

    // B1) COUNT
    @Select("""
            SELECT COUNT(o.o_orderkey) AS order_count, 
                   DATE_FORMAT(o.o_orderdate, '%Y-%m') AS order_month
            FROM orders o
            GROUP BY order_month
            """)
    List<OrderCount> b1();

    // B2) MAX
    @Select("""
            SELECT DATE_FORMAT(l.l_shipdate, '%Y-%m') AS ship_month,
                   MAX(l.l_extendedprice) AS max_price
            FROM lineitem l
            GROUP BY ship_month
            """)
    List<MaxPrice> b2();

    // C1) Non-Indexed Columns
    @Select("""
            SELECT c.c_name, o.o_orderdate, o.o_totalprice
            FROM customer c, orders o
            """)
    List<CustomerOrder> c1();

    // C2) Indexed Columns
    @Select("""
            SELECT c.c_name, o.o_orderdate, o.o_totalprice
            FROM customer c
            JOIN orders o ON c.c_custkey = o.o_custkey
            """)
    List<CustomerOrder> c2();

    // C3) Complex Join 1
    @Select("""
            SELECT c.c_name, n.n_name, o.o_orderdate, o.o_totalprice
            FROM customer c
            JOIN nation n ON c.c_nationkey = n.n_nationkey
            JOIN orders o ON c.c_custkey = o.o_custkey
            """)
    List<CustomerNationOrder> c3();

    // C4) Complex Join 2
    @Select("""
            SELECT c.c_name, n.n_name, r.r_name, o.o_orderdate, o.o_totalprice
            FROM customer c
            JOIN nation n ON c.c_nationkey = n.n_nationkey
            JOIN region r ON n.n_regionkey = r.r_regionkey
            JOIN orders o ON c.c_custkey = o.o_custkey
            """)
    List<CustomerNationRegionOrder> c4();

    // C5) Left Outer Join
    @Select("""
            SELECT c.c_custkey, c.c_name, o.o_orderkey, o.o_orderdate
            FROM customer c
            LEFT OUTER JOIN orders o ON c.c_custkey = o.o_custkey
            """)
    List<CustomerOrderDetail> c5();

    // D1) UNION
    @Select("""
            (SELECT c_nationkey AS nationkey FROM customer)
            UNION
            (SELECT s_nationkey AS nationkey FROM supplier)
            """)
    List<NationKey> d1();

    // D2) INTERSECT
    @Select("""
            SELECT DISTINCT c.c_custkey AS custkey
            FROM customer c
            WHERE c.c_custkey IN (
                SELECT s.s_suppkey
                FROM supplier s
            )
            """)
    List<CustomerKey> d2();

    // D3) DIFFERENCE
    @Select("""
            SELECT DISTINCT c.c_custkey AS custkey
            FROM customer c
            WHERE c.c_custkey NOT IN (
                SELECT DISTINCT s.s_suppkey
                FROM supplier s
            )
            """)
    List<CustomerKey> d3();

    // E1) Non-Indexed Columns Sorting
    @Select("""
            SELECT c_name, c_address, c_acctbal
            FROM customer
            ORDER BY c_acctbal DESC
            """)
    List<CustomerDetail> e1();

    // E2) Indexed Columns Sorting
    @Select("""
            SELECT o_orderkey, o_custkey, o_orderdate, o_totalprice
            FROM orders
            ORDER BY o_orderkey
            """)
    List<OrderDetail> e2();

    // E3) Distinct
    @Select("""
            SELECT DISTINCT c_nationkey, c_mktsegment
            FROM customer
            """)
    List<QueryResult> e3();

    @Select("""
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
              DATE_SUB('1998-12-01', INTERVAL #{days} DAY)
            GROUP BY l_returnflag, l_linestatus
            ORDER BY l_returnflag, l_linestatus
            """)
    List<PricingSummary> q1(int days);

    @Select("""
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
              AND p.p_size = #{size}
              AND p.p_type LIKE #{type}
              AND s.s_nationkey = n.n_nationkey
              AND n.n_regionkey = r.r_regionkey
              AND r.r_name = #{region}
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
                  AND r.r_name = #{region}
              )
            ORDER BY
              s.s_acctbal DESC,
              n.n_name,
              s.s_name,
              p.p_partkey
            LIMIT 100
            """)
    List<MinimumCostSupplier> q2(@Param("size") int size, @Param("type") String type, @Param("region") String region);

    @Select("""
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
              c.c_mktsegment = #{segment}
              AND c.c_custkey = o.o_custkey
              AND l.l_orderkey = o.o_orderkey
              AND o.o_orderdate < #{orderDate}
              AND l.l_shipdate > #{shipDate}
            GROUP BY
              l.l_orderkey,
              o.o_orderdate,
              o.o_shippriority
            ORDER BY
              revenue DESC,
              o.o_orderdate
            LIMIT 10
            """)
    List<ShippingPriority> q3(@Param("segment") String segment, @Param("orderDate") LocalDate orderDate, @Param("shipDate") LocalDate shipDate);

    @Select("""
            SELECT
              o_orderpriority,
              COUNT(*) AS order_count
            FROM
              orders
            WHERE
              o_orderdate >= #{orderDate}
              AND o_orderdate < DATE_ADD(#{orderDate}, INTERVAL 3 MONTH)
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
            """)
    List<OrderPriorityChecking> q4(@Param("orderDate") LocalDate orderDate);

    @Select("""
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
              AND r.r_name = #{region}
              AND o.o_orderdate >= #{orderDate}
              AND o.o_orderdate < DATE_ADD(#{orderDate}, INTERVAL 1 YEAR)
            GROUP BY
              n.n_name
            ORDER BY
              revenue DESC
            """)
    List<LocalSupplierVolume> q5(@Param("region") String region, @Param("orderDate") LocalDate orderDate);
}
