package cz.cuni.mff.java.kurinna.common.utils;

import java.lang.management.*;
import java.util.List;

public class MemUtil {
    private static final List<MemoryPoolMXBean> HEAP_POOLS =
            ManagementFactory.getMemoryPoolMXBeans().stream()
                    .filter(p -> p.getType()==MemoryType.HEAP)
                    .toList();

    public static long usedHeapBytes() {
        return HEAP_POOLS.stream()
                .mapToLong(p -> p.getUsage().getUsed())
                .sum();
    }
}
