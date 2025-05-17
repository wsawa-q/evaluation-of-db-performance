package cz.cuni.mff.java.kurinna.microservice.repository;

import org.apache.cayenne.DataRow;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.query.SQLSelect;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UniversalRepository {
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
                "    p.p_partkey " +
                "LIMIT 100";

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
                "    o.o_orderdate " +
                "LIMIT 10";

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
