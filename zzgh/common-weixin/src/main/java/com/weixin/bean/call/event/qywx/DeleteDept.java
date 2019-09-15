package com.weixin.bean.call.event.qywx;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2017/8/1.
 */
public class DeleteDept extends ChangeEvent {

    @JsonProperty("Id")
    private Integer id;

    public DeleteDept() {
        super("delete_party");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
