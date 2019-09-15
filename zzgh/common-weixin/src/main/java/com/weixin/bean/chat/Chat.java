package com.weixin.bean.chat;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class Chat {

    public enum Type{
        SINGLE("single"),
        GROUP("gorup");

        private String type;

        Type(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    private String chatid;
    private String name;
    private String owner;
    private List<String> userlist;

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
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

    public List<String> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<String> userlist) {
        this.userlist = userlist;
    }
}
