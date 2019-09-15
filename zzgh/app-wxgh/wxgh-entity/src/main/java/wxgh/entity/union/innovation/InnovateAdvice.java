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
 * Created by ✔ on 2016/11/10.
 */
@Entity
@Table(name = "t_innovate_advice")
public class InnovateAdvice implements Serializable {

    private Integer id;
    private Integer type;
    private Date addTime;
    private Integer pid;
    private String advice;
    private String title;
    private Integer deptid;
    private Integer seeNumb; //浏览次数
    private Integer zanNumb; //赞数量

    private Boolean zanIs;
    private String avatar;
    private String username;

    /**
     * @Author: ✔
     * @Description: plus
     * @Date: 10:27, 2016/11/15
     */
    private String typeName;
    private Date applyTime;
    private Integer status;
    private String auditIdea;
    private Date auditTime;
    private String content;
    private String deptname;
    private Integer applyStatus;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    private String addTimeStr;

    public Boolean getZanIs() {
        return zanIs;
    }

    public void setZanIs(Boolean zanIs) {
        this.zanIs = zanIs;
    }

    public String getAddTimeStr() {
        return addTimeStr;
    }

    public void setAddTimeStr(String addTimeStr) {
        this.addTimeStr = addTimeStr;
    }

    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditIdea() {
        return auditIdea;
    }

    public void setAuditIdea(String auditIdea) {
        this.auditIdea = auditIdea;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    @Column(name = "pid")
    public Integer getPid() {
        return pid;
    }

    @Column(name = "advice")
    public String getAdvice() {
        return advice;
    }

    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    @Column(name = "see_numb")
    public Integer getSeeNumb() {
        return seeNumb;
    }

    public void setSeeNumb(Integer seeNumb) {
        this.seeNumb = seeNumb;
    }

    @Column(name = "zan_numb")
    public Integer getZanNumb() {
        return zanNumb;
    }

    public void setZanNumb(Integer zanNumb) {
        this.zanNumb = zanNumb;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }
}
