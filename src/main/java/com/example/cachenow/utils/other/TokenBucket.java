package com.example.cachenow.utils.other;
import com.example.cachenow.utils.other.SystemLoadMonitor.Linux;
import com.example.cachenow.utils.other.SystemLoadMonitor.SystemLoadInterface;
import com.example.cachenow.utils.other.SystemLoadMonitor.Win;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 时间  17/10/2023 上午 9:28
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 令牌桶算法控制流量
 * 此算法给出的默认值会自动的根据系统的负载状况进行调整
 * 最好是开始前测试下最小和最大的rate值,适配自己的硬件环境
 */
@Component
@Slf4j
public class TokenBucket {
    private int capacity=2000;  // 令牌桶容量
    private int rate=500;      // 令牌生成速率(计算方法为 每1000/rate ms生成一个)
    private final int maxrate=2000;  // 最大令牌生成速率(计算方法为 每1000/rate ms生成一个)
    private final int minrate=200; //   最小令牌牌生成速率(计算方法为 每500/rate ms生成一个)
    private static BlockingQueue<Object> tokens;           // 令牌队列;
    private static SystemLoadInterface systemLoadMonitor;  // cpu占用率接口
    private static boolean IS_AUTO_RATE = true;  //是否开启自动调节rate(关闭了可能需要自己调试适配自己硬件的rate)



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
        //选择系统
        selectLoadSystem();
        // 启动令牌生成线程
        Thread tokenGenerationThread = new Thread(this::generateTokens);
        tokenGenerationThread.start();
        // 启动令牌速率调节线程
        if (IS_AUTO_RATE && systemLoadMonitor != null){
            Thread rateAdjustmentThread = new Thread(this::adjustRate);
            rateAdjustmentThread.start();
        }

    }

    public void selectLoadSystem() {
        //设置和系统匹配的获取cpu负载的类(win,或者是centos)
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.contains("windows")) {
            log.info("调用win系统");
            systemLoadMonitor = new Win();
        }else if(osName.contains("linux")){
            log.info("调用centos系统");
            systemLoadMonitor = new Linux();
        }else {
            systemLoadMonitor = new Win(); //默认是win系统
            log.error("暂时不支持您的系统,默认使用win系统的实现,关闭rate自动调节功能" +
                    ",您可能需要自己进行调试你的rate以让您的系统的性能到达最优值");
            IS_AUTO_RATE = false;
        }
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
                if (systemLoadMonitor.isOverLoad()&&rate > minrate) {

                    rate = (int) (rate * 0.8); // 当 CPU 利用率高于阈值时，降低令牌生成速率
                    log.info("cpu利用率过高，令牌生成速率调整为：" + rate);
                } else {
                    if (rate<maxrate) {

                        rate = (int) (rate / 0.8); // 当 CPU 利用率低于或等于阈值时，恢复令牌生成速率
                        log.info("cpu占用用率正常，令牌生成速率调整为：" + rate);
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 3, TimeUnit.SECONDS);
    }
}