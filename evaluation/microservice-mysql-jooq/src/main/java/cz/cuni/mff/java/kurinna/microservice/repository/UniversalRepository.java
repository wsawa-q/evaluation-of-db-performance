package cz.cuni.mff.java.kurinna.microservice.repository;

import cz.cuni.mff.java.kurinna.microservice.dto.PricingSummary;
import org.jooq.DSLContext;
import org.jooq.Record10;
import static org.jooq.impl.DSL.*;
import org.jooq.Result;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static cz.cuni.mff.java.kurinna.microservice.model.tables.Lineitem.LINEITEM;

@Repository
public class UniversalRepository {
    private final DSLContext dslContext;

    public UniversalRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public List<PricingSummary> q1(int days) {
    LocalDate cutoff = LocalDate.of(1998, 12, 1)
            .minusDays(days);

    return dslContext
        .select(
            LINEITEM.L_RETURNFLAG,
            LINEITEM.L_LINESTATUS,
            sum(LINEITEM.L_QUANTITY).as("sum_qty"),
            sum(LINEITEM.L_EXTENDEDPRICE).as("sum_base_price"),
            sum(LINEITEM.L_EXTENDEDPRICE.mul(one().minus(LINEITEM.L_DISCOUNT))).as("sum_disc_price"),
            sum(LINEITEM.L_EXTENDEDPRICE.mul(one().minus(LINEITEM.L_DISCOUNT)).mul(one().plus(LINEITEM.L_TAX))).as("sum_charge"),
            avg(LINEITEM.L_QUANTITY).as("avg_qty"),
            avg(LINEITEM.L_EXTENDEDPRICE).as("avg_price"),
            avg(LINEITEM.L_DISCOUNT).as("avg_disc"),
            count().as("count_order")
        )
        .from(LINEITEM)
        .where(LINEITEM.L_SHIPDATE.le(val(cutoff)))
        .groupBy(LINEITEM.L_RETURNFLAG, LINEITEM.L_LINESTATUS)
        .orderBy(LINEITEM.L_RETURNFLAG, LINEITEM.L_LINESTATUS)
        .fetchInto(PricingSummary.class);
    }

}
