package com.example.cachenow.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SyncMessage implements Serializable {
    private Long id;
    private String name;
    private String description;

    public SyncMessage(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // 省略构造函数、getter和setter方法
}