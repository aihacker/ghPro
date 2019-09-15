package com.weixin.bean.result.batch;

import com.weixin.bean.ErrResult;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class SimpleResult extends ErrResult {

    private Integer action;

    public Integer getAction() {
        return action;
    }

    public void setAction(Integer action) {
        this.action = action;
    }
}
