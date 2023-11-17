package com.example.cachenow.dto;

import com.example.cachenow.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Ifela
 * @Date: 2023/11/12 21:44:43
 */
@Data
public class UserQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * uid
     */
    private Integer user_id;

    /**
     * 消息
     */
    private String username;

    /**
     * 角色
     */
    private String role;

    /**
     * 哟许
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;


}
