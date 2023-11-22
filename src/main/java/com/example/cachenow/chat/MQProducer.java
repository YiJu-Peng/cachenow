package com.example.cachenow.chat;

import cn.hutool.json.JSONUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Ifela
 * @Date: 2023/11/18 19:59:52
 */
public class MQProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMsg(String exchange, String routingKey, String msg) {
        amqpTemplate.convertAndSend(exchange, routingKey, msg);
    }

}
