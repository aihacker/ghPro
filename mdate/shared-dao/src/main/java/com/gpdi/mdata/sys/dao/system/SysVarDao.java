package com.gpdi.mdata.sys.dao.system;

import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.sys.entity.system.SysVar;
import pub.dao.Dao;

import java.util.List;

/**
 * Created by yanqi on 2016/8/26.
 */
public interface SysVarDao extends Dao<SysVar> {

    List<SysVar> getSysvar();

}
