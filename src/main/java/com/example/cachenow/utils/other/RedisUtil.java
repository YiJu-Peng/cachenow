package com.example.cachenow.utils.other;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 时间  2023/11/3 14:48
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
public class RedisUtil {
    public static RedisTemplate<String, Object> redisTemplate ;

    static {
        if (redisTemplate == null) {
            redisTemplate   = new RedisTemplate<String, Object>();
        }
    }
    public static void set(String key, Object value,Long seconds){
        redisTemplate.opsForValue().set(key, value,seconds, TimeUnit.SECONDS);
    }
    public static Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

}
