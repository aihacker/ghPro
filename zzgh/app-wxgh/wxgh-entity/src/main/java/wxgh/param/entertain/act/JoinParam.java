package wxgh.param.entertain.act;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/7/25.
 */
public class JoinParam extends Page {

    private Integer type;
    private Integer actId;
    private Integer dateid;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public Integer getDateid() {
        return dateid;
    }

    public void setDateid(Integer dateid) {
        this.dateid = dateid;
    }

    @Override
    public String toString() {
        return "JoinParam{" +
                "type=" + type +
                ", actId=" + actId +
                ", dateid=" + dateid +
                '}';
    }
}
