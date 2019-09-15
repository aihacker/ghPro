package wxgh.entity.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 09:03
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_act_apply")
public class ActApply implements Serializable {

    private Integer id;
    private Date actTime;
    private Integer joinNumb;
    private Float actCost;
    private String mobile;
    private String actInfo;
    private String reason;
    private Integer status;
    private String userid;
    private Integer deptid;
    private String auditIdea;
    private Float auditCost;
    private Date auditTime;
    private String auditUserid;
    private Date applyTime;
    private String address;
    private String actName;

    private String deptName;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "act_time")
    public Date getActTime() {
        return actTime;
    }

    public void setActTime(Date actTime) {
        this.actTime = actTime;
    }

    @Column(name = "join_numb")
    public Integer getJoinNumb() {
        return joinNumb;
    }

    public void setJoinNumb(Integer joinNumb) {
        this.joinNumb = joinNumb;
    }

    @Column(name = "act_cost")
    public Float getActCost() {
        return actCost;
    }

    public void setActCost(Float actCost) {
        this.actCost = actCost;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "act_info")
    public String getActInfo() {
        return actInfo;
    }

    public void setActInfo(String actInfo) {
        this.actInfo = actInfo;
    }

    @Column(name = "reason")
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    @Column(name = "audit_idea")
    public String getAuditIdea() {
        return auditIdea;
    }

    public void setAuditIdea(String auditIdea) {
        this.auditIdea = auditIdea;
    }

    @Column(name = "audit_cost")
    public Float getAuditCost() {
        return auditCost;
    }

    public void setAuditCost(Float auditCost) {
        this.auditCost = auditCost;
    }

    @Column(name = "audit_time")
    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    @Column(name = "audit_userid")
    public String getAuditUserid() {
        return auditUserid;
    }

    public void setAuditUserid(String auditUserid) {
        this.auditUserid = auditUserid;
    }

    @Column(name = "apply_time")
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "act_name")
    public String getActName() {
        return actName;
    }

    public void setActName(String actName) {
        this.actName = actName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
}


