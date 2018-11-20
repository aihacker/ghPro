package com.gpdi.mdata.sys.dao.system;

import com.gpdi.mdata.sys.entity.system.SysRoleModule;
import com.gpdi.mdata.sys.entity.system.SysVar;
import pub.dao.Dao;

import java.util.List;
import java.util.Map;

/**
 * Created by yanqi on 2016/8/26.
 */
public interface SysRoleModuleDao extends Dao<SysRoleModule> {

    SysRoleModule getByModuleId(Integer id);

    void updataTree(Map map);

}
