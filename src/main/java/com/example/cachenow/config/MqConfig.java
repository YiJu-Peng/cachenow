package com.example.cachenow.config;


import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 时间  13/10/2023 下午 6:18
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 消息队列的基本配置类
 */
@Configuration
public class MqConfig {
    @Bean
    public Queue resourceQueue() {
        return new Queue("ResourceQueue", true, false, false);
    }
    @Bean
    public Queue userQueue() {
        return new Queue("UserQueue", true, false, false);
    }

    @Bean
    public Queue resourceQueueDelete() {
        return new Queue("ResourceQueueDelete", true, false, false);
    }


//    @Bean
//    public ConnectionFactory connectionFactory() {
//        ConnectionFactory connectionFactory = new ConnectionFactory();
//        // 从 YAML 文件中读取 RabbitMQ 连接配置
//        connectionFactory.setHost("192.168.253.132");
//        connectionFactory.setPort(5672);
//        connectionFactory.setUsername("root");
//        connectionFactory.setPassword("asdf");
//        connectionFactory.setVirtualHost("/");
//        return connectionFactory;
//    }
//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
//        RabbitAdmin rabbitAdmin = new RabbitAdmin((org.springframework.amqp.rabbit.connection.ConnectionFactory) connectionFactory);
//        rabbitAdmin.setAutoStartup(true);
//        return rabbitAdmin;
//    }

}
