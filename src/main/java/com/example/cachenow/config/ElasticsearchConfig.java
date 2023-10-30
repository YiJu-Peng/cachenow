package com.example.cachenow.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;


/**
 * 时间  30/10/2023 下午 3:56
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 对于分布式搜索类的基本配置
 */
@Configuration
public class ElasticsearchConfig {
    @Value("${spring.elasticsearch.rest.uris}")
    private String elasticsearchUri;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(HttpHost.create(elasticsearchUri))
        );
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate(RestHighLevelClient client) {
        return new ElasticsearchRestTemplate(client);
    }

    @Bean
    public IndexOperations indexOperations(ElasticsearchOperations operations) {
        IndexCoordinates indexCoordinates = IndexCoordinates.of("my-index");
        return operations.indexOps(indexCoordinates);
    }
}
