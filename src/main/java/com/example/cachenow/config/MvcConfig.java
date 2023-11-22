package com.example.cachenow.config;

import com.example.cachenow.utils.interception.LoginInterceptor;
import com.example.cachenow.utils.interception.RefreshTokenInterceptor;
import com.example.cachenow.utils.other.LongToLocalDateTimeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.mappers.ModelMapper;

import javax.annotation.Resource;

/**
 * 时间  1/11/2023 下午 7:41
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器
//        registry.addInterceptor(new LoginInterceptor())
//                .excludePathPatterns(
////                        "/blog/hot",
//
//                        //TODO: 不需要用户登录的页面进行放行
//                ).order(1);
        // token刷新的拦截器
        // TODO: 实现token刷新功能 现在测试的时候就不用了,到时候上线了之后再使用
        //registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).addPathPatterns("/**").order(0);
//    }



    //时间转换器,将long类型转为时间类型
    @Autowired
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new LongToLocalDateTimeConverter());
    }

    //跨域放行
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // 允许跨域访问的源，可以使用通配符"*"表示允许所有源
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 允许跨域访问的HTTP方法
                .allowedHeaders("Authorization", "Content-Type") // 允许跨域访问的请求头
                .allowCredentials(true) // 是否允许发送认证信息（如Cookie）
                .maxAge(3600); // 预检请求的有效期，单位为秒
    }
}
