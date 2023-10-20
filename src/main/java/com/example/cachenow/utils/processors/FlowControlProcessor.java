package com.example.cachenow.utils.processors;

import com.example.cachenow.dto.Result;
import com.example.cachenow.utils.annotation.FlowControl;
import com.example.cachenow.utils.interception.TokenBucketInterception;
import com.example.cachenow.utils.other.TokenConsumer;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * 时间  17/10/2023 下午 11:03
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
@Slf4j
@Component
@Aspect
public class FlowControlProcessor {

    TokenConsumer tokenConsumer;

    private FlowControlProcessor() {
        tokenConsumer  = new TokenConsumer();
    }
    @Around("@annotation(flowControl)")
    public Object flowControl(ProceedingJoinPoint joinPoint, FlowControl flowControl) throws Throwable {
        // 调用获得令牌的方法
        final boolean process = tokenConsumer.process();
        if (process) {//是非获得令牌
            return joinPoint.proceed();
        }
        //如果没有拿到令牌的话就返回空值
        log.info(Thread.currentThread().getName()+"线程没有拿到令牌");
        return null;


    }


}
