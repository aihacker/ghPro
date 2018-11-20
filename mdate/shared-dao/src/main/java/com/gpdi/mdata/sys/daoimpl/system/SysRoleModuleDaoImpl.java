package com.gpdi.mdata.sys.daoimpl.system;

import com.gpdi.mdata.sys.dao.system.SysRoleModuleDao;
import com.gpdi.mdata.sys.entity.system.SysRoleModule;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

import java.util.Map;


@Repository
public class SysRoleModuleDaoImpl extends MyBatisDao<SysRoleModule> implements SysRoleModuleDao {

    public SysRoleModuleDaoImpl() {
        super(SysRoleModule.class);
    }

    @Override
    public SysRoleModule getByModuleId(Integer id) {
        return null;
    }

    @Override
    public void updataTree(Map map) {
        getSqlSession().update("com.gpdi.mdata.sys.entity.system.SysRoleModule.updataTree", map);
    }
}
