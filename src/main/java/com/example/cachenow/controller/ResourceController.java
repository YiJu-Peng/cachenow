package com.example.cachenow.controller;


import com.example.cachenow.domain.Resource;
import com.example.cachenow.dto.ResourceDTO;
import com.example.cachenow.dto.Result;
import com.example.cachenow.es.esservice.ResourceService;
import com.example.cachenow.service.impl.ResourceServiceImpl;
import com.example.cachenow.utils.other.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    ResourceService resourceESService;
    // 添加资源
    @PostMapping("/create")
    public Result addResource(@RequestBody ResourceDTO resourceDTO) {
        resourceDTO.setUserId(UserHolder.getUser().getId());
        Resource resource = new Resource(resourceDTO);
        resourceService.addResource(resource);
        return Result.ok();
    }

    //查询框使用的模糊查询,可以查询所有的有关的资料,但是是存在了es中的
    @GetMapping("/{search}")
    public Result getResource( @PathVariable String search) {
        final List<Resource> resourcesByTitle =
                resourceESService.searchByTitleAndContent(search);
        final int size = resourcesByTitle.size();
        return Result.ok(resourcesByTitle, (long) size);
    }



    // 按分类检索资源
    @GetMapping("/category/{categoryId}")
    public Result getResourcesByCategory(@PathVariable Integer categoryId) {
        final List<Resource> resourcesByCategory = resourceService.getResourcesByCategory(categoryId);
        final Long size = (long) resourcesByCategory.size();
        return Result.ok(resourcesByCategory, size);
    }

    // 获取资源的分类信息
    @GetMapping("/category/{resourceId}/info")
    public Result getCategoryById(@PathVariable Integer resourceId) {
        return Result.ok(resourceService.getCategoryById(resourceId));
    }
}

