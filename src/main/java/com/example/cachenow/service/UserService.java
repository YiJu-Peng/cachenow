package com.example.cachenow.service;

import com.example.cachenow.dto.Result;
import com.example.cachenow.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 时间  9/10/2023 下午 3:25
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;


    public Result getUser(Long id) {
        return Result.ok(userMapper.findById(id));
    }
}
