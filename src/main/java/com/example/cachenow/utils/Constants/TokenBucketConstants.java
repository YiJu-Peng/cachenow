package com.example.cachenow.utils.Constants;

/**
 * 时间  18/10/2023 下午 9:38
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
public class TokenBucketConstants {

   //这个地方别把阀值看的太死了,比如说一个不是很重要的业务
   //如果当系统自生的资源不足的时候,超过了50%负载我们就降低他的令牌生成速度
   //防止这个业务影响主要的业务
   public static final double SYSTEM_THRESHOLD= 80.0;//系统过载的阀值,我们默认是超过80%就为过载
   public final static int CAPACITY=2000;  // 令牌桶容量
   public final static int RATE=500;      // 令牌生成速率(计算方法为 每1000/rate ms生成一个)
   public final static int MAX_RATE=2000;  // 最大令牌生成速率(计算方法为 每1000/rate ms生成一个)
   public final static int MIN_RATE=200; //   最小令牌牌生成速率(计算方法为 每500/rate ms生成一个)
   public final static int ADJUST_CYCLE=5 ;// 根据负载调整rate的大小的周期,单位是秒
}
