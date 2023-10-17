package com.example.cachenow.utils.processors;

import com.example.cachenow.dto.Result;
import com.example.cachenow.utils.annotation.BatchQuery;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 时间  14/10/2023 下午 2:03
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 这个方法不能用来查询,这个方法是用来缓存大批量的数据的
 * perfix就是需要缓存的表的名称
 */
@Component
@Aspect
@Slf4j
public class BatchQueryProcessor {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(batchQuery)")
    public Object cacheAndExecute(ProceedingJoinPoint joinPoint, BatchQuery batchQuery) throws Throwable {
        final Object arg = joinPoint.getArgs()[0];
        final String keyperfix = arg.toString();
        final List<Object> proceed =  (List<Object>)joinPoint.proceed();

        //获得对象的类型
         Object object= proceed.get(0);
        final Class<?> aClass = object.getClass();

        for (Object o : proceed) {
            //使用反射来获取主键(注意这个地方的id是泛指主键,主键可以在注解使用的时候设定,默认为id)
            final Field field = aClass.getDeclaredField(batchQuery.primaryKey());
            field.setAccessible(true);
            final Object id = field.get(o);
            final String redisKey = keyperfix+":"+id;
            redisTemplate.opsForValue().set(redisKey, o);
        }
        return proceed;
    }
}
