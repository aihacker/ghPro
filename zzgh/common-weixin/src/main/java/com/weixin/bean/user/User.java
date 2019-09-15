package com.weixin.bean.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.libs.common.type.TypeUtils;
import com.weixin.bean.call.event.qywx.QyUser;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class User extends SimpleUser {

    public enum Status {
        ALL(0), //全部成员
        ATTENTION(1), //已关注
        FORBID(2), //已禁用
        NO_ATTENTION(4); //未关注

        private int status;

        Status(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

    private String position;
    private String mobile;
    private Integer gender;
    private String email;
    private String weixinid;
    private String avatar;
    private UserExtattr extattr;

    @JsonProperty("avatar_mediaid")
    private String avatarMediaid;
    private Integer enable;
    private Integer status;

    public User() {
    }

    public User(QyUser user) {
        this.position = user.getPosition();
        this.mobile = user.getMobile();
        this.gender = user.getGender();
        this.email = user.getEmail();
        this.avatar = user.getAvatar();
        this.status = user.getStatus();
        this.setUserid(user.getUserid());
        this.setName(user.getName());
        this.setDepartment(TypeUtils.strToList(user.getDepartment()));
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

    public String getWeixinid() {
        return weixinid;
    }

    public void setWeixinid(String weixinid) {
        this.weixinid = weixinid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserExtattr getExtattr() {
        return extattr;
    }

    public void setExtattr(UserExtattr extattr) {
        this.extattr = extattr;
    }

    public String getAvatarMediaid() {
        return avatarMediaid;
    }

    public void setAvatarMediaid(String avatarMediaid) {
        this.avatarMediaid = avatarMediaid;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "position='" + position + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", weixinid='" + weixinid + '\'' +
                ", avatar='" + avatar + '\'' +
                ", extattr=" + extattr +
                ", avatarMediaid='" + avatarMediaid + '\'' +
                ", enable=" + enable +
                ", status=" + status +
                '}';
    }
}
