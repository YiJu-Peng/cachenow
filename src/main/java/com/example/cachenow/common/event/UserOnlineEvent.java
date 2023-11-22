package com.example.cachenow.common.event;

import com.example.cachenow.dto.UserDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 用户上线事件
 *
 * @Author: Ifela
 * @Date: 2023/11/20 19:44:14
 */
@Getter
public class UserOnlineEvent extends ApplicationEvent {
    private final UserDTO userDTO;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param source the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})
     */
    public UserOnlineEvent(Object source, UserDTO userDTO) {
        super(source);
        this.userDTO = userDTO;
    }

}
