package com.example.cachenow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.cachenow.dto.ResourceDTO;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

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
 */
@Data
@Entity
@Document(indexName = "resource_index")
public class Resource implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @TableId(value = "resource_id", type = IdType.ASSIGN_ID)
    private Integer resource_id;

    private String title;

    private String content;

    //这个是用来标识分类的
    private Integer category_id;

    private LocalDateTime created_at;

    //上传者的id
    private Integer uploader_id;

    private Float rating;//记录的是整体的评分

    private Integer total_ratings;//记录的是评价的次数

    public Resource(ResourceDTO resourceDTO) {
        this.title = resourceDTO.getTitle();
        this.content = resourceDTO.getContent();
    }

    public Resource() {

    }

    public Resource(Long id, String name, String description) {
        this.resource_id = id.intValue();
        this.title = name;
        this.content = description;
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
