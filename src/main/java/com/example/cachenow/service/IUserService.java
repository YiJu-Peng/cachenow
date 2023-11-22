package com.example.cachenow.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cachenow.domain.Resource;
import com.example.cachenow.domain.User;
import com.example.cachenow.dto.LoginFormDTO;
import com.example.cachenow.dto.ResourceDTO;
import com.example.cachenow.dto.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
public interface IUserService extends IService<User> {

    Result sendCode(String mail);

    Result login(LoginFormDTO loginForm, HttpSession session);

    Result sign();

    Result signCount();

    Result register(LoginFormDTO loginForm, HttpSession session);

    Result userProfile();

    List<Resource> userHistory();
    List<ResourceDTO> userHistory(Long pageNumber);
    List<ResourceDTO> userHistoryClean();


    Result logout(HttpServletRequest request);
}