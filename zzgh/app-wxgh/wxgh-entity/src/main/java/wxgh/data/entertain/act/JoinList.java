package wxgh.data.entertain.act;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/25.
 */
public class JoinList {

    private String userid;
    private String username;
    private String avatar;
    private String mobile;
    private String deptname;
    private String dept;
    private Date addTime;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

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

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "JoinList{" +
                "userid='" + userid + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", mobile='" + mobile + '\'' +
                ", deptname='" + deptname + '\'' +
                ", dept='" + dept + '\'' +
                ", addTime=" + addTime +
                '}';
    }
}
