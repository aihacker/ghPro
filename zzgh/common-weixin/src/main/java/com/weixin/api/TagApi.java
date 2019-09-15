package com.weixin.api;

import com.weixin.Weixin;
import com.weixin.WeixinException;
import com.weixin.bean.ErrResult;
import com.weixin.bean.result.tag.AddTagUserResult;
import com.weixin.bean.result.tag.TagResult;
import com.weixin.bean.result.tag.TagUserResult;
import com.weixin.bean.tag.Tag;
import com.weixin.bean.tag.TagUser;
import com.weixin.utils.WeixinHttp;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class TagApi {

    /**
     * 创建标签
     *
     * @param tag
     * @throws WeixinException
     */
    public static void create(Tag tag) throws WeixinException {
        String url = Weixin.getTokenURL("tag/create");
        WeixinHttp.post(url, tag, ErrResult.class);
    }

    /**
     * 更新标签
     *
     * @param tag
     * @throws WeixinException
     */
    public static void update(Tag tag) throws WeixinException {
        String url = Weixin.getTokenURL("tag/update");
        WeixinHttp.post(url, tag, ErrResult.class);
    }

    /**
     * 删除标签
     *
     * @param id
     * @throws WeixinException
     */
    public static void delete(int id) throws WeixinException {
        String url = Weixin.getTokenURL("tag/delete?tagid=%d", id);
        WeixinHttp.get(url, ErrResult.class);
    }

    /**
     * 获取标签成员
     *
     * @param id
     * @return
     * @throws WeixinException
     */
    public static TagUserResult get(int id) throws WeixinException {
        String url = Weixin.getContactURL("tag/get?tagid=%d", id);
        return WeixinHttp.get(url, TagUserResult.class);
    }

    /**
     * 增加标签成员
     *
     * @param tagUser
     * @return
     * @throws WeixinException
     */
    public static AddTagUserResult addtagusers(TagUser tagUser) throws WeixinException {
        if (tagUser.getUserlist().size() > 1000) {
            throw new WeixinException("single request not more than 1000 when add tag users (userlist size=" + tagUser.getUserlist().size() + ")");
        }
        if (tagUser.getPartylist().size() > 100) {
            throw new WeixinException("single request not more than 1000 when add tag partys (partys size=" + tagUser.getPartylist().size() + ")");
        }
        String url = Weixin.getContactURL("tag/addtagusers");
        return WeixinHttp.post(url, tagUser, AddTagUserResult.class);
    }

    /**
     * 删除标签成员
     *
     * @param tagUser
     * @return
     * @throws WeixinException
     */
    public static AddTagUserResult deltagusers(TagUser tagUser) throws WeixinException {
        String url = Weixin.getContactURL("tag/deltagusers");
        return WeixinHttp.post(url, tagUser, AddTagUserResult.class);
    }

    /**
     * 获取标签列表
     *
     * @return
     * @throws WeixinException
     */
    public static List<Tag> list() throws WeixinException {
        String url = Weixin.getContactURL("tag/list");
        TagResult result = WeixinHttp.get(url, TagResult.class);
        return result.getTaglist();
    }
}
