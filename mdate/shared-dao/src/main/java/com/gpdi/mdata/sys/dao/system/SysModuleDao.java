package com.gpdi.mdata.sys.dao.system;

import com.gpdi.mdata.sys.entity.system.QueryData;
import com.gpdi.mdata.sys.entity.system.SysModule;
import pub.dao.Dao;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface SysModuleDao extends Dao<SysModule> {

    List<SysModule> getParentByRole(Integer parentId);

    List<SysModule> getParent();

    List<SysModule> getChild(Integer id);

    List<SysModule> getRoleMoudleTree(Integer id);

    List<SysModule> getUsedMoudleByRole(Integer id);

    List<String>  getMoudleByRoleAndParent(QueryData queryData);


}

