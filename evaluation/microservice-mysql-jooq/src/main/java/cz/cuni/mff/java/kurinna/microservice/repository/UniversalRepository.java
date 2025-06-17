package cz.cuni.mff.java.kurinna.microservice.repository;

import org.jooq.DSLContext;
import static org.jooq.impl.DSL.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static cz.cuni.mff.java.kurinna.microservice.model.tables.Lineitem.LINEITEM;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Part.PART;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Supplier.SUPPLIER;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Partsupp.PARTSUPP;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Nation.NATION;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Region.REGION;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Customer.CUSTOMER;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Orders.ORDERS;

@Repository
public class UniversalRepository {
    private final DSLContext dslContext;

    public UniversalRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    // A1) Non-Indexed Columns
    public List<?> a1() {
        return dslContext
            .select()
            .from(LINEITEM)
            .fetch();
    }

    // A2) Non-Indexed Columns — Range Query
    public List<?> a2(LocalDate startDate, LocalDate endDate) {
        return dslContext
            .select()
            .from(ORDERS)
            .where(ORDERS.O_ORDERDATE.between(startDate, endDate))
            .fetch();
    }

    // A3) Indexed Columns
    public List<?> a3() {
        return dslContext
            .select()
            .from(CUSTOMER)
            .fetch();
    }

    // A4) Indexed Columns — Range Query
    public List<?> a4(int minOrderKey, int maxOrderKey) {
        return dslContext
            .select()
            .from(ORDERS)
            .where(ORDERS.O_ORDERKEY.between((long) minOrderKey, (long) maxOrderKey))
            .fetch();
    }

    // B1) COUNT
    public List<?> b1() {
        return dslContext
            .select(
                count(ORDERS.O_ORDERKEY).as("order_count"),
                function("DATE_FORMAT", String.class, ORDERS.O_ORDERDATE, inline("%Y-%m")).as("order_month")
            )
            .from(ORDERS)
            .groupBy(field("order_month"))
            .fetch();
    }

    // B2) MAX
    public List<?> b2() {
        return dslContext
            .select(
                function("DATE_FORMAT", String.class, LINEITEM.L_SHIPDATE, inline("%Y-%m")).as("ship_month"),
                max(LINEITEM.L_EXTENDEDPRICE).as("max_price")
            )
            .from(LINEITEM)
            .groupBy(field("ship_month"))
            .fetch();
    }

    // C1) Non-Indexed Columns
    public List<?> c1() {
        return dslContext
            .select(
                CUSTOMER.C_NAME,
                ORDERS.O_ORDERDATE,
                ORDERS.O_TOTALPRICE
            )
            .from(CUSTOMER, ORDERS)
            .fetch();
    }

    // C2) Indexed Columns
    public List<?> c2() {
        return dslContext
            .select(
                CUSTOMER.C_NAME,
                ORDERS.O_ORDERDATE,
                ORDERS.O_TOTALPRICE
            )
            .from(CUSTOMER)
            .join(ORDERS).on(CUSTOMER.C_CUSTKEY.eq(ORDERS.O_CUSTKEY))
            .fetch();
    }

    // C3) Complex Join 1
    public List<?> c3() {
        return dslContext
            .select(
                CUSTOMER.C_NAME,
                NATION.N_NAME,
                ORDERS.O_ORDERDATE,
                ORDERS.O_TOTALPRICE
            )
            .from(CUSTOMER)
            .join(NATION).on(CUSTOMER.C_NATIONKEY.eq(NATION.N_NATIONKEY))
            .join(ORDERS).on(CUSTOMER.C_CUSTKEY.eq(ORDERS.O_CUSTKEY))
            .fetch();
    }

    // C4) Complex Join 2
    public List<?> c4() {
        return dslContext
            .select(
                CUSTOMER.C_NAME,
                NATION.N_NAME,
                REGION.R_NAME,
                ORDERS.O_ORDERDATE,
                ORDERS.O_TOTALPRICE
            )
            .from(CUSTOMER)
            .join(NATION).on(CUSTOMER.C_NATIONKEY.eq(NATION.N_NATIONKEY))
            .join(REGION).on(NATION.N_REGIONKEY.eq(REGION.R_REGIONKEY))
            .join(ORDERS).on(CUSTOMER.C_CUSTKEY.eq(ORDERS.O_CUSTKEY))
            .fetch();
    }

