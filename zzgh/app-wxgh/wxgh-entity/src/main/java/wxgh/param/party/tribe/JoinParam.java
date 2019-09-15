package wxgh.param.party.tribe;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/8/17.
 */
public class JoinParam extends Page {

    private Integer actId;
    private Integer status;

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
