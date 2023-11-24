package com.example.cachenow.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.example.cachenow.utils.interception.MyMetaObjectHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPlusConfig {

    //实现创建数据到数据库的时候会自动的填充创建时间和更新时间(这个类已经在另外的一个地方实现了,这个地方就做提示)
//    @Bean
//    public MyMetaObjectHandler metaObjectHandler() {
//        return new MyMetaObjectHandler();
//    }
    @Autowired
    private MyMetaObjectHandler myMetaObjectHandler;
    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setMetaObjectHandler(myMetaObjectHandler);
        return globalConfig;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }
    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> configuration.setMapUnderscoreToCamelCase(false);
    }
}