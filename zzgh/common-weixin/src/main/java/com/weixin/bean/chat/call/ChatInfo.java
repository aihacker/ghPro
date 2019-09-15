package com.weixin.bean.chat.call;

/**
 * Created by Administrator on 2017/4/12.
 */
public class ChatInfo {

    private String chatId;
    private String name;
    private String owner;
    private String userlist;

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
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

    public String getUserlist() {
        return userlist;
    }

    public void setUserlist(String userlist) {
        this.userlist = userlist;
    }
}
