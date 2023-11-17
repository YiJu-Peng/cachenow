package com.example.cachenow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


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
@Document(indexName = "user_index")
@TableName("User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Integer user_id;

    private String username;

    private String password;

    private LocalDateTime created_at;

    private String icon;

    private String role;

    private String email;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
        "user_id=" + user_id +
        ", username=" + username +
        ", password=" + password +
        ", created_at=" + created_at +
        ", icon=" + icon +
        ", email=" + email +
        "}";
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }
}
