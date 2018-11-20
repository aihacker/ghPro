package com.gpdi.mdata.sys.daoimpl.system;

import com.gpdi.mdata.sys.dao.system.SysModuleDao;
import com.gpdi.mdata.sys.entity.system.QueryData;
import com.gpdi.mdata.sys.entity.system.SysModule;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

import java.util.List;


@Repository
public class SysModuleDaoImpl extends MyBatisDao<SysModule> implements SysModuleDao {

    public SysModuleDaoImpl() {
        super(SysModule.class);
    }

    @Override
    public List<SysModule> getParent() {
        return getSqlSession().selectList("com.gpdi.mdata.sys.entity.system.SysModule.getParent");
    }

    @Override
    public List<SysModule> getParentByRole(Integer parentId) {
        return getSqlSession().selectList("com.gpdi.mdata.sys.entity.system.SysModule.getParentByRole", parentId);
    }

    @Override
    public List<SysModule> getChild(Integer id) {
        return null;
    }

    @Override
    public List<SysModule> getRoleMoudleTree(Integer id) {
        return getSqlSession().selectList("com.gpdi.mdata.sys.entity.system.SysModule.getRoleMoudleTree", id);
    }

    @Override
    public List<SysModule> getUsedMoudleByRole(Integer id) {
        return getSqlSession().selectList("com.gpdi.mdata.sys.entity.system.SysModule.getMoudleByRole", id);

    }

    @Override
    public List<String> getMoudleByRoleAndParent(QueryData queryData) {
        return getSqlSession().selectList("com.gpdi.mdata.sys.entity.system.SysModule.getMoudleByRoleAndParent", queryData);

    }
}
