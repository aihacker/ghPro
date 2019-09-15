package com.weixin.bean.call.event.qywx;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2017/8/1.
 */
public class DeleteUser extends ChangeEvent {

    @JsonProperty("UserID")
    private String userid;

    public DeleteUser() {
        super("delete_user");
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
