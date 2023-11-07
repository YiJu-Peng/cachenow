package com.example.cachenow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */@TableName("History")
public class History implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "history_id", type = IdType.ASSIGN_ID)
    private Integer history_id;

    private Integer user_id;

    private Integer resource_id;

    private LocalDateTime access_time;


    public Integer getHistory_id() {
        return history_id;
    }

    public void setHistory_id(Integer history_id) {
        this.history_id = history_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getResource_id() {
        return resource_id;
    }

    public void setResource_id(Integer resource_id) {
        this.resource_id = resource_id;
    }

    public LocalDateTime getAccess_time() {
        return access_time;
    }

    public void setAccess_time(LocalDateTime access_time) {
        this.access_time = access_time;
    }

    @Override
    public String toString() {
        return "History{" +
        "history_id=" + history_id +
        ", user_id=" + user_id +
        ", resource_id=" + resource_id +
        ", access_time=" + access_time +
        "}";
    }
}
