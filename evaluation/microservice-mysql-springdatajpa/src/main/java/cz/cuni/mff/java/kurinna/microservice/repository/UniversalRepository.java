package cz.cuni.mff.java.kurinna.microservice.repository;

import org.springframework.data.repository.NoRepositoryBean;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for executing SQL queries using Spring Data JPA.
 * This interface defines methods for various types of database operations and queries,
 * including simple selects, joins, aggregations, and TPC-H benchmark queries.
 * Implementations of this interface will provide the actual query execution logic.
 */
@NoRepositoryBean
public interface UniversalRepository {
    /**
     * A1) Executes a query on non-indexed columns.
     * Retrieves all records from the lineitem table.
     *
     * @return List of Object arrays containing all lineitem records
     */
    List<Object[]> a1();

    /**
     * A2) Executes a range query on non-indexed columns.
     * Retrieves orders within a specified date range.
     *
     * @param startDate The start date of the range (inclusive)
     * @param endDate The end date of the range (inclusive)
     * @return List of Object arrays containing orders within the date range
     */
    List<Object[]> a2(LocalDate startDate, LocalDate endDate);

    /**
     * A3) Executes a query on indexed columns.
     * Retrieves all records from the customer table.
     *
     * @return List of Object arrays containing all customer records
     */
    List<Object[]> a3();

    /**
     * A4) Executes a range query on indexed columns.
     * Retrieves orders within a specified order key range.
     *
     * @param minOrderKey The minimum order key (inclusive)
     * @param maxOrderKey The maximum order key (inclusive)
     * @return List of Object arrays containing orders within the order key range
     */
    List<Object[]> a4(int minOrderKey, int maxOrderKey);

    /**
     * B1) Executes a COUNT aggregation query.
     * Counts orders grouped by month and returns the count for each month.
     *
     * @return List of Object arrays containing order counts by month
     */
    List<Object[]> b1();

    /**
     * B2) Executes a MAX aggregation query.
     * Finds the maximum extended price for line items grouped by ship month.
     *
     * @return List of Object arrays containing maximum prices by ship month
     */
    List<Object[]> b2();

    /**
     * C1) Executes a join query on non-indexed columns.
     * Performs a Cartesian product between customer and orders tables.
     *
     * @return List of Object arrays containing customer names and order details
     */
    List<Object[]> c1();

    /**
     * C2) Executes a join query on indexed columns.
     * Joins customer and orders tables on customer key.
     *
     * @return List of Object arrays containing customer names and order details
     */
    List<Object[]> c2();

    /**
     * C3) Executes a complex join query (first level).
     * Joins customer, nation, and orders tables.
     *
     * @return List of Object arrays containing customer names, nation names, and order details
     */
    List<Object[]> c3();

    /**
     * C4) Executes a complex join query (second level).
     * Joins customer, nation, region, and orders tables.
     *
     * @return List of Object arrays containing customer names, nation names, region names, and order details
     */
    List<Object[]> c4();

    /**
     * C5) Executes a left outer join query.
     * Performs a left outer join between customer and orders tables.
     *
     * @return List of Object arrays containing customer details and their orders (if any)
     */
    List<Object[]> c5();

    /**
     * D1) Executes a UNION set operation query.
     * Combines nation keys from both customer and supplier tables.
     *
     * @return List of Object arrays containing unique nation keys from both tables
     */
    List<Object[]> d1();

    /**
     * D2) Executes an INTERSECT-like set operation query.
     * Finds customer keys that also exist as supplier keys.
     *
     * @return List of Object arrays containing customer keys that are also supplier keys
     */
    List<Object[]> d2();

    /**
     * D3) Executes a DIFFERENCE-like set operation query.
     * Finds customer keys that do not exist as supplier keys.
     *
     * @return List of Object arrays containing customer keys that are not supplier keys
     */
    List<Object[]> d3();

    /**
     * E1) Executes a sorting query on non-indexed columns.
     * Retrieves customer information sorted by account balance in descending order.
     *
     * @return List of Object arrays containing customer data sorted by account balance
     */
    List<Object[]> e1();

    /**
     * E2) Executes a sorting query on indexed columns.
     * Retrieves order information sorted by order key.
     *
     * @return List of Object arrays containing order data sorted by order key
     */
    List<Object[]> e2();

    /**
     * E3) Executes a query with DISTINCT operator.
     * Retrieves unique combinations of nation key and market segment from customers.
     *
     * @return List of Object arrays containing unique nation key and market segment combinations
     */
    List<Object[]> e3();

    /**
     * Executes TPC-H Query 1: Pricing Summary Report.
     * This query reports the amount of business that was billed, shipped, and returned.
     *
     * @param days Number of days to subtract from the cutoff date (1998-12-01)
     * @return List of Object arrays containing pricing summary information
     */
    List<Object[]> q1(int days);

    /**
     * Executes TPC-H Query 2: Minimum Cost Supplier.
     * This query finds which supplier should be selected to place an order for a given part in a given region.
     *
     * @param size The size of the part
     * @param type The type of the part (used in LIKE pattern)
     * @param region The name of the region
     * @return List of Object arrays containing supplier information
     */
    List<Object[]> q2(int size, String type, String region);

    /**
     * Executes TPC-H Query 3: Shipping Priority.
     * This query retrieves the 10 unshipped orders with the highest value.
     *
     * @param segment The market segment to consider
     * @param orderDate The cutoff date for orders
     * @param shipDate The cutoff date for shipments
     * @return List of Object arrays containing order information sorted by revenue
     */
    List<Object[]> q3(String segment, LocalDate orderDate, LocalDate shipDate);

    /**
     * Executes TPC-H Query 4: Order Priority Checking.
     * This query determines how well the order priority system is working.
     *
     * @param orderDate The start date for the three-month period
     * @return List of Object arrays containing order counts by priority
     */
    List<Object[]> q4(LocalDate orderDate);

    /**
     * Executes TPC-H Query 5: Local Supplier Volume.
     * This query lists the revenue volume done through local suppliers.
     *
     * @param region The name of the region
     * @param orderDate The start date for the one-year period
     * @return List of Object arrays containing revenue by nation
     */
    List<Object[]> q5(String region, LocalDate orderDate);
}
