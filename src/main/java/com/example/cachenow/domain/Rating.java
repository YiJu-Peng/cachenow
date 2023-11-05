package com.example.cachenow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */@Entity
public class Rating implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableId(value = "rating_id", type = IdType.ASSIGN_ID)
    private Integer rating_id;

    private Integer user_id;

    private Integer resource_id;

    private Float rating;

    private LocalDateTime created_at;


    public Integer getRating_id() {
        return rating_id;
    }

    public void setRating_id(Integer rating_id) {
        this.rating_id = rating_id;
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

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Rating{" +
        "rating_id=" + rating_id +
        ", user_id=" + user_id +
        ", resource_id=" + resource_id +
        ", rating=" + rating +
        ", created_at=" + created_at +
        "}";
    }
}
