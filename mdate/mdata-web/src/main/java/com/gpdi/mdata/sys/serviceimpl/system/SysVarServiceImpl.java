package com.gpdi.mdata.sys.serviceimpl.system;


import com.gpdi.mdata.sys.dao.system.SysVarDao;
import com.gpdi.mdata.sys.entity.system.SysVar;
import com.gpdi.mdata.sys.service.system.SysVarService;
import com.gpdi.mdata.web.system.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;

import java.util.List;

/**
 *
 */
@Service
@Transactional(readOnly = true)
public class SysVarServiceImpl implements SysVarService {

    @Autowired
    private SysVarDao sysVarDao;
    @Autowired
    private  GeneralDao generalDao;
    @Override
    @Transactional
    public void delete(Integer id) {
        sysVarDao.delete(id);
    }

    @Override
    @Transactional
    public Integer save(SysVar sysVar) {
        sysVarDao.save(sysVar);
        return sysVar.getId();
    }

    @Override
    public SysVar get(Integer id) {
        return sysVarDao.get(id);
    }

    @Override
    public QueryResult query(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String where = "";
        if(StrFuncs.notEmpty(queryData.getName())) {
            where += " and i.var_name like :var_name";
            query.put("var_name", StrFuncs.anyLike(queryData.getName()));
        }
        query.select("i.*")
                .from("sys_var i")
                .where(where);
        generalDao.execute(query);
        QueryResult queryResult=query.getResult();
        return queryResult;
    }

    @Override
    public SysVar getVarByCode(String code) {
        String sql = "select * from sys_var where var_code ='" + code + "'";
        SysVar sysVar = generalDao.queryValue(SysVar.class,sql);
        return sysVar;
    }

}
