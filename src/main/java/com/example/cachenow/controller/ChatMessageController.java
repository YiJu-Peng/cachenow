package com.example.cachenow.controller;


import com.example.cachenow.domain.ChatMessage;
import com.example.cachenow.dto.ChatMessageDTO;
import com.example.cachenow.dto.Result;
import com.example.cachenow.service.impl.ChatMessageServiceImpl;
import com.example.cachenow.utils.other.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@RestController
@RequestMapping("/chatMessage")
public class ChatMessageController {
    private final ChatMessageServiceImpl chatMessageService;

    @Autowired
    public ChatMessageController(ChatMessageServiceImpl chatMessageService) {
        this.chatMessageService = chatMessageService;
    }

    /**
     * 消息的存储
     * @param chatMessageDTO 前端需要传入接收者
     * @return 返回成功
     */
    @PostMapping("/chat-messages")
    public Result createChatMessage(@RequestBody ChatMessageDTO chatMessageDTO) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setSender_id(Math.toIntExact(UserHolder.getUser().getId()));
        chatMessage.setReceiver_id(chatMessageDTO.getReceiver_id());
        chatMessage.setMessage(chatMessageDTO.getMessage());
        chatMessage.setCreated_at(LocalDateTime.now());
        chatMessageService.save(chatMessage);
        return Result.ok();
    }
}

