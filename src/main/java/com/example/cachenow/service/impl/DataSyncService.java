package com.example.cachenow.service.impl;

import com.example.cachenow.dto.SyncMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataSyncService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void syncDataToRabbitMQ(Long id, String name, String description) {
        SyncMessage syncMessage = new SyncMessage(id, name, description);
        rabbitTemplate.convertAndSend("dataSyncQueue", syncMessage);
    }
}