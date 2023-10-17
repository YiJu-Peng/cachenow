package com.example.cachenow.utils.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 时间  8/10/2023 下午 9:16
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 将mysql查询的数据放入redis中,
 * 如果在redis存在则直接放回

 * 注意!!:这个注解只能运用在对id进行查询的时候
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsInMysql {

    long expireSeconds() default 60L; // 缓存过期时间，默认为60秒
    String prefixKey(); // Redis缓存键名前缀


}
