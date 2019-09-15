package com.weixin.bean.chat.call;

/**
 * Created by Administrator on 2017/4/12.
 */
public class EventItem extends BaseItem {

    private String event;

    public EventItem() {
    }

    public EventItem(EventItem item) {
        this.event = item.getEvent();
        this.setFromName(item.getFromName());
        this.setCreateTime(item.getCreateTime());
        this.setMsgType(item.getMsgType());
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public static <T extends BaseItem> EventItem extend(T item) {
        EventItem eventItem = new EventItem();
        eventItem.setFromName(item.getFromName());
        eventItem.setMsgType(item.getMsgType());
        eventItem.setCreateTime(item.getCreateTime());
        return eventItem;
    }

}
