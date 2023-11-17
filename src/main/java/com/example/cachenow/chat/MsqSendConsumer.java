package com.example.cachenow.chat;

import com.example.cachenow.config.MqConfig;
import com.example.cachenow.dto.MsgSendDTO;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * mq 消息消费者
 * @Author: Ifela
 * @Date: 2023/11/17 10:52:47
 */
@Component
@RabbitListener(queues = MqConfig.CHAT_MULTIPLE_QUEUE)
public class MsqSendConsumer {
    @RabbitHandler
    public void onMessage(MsgSendDTO dto){
        // todo 推送至房间
        System.out.println(dto);
        System.out.println("消费消息######");
    }


}
