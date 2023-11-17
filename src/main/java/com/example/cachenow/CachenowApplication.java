package com.example.cachenow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@MapperScan("com.example.cachenow.mapper")
@EnableRabbit
@SpringBootApplication(exclude = {JpaRepositoriesAutoConfiguration.class,
        RedisAutoConfiguration.class, DataSourceAutoConfiguration.class})
public class CachenowApplication {
    public static void main(String[] args) {
        SpringApplication.run(CachenowApplication.class, args);
    }
}
