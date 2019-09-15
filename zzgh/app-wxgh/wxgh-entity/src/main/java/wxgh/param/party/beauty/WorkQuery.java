package wxgh.param.party.beauty;


import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/4/28.
 */
public class WorkQuery extends Page {

    private Integer type;
    private Integer status;
    private Integer deptid;

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
