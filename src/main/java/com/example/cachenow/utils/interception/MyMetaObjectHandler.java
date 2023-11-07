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
        //如果属性有值则不覆盖,如果填充值为null则不填充.
        this.strictInsertFill(metaObject, "created_at", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "create_time", LocalDateTime.class, LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //无论属性是否有值都填充
        this.strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
        this.strictUpdateFill(metaObject, "update_time", LocalDateTime.class, LocalDateTime.now());
        this.strictUpdateFill(metaObject, "uploaded_at", LocalDateTime.class, LocalDateTime.now());
    }
}