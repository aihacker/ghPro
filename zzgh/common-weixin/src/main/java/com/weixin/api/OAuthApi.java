package com.weixin.api;

import com.libs.common.http.HttpClient;
import com.libs.common.http.HttpException;
import com.weixin.Weixin;
import com.weixin.WeixinException;
import com.weixin.bean.ErrResult;
import com.weixin.bean.oauth.UserConver;
import com.weixin.bean.oauth.UserInfo;
import com.weixin.bean.result.oauth.ConverOpenidResult;
import com.weixin.bean.result.oauth.ConverUseridResult;
import com.weixin.bean.result.user.UserResult;
import com.weixin.bean.user.User;
import com.weixin.config.Config;
import com.weixin.utils.WeixinHttp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by XLKAI on 2017/7/10.
 */
public class OAuthApi {

    /**
     * 根据code获取成员信息
     *
     * @param code
     * @return
     * @throws WeixinException
     */
    public static UserInfo getuserinfo(String code) throws WeixinException {
        String url = Weixin.getTokenURL("user/getuserinfo?code=%s", code);
        return WeixinHttp.get(url, UserInfo.class);
    }

    /**
     * 根据user_ticket获取成员详情
     *
     * @param userTicket
     * @return
     * @throws WeixinException
     */
    public static User getuserdetail(String userTicket) throws WeixinException {
        String url = Weixin.getTokenURL("user/getuserdetail");
        String json = String.format("{\"user_ticket\":\"%s\"}", userTicket);
        try {
            UserResult result = HttpClient.post(url, json, UserResult.class);
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
     * userid转换成openid接口
     *
     * @param conver
     * @return
     * @throws WeixinException
     */
    public static ConverOpenidResult converToOpenid(UserConver conver) throws WeixinException {
        String url = Weixin.getTokenURL("user/convert_to_openid");
        return WeixinHttp.post(url, conver, ConverOpenidResult.class);
    }

    /**
     * openid转换成userid接口
     *
     * @param openid
     * @return
     * @throws WeixinException
     */
    public static String converToUserid(String openid) throws WeixinException {
        String url = Weixin.getTokenURL("user/convert_to_userid");
        String json = "{\"openid\":\"" + openid + "\"}";
        ConverUseridResult result = WeixinHttp.post(url, json, ConverUseridResult.class);
        return result.getUserid();
    }

    public static String getOAuthUrl(String backUrl) {
        System.out.println(backUrl);
        try {
            backUrl = URLEncoder.encode(backUrl, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("https://open.weixin.qq.com/connect/oauth2/authorize");
        builder.append("?appid=" + Config.APPID);
        builder.append("&redirect_uri=" + backUrl);
        builder.append("&response_type=code");
        builder.append("&scope=snsapi_base");
        builder.append("&state=STATE#wechat_redirect");

        System.out.println(builder.toString());

        return builder.toString();
    }

}
