package com.example.cachenow.utils.other;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * 时间  17/10/2023 上午 9:28
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 令牌桶算法控制流量
 * 此算法给出的默认值会自动的根据系统的负载状况进行调整
 */
@Component
public class TokenBucket {
    private int capacity=2000;  // 令牌桶容量
    private int rate=500;      // 令牌生成速率
    private static BlockingQueue<Object> tokens;  // 令牌队列



    public TokenBucket(int capacity, int rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.tokens = new LinkedBlockingQueue<>(capacity);

        // 启动令牌生成线程
        Thread tokenGenerationThread = new Thread(this::generateTokens);
        tokenGenerationThread.start();
        // 启动令牌速率调节线程
        Thread rateAdjustmentThread = new Thread(this::adjustRate);
        rateAdjustmentThread.start();
    }
    public TokenBucket() {
    }

    public boolean getToken() {
        return tokens.poll() != null;
    }

    private void generateTokens() {
        while (true) {
            if (tokens.size() < capacity) {
                tokens.add(new Object());// 生成新的令牌
            }
            try {
                Thread.sleep(1000 / rate);  // 按照速率控制令牌生成的时间间隔
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void adjustRate(){

        while(true){
            try {
                if (SystemLoadMonitor.isOverLoad()) {
                    rate = (int) (rate * 0.9);  // 当 CPU 利用率高于阈值时，降低令牌生成速率
                } else {
                    rate = (int) (rate / 0.9);  // 当 CPU 利用率低于或等于阈值时，恢复令牌生成速率
                    if (rate > 0 && rate < capacity) {
                        rate++;  // 防止速率过小，导致无法生成足够的令牌
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}