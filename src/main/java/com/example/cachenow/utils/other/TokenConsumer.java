package com.example.cachenow.utils.other;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 时间  17/10/2023 下午 11:22
 * 作者 Ctrlcv工程师  在线面对百度编程
 *
 */
@Slf4j
public class TokenConsumer {
    TokenBucket tokenBucket;
    public TokenConsumer(){
        tokenBucket = new TokenBucket();
    }
    public  boolean process(){
        //判断是否拿到令牌,如果拿到令牌了的话就通行,要不然就返回错误代码
        if (!tokenBucket.getToken()) {
            // 没有，需要拦截，设置状态码
            log.error("token bucket is empty");
            // 拦截
            return false;
        }
        return true;
    }
}
