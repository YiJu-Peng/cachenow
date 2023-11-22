package com.example.cachenow.chat;

import cn.hutool.json.JSONUtil;
import com.example.cachenow.dto.ChatMessageDTO;
import com.example.cachenow.utils.Constants.MqConstants;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * mq 消息监听器
 *
 * @Author: Ifela
 * @Date: 2023/11/17 10:52:47
 */
@Component
@RabbitListener(queues = MqConstants.SEND_MSG_QUEUE_PREFIX)
public class MsgSendConsumer {

    @RabbitHandler
    public void onReceiving(String msg) {
        ChatMessageDTO dto = JSONUtil.toBean(msg, ChatMessageDTO.class);
        System.out.println("#####收到一个房间消息######");
        System.out.println(dto);
        // TODO: 根据 rid 和 uIds 发送至对应 channel
    }


}
