package org.overflow.util.profiling;

import java.lang.management.ManagementFactory;

/**
 * Author: Tom
 * Date: 03/04/13
 * Time: 12:52
 */
public final class SystemProfiler {

    private static final double BYTE_TO_MB = 1048576;
    private static int updateRate = 300;

    private final Runtime         runtime;
    private final ThreadGroupData scriptThreadData;

    private double totalMemory;
    private double freeMemory;
    private double usedMemory;

    private long nextUpdate = 0;

    public SystemProfiler(final ThreadGroup threadGroup) {
        scriptThreadData = new ThreadGroupData(runtime = Runtime.getRuntime(),  ManagementFactory.getThreadMXBean(), threadGroup);
    }

    public SystemProfiler() {
        this(Thread.currentThread().getThreadGroup());
    }

    public final void update() {
        if (System.currentTimeMillis() > nextUpdate) {
            long currentTime = System.currentTimeMillis();
            totalMemory = runtime.totalMemory() / BYTE_TO_MB;
            freeMemory = runtime.freeMemory() / BYTE_TO_MB;
            usedMemory = (totalMemory - freeMemory);
            nextUpdate = currentTime + updateRate;
        }
    }

    public final double getScriptCpuUsage() {
        return scriptThreadData.getCpuUsage();
    }

    public final double getScriptAverageCpuUsage() {
        return scriptThreadData.getAverageCpuUsage();
    }

    public final ThreadGroupData getScriptThreadData() {
        return scriptThreadData;
    }

    public final double getFreeMemory() {
        update();
        return freeMemory;
    }

    public final double getTotalMemory() {
        update();
        return totalMemory;
    }

    public final double getUsedMemory() {
        update();
        return usedMemory;
    }

}
