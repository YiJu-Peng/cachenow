package com.example.cachenow.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 时间  10/10/2023 上午 9:22
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
@Data
@TableName("users")
public class User implements Serializable {
    String name;
    int age;
    int id;
}
