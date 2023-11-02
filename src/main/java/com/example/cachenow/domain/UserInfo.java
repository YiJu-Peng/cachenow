package com.example.cachenow.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-11-02
 */
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键，用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private Long user_id;
    /**
     * 城市名称
     */
    private String city;
    /**
     * 个人介绍，不要超过128个字符
     */
    private String introduce;
    /**
     * 粉丝数量
     */
    private Integer fans;
    /**
     * 关注的人的数量
     */
    private Integer followee;
    /**
     * 性别，0：男，1：女
     */
    private Boolean gender;
    /**
     * 生日
     */
    private LocalDate birthday;
    /**
     * 积分
     */
    private Integer credits;
    /**
     * 会员级别，0~9级,0代表未开通会员
     */
    private Boolean level;
    /**
     * 创建时间
     */
    private LocalDateTime create_time;
    /**
     * 更新时间
     */
    private LocalDateTime update_time;


    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getFollowee() {
        return followee;
    }

    public void setFollowee(Integer followee) {
        this.followee = followee;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Boolean getLevel() {
        return level;
    }

    public void setLevel(Boolean level) {
        this.level = level;
    }

    public LocalDateTime getCreatetime() {
        return create_time;
    }

    public void setCreatetime(LocalDateTime create_time) {
        this.create_time = create_time;
    }

    public LocalDateTime getUpdatetime() {
        return update_time;
    }

    public void setUpdatetime(LocalDateTime update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
        "user_id=" + user_id +
        ", city=" + city +
        ", introduce=" + introduce +
        ", fans=" + fans +
        ", followee=" + followee +
        ", gender=" + gender +
        ", birthday=" + birthday +
        ", credits=" + credits +
        ", level=" + level +
        ", create_time=" + create_time +
        ", update_time=" + update_time +
        "}";
    }
}
