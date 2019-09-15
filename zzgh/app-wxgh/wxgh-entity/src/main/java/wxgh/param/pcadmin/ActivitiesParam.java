package wxgh.param.pcadmin;

import pub.dao.page.Page;

import java.io.Serializable;
import java.util.Date;

/**
 * @author hhl
 * @create 2017-08-22
 **/
public class ActivitiesParam extends Page implements Serializable{
    private String userid;
    private Integer deptid;
    private Integer nodeptid;
    private Integer status = 1;
    private Integer actType;
    private Integer id;
    private Integer is_give_points;

    private String admin_apply;
    private Integer actOldestId;
    private Integer num;
    private Integer isFirst;

    private Integer start;

    private Integer status2;

    private Date applyTime;//审核时间

    private String aduitAdmin;

    private Integer isCount;

    public Integer getIsCount() {
        return isCount;
    }

    public void setIsCount(Integer isCount) {
        this.isCount = isCount;
    }

    public String getAduitAdmin() {
        return aduitAdmin;
    }

    public void setAduitAdmin(String aduitAdmin) {
        this.aduitAdmin = aduitAdmin;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getStatus2() {
        return status2;
    }

    public void setStatus2(Integer status2) {
        this.status2 = status2;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getActOldestId() {
        return actOldestId;
    }

    public void setActOldestId(Integer actOldestId) {
        this.actOldestId = actOldestId;
    }

    public String getAdmin_apply() {
        return admin_apply;
    }

    public void setAdmin_apply(String admin_apply) {
        this.admin_apply = admin_apply;
    }

    public Integer getIs_give_points() {
        return is_give_points;
    }

    public void setIs_give_points(Integer is_give_points) {
        this.is_give_points = is_give_points;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getActType() {
        return actType;
    }

    public void setActType(Integer actType) {
        this.actType = actType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNodeptid() {
        return nodeptid;
    }

    public void setNodeptid(Integer nodeptid) {
        this.nodeptid = nodeptid;
    }
}
