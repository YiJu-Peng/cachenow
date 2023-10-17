package com.example.cachenow.utils.other;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;

/**
 * 时间  17/10/2023 上午 10:16
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 计算jvm的负载情况
 * 因为我们这个地方计算阈值的方法是通过线程数来进行计算的
 * 然后我们的计算机是可以通过片值的转换在多个线程之间来回的进行转化
 * 差不多运行中的线程有核心的两倍的时候可以认为是最优效率
 */

class SystemLoadMonitor {
    private static final double cpuUsageThreshold = 1.9;  // 设置默认的 CPU 利用率阈值为 1.9
    public static boolean isOverLoad() {

        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();  // 获取 ThreadMXBean 对象
        int threadCount = threadMXBean.getThreadCount();  // 获取当前运行的线程数
        double cpuUsage =
                (double) threadCount / Runtime.getRuntime().availableProcessors();  // 计算 CPU 利用率
        //这个地方的分母我们一般是认为是线程的最大值
        return cpuUsage>cpuUsageThreshold;
    }

}