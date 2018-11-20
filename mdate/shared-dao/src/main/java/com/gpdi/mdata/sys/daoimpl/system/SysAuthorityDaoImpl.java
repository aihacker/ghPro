package com.gpdi.mdata.sys.daoimpl.system;

import com.gpdi.mdata.sys.dao.system.SysAuthorityDao;
import com.gpdi.mdata.sys.entity.system.SysModule;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * Created by yanqi on 2016/8/26.
 */
@Repository
public class SysAuthorityDaoImpl extends MyBatisDao<SysModule> implements SysAuthorityDao {
    public SysAuthorityDaoImpl(){
        super(SysModule.class);
    }
}
