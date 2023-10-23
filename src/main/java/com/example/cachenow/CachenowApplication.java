package com.example.cachenow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.cachenow.mapper")
@SpringBootApplication
@EnableRabbit
public class CachenowApplication {
    public static void main(String[] args) {
        SpringApplication.run(CachenowApplication.class, args);
    }
}
