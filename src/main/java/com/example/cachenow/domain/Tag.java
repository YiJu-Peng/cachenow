package com.example.cachenow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.data.elasticsearch.annotations.Document;

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
 */

@Document(indexName = "tag_index")
@Entity
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableId(value = "tag_id", type = IdType.ASSIGN_ID)
    private Integer tag_id;

    private String name;

    private Integer resource_id;


    public Integer getTag_id() {
        return tag_id;
    }

    public void setTag_id(Integer tag_id) {
        this.tag_id = tag_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResource_id() {
        return resource_id;
    }

    public void setResource_id(Integer resource_id) {
        this.resource_id = resource_id;
    }

    @Override
    public String toString() {
        return "Tag{" +
        "tag_id=" + tag_id +
        ", name=" + name +
        ", resource_id=" + resource_id +
        "}";
    }
}
