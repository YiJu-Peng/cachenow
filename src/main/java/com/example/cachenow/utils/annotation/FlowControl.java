package com.example.cachenow.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 时间  17/10/2023 上午 8:28
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 控制流量的接口注解,可以使用在多种接口上面,但是推荐是使用在mapper上
 * !!注意,这个接口不能保证所有的请求都被消费,有小概率被拒绝的可能,也就是返回空值(保护策略)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FlowControl {
}
