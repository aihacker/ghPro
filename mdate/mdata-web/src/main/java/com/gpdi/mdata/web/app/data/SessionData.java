package com.gpdi.mdata.web.app.data;

import com.gpdi.mdata.sys.entity.system.SysModule;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zzl on 2015/11/13.
 */
public class SessionData implements Serializable {

    private Integer userId;
    private Integer userType;
    private String userName;
    private List<String> moduleCode;

    public List<String> getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(List<String> moduleCode) {
        this.moduleCode = moduleCode;
    }

    public boolean isAdmin() {
        return userId == 0;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
 