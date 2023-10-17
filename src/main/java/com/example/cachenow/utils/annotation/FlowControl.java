package com.example.cachenow.utils.annotation;

import com.example.cachenow.utils.interception.TokenBucketInterception;
import com.example.cachenow.utils.other.TokenBucket;
import org.aopalliance.intercept.Interceptor;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 时间  17/10/2023 上午 8:28
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 控制流量的接口注解,可以使用在多种接口上面,但是推荐是使用在mapper上
 */
public @interface FlowControl {
    //可以选择自己实现的控制流量的策略,我们这个地方使用的是自己实现的一个
    Class<? extends HandlerInterceptor> value() default TokenBucketInterception.class;
}
