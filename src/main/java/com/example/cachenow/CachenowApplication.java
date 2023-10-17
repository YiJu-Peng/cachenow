package com.example.cachenow;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.example.cachenow.mapper")
@SpringBootApplication
public class CachenowApplication {

    public static void main(String[] args) {
        SpringApplication.run(CachenowApplication.class, args);
    }

}
