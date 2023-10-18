package com.example.cachenow.utils.other;
import com.example.cachenow.utils.other.SystemLoadMonitor.CentOs;
import com.example.cachenow.utils.other.SystemLoadMonitor.SystemLoadInterface;
import com.example.cachenow.utils.other.SystemLoadMonitor.Win;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.*;

/**
 * 时间  17/10/2023 上午 9:28
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 令牌桶算法控制流量
 * 此算法给出的默认值会自动的根据系统的负载状况进行调整
 */
@Component
@Slf4j
public class TokenBucket {
    private int capacity=2000;  // 令牌桶容量
    private int rate=500;      // 令牌生成速率
    private static BlockingQueue<Object> tokens;  // 令牌队列;
    private static SystemLoadInterface systemLoadMonitor;



    public TokenBucket(int capacity, int rate) {
        this.capacity = capacity;
        this.rate = rate;
        tokens = new LinkedBlockingQueue<>(capacity);
        TokenBucketstart();
    }
    public TokenBucket() {
        tokens = new LinkedBlockingQueue<>(capacity);
        // 启动令牌生成线程
        TokenBucketstart();
    }
    private void TokenBucketstart (){

        //设置和系统匹配的获取cpu的类(win,或者是centos)
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.contains("windows")) {
            systemLoadMonitor = new Win();
        }else if(osName.contains("linux")){
            systemLoadMonitor = new CentOs();
        }else {
            systemLoadMonitor = new Win(); //默认是win系统
            log.error("暂时不支持您的系统,默认使用win系统的实现,关闭rate自动调节功能");
        }


        Thread tokenGenerationThread = new Thread(this::generateTokens);
        tokenGenerationThread.start();
        // 启动令牌速率调节线程
        Thread rateAdjustmentThread = new Thread(this::adjustRate);
        rateAdjustmentThread.start();
    }

    public boolean getToken() {
        return tokens.poll() != null;
    }

    private void generateTokens() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            if (tokens.size() < capacity) {
                tokens.add(new Object());// 生成新的令牌
            }
        }, 0, 1000 / rate, TimeUnit.MILLISECONDS);
    }
    private void adjustRate() {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(() -> {
            try {
                if (systemLoadMonitor.isOverLoad()) {
                    log.error("CPU利用率过高，令牌生成速率调整为：" + rate);
                    rate = (int) (rate * 0.9); // 当 CPU 利用率高于阈值时，降低令牌生成速率
                } else {
                    rate = (int) (rate / 0.9); // 当 CPU 利用率低于或等于阈值时，恢复令牌生成速率
                    if (rate > 0 && rate < capacity) {
                        rate++; // 防止速率过小，导致无法生成足够的令牌
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
}