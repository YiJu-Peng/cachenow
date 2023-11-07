package com.example.cachenow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.example.cachenow.dto.ResourceDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
@Document(indexName = "resource_index")
@TableName("Resource")
public class Resource implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @TableId(value = "resource_id", type = IdType.AUTO)
    private Long resource_id;

    private String title;

    private String content;

    //这个是用来标识分类的
    private Integer category_id;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime created_at;

    //上传者的id
    private Long uploader_id;

    private Float rating;//记录的是整体的评分

    private Integer total_ratings;//记录的是评价的次数

    public Long getId() {
        return resource_id;
    }
    public Resource(ResourceDTO resourceDTO) {
        //这个后面还是改改吧这个id生成地策略不是很好
        this.resource_id =System.currentTimeMillis();
        this.title = resourceDTO.getTitle();
        this.content = resourceDTO.getContent();
        this.category_id = resourceDTO.getCategoryid();
        this.created_at = LocalDateTime.now();
        this.uploader_id=resourceDTO.getUserId();
        this.rating = 0f;
        this.total_ratings = 0;
    }

    public Resource() {

    }

    public Resource(Long id, String name, String description) {
        this.resource_id = id;
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
