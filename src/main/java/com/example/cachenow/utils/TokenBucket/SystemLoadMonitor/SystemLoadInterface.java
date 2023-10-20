package com.example.cachenow.utils.TokenBucket.SystemLoadMonitor;

import static com.example.cachenow.utils.Constants.TokenBucketConstants.SYSTEM_THRESHOLD;

/**
 * 时间  18/10/2023 下午 4:56
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
public interface SystemLoadInterface {
    double THRESHOLD = SYSTEM_THRESHOLD;  // 定义CPU设定的过载阀值,这个地方是说的是百分数

    boolean isOverLoad();
}
