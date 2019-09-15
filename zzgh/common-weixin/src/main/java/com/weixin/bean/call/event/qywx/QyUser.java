package com.weixin.bean.call.event.qywx;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2017/8/1.
 */
public class QyUser extends ChangeEvent {

    public QyUser(String changeType) {
        super(changeType);
    }

    @JsonProperty("UserID")
    private String userid;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Department")
    private String department;
    @JsonProperty("Position")
    private String position;
    @JsonProperty("Mobile")
    private String mobile;
    @JsonProperty("Gender")
    private Integer gender;
    @JsonProperty("Email")
    private String email;
    @JsonProperty("Status")
    private Integer status;
    @JsonProperty("Avatar")
    private String avatar;
    @JsonProperty("EnglishName")
    private String englishName;
    @JsonProperty("IsLeader")
    private Integer isLeader;
    @JsonProperty("Telephone")
    private String telephone;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Integer getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
