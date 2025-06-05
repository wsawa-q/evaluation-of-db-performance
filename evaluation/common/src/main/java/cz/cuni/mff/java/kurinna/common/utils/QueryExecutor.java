package cz.cuni.mff.java.kurinna.common.utils;

import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
        Recording recording = new Recording();
        Path tempJfrFile = null;

        try {
            recording.setName("QueryRecording");
            recording.enable("jdk.ObjectAllocationInNewTLAB");
            recording.enable("jdk.ObjectAllocationOutsideTLAB");
            recording.enable("jdk.GarbageCollection");
            recording.enable("jdk.HeapSummary");

            System.gc();
            Thread.sleep(100);

            recording.start();
//            List<MemoryPoolMXBean> heapPoolsBefore = MemUtil.heapPools();
//            long altBefore = MemUtil.getUsage();
//            long before = MemUtil.usedHeapBytes();
            long t0 = System.nanoTime();

            // Execute the query
            T result = supplier.get();

            long elapsed = System.nanoTime() - t0;
//            System.gc();
//            Thread.sleep(100);

//            long altAfter = MemUtil.getUsage();
//            long after = MemUtil.usedHeapBytes();
//            List<MemoryPoolMXBean> heapPoolsAfter = MemUtil.heapPools();
//            long diff = after - before;

            recording.stop();
            tempJfrFile = Files.createTempFile("query-execution", ".jfr");
            recording.dump(tempJfrFile);

            Map<String, Object> jfrStats = parseJfrFile(tempJfrFile);

            response.put("delta", jfrStats.get("totalAllocated"));
            response.put("jfr", jfrStats);
//            response.put("altBefore", altBefore);
//            response.put("altAfter", altAfter);
//            response.put("altDiff", altAfter - altBefore);
//            response.put("memoryBefore", heapPoolsBefore.stream()
//                    .map(p -> Map.of(
//                            "name", p.getName(),
//                            "used", p.getUsage().getUsed(),
//                            "committed", p.getUsage().getCommitted(),
//                            "max", p.getUsage().getMax(),
//                            "type", p.getType().name()))
//                    .toList());
//            response.put("memoryAfter", heapPoolsAfter.stream()
//                    .map(p -> Map.of(
//                            "name", p.getName(),
//                            "used", p.getUsage().getUsed(),
//                            "committed", p.getUsage().getCommitted(),
//                            "max", p.getUsage().getMax(),
//                            "type", p.getType().name()))
//                    .toList());
            response.put("elapsed", elapsed);
            response.put("status", "success");
            response.put("result", result);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("error", e.getMessage());
        } finally {
            recording.close();
            try {
                if (tempJfrFile != null) Files.deleteIfExists(tempJfrFile);
            } catch (IOException ignored) {}
        }

        return response;
    }

    private static Map<String, Object> parseJfrFile(Path jfrFile) {
        Map<String, Object> jfrStats = new HashMap<>();
        int gcCount = 0;
        long totalHeapUsed = 0;
        int heapSamples = 0;
        long allocatedInsideTLAB = 0;
        long allocatedOutsideTLAB = 0;

        try (RecordingFile rf = new RecordingFile(jfrFile)) {
            while (rf.hasMoreEvents()) {
                RecordedEvent event = rf.readEvent();
                String name = event.getEventType().getName();

                switch (name) {
                    case "jdk.HeapSummary" -> {
                        long heapUsed = event.getLong("heapUsed");
                        totalHeapUsed += heapUsed;
                        heapSamples++;
                    }
                    case "jdk.GarbageCollection" -> {
                        gcCount++;
                    }
                    case "jdk.ObjectAllocationInNewTLAB" -> {
                        long size = event.getLong("allocationSize");
                        allocatedInsideTLAB += size;
                    }
                    case "jdk.ObjectAllocationOutsideTLAB" -> {
                        long size = event.getLong("allocationSize");
                        allocatedOutsideTLAB += size;
                    }
                }
            }
        } catch (Exception ignored) {}

        jfrStats.put("heapUsedAvg", heapSamples > 0 ? totalHeapUsed / heapSamples : 0);
        jfrStats.put("gcCount", gcCount);
        jfrStats.put("allocatedInsideTLAB", allocatedInsideTLAB);
        jfrStats.put("allocatedOutsideTLAB", allocatedOutsideTLAB);
        jfrStats.put("totalAllocated", allocatedInsideTLAB + allocatedOutsideTLAB);

        return jfrStats;
    }
}
