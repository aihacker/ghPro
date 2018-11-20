package com.gpdi.mdata.web.system.data;

import com.gpdi.mdata.sys.entity.system.SysRole;

import java.util.List;

/**
 *
 */
public class UserData {

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    private  Integer userId;
    private String oldPass;
    private String newPass;

    private List<Integer> roles;

    private List<Integer> regions;

    private List<SysRole> allRoles;

    public String getTree() {
        return tree;
    }

    public void setTree(String tree) {
        this.tree = tree;
    }

    private String tree;

    public List<SysRole> getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(List<SysRole> allRoles) {
        this.allRoles = allRoles;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public List<Integer> getRoles() {
        return roles;
    }

    public void setRoles(List<Integer> roles) {
        this.roles = roles;
    }

    public List<Integer> getRegions() {
        return regions;
    }

    public void setRegions(List<Integer> regions) {
        this.regions = regions;
    }
}
