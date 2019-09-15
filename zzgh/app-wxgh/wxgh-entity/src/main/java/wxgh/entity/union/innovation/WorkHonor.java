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
 * Created by WIN on 2016/9/12.
 */
@Entity
@Table(name = "t_work_honor")
public class WorkHonor implements Serializable {

    /*column*/
    private Integer id;
    private String name;
    private String awardGrade;
    private String userId;
    private Date applyTime;
    private Integer status;
    private String remark;
    private String adminId;
    private String people;

    /*AS*/
    private String userName;
    private String savePath;
    private Integer deptid;
    private String deptName;

    @Column(name = "people")
    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Column(name = "admin_id")
    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "award_grade")
    public String getAwardGrade() {
        return awardGrade;
    }

    @Column(name = "userid")
    public String getUserId() {
        return userId;
    }

    @Column(name = "apply_time")
    public Date getApplyTime() {
        return applyTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAwardGrade(String awardGrade) {
        this.awardGrade = awardGrade;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }
}
