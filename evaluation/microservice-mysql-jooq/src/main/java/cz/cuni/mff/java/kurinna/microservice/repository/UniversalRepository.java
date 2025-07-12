package cz.cuni.mff.java.kurinna.microservice.repository;

import org.jooq.DSLContext;
import static org.jooq.impl.DSL.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static cz.cuni.mff.java.kurinna.microservice.model.tables.Lineitem.LINEITEM;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Part.PART;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Supplier.SUPPLIER;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Partsupp.PARTSUPP;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Nation.NATION;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Region.REGION;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Customer.CUSTOMER;
import static cz.cuni.mff.java.kurinna.microservice.model.tables.Orders.ORDERS;

/**
 * Repository class for executing SQL queries using jOOQ.
 * This class provides methods for various types of database operations and queries,
 * including simple selects, joins, aggregations, and TPC-H benchmark queries.
 * jOOQ provides a type-safe SQL building API that allows for fluent query construction.
 */
@Repository
public class UniversalRepository {
    private final DSLContext dslContext;

    /**
     * Constructs a new UniversalRepository with the specified DSLContext.
     *
     * @param dslContext The jOOQ DSLContext used for building and executing queries
     */
    public UniversalRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    /**
     * A1) Executes a query on non-indexed columns.
     * Retrieves all records from the lineitem table.
     *
     * @return List of maps containing all lineitem records
     */
    public List<Map<String, Object>> a1() {
        return dslContext
            .select()
            .from(LINEITEM)
            .fetchMaps();
    }

    /**
     * A2) Executes a range query on non-indexed columns.
     * Retrieves orders within a specified date range.
     *
     * @param startDate The start date of the range (inclusive)
     * @param endDate The end date of the range (inclusive)
     * @return List of maps containing orders within the date range
     */
    public List<Map<String, Object>> a2(LocalDate startDate, LocalDate endDate) {
        return dslContext
            .select()
            .from(ORDERS)
            .where(ORDERS.O_ORDERDATE.between(startDate, endDate))
            .fetchMaps();
    }

    /**
     * A3) Executes a query on indexed columns.
     * Retrieves all records from the customer table.
     *
     * @return List of maps containing all customer records
     */
    public List<Map<String, Object>> a3() {
        return dslContext
            .select()
            .from(CUSTOMER)
            .fetchMaps();
    }

    /**
     * A4) Executes a range query on indexed columns.
     * Retrieves orders within a specified order key range.
     *
     * @param minOrderKey The minimum order key (inclusive)
     * @param maxOrderKey The maximum order key (inclusive)
     * @return List of maps containing orders within the order key range
     */
    public List<Map<String, Object>> a4(int minOrderKey, int maxOrderKey) {
        return dslContext
            .select()
            .from(ORDERS)
            .where(ORDERS.O_ORDERKEY.between((long) minOrderKey, (long) maxOrderKey))
            .fetchMaps();
    }

    /**
     * B1) Executes a COUNT aggregation query.
     * Counts orders grouped by month and returns the count for each month.
     *
     * @return List of maps containing order counts by month
     */
    public List<Map<String, Object>> b1() {
        return dslContext
            .select(
                count(ORDERS.O_ORDERKEY).as("order_count"),
                function("DATE_FORMAT", String.class, ORDERS.O_ORDERDATE, inline("%Y-%m")).as("order_month")
            )
            .from(ORDERS)
            .groupBy(field("order_month"))
            .fetchMaps();
    }

    /**
     * B2) Executes a MAX aggregation query.
     * Finds the maximum extended price for line items grouped by ship month.
     *
     * @return List of maps containing maximum prices by ship month
     */
    public List<Map<String, Object>> b2() {
        return dslContext
            .select(
                function("DATE_FORMAT", String.class, LINEITEM.L_SHIPDATE, inline("%Y-%m")).as("ship_month"),
                max(LINEITEM.L_EXTENDEDPRICE).as("max_price")
            )
            .from(LINEITEM)
            .groupBy(field("ship_month"))
            .fetchMaps();
    }

    /**
     * C1) Executes a join query on non-indexed columns.
     * Performs a Cartesian product between customer and orders tables.
     *
     * @return List of maps containing customer names and order details
     */
    public List<Map<String, Object>> c1() {
        return dslContext
            .select(
                CUSTOMER.C_NAME,
                ORDERS.O_ORDERDATE,
                ORDERS.O_TOTALPRICE
            )
            .from(CUSTOMER, ORDERS)
            .fetchMaps();
    }

    /**
     * C2) Executes a join query on indexed columns.
     * Joins customer and orders tables on customer key.
     *
     * @return List of maps containing customer names and order details
     */
    public List<Map<String, Object>> c2() {
        return dslContext
            .select(
                CUSTOMER.C_NAME,
                ORDERS.O_ORDERDATE,
                ORDERS.O_TOTALPRICE
            )
            .from(CUSTOMER)
            .join(ORDERS).on(CUSTOMER.C_CUSTKEY.eq(ORDERS.O_CUSTKEY))
            .fetchMaps();
    }

    /**
     * C3) Executes a complex join query (first level).
     * Joins customer, nation, and orders tables.
     *
     * @return List of maps containing customer names, nation names, and order details
     */
    public List<Map<String, Object>> c3() {
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
            .fetchMaps();
    }

    /**
     * C4) Executes a complex join query (second level).
     * Joins customer, nation, region, and orders tables.
     *
     * @return List of maps containing customer names, nation names, region names, and order details
     */
    public List<Map<String, Object>> c4() {
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
            .fetchMaps();
    }

