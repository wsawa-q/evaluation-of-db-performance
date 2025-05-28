package cz.cuni.mff.java.kurinna.common.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class QueryExecutor {
    /**
     * Helper method to execute a query and measure its execution time and memory usage
     *
     * @param supplier A lambda that executes the query and returns the result
     * @return A map containing the result, execution time, and memory usage
     */
    public static <T> Map<String, Object> executeWithMeasurement(Supplier<T> supplier) {
        Map<String, Object> response = new HashMap<>();

        try {
            System.gc();
            Thread.sleep(50);
            long before = MemUtil.usedHeapBytes();
            long t0 = System.nanoTime();

            // Execute the query
            T result = supplier.get();

            long elapsed = System.nanoTime() - t0;
            System.gc();
            Thread.sleep(50);

            long after = MemUtil.usedHeapBytes();
            long diff = after - before;

            response.put("delta", diff);
            response.put("elapsed", elapsed);

            response.put("status", "success");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("error", e.getMessage());
        }

        return response;
    }
}
