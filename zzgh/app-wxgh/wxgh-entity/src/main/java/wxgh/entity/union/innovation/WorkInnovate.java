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
 * Created by WIN on 2016/8/24.
 */
@Entity
@Table(name = "t_work_innovate")
public class WorkInnovate implements Serializable {

    private Integer id;
    private String name;
    private String projectName;
    private Integer isIng; //是否已实施
    private String userId; //带头人
    private String address; //项目地址
    private String innovate; //创新点
    private String analyse; //可行性分析
    private String resultInfo; //效益描述或预期目的（简介）
    private String remark; //备注
    private Integer status; //状态（默认0未审核，1进行中，2审核未通过）
    private String userName;

    private Date applyTime;

    @Column(name = "apply_time")
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name = "is_ing")
    public Integer getIsIng() {
        return isIng;
    }

    public void setIsIng(Integer isIng) {
        this.isIng = isIng;
    }

    @Column(name = "user_id")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name = "innovate")
    public String getInnovate() {
        return innovate;
    }

    public void setInnovate(String innovate) {
        this.innovate = innovate;
    }

    @Column(name = "analyse")
    public String getAnalyse() {
        return analyse;
    }

    public void setAnalyse(String analyse) {
        this.analyse = analyse;
    }

    @Column(name = "result_info")
    public String getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(String resultInfo) {
        this.resultInfo = resultInfo;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
