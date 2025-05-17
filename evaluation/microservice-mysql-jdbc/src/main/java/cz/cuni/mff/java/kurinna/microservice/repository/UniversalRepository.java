package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import cz.cuni.mff.java.kurinna.microservice.dto.MinimumCostSupplier;
import cz.cuni.mff.java.kurinna.microservice.dto.ShippingPriority;
import cz.cuni.mff.java.kurinna.microservice.dto.OrderPriorityChecking;
import cz.cuni.mff.java.kurinna.microservice.dto.LocalSupplierVolume;
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

    public List<PricingSummary> fetchPricingSummary(int days) {
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

    public List<MinimumCostSupplier> fetchMinimumCostSupplier(int size, String type, String region) {
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

    public List<ShippingPriority> fetchShippingPriority(String segment, LocalDate orderDate, LocalDate shipDate) {
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

    public List<OrderPriorityChecking> fetchOrderPriorityChecking(LocalDate orderDate) {
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

    public List<LocalSupplierVolume> fetchLocalSupplierVolume(String region, LocalDate orderDate) {
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
}
