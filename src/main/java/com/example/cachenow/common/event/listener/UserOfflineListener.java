package com.example.cachenow.common.event.listener;

import com.example.cachenow.common.cache.UserCache;
import com.example.cachenow.common.event.UserOfflineEvent;
import com.example.cachenow.common.event.UserOnlineEvent;
import com.example.cachenow.domain.User;
import com.example.cachenow.dto.UserDTO;
import com.example.cachenow.mapper.GroupMemberDao;
import com.example.cachenow.mapper.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用户下线监听器
 *
 * @Author: Ifela
 * @Date: 2023/11/20 19:46:22
 */
@Slf4j
@Component
public class UserOfflineListener {

    @Autowired
    private UserCache userCache;
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Autowired
    private GroupMemberDao groupMemberDao;
    @Autowired
    private UserDao userDao;

    @Async
    @EventListener(classes = UserOfflineListener.class)
    public void saveCacheAndPush(UserOfflineEvent event) {
        // todo 这里统一做用户下线逻辑
        UserDTO loginUser = event.getUserDTO();
        userCache.offline(Math.toIntExact(loginUser.getId()));

    }

    @Async
    @EventListener(classes = UserOfflineEvent.class)
    public void saveDB(UserOnlineEvent event) {
        Long uid = event.getUserDTO().getId();
        User user = new User();
        user.setUser_id(Math.toIntExact(uid));
        user.setActive_status(2);// 2为离线状态
        userDao.updateById(user);
    }
}
