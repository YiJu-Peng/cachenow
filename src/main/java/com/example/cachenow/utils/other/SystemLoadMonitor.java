package com.example.cachenow.utils.other;

/**
 * 时间  17/10/2023 上午 10:16
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

class SystemLoadMonitor {
    public boolean isOverLoad() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        // 获取系统负载
        double systemLoadAverage = osBean.getSystemLoadAverage();


        // 获取可用处理器数量
        int availableProcessors = osBean.getAvailableProcessors();

        return availableProcessors > 0.8;
    }
}