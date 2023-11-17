package com.example.cachenow.config;


import com.alibaba.fastjson.JSON;
import com.example.cachenow.utils.Constants.MqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rx.Single;

/**
 * 时间  13/10/2023 下午 6:18
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 消息队列的基本配置类
 */
@Configuration
@Slf4j
public class MqConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;
    public static final String CHAT_MULTIPLE_QUEUE = "chat.multiple.queue";
    public static final String CHAT_SINGLE_QUEUE = "chat.single.queue";



    public static final String mqConvertClassName = "com.example.cachenow.dto.MsgSendDTO";

    @Bean
    public void setChatMultipleQueue() {
        Queue queue = new Queue(CHAT_MULTIPLE_QUEUE, true, false, false);
        amqpAdmin.declareQueue(queue);
        Binding binding = new Binding(CHAT_MULTIPLE_QUEUE, Binding.DestinationType.QUEUE, MqConstants.EXNAME, "multiple", null);
        amqpAdmin.declareBinding(binding);
    }
    @Bean
    public void setChatSingleQueue() {
        Queue queue = new Queue(CHAT_SINGLE_QUEUE, true, false, false);
        amqpAdmin.declareQueue(queue);
        Binding binding = new Binding(CHAT_SINGLE_QUEUE, Binding.DestinationType.QUEUE, MqConstants.EXNAME, "single", null);
        amqpAdmin.declareBinding(binding);
    }

    @Bean
    public MessageConverter messageConverter() {

        return new MessageConverter() {
            /**
             * 发送消息时转换
             */
            @Override
            public Message toMessage(Object o, MessageProperties messageProperties) throws MessageConversionException {
                return new Message(JSON.toJSONBytes(o));
            }

            /**
             * 接收消息时转换
             */
            @Override
            public Object fromMessage(Message message) throws MessageConversionException {
                byte[] body = message.getBody();
                try {
                    return JSON.parseObject(body, Class.forName(mqConvertClassName));
                } catch (ClassNotFoundException e) {
                    log.error("mq 消息类型转换错误: message ==> {} body ==> {}", JSON.toJSONString(message), new String(body), e);
                    throw new RuntimeException(e);
                }
            }

           };
    }

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


}
