package com.weixin.bean.chat.call;

/**
 * Created by Administrator on 2017/4/12.
 */
public class CreateItem extends EventItem {

    public CreateItem() {
    }

    public CreateItem(EventItem item) {
        super(item);
    }

    private ChatInfo chatInfo;

    public ChatInfo getChatInfo() {
        return chatInfo;
    }

    public void setChatInfo(ChatInfo chatInfo) {
        this.chatInfo = chatInfo;
    }
}
