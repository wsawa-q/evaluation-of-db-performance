package cz.cuni.mff.java.kurinna.common.service;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface defining database query operations for performance evaluation across different database technologies.
 * This interface provides a standardized set of query methods that can be implemented by various
 * database access technologies (JDBC, jOOQ, MyBatis, Spring Data JPA, etc.) for performance comparison.
 *
 * @param <T> the type of objects returned in the result list, varies by implementation technology
 *           (e.g., Map&lt;String, Object&gt; for JDBC, DataRow for Cayenne, Object[] for Spring Data JPA)
 */
public interface IQueryService<T> {
    /**
     * Query A1: Performs a query on non-indexed columns.
     * Tests basic query performance without index optimization.
     *
     * @return a list of results from the query
     */
    public List<T> a1();

    /**
     * Query A2: Performs a range query on non-indexed columns.
     * Tests range query performance without index optimization.
     *
     * @param startDate the start date for the range query
     * @param endDate the end date for the range query
     * @return a list of results from the query
     */
    public List<T> a2(LocalDate startDate, LocalDate endDate);

    /**
     * Query A3: Performs a query on indexed columns.
     * Tests basic query performance with index optimization.
     *
     * @return a list of results from the query
     */
    public List<T> a3();

    /**
     * Query A4: Performs a range query on indexed columns.
     * Tests range query performance with index optimization.
     *
     * @param minOrderKey the minimum order key for the range query
     * @param maxOrderKey the maximum order key for the range query
     * @return a list of results from the query
     */
    public List<T> a4(int minOrderKey, int maxOrderKey);

    /**
     * Query B1: Performs a COUNT aggregate function.
     * Tests performance of the COUNT operation.
     *
     * @return a list of results containing count values
     */
    public List<T> b1();

    /**
     * Query B2: Performs a MAX aggregate function.
     * Tests performance of the MAX operation.
     *
     * @return a list of results containing maximum values
     */
    public List<T> b2();

    /**
     * Query C1: Performs a join operation on non-indexed columns.
     * Tests join performance without index optimization.
     *
     * @return a list of results from the join operation
     */
    public List<T> c1();

    /**
     * Query C2: Performs a join operation on indexed columns.
     * Tests join performance with index optimization.
     *
     * @return a list of results from the join operation
     */
    public List<T> c2();

    /**
     * Query C3: Performs a complex join operation (type 1).
     * Tests performance of complex joins involving multiple tables.
     *
     * @return a list of results from the complex join operation
     */
    public List<T> c3();

    /**
     * Query C4: Performs a complex join operation (type 2).
     * Tests performance of another type of complex join involving multiple tables.
     *
     * @return a list of results from the complex join operation
     */
    public List<T> c4();

    /**
     * Query C5: Performs a left outer join operation.
     * Tests performance of left outer joins.
     *
     * @return a list of results from the left outer join operation
     */
    public List<T> c5();

    /**
     * Query D1: Performs a UNION set operation.
     * Tests performance of the UNION operation.
     *
     * @return a list of results from the UNION operation
     */
    public List<T> d1();

    /**
     * Query D2: Performs an INTERSECT set operation.
     * Tests performance of the INTERSECT operation.
     *
     * @return a list of results from the INTERSECT operation
     */
    public List<T> d2();

    /**
     * Query D3: Performs a DIFFERENCE set operation (EXCEPT or MINUS).
     * Tests performance of the DIFFERENCE operation.
     *
     * @return a list of results from the DIFFERENCE operation
     */
    public List<T> d3();

    /**
     * Query E1: Performs sorting on non-indexed columns.
     * Tests sorting performance without index optimization.
     *
     * @return a list of sorted results
     */
    public List<T> e1();

    /**
     * Query E2: Performs sorting on indexed columns.
     * Tests sorting performance with index optimization.
     *
     * @return a list of sorted results
     */
    public List<T> e2();

    /**
     * Query E3: Performs a DISTINCT operation.
     * Tests performance of the DISTINCT operation.
     *
     * @return a list of distinct results
     */
    public List<T> e3();

    /**
     * Business Query Q1: Retrieves data based on a date range calculated from current date.
     * 
     * @param deltaDays the number of days to subtract from current date
     * @return a list of results matching the date criteria
     */
    public List<T> q1(int deltaDays);

    /**
     * Business Query Q2: Retrieves data filtered by size, type, and region.
     * 
     * @param size the size parameter for filtering
     * @param type the type parameter for filtering
     * @param region the region parameter for filtering
     * @return a list of results matching the filter criteria
     */
    public List<T> q2(int size, String type, String region);

    /**
     * Business Query Q3: Retrieves data filtered by segment and date range.
     * 
     * @param segment the segment parameter for filtering
     * @param orderDate the order date parameter for filtering
     * @param shipDate the ship date parameter for filtering
     * @return a list of results matching the filter criteria
     */
    public List<T> q3(String segment, LocalDate orderDate, LocalDate shipDate);

    /**
     * Business Query Q4: Retrieves data filtered by order date.
     * 
     * @param orderDate the order date parameter for filtering
     * @return a list of results matching the order date
     */
    public List<T> q4(LocalDate orderDate);

    /**
     * Business Query Q5: Retrieves data filtered by region and order date.
     * 
     * @param region the region parameter for filtering
     * @param orderDate the order date parameter for filtering
     * @return a list of results matching the region and order date
     */
    public List<T> q5(String region, LocalDate orderDate);
}
