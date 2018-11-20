package com.gpdi.mdata.sys.daoimpl.system;

import com.gpdi.mdata.sys.dao.system.SysVarDao;
import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.sys.entity.system.SysVar;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

import java.util.List;

/**
 * Created by yanqi on 2016/8/26.
 */
@Repository
public class SysVarDaoImpl extends MyBatisDao<SysVar> implements SysVarDao {

    public SysVarDaoImpl(){
        super(SysVar.class);
    }

    @Override
    public List<SysVar> getSysvar() {
        return getSqlSession().selectList("com.gpdi.mdata.sys.entity.system.SysVar.getVar");
    }

//    @Override
//    public List<SysRole> getRoleByUserId(Integer userId) {
//        List<SysRole> list=getSqlSession().selectList("com.gpdi.mdata.sys.entity.system.SysRole.getRoleByUserId",userId);
//        return list ;
//    }
}
