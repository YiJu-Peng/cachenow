package com.example.cachenow;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
@RunWith(SpringRunner.class)
@SpringBootTest
class CachenowApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    public void getLearn() throws IOException {
        GetRequest getRequest = new GetRequest("fn_rmsv2_supervise_log","_doc","336198460682276870");
        RequestOptions requestOptions = RequestOptions.DEFAULT;
        GetResponse response = client.get(getRequest,requestOptions);
        System.out.println(response.getId());
    }

}
