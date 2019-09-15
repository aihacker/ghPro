package com.weixin;

import com.libs.common.crypt.SHA1;
import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.api.ContactTicketApi;
import com.weixin.api.TicketApi;
import com.weixin.api.TokenApi;
import com.weixin.bean.ContactTicket;
import com.weixin.config.Config;
import org.apache.commons.collections.map.HashedMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by XLKAI on 2017/7/8.
 */
public class Weixin {


    public static String getURL(String url, Object... params) {
        StringBuilder builder = new StringBuilder(Config.HOST);
        builder.append("/");
        builder.append(String.format(url, params));
        return builder.toString();
    }

    public static String getAgentURL(String url, Integer agentId, Object... params) throws WeixinException {
        StringBuilder builder = new StringBuilder(Config.HOST);
        builder.append("/");
        url = String.format(url, params);
        if (url.indexOf("?") > 0) {
            url += "&access_token=" + getToken(agentId);
        } else {
            url += "?access_token=" + getToken(agentId);
        }
        builder.append(url);
        return builder.toString();
    }

    public static String getContactURL(String url, Object... params) throws WeixinException {
        return getAgentURL(url, Agent.CONCAT.getAgentId(), params);
    }

    public static String getTokenURL(String url, Object... params) throws WeixinException {
        StringBuilder builder = new StringBuilder(Config.HOST);
        builder.append("/");

        url = String.format(url, params);
        if (url.indexOf("?") > 0) {
            url += "&access_token=" + getToken();
        } else {
            url += "?access_token=" + getToken();
        }
        builder.append(url);
        return builder.toString();
    }

    /*public static String getTokenURL(String url, Integer agentid, Object... params) throws WeixinException {
        StringBuilder builder = new StringBuilder(Config.HOST);
        builder.append("/");

        url = String.format(url, params);
        if (url.indexOf("?") > 0) {
            url += "&access_token=" + getToken(agentid);
        } else {
            url += "?access_token=" + getToken(agentid);
        }
        builder.append(url);
        return builder.toString();
    }*/

    public static String getToken() throws WeixinException {
        return TokenApi.getToken().getToken();
    }

    public static String getToken(Integer agentId) throws WeixinException {
        return TokenApi.getToken(agentId).getToken();
    }

    public static Map<String, Object> sign(String url, List<String> apiList) throws WeixinException {
        return sign(url, apiList, null);
    }

    public static Map<String, Object> sign_contact(String url) throws WeixinException {
        Map<String, Object> map = new HashedMap();
        ContactTicket ticket = ContactTicketApi.getTicket();
        String noneStr = StringUtils.uuid();
        Long timestamp = System.currentTimeMillis() / 1000;

        String string = "group_ticket=" + ticket.getTicket() +
                "&noncestr=" + noneStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        String signature = SHA1.encrypt(string);

        map.put("url", url);
        map.put("nonceStr", noneStr);
        map.put("timestamp", timestamp);
        map.put("signature", signature);
        map.put("groupId", ticket.getGroupId());

        return map;
    }

    /**
     * jsapi签名
     *
     * @param url
     * @param apiList
     * @param menuList
     * @return
     * @throws WeixinException
     */
    public static Map<String, Object> sign(String url, List<String> apiList, List<String> menuList) throws WeixinException {
        String ticket = TicketApi.getTicket();
        String noneStr = StringUtils.uuid();
        Long timestamp = System.currentTimeMillis() / 1000;

        StringBuffer builder = new StringBuffer();
        builder.append("jsapi_ticket=" + ticket);
        builder.append("&noncestr=" + noneStr);
        builder.append("&timestamp=" + timestamp);
        builder.append("&url=" + url);

        String signature = SHA1.encrypt(builder.toString());

        Map<String, Object> map = new HashMap<>();
        map.put("url", url);
        map.put("jsapi_ticket", ticket);
        map.put("nonceStr", noneStr);
        map.put("timestamp", timestamp);
        map.put("signature", signature);
        map.put("appId", Config.APPID);
        map.put("debug", Config.DEBUG);
        if (!TypeUtils.empty(menuList)) {
            map.put("menuList", menuList);
        }
        map.put("apiList", apiList);

        return map;
    }

    public static String getName(String name, Integer agentId) {
        if (agentId != null) {
            name += "_" + agentId;
        }
        return name;
    }

}
