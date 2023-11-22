package com.example.cachenow.service.impl;


import cn.hutool.core.collection.ConcurrentHashSet;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachenow.chat.MQProducer;
import com.example.cachenow.common.ErrorCode;
import com.example.cachenow.common.cache.UserCache;
import com.example.cachenow.domain.ChatMessage;
import com.example.cachenow.domain.GroupMember;
import com.example.cachenow.domain.User;
import com.example.cachenow.dto.ChatMessageDTO;
import com.example.cachenow.expetion.BizException;
import com.example.cachenow.mapper.ChatMessageDao;
import com.example.cachenow.mapper.GroupMemberDao;
import com.example.cachenow.mapper.UserDao;
import com.example.cachenow.service.IChatMessageService;
import com.example.cachenow.utils.Constants.MqConstants;
import com.example.cachenow.utils.Constants.RedisConstants;
import com.example.cachenow.utils.other.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@Service
public class ChatMessageServiceImpl extends ServiceImpl<ChatMessageDao, ChatMessage> implements IChatMessageService {

    @Autowired
    private MQProducer mqProducer;
    @Autowired
    private UserCache userCache;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ChatMessageDao chatMessageDao;
    @Autowired
    private GroupMemberDao groupMemberDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Override
    public Integer sendMsg(ChatMessageDTO request) {
        ChatMessage message = new ChatMessage();
        message.setSender_id(Math.toIntExact(UserHolder.getUser().getId()));
        message.setMessage(request.getMessage());
        message.setReceiver_id(request.getRoomId());
        chatMessageDao.insert(message);
        Integer msgId = message.getMessage_id();

        // 查询房间中的所有用户
        List<Integer> uIds = new ArrayList<>();
        QueryWrapper<GroupMember> groupMemberQueryWrapper = new QueryWrapper<GroupMember>().
                eq("group_id", request.getRoomId()).
                select("user_id");
        groupMemberDao.
                selectList(groupMemberQueryWrapper).
                forEach(member -> uIds.add(member.getUser_id()));

        ConcurrentHashSet<Integer> onlineUserSet = userCache.getOnlineSet();

        // 查询房间中在线用户
        List<Integer> activeUidList = new ArrayList<>();
        if (uIds.size() == 0) {
            throw new BizException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<User>().
                in("user_id", uIds).
                eq("active_status", 1).// 1表示在线状态
                        select("user_id");
        userDao.selectList(userQueryWrapper).
                forEach(activeUser -> activeUidList.add(activeUser.getUser_id()));
        // 离线用户，排除在线用户
        uIds.removeAll(activeUidList);
        List<Integer> inactiveUidList = uIds;// x7o

        // 在线用户实时推送，离线用户离线存储
        ChatMessageDTO dto = new ChatMessageDTO();
        dto.setMessage(request.getMessage());
        dto.setRoomId(request.getRoomId());
        dto.setUidList(activeUidList);
        msgRouting(dto);

        dto.setUidList(inactiveUidList);
        store(dto);

        executor.execute(() -> {
            System.out.println("throw a e.");
            throw new BizException(ErrorCode.SYSTEM_ERROR,"1234");
        });
        return msgId;
    }

    private void msgRouting(ChatMessageDTO chatMessageDTO) {
        mqProducer.sendMsg(MqConstants.SEND_MSG_EXCHANGE,
                null,
                JSONUtil.toJsonStr(chatMessageDTO));
    }

    @Async
    protected void store(ChatMessageDTO dto) {
        stringRedisTemplate.opsForValue().set(RedisConstants.USER_OFFLINE_MSG,
                JSONUtil.toJsonStr(dto));
    }


}
