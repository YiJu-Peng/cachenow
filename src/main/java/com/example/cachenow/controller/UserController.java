package com.example.cachenow.controller;

import com.example.cachenow.dto.Result;
import com.example.cachenow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 时间  10/10/2023 上午 9:36
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/{id}")
    public Result getUser(@PathVariable("id")Long id){
        return userService.getUser(id);
    }

    @GetMapping
    ("/cacheAll/{tablename}")
    public Result cacheAll(@PathVariable("tablename")String tableName){
        return userService.cacheAll(tableName);
    }
}
