package com.example.cachenow.controller;

import com.example.cachenow.dto.Result;
import com.example.cachenow.dto.Result;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.example.cachenow.utils.Constants.MqConstants.EXNAME;
import static com.example.cachenow.utils.Constants.MqConstants.QUEUENAME;


/**
 * 时间  13/10/2023 下午 7:38
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
@RestController
public class MqController {
    @Resource
    private AmqpTemplate amqpTemplate;

    @GetMapping("/direct/send")
    public Object send(Object msg){
        System.out.println("send :" + msg);
        amqpTemplate.convertAndSend(EXNAME,null,msg);
        return Result.ok();
    }
    

    @RabbitListener(queues = QUEUENAME)
    public void processA(String msg) {
        System.out.println("Receiver A:" + msg);
    }


}
