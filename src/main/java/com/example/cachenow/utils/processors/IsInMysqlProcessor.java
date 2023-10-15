package com.example.cachenow.utils.processors;

import com.example.cachenow.utils.annotation.IsInMysql;
import com.example.cachenow.utils.annotation.IsInMysql;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 时间  8/10/2023 下午 9:30
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 这个是一个注解处理器
 * 使用@IsInMysql注解之后就会使用这个注解处理器来进行处理
 * 作用是查看数据是否在缓存中,如果是的话就返回缓存,要不然就从数据库中读取
 * 然后再将查询好了的数据直接存入redis中
 */
//@Slf4j
//@Component
//@Aspect
//public class IsInMysqlProcessor{
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Around("@annotation(isInMysql)")
//    public Object cacheAndExecute(ProceedingJoinPoint joinPoint, IsInMysql isInMysql) throws Throwable {
//        log.error("now i am arrived");
//        log.debug("cacheing");
//        String redisKey ;
//        String keyFormat = isInMysql.keyperfix();
//        if (keyFormat.contains("%s")) {
//            // 如果 keyFormat 中包含 %s 占位符，那么将其替换为参数
//            String argsString = Arrays.toString(joinPoint.getArgs());
//            String formattedKey = String.format(keyFormat, argsString.substring(1, argsString.length() - 1));
//            redisKey = formattedKey;  // 更新 redisKey 的值
//        } else {
//            // 如果 keyFormat 不包含 %s 占位符，那么直接进行拼接
//            StringBuilder sb = new StringBuilder();
//            sb.append(keyFormat);
//            final Object args = Arrays.asList(joinPoint.getArgs()).get(0);
//            sb.append(".").append(args.toString());
//            redisKey = sb.toString();  // 更新 redisKey 的值
//        }
//        long expireSeconds = isInMysql.expireSeconds();
//        // 查询Redis缓存
//        Object cachedResult = redisTemplate.opsForValue().get(redisKey);
//        if (cachedResult != null) {
//            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//            Class<?> returnType = methodSignature.getMethod().getReturnType();
//            ObjectMapper objectMapper = new ObjectMapper();
//            log.error(returnType.toString());
//            return objectMapper.readValue(cachedResult.toString(), returnType);
//            //return JSON.parseObject(cachedResult, returnType);
//        }
//
//        // 执行方法并缓存结果
//        Object result = joinPoint.proceed();
//        if (result != null) {
//            log.debug("成功查询并缓存");
//            redisTemplate.opsForValue().set(redisKey, result, expireSeconds, TimeUnit.SECONDS);
//        }
//        log.debug("现在是返回sql查询的数据");
//            return result;
//    }
//}
@Slf4j
@Component
@Aspect
public class IsInMysqlProcessor{
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(isInMysql)")
    public Object cacheAndExecute(ProceedingJoinPoint joinPoint, IsInMysql isInMysql) throws Throwable {
        log.debug("cacheing");
        String redisKey = String.format(isInMysql.keyperfix(), Arrays.toString(joinPoint.getArgs()));
        long expireSeconds = isInMysql.expireSeconds();

        // 查询Redis缓存
        Object cachedResult = redisTemplate.opsForValue().get(redisKey);
        if (cachedResult != null) {
            return cachedResult;
        }

        // 执行方法并缓存结果
        Object result = joinPoint.proceed();
        if (result != null) {
            log.debug("成功查询并缓存");
            redisTemplate.opsForValue().set(redisKey, result, expireSeconds, TimeUnit.SECONDS);
        }
        log.debug("现在是返回sql查询的数据");

        return result;
    }
}

