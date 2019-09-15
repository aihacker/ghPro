package wxgh.entity.common.disease;

import pub.dao.page.Page;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Entity
@Table(name = "t_disease_apply")
public class DiseaseApply extends Page implements Serializable {

    private Integer id;
    private String category;
    private String name;
    private String userid;
    private String mobile;
    private String remark;
    private Date applyTime; //申请时间
    private Float applyMoney; //申请金额
    private Date auditTime; //审核时间
    private String auditIdea; //审核意见
    private Float auditMoney;  //审核资金
    private Integer status; //审核状态
    private Integer step; //步骤流程
    private String auditAdmin; //审核管理员
    private String cateId; //分类标识

    private Integer deptid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "category")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "apply_time")
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Column(name = "apply_money")
    public Float getApplyMoney() {
        return applyMoney;
    }

    public void setApplyMoney(Float applyMoney) {
        this.applyMoney = applyMoney;
    }

    @Column(name = "audit_time")
    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    @Column(name = "audit_idea")
    public String getAuditIdea() {
        return auditIdea;
    }

    public void setAuditIdea(String auditIdea) {
        this.auditIdea = auditIdea;
    }

    @Column(name = "audit_money")
    public Float getAuditMoney() {
        return auditMoney;
    }

    public void setAuditMoney(Float auditMoney) {
        this.auditMoney = auditMoney;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "step")
    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    @Column(name = "audit_admin")
    public String getAuditAdmin() {
        return auditAdmin;
    }

    public void setAuditAdmin(String auditAdmin) {
        this.auditAdmin = auditAdmin;
    }

    @Column(name = "cate_id")
    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }
}
