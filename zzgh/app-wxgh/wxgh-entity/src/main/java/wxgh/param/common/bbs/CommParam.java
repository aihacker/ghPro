package wxgh.param.common.bbs;

import pub.dao.page.Page;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-26 11:13
 *----------------------------------------------------------
 */
public class CommParam extends Page {

    private Integer articleId;
    private Integer isdel = 1;
    private Integer status = 1;
    private String userid;
    private Integer type;

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Integer getIsdel() {
        return isdel;
    }

    public void setIsdel(Integer isdel) {
        this.isdel = isdel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "CommParam{" +
                "articleId=" + articleId +
                ", isdel=" + isdel +
                ", status=" + status +
                ", userid='" + userid + '\'' +
                ", type=" + type +
                '}';
    }
}

