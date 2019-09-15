package wxgh.app.session.bean;


import com.libs.common.type.TypeUtils;
import wxgh.entity.pub.User;

/**
 * Created by Administrator on 2017/7/13.
 */
public class SeUser {

    private String userid;
    private String name;
    private String avatar;
    private String mobile;
    private Integer deptid;
    private Integer gender;
    private Integer companyId;

    public SeUser() {
    }

    public SeUser(User user) {
        this.userid = user.getUserid();
        this.name = user.getName();
        this.mobile = user.getMobile();
        this.deptid = user.getDeptid();
        this.avatar = user.getAvatar();
        this.gender = user.getGender();
        String dept = user.getDepartment();
        if (!TypeUtils.empty(dept)) {
            if(dept.split(",").length > 1) {
                String[] depts = dept.split(",");
                this.companyId = Integer.valueOf(depts[depts.length - 1]);
            }else{
                this.companyId=Integer.valueOf(dept);
            }
        }
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Override
    public String toString() {
        return "SeUser{" +
                "userid='" + userid + '\'' +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", mobile='" + mobile + '\'' +
                ", deptid=" + deptid +
                ", gender=" + gender +
                ", companyId=" + companyId +
                '}';
    }
}
