package com.example.cachenow.utils.other;

import com.example.cachenow.domain.User;
import com.example.cachenow.dto.UserDTO;

public class UserHolder {
    //TODO 现在是测试环境,所以返回地是管理员,到后面记得改下
    static UserDTO user=new UserDTO();
    static {
        user.setId(1L);
        user.setUsername("管理员");
    }

    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        tl.set(user);
    }

    public static UserDTO getUser(){
        return user;
        //return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
