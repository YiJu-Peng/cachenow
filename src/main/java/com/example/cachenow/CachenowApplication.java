package com.example.cachenow;

import com.example.cachenow.es.esmapper.UserRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchRepositoryFactoryBean;

@MapperScan("com.example.cachenow.mapper")
@EnableRabbit
@SpringBootApplication(exclude = {JpaRepositoriesAutoConfiguration.class})
public class CachenowApplication {
    public static void main(String[] args) {
        SpringApplication.run(CachenowApplication.class, args);
    }
}
