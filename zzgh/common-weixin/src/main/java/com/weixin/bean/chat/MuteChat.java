package com.weixin.bean.chat;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class MuteChat {

    private String userid;
    private Integer status; //免打扰状态，0关闭，1开启，默认0

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
