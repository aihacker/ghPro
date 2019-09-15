package com.weixin.bean.chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class UpdateChat {

    private String chatid;
    @JsonProperty("op_user")
    private String opUser;
    private String name;
    private String owner;
    @JsonProperty("add_user_list")
    private List<String> addUsers;
    @JsonProperty("del_user_list")
    private List<String> delUsers;

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getAddUsers() {
        return addUsers;
    }

    public void setAddUsers(List<String> addUsers) {
        this.addUsers = addUsers;
    }

    public List<String> getDelUsers() {
        return delUsers;
    }

    public void setDelUsers(List<String> delUsers) {
        this.delUsers = delUsers;
    }
}