    // C5) Left Outer Join
    public List<?> c5() {
        return dslContext
            .select(
                CUSTOMER.C_CUSTKEY,
                CUSTOMER.C_NAME,
                ORDERS.O_ORDERKEY,
                ORDERS.O_ORDERDATE
            )
            .from(CUSTOMER)
            .leftOuterJoin(ORDERS).on(CUSTOMER.C_CUSTKEY.eq(ORDERS.O_CUSTKEY))
            .fetch();
    }

    // D1) UNION
    public List<?> d1() {
        return dslContext
            .select(CUSTOMER.C_NATIONKEY.as("nationkey"))
            .from(CUSTOMER)
            .union(
                select(SUPPLIER.S_NATIONKEY.as("nationkey"))
                .from(SUPPLIER)
            )
            .fetch();
    }

    // D2) INTERSECT
    public List<?> d2() {
        return dslContext
            .selectDistinct(CUSTOMER.C_CUSTKEY.as("custkey"))
            .from(CUSTOMER)
            .where(CUSTOMER.C_CUSTKEY.in(
                select(SUPPLIER.S_SUPPKEY)
                .from(SUPPLIER)
            ))
            .fetch();
    }

    // D3) DIFFERENCE
    public List<?> d3() {
        return dslContext
            .selectDistinct(CUSTOMER.C_CUSTKEY.as("custkey"))
            .from(CUSTOMER)
            .where(CUSTOMER.C_CUSTKEY.notIn(
                selectDistinct(SUPPLIER.S_SUPPKEY)
                .from(SUPPLIER)
            ))
            .fetch();
    }

    // E1) Non-Indexed Columns Sorting
    public List<?> e1() {
        return dslContext
            .select(
                CUSTOMER.C_NAME,
                CUSTOMER.C_ADDRESS,
                CUSTOMER.C_ACCTBAL
            )
            .from(CUSTOMER)
            .orderBy(CUSTOMER.C_ACCTBAL.desc())
            .fetch();
    }

    // E2) Indexed Columns Sorting
    public List<?> e2() {
        return dslContext
            .select(
                ORDERS.O_ORDERKEY,
                ORDERS.O_CUSTKEY,
                ORDERS.O_ORDERDATE,
                ORDERS.O_TOTALPRICE
            )
            .from(ORDERS)
            .orderBy(ORDERS.O_ORDERKEY.asc())
            .fetch();
    }

    // E3) Distinct
    public List<?> e3() {
        return dslContext
            .selectDistinct(
                CUSTOMER.C_NATIONKEY,
                CUSTOMER.C_MKTSEGMENT
            )
            .from(CUSTOMER)
            .fetch();
    }

    public List<?> q1(int days) {
    // Using static value 90 instead of parameterized days
    LocalDate cutoff = LocalDate.of(1998, 12, 1)
            .minusDays(90);

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
        .fetch();
    }

    public List<?> q2(int size, String type, String region) {
        return dslContext
            .select(
                SUPPLIER.S_ACCTBAL.as("acctbal"),
                SUPPLIER.S_NAME.as("name"),
                NATION.N_NAME.as("nationName"),
                PART.P_PARTKEY.as("partKey"),
                PART.P_MFGR.as("mfgr"),
                SUPPLIER.S_ADDRESS.as("address"),
                SUPPLIER.S_PHONE.as("phone"),
                SUPPLIER.S_COMMENT.as("comment")
            )
            .from(PART)
            .join(PARTSUPP).on(PART.P_PARTKEY.eq(PARTSUPP.PS_PARTKEY))
            .join(SUPPLIER).on(SUPPLIER.S_SUPPKEY.eq(PARTSUPP.PS_SUPPKEY))
            .join(NATION).on(SUPPLIER.S_NATIONKEY.eq(NATION.N_NATIONKEY))
            .join(REGION).on(NATION.N_REGIONKEY.eq(REGION.R_REGIONKEY))
            .where(PART.P_SIZE.eq(size))
            .and(PART.P_TYPE.like(type))
            .and(REGION.R_NAME.eq(region))
            .and(PARTSUPP.PS_SUPPLYCOST.eq(
                select(min(PARTSUPP.PS_SUPPLYCOST))
                .from(PARTSUPP)
                .join(SUPPLIER).on(SUPPLIER.S_SUPPKEY.eq(PARTSUPP.PS_SUPPKEY))
                .join(NATION).on(SUPPLIER.S_NATIONKEY.eq(NATION.N_NATIONKEY))
                .join(REGION).on(NATION.N_REGIONKEY.eq(REGION.R_REGIONKEY))
                .where(PART.P_PARTKEY.eq(PARTSUPP.PS_PARTKEY))
                .and(REGION.R_NAME.eq(region))
            ))
            .orderBy(
                SUPPLIER.S_ACCTBAL.desc(),
                NATION.N_NAME.asc(),
                SUPPLIER.S_NAME.asc(),
                PART.P_PARTKEY.asc()
            )
            .limit(100)
            .fetch();
    }

