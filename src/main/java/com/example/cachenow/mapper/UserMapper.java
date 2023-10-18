package com.example.cachenow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cachenow.domain.User;
import com.example.cachenow.utils.annotation.BatchQuery;
import com.example.cachenow.utils.annotation.FlowControl;
import com.example.cachenow.utils.annotation.IsInMysql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 时间  9/10/2023 下午 3:17
 * 作者 Ctrlcv工程师  在线面对百度编程
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @FlowControl
    @IsInMysql(prefixKey = "user_%s", expireSeconds = 3600L)
    @Select("select * from cloud_user.users where id = #{id}")
    User findById(Long id);

    @FlowControl
    @BatchQuery(expireSeconds = 3600L)
    @Select("select * from cloud_user.users ")
    List<User> findAll(String tableName);

}
