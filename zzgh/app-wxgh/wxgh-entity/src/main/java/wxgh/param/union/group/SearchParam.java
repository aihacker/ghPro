package wxgh.param.union.group;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/7/31.
 */
public class SearchParam extends Page {

    private Integer status;
    private String key;
    private String userid;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
