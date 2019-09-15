package com.weixin.bean.message;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class MpNews extends Msg {

    private List<MpArticle> articles;

    public MpNews() {
        super("mpnews");
    }

    public List<MpArticle> getArticles() {
        return articles;
    }

    public void setArticles(List<MpArticle> articles) {
        this.articles = articles;
    }
}
