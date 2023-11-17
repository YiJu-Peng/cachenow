package com.example.cachenow.controller;


import com.example.cachenow.domain.GroupMember;
import com.example.cachenow.dto.Result;
import com.example.cachenow.service.IGroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.FutureTask;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@RestController
@RequestMapping("/groupMember")
public class GroupMemberController {

    @Autowired
    private IGroupMemberService groupMemberService;

    @PostMapping("/add")
    public Result add(int gid,int uid){
        FutureTask task;
        GroupMember groupMember = new GroupMember();
        groupMember.setGroup_id(gid);
        groupMember.setUser_id(uid);
        boolean res = groupMemberService.save(groupMember);
        return Result.ok(res);
    }

    @PostMapping("/delete")
    public Result delete(int groupMemberId){
        boolean res = groupMemberService.removeById(groupMemberId);
        return Result.ok(res);
    }
}

