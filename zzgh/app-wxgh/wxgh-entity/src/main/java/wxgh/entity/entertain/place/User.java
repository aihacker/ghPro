package wxgh.entity.entertain.place;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/3.
 */
@Entity
@Table(name = "t_user")
public class User implements Serializable {

    public static final Integer STATUS_ATTENTION = 1; //已关注
    public static final Integer STATUS_NO_ATTENTION = 4; //未关注
    public static final Integer STATUS_FORBID = 2; //已禁用

    private int id;
    private String userid;
    private String name;
    private String mobile;
    private String gender;
    private String avatar;
    private String status;
    private String position;
    private String email;
    private String weixinid;
    private Integer points;

    //
    private Integer companyid;
    private Integer areaid;
    private Integer deptid;

    private Integer applyStatus; //审核状态
    private Date applyTime; //审核时间
    private Date userTime; //用户申请时间
    private Integer stepNumb; //申请步骤
    private String openid; //微信用户openid
    private Integer oldDeptid; //原部门ID
    private Integer isApply; //标识是否为申请入会用户，（默认1已经是入会成员，2为入会申请用户）

    private String deptStr;

    private Integer addPoints;

    /*plus*/
    private String deptname;

    public String getDeptname() {

        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public Integer getAddPoints() {
        return addPoints;
    }

    public void setAddPoints(Integer addPoints) {
        this.addPoints = addPoints;
    }

    @Column(name = "points")
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    @Column(name = "position")
    public String getStatus() {
        return status;
    }

    @Column(name = "position")
    public String getPosition() {
        return position;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "weixinid")
    public String getWeixinid() {
        return weixinid;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWeixinid(String weixinid) {
        this.weixinid = weixinid;
    }

    @Column(name = "companyid")
    public Integer getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Integer companyid) {
        this.companyid = companyid;
    }

    @Column(name = "areaid")
    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getDeptStr() {
        return deptStr;
    }

    public void setDeptStr(String deptStr) {
        this.deptStr = deptStr;
    }

    @Column(name = "apply_status")
    public Integer getApplyStatus() {
        return applyStatus;
    }

    public void setApplyStatus(Integer applyStatus) {
        this.applyStatus = applyStatus;
    }

    @Column(name = "apply_time")
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Column(name = "user_time")
    public Date getUserTime() {
        return userTime;
    }

    public void setUserTime(Date userTime) {
        this.userTime = userTime;
    }

    @Column(name = "step_numb")
    public Integer getStepNumb() {
        return stepNumb;
    }

    public void setStepNumb(Integer stepNumb) {
        this.stepNumb = stepNumb;
    }

    @Column(name = "openid")
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Column(name = "old_deptid")
    public Integer getOldDeptid() {
        return oldDeptid;
    }

    public void setOldDeptid(Integer oldDeptid) {
        this.oldDeptid = oldDeptid;
    }

    @Column(name = "is_apply")
    public Integer getIsApply() {
        return isApply;
    }

    public void setIsApply(Integer isApply) {
        this.isApply = isApply;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", gender='" + gender + '\'' +
                ", avatar='" + avatar + '\'' +
                ", status='" + status + '\'' +
                ", position='" + position + '\'' +
                ", email='" + email + '\'' +
                ", weixinid='" + weixinid + '\'' +
                ", companyid=" + companyid +
                ", areaid=" + areaid +
                ", deptid=" + deptid +
                '}';
    }
}
