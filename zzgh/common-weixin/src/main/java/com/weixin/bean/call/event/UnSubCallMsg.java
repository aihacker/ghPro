package com.weixin.bean.call.event;

/**
 * 用户取消关注事件
 * Created by Administrator on 2017/7/13.
 */
public class UnSubCallMsg extends EventCallMsg {

    public UnSubCallMsg() {
        super("unsubscribe");
    }
}
