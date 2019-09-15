package com.weixin.bean.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.libs.common.type.TypeUtils;
import com.weixin.json.MessageSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XLKAI on 2017/7/11.
 */
@JsonSerialize(using = MessageSerialize.class)
public class Message<T extends Msg> {

    private List<String> touser;
    private List<String> toparty;
    private List<String> totag;
    private Integer agentid;
    private Integer safe;
    private T msg;

    public Message(Integer agentid) {
        this.agentid = agentid;
    }

    public Message(Integer agentid, T msg) {
        this.agentid = agentid;
        this.msg = msg;
    }

    public Message() {
    }

    public void addUser(String userid) {
        if (TypeUtils.empty(touser)) {
            touser = new ArrayList<>();
        }
        touser.add(userid);
    }

    public void addParty(Integer party) {
        if (TypeUtils.empty(toparty)) {
            toparty = new ArrayList<>();
        }
        toparty.add(party.toString());
    }

    public void addTag(Integer tagid) {
        if (TypeUtils.empty(totag)) {
            totag = new ArrayList<>();
        }
        totag.add(tagid.toString());
    }

    public List<String> getTouser() {
        return touser;
    }

    public void setTouser(List<String> touser) {
        this.touser = touser;
    }

    public List<String> getToparty() {
        return toparty;
    }

    public void setToparty(List<String> toparty) {
        this.toparty = toparty;
    }

    public List<String> getTotag() {
        return totag;
    }

    public void setTotag(List<String> totag) {
        this.totag = totag;
    }

    public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public Integer getSafe() {
        return safe;
    }

    public void setSafe(Integer safe) {
        this.safe = safe;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "touser=" + touser +
                ", toparty=" + toparty +
                ", totag=" + totag +
                ", agentid=" + agentid +
                ", safe=" + safe +
                ", msg=" + msg +
                '}';
    }
}
