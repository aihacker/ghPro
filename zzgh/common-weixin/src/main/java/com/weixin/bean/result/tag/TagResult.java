package com.weixin.bean.result.tag;

import com.weixin.bean.ErrResult;
import com.weixin.bean.tag.Tag;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class TagResult extends ErrResult {

    private List<Tag> taglist;

    public List<Tag> getTaglist() {
        return taglist;
    }

    public void setTaglist(List<Tag> taglist) {
        this.taglist = taglist;
    }
}
