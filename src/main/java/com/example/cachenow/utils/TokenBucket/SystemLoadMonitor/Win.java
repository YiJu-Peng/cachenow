package com.example.cachenow.utils.TokenBucket.SystemLoadMonitor;

import lombok.extern.slf4j.Slf4j;


/**
 * 时间  18/10/2023 下午 4:55
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 这个类是进行cpu占用率的读取的
 * 因为是直接的读取系统给出的占用率,而没有经过自己的运算,所以速度还算快
 * 注意此方法不是百分百给出cpu,当内存或是计算机的资源紧张的的时候就会出现获取失败的情况
 */
@Slf4j
public class Win extends SystemLoadInterface {

    static String[] command = new String[] { "wmic", "cpu", "get", "loadpercentage"};
    public boolean isOverLoad() {
        return process(command);
    }

}
