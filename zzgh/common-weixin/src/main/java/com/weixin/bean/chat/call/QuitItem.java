package com.weixin.bean.chat.call;

/**
 * Created by Administrator on 2017/4/12.
 */
public class QuitItem extends EventItem {

    private String chatid;

    public QuitItem() {
    }

    public QuitItem(EventItem item) {
        super(item);
    }

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }
}
