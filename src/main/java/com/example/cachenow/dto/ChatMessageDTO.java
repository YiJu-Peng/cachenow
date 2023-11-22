package com.example.cachenow.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 时间  2023/11/9 22:48
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
@Data
@ToString
public class ChatMessageDTO {

    private Integer roomId;

    private String message;

    // 1群聊/2单聊
    private Integer type;

    // 待发送的用户ids
    private List<Integer> uidList;

}
