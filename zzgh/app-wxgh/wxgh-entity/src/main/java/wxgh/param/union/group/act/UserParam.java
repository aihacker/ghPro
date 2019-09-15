package wxgh.param.union.group.act;

import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/7/26.
 */
public class UserParam extends Page {

    private Integer actId; //活动ID
    private Integer type; //参与类型
    private Integer deptId;


    public Integer getDeptid() {
        return deptId;
    }

    public void setDeptid(Integer deptId) {
        this.deptId = deptId;
    }

    public Integer getActId() {
        return actId;
    }

    public void setActId(Integer actId) {
        this.actId = actId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
