package com.example.cachenow.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cachenow.domain.ChatMessage;
import com.example.cachenow.dto.ChatMessageDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
public interface IChatMessageService extends IService<ChatMessage> {
    Integer sendMsg(ChatMessageDTO request);
}
