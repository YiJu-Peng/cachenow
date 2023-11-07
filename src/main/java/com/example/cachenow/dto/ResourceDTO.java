package com.example.cachenow.dto;

import com.example.cachenow.domain.Resource;
import lombok.Data;

@Data
public class ResourceDTO {
    private String title;
    private String content;
    private Long userId;
    private Integer categoryid;

    public ResourceDTO(String title, String content,Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
    public ResourceDTO(Resource resource) {
        this.title = resource.getTitle();
        this.content = resource.getContent();
        this.userId = resource.getUploader_id();
    }
}