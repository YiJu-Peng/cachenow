package com.example.cachenow.controller;


import com.example.cachenow.domain.Group;
import com.example.cachenow.dto.Result;
import com.example.cachenow.service.IGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private IGroupService groupService;


    @PostMapping("/add")
    public Result add(String groupName){
        Group group = new Group();
        group.setName(groupName);
        boolean res = groupService.save(group);
        return Result.ok(res);
    }

    @PostMapping("/delete")
    public Result delete(long id){
        // TODO: 判断群组是由谁创建的
        boolean res = groupService.removeById(id);
        return Result.ok(res);
    }

    @PostMapping("/update")
    public Result update(long id,String groupName){
        // TODO: 判断群组是由谁创建的
        Group group = new Group();
        group.setGroup_id(Math.toIntExact(id));
        group.setName(groupName);
        boolean res = groupService.updateById(group);
        return Result.ok(res);
    }
}

