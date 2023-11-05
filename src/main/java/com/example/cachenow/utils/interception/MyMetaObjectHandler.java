package com.example.cachenow.utils.interception;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component // 为了能够被 Spring 自动扫描并注入 BeanFactory
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 自动填充创建时间和修改时间
        this.strictInsertFill(metaObject, "created_at", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "create_time", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "update_time", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "uploaded_at", LocalDateTime.class, LocalDateTime.now());

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 自动填充修改时间
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    }
}