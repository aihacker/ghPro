package wxgh.param.union.innovation.work;


import pub.dao.page.Page;

/**
 * Created by Administrator on 2017/5/4.
 */
public class ApplyQuery extends Page {

    private Integer type; //申请类别
    private Integer status; //申请状态
    private Integer applyStep = 1; //默认进行到1步
    private Integer deptid; //申请部门
    private String userid; //申请人

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

    public Integer getApplyStep() {
        return applyStep;
    }

    public void setApplyStep(Integer applyStep) {
        this.applyStep = applyStep;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
