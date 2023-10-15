package com.example.cachenow.utils.processors;

import com.example.cachenow.utils.annotation.IsInMysql;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 时间  14/10/2023 下午 2:03
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
@Component
@Aspect
@Slf4j
public class BatchQueryProcessor {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Around("@annotation(isInMysql)")
    public Object cacheAndExecute(ProceedingJoinPoint joinPoint, IsInMysql isInMysql) throws Throwable {
        log.debug("cacheing");
        String redisKey;
        String keyFormat = isInMysql.keyperfix();
        if (keyFormat.contains("%s")) {
            // 如果 keyFormat 中包含 %s 占位符，那么将其替换为参数
            String argsString = Arrays.toString(joinPoint.getArgs());
            String formattedKey = String.format(keyFormat, argsString.substring(1, argsString.length() - 1));
            redisKey = formattedKey;  // 更新 redisKey 的值
        } else {
            // 如果 keyFormat 不包含 %s 占位符，那么直接进行拼接
            StringBuilder sb = new StringBuilder();
            sb.append(keyFormat);
            for (Object arg : joinPoint.getArgs()) {
                sb.append("_").append(arg.toString());
            }
            redisKey = sb.toString();  // 更新 redisKey 的值
        }
        int randomSeconds = new Random().nextInt(10) + 1;
        long expireSeconds = isInMysql.expireSeconds() + randomSeconds;//加入随机值防止缓存雪崩
        // 查询Redis缓存
        Object cachedResult = redisTemplate.opsForValue().get(redisKey);
        if (cachedResult != null) {
            return cachedResult;
        }

        // 执行方法并缓存结果
        Object result = joinPoint.proceed();
        if (result != null) {
            log.debug("成功查询并缓存");
            redisTemplate.opsForValue().
                    set(redisKey, String.valueOf(result), expireSeconds, TimeUnit.SECONDS);
        }
        log.debug("现在是返回sql查询的数据");

        return result;
    }
}
