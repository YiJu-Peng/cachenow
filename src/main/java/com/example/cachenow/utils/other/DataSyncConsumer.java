package com.example.cachenow.utils.other;

import com.example.cachenow.domain.Resource;
import com.example.cachenow.dto.SyncMessage;
import com.example.cachenow.es.esmapper.ResourceRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;

/**
 * 用来同步es和mysql中的记录的,将mysql中的消息异步的传输到es中
 */
@Component
public class DataSyncConsumer {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /**
     * 这个使用来异步接受resource
     */
    @RabbitListener(queues = "dataSyncQueue")
    public void onDataSyncMessage(SyncMessage syncMessage) {
        Resource resource = new Resource(syncMessage.getId(), syncMessage.getName(), syncMessage.getDescription());
        resourceRepository.save(resource);
        elasticsearchOperations.save(resource);
    }
}