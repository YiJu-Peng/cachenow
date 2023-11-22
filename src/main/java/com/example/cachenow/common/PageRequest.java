package com.example.cachenow.common;

import lombok.Data;

/**
 * 分页请求
 *
 * @Author: Ifela
 * @Date: 2023/11/15 18:18:44
 */
@Data
public class PageRequest {
    /**
     * 当前页号
     */
    private long current = 1;

    /**
     * 页面大小
     */
    private long pageSize = 10;

    /**
     * 排序字段
     */
    private String sortField;

    /**
     * 排序顺序（默认降序）
     */
    private String sortOrder = "descend";
}
