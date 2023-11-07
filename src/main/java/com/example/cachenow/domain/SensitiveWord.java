package com.example.cachenow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */@TableName("SensitiveWord")
public class SensitiveWord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "word_id", type = IdType.ASSIGN_ID)
    private Integer word_id;

    private String word;


    public Integer getWord_id() {
        return word_id;
    }

    public void setWord_id(Integer word_id) {
        this.word_id = word_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "SensitiveWord{" +
        "word_id=" + word_id +
        ", word=" + word +
        "}";
    }
}
