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
        LocalDate shipDateThresholdJava = baseDate.minusDays(deltaDays);

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
        parameters.put("ship_date_threshold", Date.valueOf(shipDateThresholdJava));
        query.params(parameters);

        return query.select(context);
    }
}
