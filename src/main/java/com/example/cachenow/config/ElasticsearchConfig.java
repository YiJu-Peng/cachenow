package com.example.cachenow.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    // 用于与 Elasticsearch 进行交互。
    // 它封装了底层的 HTTP 请求，并提供了一组易于使用的方法来处理 Elasticsearch 中的数据
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(HttpHost.create(elasticsearchUri))
        );
    }


    //定义了一组用于执行 Elasticsearch 操作的方法，包括索引文档、获取文档、删除文档等。
    // 通过 ElasticsearchOperations，可以更方便地与 Elasticsearch 进行交互，无需编写复杂的请求和解析逻辑。
    @Bean
    public ElasticsearchOperations elasticsearchTemplate(RestHighLevelClient client) {
        return new ElasticsearchRestTemplate(client);
    }
//    @Autowired
//    @Bean
//    public ElasticsearchRestTemplate elasticsearchRestTemplate(RestHighLevelClient client) {
//        return new ElasticsearchRestTemplate(client);
//    }

    //可以执行一些索引相关的操作，比如创建索引、删除索引、设置索引映射等。它提供了一组简化的方法，使得管理索引变得更加容易。
    @Bean
    public IndexOperations indexOperations(ElasticsearchOperations operations) {
        IndexCoordinates indexCoordinates = IndexCoordinates.of("my-index");
        return operations.indexOps(indexCoordinates);
    }
}
