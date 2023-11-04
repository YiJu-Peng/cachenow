package com.example.cachenow.controller;


import com.example.cachenow.domain.Resource;
import com.example.cachenow.dto.Result;
import com.example.cachenow.service.impl.ResourceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@Controller
@RequestMapping("/resource")
public class ResourceController {
    @Autowired
    ResourceServiceImpl resourceService;
    @PostMapping("/create")
    public Result creat(){
        return Result.ok(resourceService.creatResoure());
    }
}

