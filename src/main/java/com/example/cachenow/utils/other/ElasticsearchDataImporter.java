package com.example.cachenow.utils.other;

import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * 时间  10/10/2023 上午 9:52
 * 作者 Ctrlcv工程师  在线面对百度编程
 * es数据从数据库导入工具类
 * 目前还未完善
 */
//TODO: 现在只是一个样例
public class ElasticsearchDataImporter {
    public static void main(String[] args) {
        // 设置数据库连接信息
        String dbUrl = "jdbc:mysql://localhost:3306/your_database";
        String username = "your_username";
        String password = "your_password";

        // Elasticsearch连接信息
        String esHost = "localhost";
        int esPort = 9200;

        try {
            // 连接到数据库
            Connection connection = DriverManager.getConnection(dbUrl, username, password);
            Statement statement = connection.createStatement();

            // 查询数据库表并获取结果集
            ResultSet resultSet = statement.executeQuery("SELECT * FROM your_table");

            // 创建Elasticsearch客户端
            RestHighLevelClient client = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(esHost, esPort, "http")));

            // 创建批量请求
            BulkRequest bulkRequest = new BulkRequest();

            // 遍历结果集并构造索引请求
            while (resultSet.next()) {
                // 从结果集中提取数据
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                // ...

                // 构造索引请求
                IndexRequest indexRequest = new IndexRequest("your_index")
                        .source("id", id,
                                "name", name
                        );

                // 添加索引请求到批量请求
                bulkRequest.add(indexRequest);
            }

            // 执行批量请求
            client.bulk(bulkRequest, RequestOptions.DEFAULT);

            // 关闭连接
            client.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}