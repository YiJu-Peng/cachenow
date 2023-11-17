package com.example.cachenow.dto;

import lombok.Data;
import lombok.ToString;

/**
 * 时间  2023/11/9 22:48
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
@Data
@ToString
public class MsgSendDTO {
    private Integer receiver_id;

    private String message;
}
