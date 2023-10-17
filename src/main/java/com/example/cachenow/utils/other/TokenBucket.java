package com.example.cachenow.utils.other;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
/**
 * 时间  17/10/2023 上午 9:28
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
public class TokenBucket {
    private int capacity=2000;  // 令牌桶容量
    private int rate=1000;      // 令牌生成速率 (个/秒)
    private BlockingQueue<Object> tokens;  // 令牌队列
    private final double cpuUsageThreshold = 0.75;  // 设置默认的 CPU 利用率阈值为 0.75

    public TokenBucket(int capacity, int rate) {
        this.capacity = capacity;
        this.rate = rate;
        this.tokens = new LinkedBlockingQueue<>(capacity);

        // 启动令牌生成线程
        Thread tokenGenerationThread = new Thread(this::generateTokens);
        tokenGenerationThread.start();
    }
    public TokenBucket() {
    }

    public boolean getToken() {
        return tokens.poll() != null;
    }

    private void generateTokens() {
        while (true) {
            if (tokens.size() < capacity) {
                tokens.add(new Object());  // 生成新的令牌
            }
            try {
                Thread.sleep(1000 / rate);  // 按照速率控制令牌生成的时间间隔
                adjustRate();  // 调节令牌生成速率
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void adjustRate() {
        try {
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();  // 获取 ThreadMXBean 对象
            int threadCount = threadMXBean.getThreadCount();  // 获取当前运行的线程数
            double cpuUsage = (double) threadCount / Runtime.getRuntime().availableProcessors();  // 计算 CPU 利用率
            //这个地方的分母我们一般是认为是线程的最大值


            if (cpuUsage > cpuUsageThreshold) {
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
    }
}