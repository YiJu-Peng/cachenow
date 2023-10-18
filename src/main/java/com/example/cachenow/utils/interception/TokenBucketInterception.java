package com.example.cachenow.utils.interception;

import com.example.cachenow.utils.other.TokenBucket;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Intercepts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 时间  17/10/2023 下午 12:13
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 如果不想要使用注解,或者是想要使用全局的流量控制
 * 可以将下面的拦截器放入拦截器链里面
 */
@Component
public class TokenBucketInterception implements HandlerInterceptor {


    @Autowired
    TokenBucket  tokenBucket ;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断是否拿到令牌,如果拿到令牌了的话就通行,要不然就返回错误代码
        if (!tokenBucket.getToken()) {
            // 没有，需要拦截，设置状态码
            response.setStatus(401);
            // 拦截
            return false;
        }
        return true;
    }
}
