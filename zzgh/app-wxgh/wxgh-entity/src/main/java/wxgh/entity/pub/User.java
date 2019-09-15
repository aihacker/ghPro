package wxgh.entity.pub;

import com.libs.common.type.TypeUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户
 * Created by Administrator on 2017/7/13.
 */
@Entity
@Table
public class User implements Serializable {

    private Integer id;
    private String userid;
    private String name;
    private String mobile;
    private String department;
    private Integer deptid;
    private String position;
    private Integer gender;
    private String email;
    private String weixinid;
    private String avatar;
    private Integer status;

    public User() {
    }

    public User(com.weixin.bean.user.User user) {
        this.userid = user.getUserid();
        this.name = user.getName();
        this.mobile = user.getMobile() == null ? "" : user.getMobile();
        this.department = TypeUtils.listToStr(user.getDepartment());
        this.position = user.getPosition();
        this.gender = user.getGender();
        this.email = user.getEmail() == null ? "" : user.getEmail();
        this.weixinid = user.getWeixinid() == null ? "" : user.getWeixinid();
        this.avatar = user.getAvatar();
        this.status = user.getStatus();
    }

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Column
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Column
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column
    public String getWeixinid() {
        return weixinid;
    }

    public void setWeixinid(String weixinid) {
        this.weixinid = weixinid;
    }

    @Column
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Column
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", department='" + department + '\'' +
                ", deptid=" + deptid +
                ", position='" + position + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", weixinid='" + weixinid + '\'' +
                ", avatar='" + avatar + '\'' +
                ", status=" + status +
                '}';
    }
}
