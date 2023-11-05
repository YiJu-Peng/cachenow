package com.example.cachenow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */@Entity
public class GroupMember implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableId(value = "member_id", type = IdType.ASSIGN_ID)
    private Integer member_id;

    private Integer group_id;

    private Integer user_id;


    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public Integer getGroup_id() {
        return group_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "GroupMember{" +
        "member_id=" + member_id +
        ", group_id=" + group_id +
        ", user_id=" + user_id +
        "}";
    }
}
