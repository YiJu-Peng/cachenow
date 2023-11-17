package com.example.cachenow.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.mysql.cj.protocol.x.FieldFactory;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */@TableName("Comment")
@Data
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Integer comment_id;

    private Integer user_id;

    private Integer resource_id;

    private String comment;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime created_at;

    private Integer super_id;


    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Comment{" +
        "comment_id=" + comment_id +
        ", user_id=" + user_id +
        ", resource_id=" + resource_id +
        ", comment=" + comment +
        ", created_at=" + created_at +
        "}";
    }
}
