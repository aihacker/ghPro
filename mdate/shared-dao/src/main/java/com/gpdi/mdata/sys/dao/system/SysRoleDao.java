package com.gpdi.mdata.sys.dao.system;

import com.gpdi.mdata.sys.entity.system.SysRole;
import pub.dao.Dao;

import java.util.List;

/**
 *
 */
public interface SysRoleDao extends Dao<SysRole> {

    List<Integer> getRoleByUserId(Integer userId);

    List<Integer> getAllRoleId();

}

