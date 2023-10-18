package com.example.cachenow.utils.other.SystemLoadMonitor;

/**
 * 时间  18/10/2023 下午 4:56
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
public interface SystemLoadInterface {
    double THRESHOLD = 80.0;  // 定义设定的阀值

    boolean isOverLoad();
}
