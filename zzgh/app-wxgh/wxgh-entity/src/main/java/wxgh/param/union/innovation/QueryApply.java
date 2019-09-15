package wxgh.param.union.innovation;


import pub.dao.page.Page;

/**
 * @Author xlkai
 * @Version 2016/11/29
 */
public class QueryApply extends Page {

    private Integer applyType;
    private Integer status;
    private String userid;
    private Integer deptid;
    private Integer applyStep;

    private Integer start;
    private Integer num;


    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
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

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public Integer getApplyStep() {
        return applyStep;
    }

    public void setApplyStep(Integer applyStep) {
        this.applyStep = applyStep;
    }
}
