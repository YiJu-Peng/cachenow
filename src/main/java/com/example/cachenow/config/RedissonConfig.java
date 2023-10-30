package com.example.cachenow.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 时间  10/10/2023 下午 6:37
 * 作者 Ctrlcv工程师  在线面对百度编程
 * redis的基本配置类
 */
@Configuration
public class RedissonConfig {
//    @Bean
//    public RedissonClient redissonClient(){
//        // 配置
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://192.168.253.132:6379").setPassword("asdf");
//        // 创建RedissonClient对象
//        return Redisson.create(config);
//    }
        @Bean
        public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
            RedisTemplate<String, Object> template = new RedisTemplate<>();
            template.setConnectionFactory(connectionFactory);
            //template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
            return template;
        }
}
