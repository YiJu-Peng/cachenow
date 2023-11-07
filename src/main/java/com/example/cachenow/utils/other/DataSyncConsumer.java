package com.example.cachenow.utils.other;

import com.example.cachenow.domain.Resource;
import com.example.cachenow.domain.User;
import com.example.cachenow.dto.SyncMessage;
import com.example.cachenow.es.esmapper.ResourceRepository;
import io.swagger.models.auth.In;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

/**
 * 用来同步es和mysql中的记录的,将mysql中的消息异步的传输到es中
 */
@Component
public class DataSyncConsumer {


    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    /**
     * 这个使用来异步添加resource
     */
    @RabbitListener(queues = "ResourceQueue")
    public void Resource_Message(Resource resource) {
        elasticsearchOperations.save(resource);
    }

    /**
     * 这个用来异步删除resource
     */
    @RabbitListener(queues = "ResourceQueueDelete")
    public void Resource_Delete(Integer resourceId) {
        elasticsearchOperations.delete(resourceId.toString(), Resource.class);
    }

    /**
     * 异步添加用户到es储存库中去
     * @param user 用户对象
     */
    @RabbitListener(queues = "UserQueue")
    public void User_Message(User user) {
        elasticsearchOperations.save(user);
    }
}