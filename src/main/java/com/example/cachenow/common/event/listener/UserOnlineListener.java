package com.example.cachenow.common.event.listener;

import com.example.cachenow.common.cache.UserCache;
import com.example.cachenow.common.event.UserOnlineEvent;
import com.example.cachenow.domain.User;
import com.example.cachenow.dto.UserDTO;
import com.example.cachenow.mapper.GroupMemberDao;
import com.example.cachenow.mapper.UserDao;
import com.example.cachenow.utils.Constants.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 用户上线监听器
 *
 * @Author: Ifela
 * @Date: 2023/11/20 19:46:22
 */
@Slf4j
@Component
public class UserOnlineListener {

    @Autowired
    private UserCache userCache;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private AmqpAdmin amqpAdmin;
    @Autowired
    private GroupMemberDao groupMemberDao;
    @Autowired
    private UserDao userDao;

    @Async
    @EventListener(classes = UserOnlineEvent.class)
    public void saveCacheAndPush(UserOnlineEvent event) {
        // todo 这里统一做用户上线逻辑
        UserDTO loginUser = event.getUserDTO();
        userCache.online(Math.toIntExact(loginUser.getId()));

        // 获取离线消息
        stringRedisTemplate.opsForSet().members(RedisConstants.USER_OFFLINE_MSG);

    }

    @Async
    @EventListener(classes = UserOnlineEvent.class)
    public void saveDB(UserOnlineEvent event) {
        Long uid = event.getUserDTO().getId();
        User user = new User();
        user.setUser_id(Math.toIntExact(uid));
        user.setActive_status(1);// 1为在线状态
        userDao.updateById(user);
    }
}
