package com.example.cachenow.utils.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 时间  14/10/2023 下午 2:00
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 将批量对redis进行查询,不存在则使用mq向mysql发起请求
 *
 * 被此注解标记的mapper返回的所有的对象都会被进行一个缓存
 * 这个注解使用的地方返回值一定要是list,可以没有或者是查到一个值,但是返回值定要是list
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BatchQuery {
    long expireSeconds() default 60L; // 缓存过期时间，默认为60秒
    String primaryKey() default "id";//主键名 默认是id,也可以进行自己的输入
}
