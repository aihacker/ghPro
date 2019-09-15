package wxgh.param.union.innovation;


import pub.dao.page.Page;

/**
 * @Author xlkai
 * @Version 2016/12/27
 */
public class InnovateApplyQuery extends Page {

    private Integer deptid;
    private Integer applyType;
    private Integer status;
    private Integer applyStep;
    private String userid;

    private Integer nodeptid;

    public Integer getNodeptid() {
        return nodeptid;
    }

    public void setNodeptid(Integer nodeptid) {
        this.nodeptid = nodeptid;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

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

    public Integer getApplyStep() {
        return applyStep;
    }

    public void setApplyStep(Integer applyStep) {
        this.applyStep = applyStep;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
