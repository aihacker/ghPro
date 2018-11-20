package com.gpdi.mdata.sys.dao.system;

import com.gpdi.mdata.sys.entity.system.SysUser;
import pub.dao.Dao;

import java.util.List;

/**
 *
 */
public interface SysUserDao extends Dao<SysUser> {
    void savePass(SysUser sysUser);

    SysUser getUser(Integer id);

    SysUser getUser(String userName);

    SysUser getUserByStaffId(String staffId);

    List<String> getAllStaffId();

    List<SysUser> getUserByStaffIds(String[] list);
}