    /**
     * C5) Executes a left outer join query.
     * Performs a left outer join between customer and orders tables.
     *
     * @return List of maps containing customer details and their orders (if any)
     */
    public List<Map<String, Object>> c5() {
        return dslContext
            .select(
                CUSTOMER.C_CUSTKEY,
                CUSTOMER.C_NAME,
                ORDERS.O_ORDERKEY,
                ORDERS.O_ORDERDATE
            )
            .from(CUSTOMER)
            .leftOuterJoin(ORDERS).on(CUSTOMER.C_CUSTKEY.eq(ORDERS.O_CUSTKEY))
            .fetchMaps();
    }

    /**
     * D1) Executes a UNION set operation query.
     * Combines nation keys from both customer and supplier tables.
     *
     * @return List of maps containing unique nation keys from both tables
     */
    public List<Map<String, Object>> d1() {
        return dslContext
            .select(CUSTOMER.C_NATIONKEY.as("nationkey"))
            .from(CUSTOMER)
            .union(
                select(SUPPLIER.S_NATIONKEY.as("nationkey"))
                .from(SUPPLIER)
            )
            .fetchMaps();
    }

    /**
     * D2) Executes an INTERSECT-like set operation query.
     * Finds customer keys that also exist as supplier keys.
     *
     * @return List of maps containing customer keys that are also supplier keys
     */
    public List<Map<String, Object>> d2() {
        return dslContext
            .selectDistinct(CUSTOMER.C_CUSTKEY.as("custkey"))
            .from(CUSTOMER)
            .where(CUSTOMER.C_CUSTKEY.in(
                select(SUPPLIER.S_SUPPKEY)
                .from(SUPPLIER)
            ))
            .fetchMaps();
    }

    /**
     * D3) Executes a DIFFERENCE-like set operation query.
     * Finds customer keys that do not exist as supplier keys.
     *
     * @return List of maps containing customer keys that are not supplier keys
     */
    public List<Map<String, Object>> d3() {
        return dslContext
            .selectDistinct(CUSTOMER.C_CUSTKEY.as("custkey"))
            .from(CUSTOMER)
            .where(CUSTOMER.C_CUSTKEY.notIn(
                selectDistinct(SUPPLIER.S_SUPPKEY)
                .from(SUPPLIER)
            ))
            .fetchMaps();
    }

    /**
     * E1) Executes a sorting query on non-indexed columns.
     * Retrieves customer information sorted by account balance in descending order.
     *
     * @return List of maps containing customer data sorted by account balance
     */
    public List<Map<String, Object>> e1() {
        return dslContext
            .select(
                CUSTOMER.C_NAME,
                CUSTOMER.C_ADDRESS,
                CUSTOMER.C_ACCTBAL
            )
            .from(CUSTOMER)
            .orderBy(CUSTOMER.C_ACCTBAL.desc())
            .fetchMaps();
    }

    /**
     * E2) Executes a sorting query on indexed columns.
     * Retrieves order information sorted by order key.
     *
     * @return List of maps containing order data sorted by order key
     */
    public List<Map<String, Object>> e2() {
        return dslContext
            .select(
                ORDERS.O_ORDERKEY,
                ORDERS.O_CUSTKEY,
                ORDERS.O_ORDERDATE,
                ORDERS.O_TOTALPRICE
            )
            .from(ORDERS)
            .orderBy(ORDERS.O_ORDERKEY.asc())
            .fetchMaps();
    }

    /**
     * E3) Executes a query with DISTINCT operator.
     * Retrieves unique combinations of nation key and market segment from customers.
     *
     * @return List of maps containing unique nation key and market segment combinations
     */
    public List<Map<String, Object>> e3() {
        return dslContext
            .selectDistinct(
                CUSTOMER.C_NATIONKEY,
                CUSTOMER.C_MKTSEGMENT
            )
            .from(CUSTOMER)
            .fetchMaps();
    }

    /**
     * Executes TPC-H Query 1: Pricing Summary Report.
     * This query reports the amount of business that was billed, shipped, and returned.
     * Note: This implementation uses a static value of 90 days instead of the parameterized days.
     *
     * @param days Number of days to subtract from the cutoff date (1998-12-01)
     * @return List of maps containing pricing summary information
     */
    public List<Map<String, Object>> q1(int days) {
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
        .fetchMaps();
    }

    /**
     * Executes TPC-H Query 2: Minimum Cost Supplier.
     * This query finds which supplier should be selected to place an order for a given part in a given region.
     *
     * @param size The size of the part
     * @param type The type of the part (used in LIKE pattern)
     * @param region The name of the region
     * @return List of maps containing supplier information
     */
    public List<Map<String, Object>> q2(int size, String type, String region) {
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
            .fetchMaps();
    }

    /**
     * Executes TPC-H Query 3: Shipping Priority.
     * This query retrieves the 10 unshipped orders with the highest value.
     *
     * @param segment The market segment to consider
     * @param orderDate The cutoff date for orders
     * @param shipDate The cutoff date for shipments
     * @return List of maps containing order information sorted by revenue
     */
    public List<Map<String, Object>> q3(String segment, LocalDate orderDate, LocalDate shipDate) {
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
            .fetchMaps();
    }

    /**
     * Executes TPC-H Query 4: Order Priority Checking.
     * This query determines how well the order priority system is working.
     *
     * @param orderDate The start date for the three-month period
     * @return List of maps containing order counts by priority
     */
    public List<Map<String, Object>> q4(LocalDate orderDate) {
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
            .fetchMaps();
    }

    /**
     * Executes TPC-H Query 5: Local Supplier Volume.
     * This query lists the revenue volume done through local suppliers.
     *
     * @param region The name of the region
     * @param orderDate The start date for the one-year period
     * @return List of maps containing revenue by nation
     */
    public List<Map<String, Object>> q5(String region, LocalDate orderDate) {
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
            .fetchMaps();
    }
}
