package com.weixin.bean.chat.call;

/**
 * Created by Administrator on 2017/4/12.
 */
public class EditItem extends EventItem {

    private String name;
    private String owner;
    private String addUsers;
    private String delUsers;
    private String chatid;

    public EditItem() {
    }

    public EditItem(EventItem item) {
        super(item);
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

    public String getAddUsers() {
        return addUsers;
    }

    public void setAddUsers(String addUsers) {
        this.addUsers = addUsers;
    }

    public String getDelUsers() {
        return delUsers;
    }

    public void setDelUsers(String delUsers) {
        this.delUsers = delUsers;
    }

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }
}
