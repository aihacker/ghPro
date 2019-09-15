package com.weixin.bean.tag;

import com.libs.common.json.JsonNoneNull;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class Tag extends JsonNoneNull {

    private String tagname;
    private Integer tagid;

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }
}
