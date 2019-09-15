package com.weixin.bean.chat.call;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */
public class ConversationMsg {

    private String agentType;
    private String toUser;
    private Integer itemCount;
    private String packageId;
    private List<BaseItem> items;

    public String getAgentType() {
        return agentType;
    }

    public void setAgentType(String agentType) {
        this.agentType = agentType;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public Integer getItemCount() {
        return itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public List<BaseItem> getItems() {
        return items;
    }

    public void setItems(List<BaseItem> items) {
        this.items = items;
    }
}
