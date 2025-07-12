package cz.cuni.mff.java.kurinna.common.controller;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Interface defining all query endpoints for microservices.
 * Each microservice should implement this interface to provide consistent API.
 */
public interface IQueryController {
    /**
     * Health check endpoint.
     * @return Response with status "OK" if service is running
     */
    ResponseEntity<String> health();

    /**
     * A1) Non-Indexed Columns query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> a1();

    /**
     * A2) Non-Indexed Columns — Range Query.
     * @param startDate Start date for the range query in ISO format (yyyy-MM-dd)
     * @param endDate End date for the range query in ISO format (yyyy-MM-dd)
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> a2(String startDate, String endDate);

    /**
     * A3) Indexed Columns query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> a3();

    /**
     * A4) Indexed Columns — Range Query.
     * @param minOrderKey Minimum order key value
     * @param maxOrderKey Maximum order key value
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> a4(int minOrderKey, int maxOrderKey);

    /**
     * B1) COUNT aggregate function query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> b1();

    /**
     * B2) MAX aggregate function query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> b2();

    /**
     * C1) Non-Indexed Columns join query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> c1();

    /**
     * C2) Indexed Columns join query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> c2();

    /**
     * C3) Complex Join 1 query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> c3();

    /**
     * C4) Complex Join 2 query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> c4();

    /**
     * C5) Left Outer Join query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> c5();

    /**
     * D1) UNION set operation query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> d1();

    /**
     * D2) INTERSECT set operation query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> d2();

    /**
     * D3) DIFFERENCE set operation query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> d3();

    /**
     * E1) Non-Indexed Columns Sorting query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> e1();

    /**
     * E2) Indexed Columns Sorting query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> e2();

    /**
     * E3) Distinct query.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> e3();

    /**
     * Q1) TPC-H Query 1 variant.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> q1();

    /**
     * Q2) TPC-H Query 2 variant.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> q2();

    /**
     * Q3) TPC-H Query 3 variant.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> q3();

    /**
     * Q4) TPC-H Query 4 variant.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> q4();

    /**
     * Q5) TPC-H Query 5 variant.
     * @return Response containing query results and execution metrics
     */
    ResponseEntity<Map<String, Object>> q5();
}