    public List<?> q3(String segment, LocalDate orderDate, LocalDate shipDate) {
        return dslContext
            .select(
                LINEITEM.L_ORDERKEY.as("orderKey"),
                sum(LINEITEM.L_EXTENDEDPRICE.mul(one().minus(LINEITEM.L_DISCOUNT))).as("revenue"),
                ORDERS.O_ORDERDATE.as("orderDate"),
                ORDERS.O_SHIPPRIORITY.as("shipPriority")
            )
            .from(CUSTOMER)
            .join(ORDERS).on(CUSTOMER.C_CUSTKEY.eq(ORDERS.O_CUSTKEY))
            .join(LINEITEM).on(LINEITEM.L_ORDERKEY.eq(ORDERS.O_ORDERKEY))
            .where(CUSTOMER.C_MKTSEGMENT.eq(segment))
            .and(ORDERS.O_ORDERDATE.lt(orderDate))
            .and(LINEITEM.L_SHIPDATE.gt(shipDate))
            .groupBy(LINEITEM.L_ORDERKEY, ORDERS.O_ORDERDATE, ORDERS.O_SHIPPRIORITY)
            .orderBy(field("revenue").desc(), ORDERS.O_ORDERDATE.asc())
            .limit(10)
            .fetch();
    }

    public List<?> q4(LocalDate orderDate) {
        LocalDate endDate = orderDate.plusMonths(3);

        return dslContext
            .select(
                ORDERS.O_ORDERPRIORITY.as("orderPriority"),
                count().as("orderCount")
            )
            .from(ORDERS)
            .where(ORDERS.O_ORDERDATE.ge(orderDate))
            .and(ORDERS.O_ORDERDATE.lt(endDate))
            .and(exists(
                select(val(1))
                .from(LINEITEM)
                .where(LINEITEM.L_ORDERKEY.eq(ORDERS.O_ORDERKEY))
                .and(LINEITEM.L_COMMITDATE.lt(LINEITEM.L_RECEIPTDATE))
            ))
            .groupBy(ORDERS.O_ORDERPRIORITY)
            .orderBy(ORDERS.O_ORDERPRIORITY.asc())
            .fetch();
    }

    public List<?> q5(String region, LocalDate orderDate) {
        LocalDate endDate = orderDate.plusYears(1);

        return dslContext
            .select(
                NATION.N_NAME.as("nationName"),
                sum(LINEITEM.L_EXTENDEDPRICE.mul(one().minus(LINEITEM.L_DISCOUNT))).as("revenue")
            )
            .from(CUSTOMER)
            .join(ORDERS).on(CUSTOMER.C_CUSTKEY.eq(ORDERS.O_CUSTKEY))
            .join(LINEITEM).on(LINEITEM.L_ORDERKEY.eq(ORDERS.O_ORDERKEY))
            .join(SUPPLIER).on(LINEITEM.L_SUPPKEY.eq(SUPPLIER.S_SUPPKEY))
            .join(NATION).on(SUPPLIER.S_NATIONKEY.eq(NATION.N_NATIONKEY))
            .join(REGION).on(NATION.N_REGIONKEY.eq(REGION.R_REGIONKEY))
            .where(REGION.R_NAME.eq(region))
            .and(ORDERS.O_ORDERDATE.ge(orderDate))
            .and(ORDERS.O_ORDERDATE.lt(endDate))
            .and(CUSTOMER.C_NATIONKEY.eq(SUPPLIER.S_NATIONKEY))
            .groupBy(NATION.N_NAME)
            .orderBy(field("revenue").desc())
            .fetch();
    }
}
