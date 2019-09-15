package com.weixin.bean.chat;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.weixin.json.ChatClearSerialize;

/**
 * Created by XLKAI on 2017/7/11.
 */
@JsonSerialize(using = ChatClearSerialize.class )
public class ClearChat {

    private String opUser;
    private String type;
    private String id;

    public ClearChat(String opUser, String type, String id) {
        this.opUser = opUser;
        this.type = type;
        this.id = id;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
