package com.example.cachenow.utils.TokenBucket;
import com.example.cachenow.utils.TokenBucket.SystemLoadMonitor.Linux;
import com.example.cachenow.utils.TokenBucket.SystemLoadMonitor.SystemLoadInterface;
import com.example.cachenow.utils.TokenBucket.SystemLoadMonitor.Win;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.example.cachenow.utils.Constants.TokenBucketConstants.*;

/**
 * 时间  17/10/2023 上午 9:28
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 令牌桶算法控制流量
 * 此算法给出的默认值会自动的根据系统的负载状况进行调整
 * 最好是开始前测试下最小和最大的rate值,适配自己的硬件环境
 * 这个类只能被TokenConsumer类进行消费,如果像要定义别的消费模式推荐再创建一个消费者(TokenConsumer)
 */
@Slf4j
public class TokenBucket {
    private int capacity=CAPACITY;  // 令牌桶容量
    private int rate=RATE;      // 令牌生成速率(计算方法为 每1000/rate ms生成一个)
    private final int maxrate=MAX_RATE;  // 最大令牌生成速率(计算方法为 每1000/rate ms生成一个)
    private final int minrate=MIN_RATE; //   最小令牌牌生成速率(计算方法为 每500/rate ms生成一个)
    private final int adjustcycle=ADJUST_CYCLE; // 调整速率的周期
    private static BlockingQueue<Object> tokens;           // 令牌队列;
    private static SystemLoadInterface systemLoadMonitor;  // cpu占用率接口
    private static boolean IS_AUTO_RATE = true;  //是否开启自动调节rate(关闭了可能需要自己调试适配自己硬件的rate)
    //生成令牌的线程池,不能让同一个木桶同时有多个的生成器
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);



    public TokenBucket(int capacity, int rate) {
        //允许自定义一个木桶,但是要注意,整个系统中如果没有使用这个构造函数的话是只有一个木桶的
        //也就是bean的单例模式里面的木桶,但是一旦使用这个创建了类的话,系统中就有两个木桶了
        //创建的话请慎重考虑是否需要两个木桶,及机器的各种可能出现的情况
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
            log.info("调用linux系统");
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
        }, 0, adjustcycle, TimeUnit.SECONDS);//这个地方的四个参数是执行第一次任务等待的时间,第个次是周期
    }
}