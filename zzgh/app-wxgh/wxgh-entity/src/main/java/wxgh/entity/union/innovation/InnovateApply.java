package wxgh.entity.union.innovation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author xlkai
 * @Version 2016/11/10
 */
@Entity
@Table(name = "t_innovate_apply")
public class InnovateApply implements Serializable {

    public static final Integer TYPE_ADVICE = 4; //创新建议
    public static final Integer TYPE_SHOP = 2; //工作坊
    public static final Integer TYPE_MICRO = 3; //微创新

    public static final Integer STATUS_NO = 0; //未审核
    public static final Integer STATUS_PASS = 1; //审核通过
    public static final Integer STATUS_NOPASS = 2;//审核未通过
    public static final Integer STATUS_ADVICE = 3; //资金申请中
    public static final Integer STATUS_FNISH = 4; //资金申请完成
    public static final Integer STATUS_FAILED = 5; //资金申请不通过

    private Integer id;
    private Integer applyType;
    private Integer status;
    private Date addTime;
    private Date auditTime;
    private String auditIdea;
    private Integer deptid;
    private String userid;
    private String adminUserid;
    private Integer applyStep;
    private Float applyPrice;

    private InnovateAdvice innovateAdvice;

    private String deptName;
    private String userName;
    private String title;//合理化建议的标题
    private String advice;//合理化建议的内容
    private Date addtime;//合理化建议的添加时间
    private Integer aType;//合理化建议的建议类别

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Integer getaType() {
        return aType;
    }

    public void setaType(Integer aType) {
        this.aType = aType;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public InnovateAdvice getInnovateAdvice() {
        return innovateAdvice;
    }

    public void setInnovateAdvice(InnovateAdvice innovateAdvice) {
        this.innovateAdvice = innovateAdvice;
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

    @Column(name = "apply_type")
    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
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

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "admin_userid")
    public String getAdminUserid() {
        return adminUserid;
    }

    public void setAdminUserid(String adminUserid) {
        this.adminUserid = adminUserid;
    }

    @Column(name = "apply_step")
    public Integer getApplyStep() {
        return applyStep;
    }

    public void setApplyStep(Integer applyStep) {
        this.applyStep = applyStep;
    }

    @Column(name = "apply_price")
    public Float getApplyPrice() {
        return applyPrice;
    }

    public void setApplyPrice(Float applyPrice) {
        this.applyPrice = applyPrice;
    }

    @Override
    public String toString() {
        return "InnovateApply{" +
                "id=" + id +
                ", applyType=" + applyType +
                ", status=" + status +
                ", addTime=" + addTime +
                ", auditTime=" + auditTime +
                ", auditIdea='" + auditIdea + '\'' +
                ", deptid=" + deptid +
                ", userid='" + userid + '\'' +
                ", adminUserid='" + adminUserid + '\'' +
                ", applyStep=" + applyStep +
                ", applyPrice=" + applyPrice +
                ", innovateAdvice=" + innovateAdvice +
                ", deptName='" + deptName + '\'' +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", advice='" + advice + '\'' +
                ", addtime=" + addtime +
                ", aType=" + aType +
                '}';
    }
}
