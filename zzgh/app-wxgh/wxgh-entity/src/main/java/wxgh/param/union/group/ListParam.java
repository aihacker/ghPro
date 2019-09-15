package wxgh.param.union.group;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/7/20.
 */
public class ListParam extends Page {

    private Integer status;
    private String userid;

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
}
