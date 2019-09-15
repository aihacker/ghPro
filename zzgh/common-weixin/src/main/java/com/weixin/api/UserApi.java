package com.weixin.api;

import com.libs.common.cache.AgentCache;
import com.libs.common.http.HttpClient;
import com.libs.common.http.HttpException;
import com.libs.common.json.JsonUtils;
import com.weixin.Weixin;
import com.weixin.WeixinException;
import com.weixin.bean.ErrResult;
import com.weixin.bean.result.user.SimpleUserResult;
import com.weixin.bean.result.user.UserListResult;
import com.weixin.bean.result.user.UserResult;
import com.weixin.bean.user.SimpleUser;
import com.weixin.bean.user.User;
import com.weixin.utils.WeixinHttp;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class UserApi {

    /**
     * 创建成员
     *
     * @param user
     * @throws WeixinException
     */
    public static void create(User user) throws WeixinException {
        AgentCache.set(-1);
        String url = Weixin.getTokenURL("user/create");
        WeixinHttp.post(url, user, ErrResult.class);
    }

    /**
     * 更新成员
     *
     * @param user
     * @throws WeixinException
     */
    public static void update(User user) throws WeixinException {
        AgentCache.set(-1);
        String url = Weixin.getTokenURL("user/update");
        WeixinHttp.post(url, user, ErrResult.class);
    }

    /**
     * 删除成员
     *
     * @param userid
     * @throws WeixinException
     */
    public static void delete(String userid) throws WeixinException {
        AgentCache.set(-1);
        String url = Weixin.getTokenURL("user/delete?userid=%s", userid);
        WeixinHttp.get(url, ErrResult.class);
    }

    /**
     * 批量删除成员
     *
     * @param userids
     * @throws WeixinException
     */
    public static void batchdelete(String[] userids) throws WeixinException {
        String url = Weixin.getTokenURL("user/batchdelete");
        String json = String.format("{\"useridlist\": %s}", JsonUtils.stringfy(userids));
        WeixinHttp.post(url, json, ErrResult.class);
    }

    public static void batchdelete(List<String> userids) throws WeixinException {
        Integer agentid = -1;
        String url = Weixin.getTokenURL("user/batchdelete",agentid);
        String json = String.format("{\"useridlist\": %s}", JsonUtils.stringfy(userids));
        WeixinHttp.post(url, json, ErrResult.class);
    }

    /**
     * 获取成员
     *
     * @param userid
     * @return
     * @throws WeixinException
     */
    public static User get(String userid) throws WeixinException {
        String url = Weixin.getTokenURL("user/get?userid=%s", userid);
        try {
            UserResult result = HttpClient.get(url, UserResult.class);
            ErrResult errResult = new ErrResult();
            errResult.setErrcode(result.getErrcode());
            errResult.setErrmsg(result.getErrmsg());
            WeixinHttp.parseResult(errResult);

            return result;
        } catch (HttpException e) {
            throw new WeixinException(e);
        }
    }

    /**
     * 获取部门成员
     *
     * @param deptid
     * @param fetchChild
     * @param status
     * @return
     * @throws WeixinException
     */
    public static List<SimpleUser> simplelist(Integer deptid, boolean fetchChild, User.Status status) throws WeixinException {
        String url = Weixin.getTokenURL("user/simplelist?department_id=%d&fetch_child=%d", deptid, fetchChild ? 1 : 0,status.getStatus());
        SimpleUserResult result = WeixinHttp.get(url, SimpleUserResult.class);
        return result.getUserlist();
    }

    /**
     * 获取部门成员（详情）
     *
     * @param deptid     部门Id，必填
     * @param fetchChild 是否递归获取子部门下面的成员
     * @param status     用户状态
     * @return
     * @throws WeixinException
     */
    public static List<User> list(Integer deptid, boolean fetchChild, User.Status status) throws WeixinException {
        String url = Weixin.getTokenURL("user/list?department_id=%d&fetch_child=%d&status=%d", deptid, fetchChild ? 1 : 0, status.getStatus());
        UserListResult result = WeixinHttp.get(url, UserListResult.class);
        return result.getUserlist();
    }

    /**
     * 企业号开启二次验证，调用该接口即可让成员关注成功
     *
     * @param userid
     * @throws WeixinException
     */
    public static void authsucc(String userid) throws WeixinException {
        String url = Weixin.getTokenURL("authsucc?userid=%s", userid);
        WeixinHttp.get(url, ErrResult.class);
    }

}
