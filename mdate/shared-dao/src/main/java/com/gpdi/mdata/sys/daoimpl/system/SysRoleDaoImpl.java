package com.gpdi.mdata.sys.daoimpl.system;

import com.gpdi.mdata.sys.dao.system.SysRoleDao;
import com.gpdi.mdata.sys.entity.system.SysRole;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

import java.util.List;


@Repository
public class SysRoleDaoImpl extends MyBatisDao<SysRole> implements SysRoleDao {

    public SysRoleDaoImpl(){
        super(SysRole.class);
    }

    @Override
    public List<Integer> getRoleByUserId(Integer userId) {
        List<Integer> list=getSqlSession().selectList("com.gpdi.mdata.sys.entity.system.SysRole.getRoleByUserId",userId);
        return list ;
    }

    @Override
    public List<Integer> getAllRoleId() {
       return getSqlSession().selectList("com.gpdi.mdata.sys.entity.system.SysRole.getAllRoleId");
    }

}
