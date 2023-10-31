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
 * @since 2023-10-31
 */
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "resource_id", type = IdType.ASSIGN_ID)
    private Integer resource_id;

    private String title;

    private String content;

    private Integer category_id;

    private LocalDateTime created_at;

    private Integer uploader_id;

    private Float rating;

    private Integer total_ratings;


    public Integer getResource_id() {
        return resource_id;
    }

    public void setResource_id(Integer resource_id) {
        this.resource_id = resource_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Integer getUploader_id() {
        return uploader_id;
    }

    public void setUploader_id(Integer uploader_id) {
        this.uploader_id = uploader_id;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Integer getTotal_ratings() {
        return total_ratings;
    }

    public void setTotal_ratings(Integer total_ratings) {
        this.total_ratings = total_ratings;
    }

    @Override
    public String toString() {
        return "Resource{" +
        "resource_id=" + resource_id +
        ", title=" + title +
        ", content=" + content +
        ", category_id=" + category_id +
        ", created_at=" + created_at +
        ", uploader_id=" + uploader_id +
        ", rating=" + rating +
        ", total_ratings=" + total_ratings +
        "}";
    }
}
