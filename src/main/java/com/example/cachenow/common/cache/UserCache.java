package com.example.cachenow.common.cache;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cachenow.common.ErrorCode;
import com.example.cachenow.domain.User;
import com.example.cachenow.expetion.BizException;
import com.example.cachenow.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户缓存相关
 *
 * @Author: Ifela
 * @Date: 2023/11/20 19:53:17
 */
@Component
public class UserCache {
    @Autowired
    private UserDao userDao;
    private final ConcurrentHashSet<Integer> ACTIVE_USER_SET = new ConcurrentHashSet<>(200);
    private final ConcurrentHashSet<Integer> INACTIVE_USER_SET = new ConcurrentHashSet<>(200);

    @PostConstruct
    private void init() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>().
                select("user_id", "active_status");
        List<User> userList = userDao.selectList(userQueryWrapper);
        userList.stream().
                filter(user -> user.getActive_status() == 1).// 1表示在线状态
                forEach(activeUser -> ACTIVE_USER_SET.add(activeUser.getUser_id()));
        userList.stream().
                filter(user -> user.getActive_status() == 2).// 2表示离线状态
                forEach(inactiveUser -> INACTIVE_USER_SET.add(inactiveUser.getUser_id()));
    }

    public boolean online(Integer uid) {
        // update
        boolean remove = INACTIVE_USER_SET.remove(uid);
        if (remove) {
            throw new BizException(ErrorCode.OPERATION_ERROR);
        }
        return ACTIVE_USER_SET.add(uid);
    }

    public boolean offline(Integer uid) {
        // update
        boolean remove = ACTIVE_USER_SET.remove(uid);
        if (remove) {
            throw new BizException(ErrorCode.OPERATION_ERROR);
        }
        return INACTIVE_USER_SET.add(uid);
    }

    public boolean isOnline(Integer uid) {
        return ACTIVE_USER_SET.contains(uid);
    }

    public ConcurrentHashSet<Integer> getOnlineNum() {
        return ACTIVE_USER_SET;
    }

    public List<Integer> getOfflineList() {
        return new ArrayList<>(INACTIVE_USER_SET);
    }

    public ConcurrentHashSet<Integer> getOfflineSet() {
        return  INACTIVE_USER_SET;
    }

    public ConcurrentHashSet<Integer> getOnlineSet() {
        return  ACTIVE_USER_SET;
    }
}
