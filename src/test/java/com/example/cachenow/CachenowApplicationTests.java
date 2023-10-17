package com.example.cachenow;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CachenowApplicationTests {

    @Test
    void contextLoads() {
        final int i = Runtime.getRuntime().availableProcessors();
        System.out.println(i+"是我的理论最打大线程数");
    }

}
