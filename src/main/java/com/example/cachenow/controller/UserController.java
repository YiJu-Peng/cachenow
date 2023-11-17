package com.example.cachenow.controller;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cachenow.common.ErrorCode;
import com.example.cachenow.domain.User;
import com.example.cachenow.domain.UserInfo;
import com.example.cachenow.dto.LoginFormDTO;
import com.example.cachenow.dto.Result;
import com.example.cachenow.dto.UserDTO;
import com.example.cachenow.dto.UserQueryRequest;
import com.example.cachenow.expetion.BizException;
import com.example.cachenow.service.IUserInfoService;
import com.example.cachenow.service.IUserService;
import com.example.cachenow.utils.annotation.AuthCheck;
import com.example.cachenow.utils.other.UserHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private IUserService userService;

    @Resource
    private IUserInfoService userInfoService;

    /**
     * 发送手机验证码
     */
    @PostMapping("code")
    public Result sendCode(@RequestParam("phone") String phone, HttpSession session) {
        // 发送短信验证码并保存验证码
        return userService.sendCode(phone, session);
    }

    /**
     * 登录功能
     *
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm, HttpSession session) {
        // 实现登录功能
        return userService.login(loginForm, session);
    }

    /**
     * 注册功能
     *
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     */
    @PostMapping("/register")
    public Result register(@RequestBody LoginFormDTO loginForm, HttpSession session){
        // 实现注册功能
        return userService.register(loginForm, session);
    }

    /**
     * 登出功能
     *
     * @return 无
     */
    @PostMapping("/logout")
    public Result logout(){
        // TODO 实现登出功能
        UserHolder.removeUser();
        return Result.ok();
    }

    /**
     * 返回自己的信息獲得
     *
     * @return UserDTO
     */
    @GetMapping("/me")
    public Result me() {
        // 获取当前登录的用户并返回
        UserDTO user = UserHolder.getUser();
        return Result.ok(user);
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long userId) {
        // 查询详情
        UserInfo info = userInfoService.getById(userId);
        if (info == null) {
            // todo 为空 == 查询不到此用户
            // 没有详情，应该是第一次查看详情
            return Result.ok();
        }
        info.setCreatetime(null);
        info.setUpdatetime(null);
        // 返回
        return Result.ok(info);
    }

    @GetMapping("/{id}")
    public Result queryUserById(@PathVariable("id") Long userId) {
        // 查询详情
        User user = userService.getById(userId);
        if (user == null) {
            return Result.ok();
        }
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        // 返回
        return Result.ok(userDTO);
    }

    /**
     * 签到接口
     *
     * @return 无
     */
    @PostMapping("/sign")
    public Result sign() {
        return userService.sign();
    }

    /**
     * 签到的总天数
     *
     * @return 返回签到的总天数
     */
    @GetMapping("/sign/count")
    public Result signCount() {
        return userService.signCount();
    }

    /**
     * 返回用户的额外信息 城市,个人介绍,性别,关注的人的数量,生日,积分,会员级别,创建时间,更新时间
     *
     * @return UserInfo
     */
    @GetMapping("/sign/profile")
    public Result userProfile() {
        return userService.userProfile();
    }


    @GetMapping("/history")
    public Result userHistory() {
        final List<com.example.cachenow.domain.Resource> resources = userService.userHistory();
        final int size = resources.size();
        return Result.ok(resources, (long) size);
    }

    @GetMapping("/history/clean")
    public Result userHistoryClean() {
        final List<com.example.cachenow.domain.Resource> resources = userService.userHistory();
        final int size = resources.size();
        return Result.ok(resources, (long) size);
    }

    @GetMapping("/history/{pageNumber}")
    public Result userHistoryClean(@PathVariable("pageNumber") Long pageNumber) {

        final List<com.example.cachenow.domain.Resource> resources = userService.userHistory(pageNumber);
        final int size = resources.size();
        return Result.ok(resources, (long) size);
    }

    @PostMapping("/update")
    @AuthCheck(expectedRole = "admin")
    public Result update(@RequestBody User user) {
        if (user == null || user.getUser_id() < 0) {
            throw new BizException(ErrorCode.PARAMS_ERROR);
        }
        User oldUser = userService.getById(user.getUser_id());
        if (oldUser == null) {
            throw new BizException(ErrorCode.NOT_FOUND_ERROR);
        }
        boolean result = userService.updateById(user);
        return Result.ok(result);
    }

    @GetMapping("/list")
    @AuthCheck(expectedRole = "admin")
    public Result list(@RequestBody UserQueryRequest queryRequest) {
        if (queryRequest == null) {
            throw new BizException(ErrorCode.PARAMS_ERROR);
        }

        long current = queryRequest.getCurrent();
        long size = queryRequest.getPageSize();
        String sortField = queryRequest.getSortField();
        String sortOrder = queryRequest.getSortOrder();
        String username = queryRequest.getUsername();
        String phone = queryRequest.getPhone();
        String email = queryRequest.getEmail();

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username),"username", username);
        queryWrapper.like(StringUtils.isNotBlank(username),"phone",phone);
        queryWrapper.like(StringUtils.isNotBlank(username),"email",email);
        queryWrapper.orderBy(StringUtils.isNotBlank(sortField),
                sortOrder.equals("descend"), sortField);
        Page<User> userPage = userService.page(new Page<>(current, size), queryWrapper);
        return Result.ok(userPage);
    }

}

