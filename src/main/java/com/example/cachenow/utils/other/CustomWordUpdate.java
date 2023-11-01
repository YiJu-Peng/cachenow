package com.example.cachenow.utils.other;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;
import java.util.Collections;

/**
 * 时间  10/10/2023 上午 9:52
 * 作者 Ctrlcv工程师  在线面对百度编程
 * ik分词器加入禁用词所用工具类
 * 目前还未完善
 */
//TODO: 现在只是一个样例
public class CustomWordUpdate {
    public static void main(String[] args) {
        // 创建 RestHighLevelClient 客户端连接
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")
                )
        );

        // 定义要添加的自定义词
        String customWord = "最好听的歌";

        // 定义要修改的索引名称和分词器名称
        String indexName = "your_index_name";
        String analyzerName = "your_analyzer_name";

        // 创建一个更新词库的请求
        UpdateSettingsRequest request = new UpdateSettingsRequest(indexName);
        request.settings(
                Settings.builder()
                        .put("index.analysis.filter.my_custom_filter.type", "ik_smart")
                        .putList("index.analysis.filter.my_custom_filter.user_dict", Collections.singletonList(customWord))
                        .put("index.analysis.analyzer." + analyzerName + ".filter.1", "my_custom_filter")
        );

        try {
            // 发送更新请求
            client.indices().putSettings(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 关闭客户端连接
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}