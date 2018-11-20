package com.gpdi.mdata.sys.serviceimpl.system;


import com.gpdi.mdata.sys.dao.system.SysAuthorityDao;
import com.gpdi.mdata.sys.entity.system.SysModule;
import com.gpdi.mdata.sys.service.system.SysAuthorityService;
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
public class SysAuthorityServiceImpl implements SysAuthorityService {

    @Autowired
    private SysAuthorityDao sysAuthorityDao;
    @Autowired
    private  GeneralDao generalDao;
    @Override
    @Transactional
    public void delete(Integer id) {
        sysAuthorityDao.delete(id);
        generalDao.execute("delete from sys_role_module where module_id = "+ id);
    }

    @Override
    @Transactional
    public Integer save(SysModule sysModule) {
        sysAuthorityDao.save(sysModule);
        return sysModule.getModuleId();
    }

    @Override
    @Transactional
    public void delModuleByModuleId(Integer id) {
        String sqlM = "delete from sys_role_module where module_id=" + id;
        generalDao.execute(sqlM);
    }

    @Override
    public SysModule get(Integer id) {
        return sysAuthorityDao.get(id);
    }

    @Override
    public QueryResult query(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String where = "";

        if(StrFuncs.notEmpty(queryData.getName())) {
            where += " and i.module_name like :name";
            query.put("name", StrFuncs.anyLike(queryData.getName()));
        }

        if(StrFuncs.notEmpty(queryData.getCode())) {
            where += " and i.module_code like '"+queryData.getCode()+"%'";
            //where += " and i.module_code like :module_code";
            //query.put("module_code", StrFuncs.anyLike(queryData.getCode()));
        }

        query.select("i.*")
                .from("sys_module i")
                .where(where);
        generalDao.execute(query);
        QueryResult queryResult=query.getResult();
        return queryResult;
    }

    @Override
    public List<SysModule> getAllCode(){
        List<SysModule> moduleList = generalDao.queryList(SysModule.class,"select module_code from sys_module  where length(module_code) = 2");
        return moduleList;
    }

    @Override
    public Boolean testingCode(String code){
        Integer count = generalDao.queryValue(Integer.class,"select count(*) from sys_module where module_code ='"+code+"'");
        return null != count && count > 0;
    }

}
