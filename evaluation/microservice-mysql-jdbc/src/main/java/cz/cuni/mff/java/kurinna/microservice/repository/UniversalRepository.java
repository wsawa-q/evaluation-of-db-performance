package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.dto.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class UniversalRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public UniversalRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<PricingSummary> q1(int days) {
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

        return jdbc.query(sql, params, pricingSummaryMapper);
    }

    public List<MinimumCostSupplier> q2(int size, String type, String region) {
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

        return jdbc.query(sql, params, minimumCostSupplierMapper);
    }

    public List<ShippingPriority> q3(String segment, LocalDate orderDate, LocalDate shipDate) {
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

        return jdbc.query(sql, params, shippingPriorityMapper);
    }

    public List<OrderPriorityChecking> q4(LocalDate orderDate) {
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

        return jdbc.query(sql, params, orderPriorityCheckingMapper);
    }

    public List<LocalSupplierVolume> q5(String region, LocalDate orderDate) {
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

        return jdbc.query(sql, params, localSupplierVolumeMapper);
    }

    private final RowMapper<PricingSummary> pricingSummaryMapper = new RowMapper<>() {
        @Override
        public PricingSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PricingSummary(
                    rs.getString("l_returnflag"),
                    rs.getString("l_linestatus"),
                    rs.getDouble("sum_qty"),
                    rs.getDouble("sum_base_price"),
                    rs.getDouble("sum_disc_price"),
                    rs.getDouble("sum_charge"),
                    rs.getDouble("avg_qty"),
                    rs.getDouble("avg_price"),
                    rs.getDouble("avg_disc"),
                    rs.getLong("count_order")
            );
        }
    };

    private final RowMapper<MinimumCostSupplier> minimumCostSupplierMapper = new RowMapper<>() {
        @Override
        public MinimumCostSupplier mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MinimumCostSupplier(
                    rs.getDouble("s_acctbal"),
                    rs.getString("s_name"),
                    rs.getString("n_name"),
                    rs.getInt("p_partkey"),
                    rs.getString("p_mfgr"),
                    rs.getString("s_address"),
                    rs.getString("s_phone"),
                    rs.getString("s_comment")
            );
        }
    };

    private final RowMapper<ShippingPriority> shippingPriorityMapper = new RowMapper<>() {
        @Override
        public ShippingPriority mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ShippingPriority(
                    rs.getInt("l_orderkey"),
                    rs.getDouble("revenue"),
                    rs.getDate("o_orderdate").toLocalDate(),
                    rs.getInt("o_shippriority")
            );
        }
    };

    private final RowMapper<OrderPriorityChecking> orderPriorityCheckingMapper = new RowMapper<>() {
        @Override
        public OrderPriorityChecking mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new OrderPriorityChecking(
                    rs.getString("o_orderpriority"),
                    rs.getLong("order_count")
            );
        }
    };

    private final RowMapper<LocalSupplierVolume> localSupplierVolumeMapper = new RowMapper<>() {
        @Override
        public LocalSupplierVolume mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new LocalSupplierVolume(
                    rs.getString("n_name"),
                    rs.getDouble("revenue")
            );
        }
    };

    // A1) Non-Indexed Columns
    public List<LineItem> a1() {
        String sql = """
            SELECT * FROM lineitem
            """;

        return jdbc.query(sql, lineItemMapper);
    }

    // A2) Non-Indexed Columns — Range Query
    public List<Order> a2(LocalDate startDate, LocalDate endDate) {
        String sql = """
            SELECT * FROM orders
            WHERE o_orderdate 
                BETWEEN :startDate AND :endDate
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startDate", java.sql.Date.valueOf(startDate));
        params.addValue("endDate", java.sql.Date.valueOf(endDate));

        return jdbc.query(sql, params, orderMapper);
    }

    // A3) Indexed Columns
    public List<Customer> a3() {
        String sql = """
            SELECT * FROM customer
            """;

        return jdbc.query(sql, customerMapper);
    }

    // A4) Indexed Columns — Range Query
    public List<Order> a4(int startKey, int endKey) {
        String sql = """
            SELECT * FROM orders
            WHERE o_orderkey BETWEEN :startKey AND :endKey
            """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("startKey", startKey);
        params.addValue("endKey", endKey);

        return jdbc.query(sql, params, orderMapper);
    }

    // B1) COUNT
    public List<OrderCount> b1() {
        String sql = """
            SELECT COUNT(o.o_orderkey) AS order_count, 
                   DATE_FORMAT(o.o_orderdate, '%Y-%m') AS order_month
            FROM orders o
            GROUP BY order_month
            """;

        return jdbc.query(sql, orderCountMapper);
    }

    // B2) MAX
    public List<MaxPrice> b2() {
        String sql = """
            SELECT DATE_FORMAT(l.l_shipdate, '%Y-%m') AS ship_month,
                   MAX(l.l_extendedprice) AS max_price
            FROM lineitem l
            GROUP BY ship_month
            """;

        return jdbc.query(sql, maxPriceMapper);
    }

    // C1) Non-Indexed Columns
    public List<CustomerOrder> c1() {
        String sql = """
            SELECT c.c_name, o.o_orderdate, o.o_totalprice
            FROM customer c, orders o
            """;

        return jdbc.query(sql, customerOrderMapper);
    }

    // C2) Indexed Columns
    public List<CustomerOrder> c2() {
        String sql = """
            SELECT c.c_name, o.o_orderdate, o.o_totalprice
            FROM customer c
            JOIN orders o ON c.c_custkey = o.o_custkey
            """;

        return jdbc.query(sql, customerOrderMapper);
    }

    // C3) Complex Join 1
    public List<CustomerNationOrder> c3() {
        String sql = """
            SELECT c.c_name, n.n_name, o.o_orderdate, o.o_totalprice
            FROM customer c
            JOIN nation n ON c.c_nationkey = n.n_nationkey
            JOIN orders o ON c.c_custkey = o.o_custkey
            """;

        return jdbc.query(sql, customerNationOrderMapper);
    }

    // C4) Complex Join 2
    public List<CustomerNationRegionOrder> c4() {
        String sql = """
            SELECT c.c_name, n.n_name, r.r_name, o.o_orderdate, o.o_totalprice
            FROM customer c
            JOIN nation n ON c.c_nationkey = n.n_nationkey
            JOIN region r ON n.n_regionkey = r.r_regionkey
            JOIN orders o ON c.c_custkey = o.o_custkey
            """;

        return jdbc.query(sql, customerNationRegionOrderMapper);
    }

    // C5) Left Outer Join
    public List<CustomerOrderDetail> c5() {
        String sql = """
            SELECT c.c_custkey, c.c_name, o.o_orderkey, o.o_orderdate
            FROM customer c
            LEFT OUTER JOIN orders o ON c.c_custkey = o.o_custkey
            """;

        return jdbc.query(sql, customerOrderDetailMapper);
    }

    // D1) UNION
    public List<NationKey> d1() {
        String sql = """
            (SELECT c_nationkey AS nation_key FROM customer)
            UNION
            (SELECT s_nationkey AS nation_key FROM supplier)
            """;

        return jdbc.query(sql, nationKeyMapper);
    }

    // D2) INTERSECT
    public List<CustomerKey> d2() {
        String sql = """
            SELECT DISTINCT c.c_custkey AS cust_key
            FROM customer c
            WHERE c.c_custkey IN (
                SELECT s.s_suppkey
                FROM supplier s
            )
            """;

        return jdbc.query(sql, customerKeyMapper);
    }

    // D3) DIFFERENCE
    public List<CustomerKey> d3() {
        String sql = """
            SELECT DISTINCT c.c_custkey AS cust_key
            FROM customer c
            WHERE c.c_custkey NOT IN (
                SELECT DISTINCT s.s_suppkey
                FROM supplier s
            )
            """;

        return jdbc.query(sql, customerKeyMapper);
    }

    // E1) Non-Indexed Columns Sorting
    public List<CustomerDetail> e1() {
        String sql = """
            SELECT c_name, c_address, c_acctbal
            FROM customer
            ORDER BY c_acctbal DESC
            """;

        return jdbc.query(sql, customerDetailMapper);
    }

    // E2) Indexed Columns Sorting
    public List<OrderDetail> e2() {
        String sql = """
            SELECT o_orderkey, o_custkey, o_orderdate, o_totalprice
            FROM orders
            ORDER BY o_orderkey
            """;

        return jdbc.query(sql, orderDetailMapper);
    }

    // E3) Distinct
    public List<CustomerSegment> e3() {
        String sql = """
            SELECT DISTINCT c_nationkey, c_mktsegment
            FROM customer
            """;

        return jdbc.query(sql, customerSegmentMapper);
    }

    private final RowMapper<LineItem> lineItemMapper = new RowMapper<>() {
        @Override
        public LineItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new LineItem(
                    rs.getInt("l_orderkey"),
                    rs.getInt("l_partkey"),
                    rs.getInt("l_suppkey"),
                    rs.getInt("l_linenumber"),
                    rs.getDouble("l_quantity"),
                    rs.getDouble("l_extendedprice"),
                    rs.getDouble("l_discount"),
                    rs.getDouble("l_tax"),
                    rs.getString("l_returnflag"),
                    rs.getString("l_linestatus"),
                    rs.getDate("l_shipdate").toLocalDate(),
                    rs.getDate("l_commitdate").toLocalDate(),
                    rs.getDate("l_receiptdate").toLocalDate(),
                    rs.getString("l_shipinstruct"),
                    rs.getString("l_shipmode"),
                    rs.getString("l_comment")
            );
        }
    };

    private final RowMapper<Order> orderMapper = new RowMapper<>() {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Order(
                    rs.getInt("o_orderkey"),
                    rs.getInt("o_custkey"),
                    rs.getString("o_orderstatus"),
                    rs.getDouble("o_totalprice"),
                    rs.getDate("o_orderdate").toLocalDate(),
                    rs.getString("o_orderpriority"),
                    rs.getString("o_clerk"),
                    rs.getInt("o_shippriority"),
                    rs.getString("o_comment")
            );
        }
    };

    private final RowMapper<Customer> customerMapper = new RowMapper<>() {
        @Override
        public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Customer(
                    rs.getInt("c_custkey"),
                    rs.getString("c_name"),
                    rs.getString("c_address"),
                    rs.getInt("c_nationkey"),
                    rs.getString("c_phone"),
                    rs.getDouble("c_acctbal"),
                    rs.getString("c_mktsegment"),
                    rs.getString("c_comment")
            );
        }
    };

    private final RowMapper<OrderCount> orderCountMapper = new RowMapper<>() {
        @Override
        public OrderCount mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new OrderCount(
                    rs.getLong("order_count"),
                    rs.getString("order_month")
            );
        }
    };

    private final RowMapper<MaxPrice> maxPriceMapper = new RowMapper<>() {
        @Override
        public MaxPrice mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new MaxPrice(
                    rs.getString("ship_month"),
                    rs.getDouble("max_price")
            );
        }
    };

    private final RowMapper<CustomerOrder> customerOrderMapper = new RowMapper<>() {
        @Override
        public CustomerOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CustomerOrder(
                    rs.getString("c_name"),
                    rs.getDate("o_orderdate").toLocalDate(),
                    rs.getDouble("o_totalprice")
            );
        }
    };

    private final RowMapper<CustomerNationOrder> customerNationOrderMapper = new RowMapper<>() {
        @Override
        public CustomerNationOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CustomerNationOrder(
                    rs.getString("c_name"),
                    rs.getString("n_name"),
                    rs.getDate("o_orderdate").toLocalDate(),
                    rs.getDouble("o_totalprice")
            );
        }
    };

    private final RowMapper<CustomerNationRegionOrder> customerNationRegionOrderMapper = new RowMapper<>() {
        @Override
        public CustomerNationRegionOrder mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CustomerNationRegionOrder(
                    rs.getString("c_name"),
                    rs.getString("n_name"),
                    rs.getString("r_name"),
                    rs.getDate("o_orderdate").toLocalDate(),
                    rs.getDouble("o_totalprice")
            );
        }
    };

    private final RowMapper<CustomerOrderDetail> customerOrderDetailMapper = new RowMapper<>() {
        @Override
        public CustomerOrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            LocalDate orderDate = null;
            if (rs.getDate("o_orderdate") != null) {
                orderDate = rs.getDate("o_orderdate").toLocalDate();
            }
            return new CustomerOrderDetail(
                    rs.getInt("c_custkey"),
                    rs.getString("c_name"),
                    rs.getObject("o_orderkey", Integer.class),
                    orderDate
            );
        }
    };

    private final RowMapper<NationKey> nationKeyMapper = new RowMapper<>() {
        @Override
        public NationKey mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new NationKey(
                    rs.getInt("nation_key")
            );
        }
    };

    private final RowMapper<CustomerKey> customerKeyMapper = new RowMapper<>() {
        @Override
        public CustomerKey mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CustomerKey(
                    rs.getInt("cust_key")
            );
        }
    };

    private final RowMapper<CustomerDetail> customerDetailMapper = new RowMapper<>() {
        @Override
        public CustomerDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CustomerDetail(
                    rs.getString("c_name"),
                    rs.getString("c_address"),
                    rs.getDouble("c_acctbal")
            );
        }
    };

    private final RowMapper<OrderDetail> orderDetailMapper = new RowMapper<>() {
        @Override
        public OrderDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new OrderDetail(
                    rs.getInt("o_orderkey"),
                    rs.getInt("o_custkey"),
                    rs.getDate("o_orderdate").toLocalDate(),
                    rs.getDouble("o_totalprice")
            );
        }
    };

    private final RowMapper<CustomerSegment> customerSegmentMapper = new RowMapper<>() {
        @Override
        public CustomerSegment mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new CustomerSegment(
                    rs.getInt("c_nationkey"),
                    rs.getString("c_mktsegment")
            );
        }
    };
}
