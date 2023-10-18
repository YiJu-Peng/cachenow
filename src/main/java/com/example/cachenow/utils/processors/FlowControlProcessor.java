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
        // 调用TokenBucketInterception注解处理器的方法
        final boolean process = tokenConsumer.process();
        if (process) {
            return joinPoint.proceed();
        }
        return Result.fail("服务器过于繁忙");


    }


}
