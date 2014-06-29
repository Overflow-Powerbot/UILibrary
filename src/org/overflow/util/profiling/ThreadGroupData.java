package org.overflow.util.profiling;

import java.lang.management.ThreadMXBean;

/**
 * Author: Tom
 * Date: 03/04/13
 * Time: 22:20
 */

public final class ThreadGroupData {

    private static int updateRate = 300;

    private final Runtime runtime;
    private final ThreadMXBean threadMXBean;
    private final ThreadGroup threadGroup;

    public ThreadGroupData(final Runtime runtime, final ThreadMXBean threadMXBean, final ThreadGroup threadGroup) {
        this.runtime = runtime;
        this.threadMXBean = threadMXBean;
        this.threadGroup = threadGroup;
    }

    private long previousCpuTime;
    private double averageCpuUsage;
    private double lastCpuUsage;

    private double[] cpuHistory = new double[3600];
    private int historyIndex = 0;
    private int cachedValues = 0;

    private long lastUpdateTime;
    private long nextUpdate = 0;

    public void update() {
        if (System.currentTimeMillis() > nextUpdate) {
            long currentTime = System.currentTimeMillis();
            Thread[] threads = new Thread[threadGroup.activeCount()];
            threadGroup.enumerate(threads);
            long currentCpuTime = 0;
            for (Thread t : threads) {
                currentCpuTime += threadMXBean.getThreadCpuTime(t.getId());
            }
            double usedCpuTime = (currentCpuTime - previousCpuTime);
            double changeInTime = currentTime - lastUpdateTime;
            lastCpuUsage = Math.min((usedCpuTime / changeInTime) / (10000d * runtime.availableProcessors()), 100);
            cpuHistory[historyIndex = (++historyIndex % cpuHistory.length)] = lastCpuUsage;
            if (cachedValues <= cpuHistory.length) {
                cachedValues++;
            }
            double count = 0;
            for (int i = 0; i < cachedValues; i++) {
                count += cpuHistory[i];
            }
            averageCpuUsage = (count / cachedValues);
            previousCpuTime = currentCpuTime;
            lastUpdateTime = currentTime;
            nextUpdate = currentTime + updateRate;
        }
    }

    public double getAverageCpuUsage() {
        update();
        return averageCpuUsage;
    }

    public double getCpuUsage() {
        update();
        return lastCpuUsage;
    }

    public void reset() {
        cpuHistory = new double[3600];
        historyIndex = 0;
        cachedValues = 0;
    }
}
