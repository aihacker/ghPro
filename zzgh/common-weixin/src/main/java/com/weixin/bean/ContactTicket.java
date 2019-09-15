package com.weixin.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2017/7/17.
 */
public class ContactTicket extends ErrResult {

    @JsonProperty("group_id")
    private String groupId;
    private String ticket;
    @JsonProperty("expires_in")
    private Integer expiresIn;
    private Long addTime;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }
}
