package com.gpdi.mdata.sys.entity.system;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *用户表
 */
@Entity
@Table(name = "sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private String userName;
    private String userCode;
    private String pwd ;
    private Integer isEnable;
    private Integer  orgId;
    private String postLevel;
    private Date pwdLastModtime;
    private String staffId;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "user_name")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "user_code")
    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    @Column(name = "pwd")
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Column(name = "is_enable")
    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    @Column(name = "org_id")
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Column(name = "post_level")
    public String getPostLevel() {
        return postLevel;
    }

    public void setPostLevel(String postLevel) {
        this.postLevel = postLevel;
    }




    @Column(name = "pwd_last_modtime")
    public Date getPwdLastModtime() {
        return pwdLastModtime;
    }

    public void setPwdLastModtime(Date pwdLastModtime) {
        this.pwdLastModtime = pwdLastModtime;
    }


    @Column(name = "staff_id")
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
