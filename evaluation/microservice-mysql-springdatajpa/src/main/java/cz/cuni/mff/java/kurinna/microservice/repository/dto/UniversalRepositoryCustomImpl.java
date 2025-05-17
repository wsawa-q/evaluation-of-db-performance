package cz.cuni.mff.java.kurinna.microservice.repository.dto;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.dto.MinimumCostSupplier;
import cz.cuni.mff.java.kurinna.microservice.dto.ShippingPriority;
import cz.cuni.mff.java.kurinna.microservice.dto.OrderPriorityChecking;
import cz.cuni.mff.java.kurinna.microservice.dto.LocalSupplierVolume;
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
import java.util.List;

@Repository
public class UniversalRepositoryCustomImpl implements UniversalRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PricingSummary> findPricingSummaryReport(int days) {
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
    public List<MinimumCostSupplier> findMinimumCostSupplier(int size, String type, String region) {
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
                "p.p_partkey " +
                "LIMIT 100";

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
    public List<ShippingPriority> findShippingPriority(String segment, LocalDate orderDate, LocalDate shipDate) {
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
                "o.o_orderdate " +
                "LIMIT 10";

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
    public List<OrderPriorityChecking> findOrderPriorityChecking(LocalDate orderDate) {
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
    public List<LocalSupplierVolume> findLocalSupplierVolume(String region, LocalDate orderDate) {
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
