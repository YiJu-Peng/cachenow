package com.example.cachenow.config;


import com.example.cachenow.chat.MsgSendConsumer;
import com.example.cachenow.chat.MQProducer;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

import static com.example.cachenow.utils.Constants.MqConstants.*;

/**
 * 时间  13/10/2023 下午 6:18
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 消息队列的基本配置类
 */
@Configuration
public class MqConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;

    /**
     * 这个方法传入指定的参数之后可以自动的创建队列
     */
    public void ensureQueueExists(String queueName, boolean durable, boolean exclusive, boolean autoDelete) {
        Queue queue = new Queue(queueName, durable, exclusive, autoDelete);
        amqpAdmin.declareQueue(queue);
    }

    /**
     * 这个方法传入指定的参数之后可以自动的创建交换机
     */
    public void ensureExchangeExists(String exchangeName, boolean durable, boolean autoDelete) {
        DirectExchange exchange = new DirectExchange(exchangeName, durable, autoDelete);
        amqpAdmin.declareExchange(exchange);
    }

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

    @Bean
    public Queue sendMsgQueue() {
        amqpAdmin.declareExchange(new DirectExchange(SEND_MSG_EXCHANGE, true, false));
        amqpAdmin.declareQueue(new Queue(SEND_MSG_QUEUE_PREFIX, true));
        amqpAdmin.declareBinding(new Binding(
                SEND_MSG_QUEUE_PREFIX,
                Binding.DestinationType.QUEUE,
                SEND_MSG_EXCHANGE,
                "",
                new HashMap<>()));
        return new Queue(SEND_MSG_QUEUE_PREFIX, true, false, false);
    }

    @Bean
    public MsgSendConsumer getMsgSendConsumer() {
        return new MsgSendConsumer();
    }

    @Bean
    public MQProducer getMQProducer() {
        return new MQProducer();
    }

}
