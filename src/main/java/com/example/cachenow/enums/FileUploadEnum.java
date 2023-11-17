package com.example.cachenow.enums;

import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Ifela
 * @Date: 2023/11/9 22:48:56
 */
public enum FileUploadEnum {

    USER_AVATAR("用户头像", "user_avatar"),
    ARTICLE_IMAGE("文章图片", "article_image"),
    RESOURCE_FILE("资源文件", "resource_file");

    private final String text;

    private final String value;

    FileUploadEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取值列表
     *
     * @return
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value
     * @return
     */
    public static FileUploadEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (FileUploadEnum anEnum : FileUploadEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
