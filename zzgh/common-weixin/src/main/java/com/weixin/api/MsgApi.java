package com.weixin.api;

import com.weixin.Weixin;
import com.weixin.WeixinException;
import com.weixin.bean.message.Article;
import com.weixin.bean.message.Message;
import com.weixin.bean.message.Msg;
import com.weixin.bean.message.News;
import com.weixin.bean.result.message.SendResult;
import com.weixin.utils.WeixinHttp;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class MsgApi {

    public static SendResult send(Message message) throws WeixinException {
        String url = Weixin.getAgentURL("message/send", message.getAgentid());
        Msg msg = message.getMsg();
        if (msg instanceof News) {
            News news = (News) msg;
            List<Article> articles = news.getArticles();
            for (int i = 0, len = articles.size(); i < len; i++) {
                String toUrl = getUrl(articles.get(i).getUrl(), message.getAgentid());
                articles.get(i).setUrl(toUrl);
            }
        }

        return WeixinHttp.post(url.toString(), message, SendResult.class);
    }

    private static String getUrl(String url, Integer agentId) {
        if (url.indexOf("?") > 0) {
            url += "&";
        } else {
            url += "?";
        }
        url += ("agentid=" + agentId);
        return url;
    }

}
