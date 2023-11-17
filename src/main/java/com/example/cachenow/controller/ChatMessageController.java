package com.example.cachenow.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cachenow.common.ErrorCode;
import com.example.cachenow.domain.ChatMessage;
import com.example.cachenow.dto.ChatMsgQueryRequest;
import com.example.cachenow.dto.Result;
import com.example.cachenow.dto.UserDTO;
import com.example.cachenow.expetion.BizException;
import com.example.cachenow.service.IChatMessageService;
import com.example.cachenow.utils.other.UserHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@RestController
@RequestMapping("/chatMessage")
public class ChatMessageController {

    public static final int MAX_MSG_LEN = 1000;

    @Autowired
    private IChatMessageService chatMessageService;

    @Autowired
    private MqController mqController;

    // TODO: 不支持 @ 功能、不支持发送除文字外的消息。参数：群聊/用户标记
    @PostMapping("/send/{id}")
    public Result send(String msg, @PathVariable("id") long id) {
        if (msg == null || msg.length() == 0) {
            throw new BizException(ErrorCode.PARAMS_ERROR, "发送消息不能为空");
        }
        if (msg.length() > MAX_MSG_LEN) {
            throw new BizException(ErrorCode.PARAMS_ERROR, "发送消息超出限制，最长支持字数：" + MAX_MSG_LEN);
        }
        // TODO: 向 mq 发送消息
        // mqController.send(msg);
        UserDTO user = UserHolder.getUser();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(msg);
        chatMessage.setSender_id(Math.toIntExact(user.getId()));
        chatMessage.setReceiver_id(Math.toIntExact(id));
        boolean res = chatMessageService.save(chatMessage);
        return Result.ok(res);
    }

    // TODO: 如果拉取消息列表之后又有一条新的消息，前端去后端加载
    @GetMapping("/list")
    public Result list(@RequestBody ChatMsgQueryRequest queryRequest) {
        if (queryRequest == null) {
            throw new BizException(ErrorCode.PARAMS_ERROR);
        }
        ChatMessage chatMessage = new ChatMessage();
        BeanUtils.copyProperties(queryRequest, chatMessage);
        long current = queryRequest.getCurrent();
        long size = queryRequest.getPageSize();
        String message = queryRequest.getMessage();
        // 存 redis
        chatMessage.setMessage(null);
        QueryWrapper<ChatMessage> queryWrapper = new QueryWrapper<>(chatMessage);
        queryWrapper.like(StringUtils.isNotBlank(message), "message", message);
        queryWrapper.orderByDesc("created_at");// 聊天消息默认是时间倒排
        Page<ChatMessage> page = chatMessageService.page(new Page<>(current, size), queryWrapper);
        return Result.ok(page);
    }

    // TODO: 消息撤回
    @PostMapping("/withdrawn")
    public Result withdrawn() {
        return Result.fail("todo");
    }

}

