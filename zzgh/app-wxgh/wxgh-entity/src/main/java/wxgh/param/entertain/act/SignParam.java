package wxgh.param.entertain.act;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/8/30.
 */
public class SignParam extends Page {

    private Integer actId;
    private Integer status;
    private Integer dateid;

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

    public Integer getDateid() {
        return dateid;
    }

    public void setDateid(Integer dateid) {
        this.dateid = dateid;
    }
}
