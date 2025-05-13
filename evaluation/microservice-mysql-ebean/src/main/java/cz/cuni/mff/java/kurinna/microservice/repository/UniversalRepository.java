package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import io.ebean.Database;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
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
}
