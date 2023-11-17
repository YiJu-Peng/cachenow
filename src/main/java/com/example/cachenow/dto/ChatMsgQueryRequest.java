package com.example.cachenow.dto;

import com.example.cachenow.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author: Ifela
 * @Date: 2023/11/12 21:44:43
 */
@Data
public class ChatMsgQueryRequest extends PageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发送者
     */
    private Integer sender_id;

    /**
     * 接收者
     */
    private Integer receiver_id;

    /**
     * 发送时间
     */
    private LocalDateTime created_at;

    /**
     * 消息
     */
    private String message;
}
