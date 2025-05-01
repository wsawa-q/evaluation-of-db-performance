package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("days", days);

        return jdbc.query(sql, params, pricingSummaryMapper);
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
}
