package com.weixin.bean.message;

import com.libs.common.type.TypeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class News extends Msg {

    private List<Article> articles;

    public News() {
        super("news");
    }

    public void addArtice(Article article) {
        if (TypeUtils.empty(articles)) {
            articles = new ArrayList<>();
        }
        articles.add(article);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
