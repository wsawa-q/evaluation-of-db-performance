package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.dto.MinimumCostSupplier;
import cz.cuni.mff.java.kurinna.microservice.dto.ShippingPriority;
import cz.cuni.mff.java.kurinna.microservice.dto.OrderPriorityChecking;
import cz.cuni.mff.java.kurinna.microservice.dto.LocalSupplierVolume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface UniversalMapper {
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
    List<PricingSummary> fetchPricingSummary(int days);

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
    List<MinimumCostSupplier> fetchMinimumCostSupplier(@Param("size") int size, @Param("type") String type, @Param("region") String region);

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
    List<ShippingPriority> fetchShippingPriority(@Param("segment") String segment, @Param("orderDate") LocalDate orderDate, @Param("shipDate") LocalDate shipDate);

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
    List<OrderPriorityChecking> fetchOrderPriorityChecking(@Param("orderDate") LocalDate orderDate);

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
    List<LocalSupplierVolume> fetchLocalSupplierVolume(@Param("region") String region, @Param("orderDate") LocalDate orderDate);
}
