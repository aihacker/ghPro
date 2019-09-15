package wxgh.param.four.excel;


import java.util.Date;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-13  15:05
 * --------------------------------------------------------- *
 */
public class FourExcelParam {

    private String userid;
    private Integer deptid; // 部门
    private Integer fpId;   // 四小项目id
    private Integer mid;    // 营销中心
    private Integer fpcId;
    private Date buyTime;
    private Date planUpdate;

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getFpId() {
        return fpId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }


    public Integer getFpcId() {
        return fpcId;
    }

    public void setFpcId(Integer fpcId) {
        this.fpcId = fpcId;
    }

    public Date getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    public Date getPlanUpdate() {
        return planUpdate;
    }

    public void setPlanUpdate(Date planUpdate) {
        this.planUpdate = planUpdate;
    }
}
