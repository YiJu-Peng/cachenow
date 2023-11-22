package com.example.cachenow.utils.interception;

import com.example.cachenow.common.ErrorCode;
import com.example.cachenow.dto.UserDTO;
import com.example.cachenow.enums.UserRoleEnum;
import com.example.cachenow.expetion.BizException;
import com.example.cachenow.utils.annotation.AuthCheck;
import com.example.cachenow.utils.other.UserHolder;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * 权限校验 AOP
 * @Author: Ifela
 * @Date: 2023/11/11 16:26:23
 */
@Aspect
@Component
public class AuthInterceptor {

    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String expectedRole = authCheck.expectedRole();
        // 当前登录用户
        UserDTO loginUser = UserHolder.getUser();
        // 必须有该权限才通过
        if (StringUtils.isNotBlank(expectedRole)) {
            UserRoleEnum mustUserRoleEnum = UserRoleEnum.getEnumByValue(expectedRole);
            if (mustUserRoleEnum == null) {
                throw new BizException(ErrorCode.NO_AUTH_ERROR);
            }
            String userRole = loginUser.getRole();
            // 如果被封号，直接拒绝
            if (UserRoleEnum.BAN.equals(mustUserRoleEnum)) {
                throw new BizException(ErrorCode.NO_AUTH_ERROR);
            }
            // 必须先登录
            if (UserRoleEnum.USER.equals(mustUserRoleEnum)) {
                if (!expectedRole.equals(userRole)) {
                    throw new BizException(ErrorCode.NO_AUTH_ERROR,"请先登录!");
                }
            }
            // 必须有管理员权限
            if (UserRoleEnum.ADMIN.equals(mustUserRoleEnum)) {
                if (!expectedRole.equals(userRole)) {
                    throw new BizException(ErrorCode.NO_AUTH_ERROR);
                }
            }

        }
        // 通过权限校验，放行
        return joinPoint.proceed();
    }
}
