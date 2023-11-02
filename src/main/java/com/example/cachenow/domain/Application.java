package com.example.cachenow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-11-02
 */
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "application_id", type = IdType.ASSIGN_ID)
    private Integer application_id;

    private Integer application_status;

    private Integer application_type;

    private Integer user_id;

    private String application_reason;

    private String rejection_reason;

    private LocalDateTime create_time;

    private LocalDateTime update_time;


    public Integer getApplication_id() {
        return application_id;
    }

    public void setApplication_id(Integer application_id) {
        this.application_id = application_id;
    }

    public Integer getApplication_status() {
        return application_status;
    }

    public void setApplication_status(Integer application_status) {
        this.application_status = application_status;
    }

    public Integer getApplication_type() {
        return application_type;
    }

    public void setApplication_type(Integer application_type) {
        this.application_type = application_type;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getApplication_reason() {
        return application_reason;
    }

    public void setApplication_reason(String application_reason) {
        this.application_reason = application_reason;
    }

    public String getRejection_reason() {
        return rejection_reason;
    }

    public void setRejection_reason(String rejection_reason) {
        this.rejection_reason = rejection_reason;
    }

    public LocalDateTime getCreate_time() {
        return create_time;
    }

    public void setCreate_time(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public LocalDateTime getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(LocalDateTime update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "Application{" +
        "application_id=" + application_id +
        ", application_status=" + application_status +
        ", application_type=" + application_type +
        ", user_id=" + user_id +
        ", application_reason=" + application_reason +
        ", rejection_reason=" + rejection_reason +
        ", create_time=" + create_time +
        ", update_time=" + update_time +
        "}";
    }
}
