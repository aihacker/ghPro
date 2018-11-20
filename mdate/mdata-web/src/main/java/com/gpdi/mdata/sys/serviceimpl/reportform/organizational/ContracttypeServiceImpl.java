package com.gpdi.mdata.sys.serviceimpl.reportform.organizational;

import com.gpdi.mdata.sys.entity.report.ContractType;
import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.sys.service.reportform.organizational.ContracttypeService;
import org.springframework.beans.factory.annotation.Autowired;
import com.gpdi.mdata.web.system.data.QueryData;
import org.springframework.stereotype.Service;
import pub.dao.GeneralDao;
import pub.dao.jdbc.ListQuery;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.PageSettings;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;

/**
 * @description:组织架构
 * @author: WangXiaoGang
 * @data: Created in 2018/11/7 10:22
 * @modifier:
 */
@Service
public class ContracttypeServiceImpl implements ContracttypeService {

    @Autowired
    private GeneralDao generalDao;

    /**
     * 查询所有的合同类型大类
     * @param queryData
     * @param settings
     * @return
     */
    @Override
      public QueryResult queryType(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery(settings);
        String typeName =queryData.getName();
        String where ="";
        if (StrFuncs.notEmpty(typeName)){
            typeName = typeName.trim();
            where += "and type_name like '%"+typeName+"%' ";
        }
        query.select("*").from("t_contract_type").where(where);
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public Boolean saveBigType(ContractType contractType) {
        Integer id = contractType.getId();
        String typeName = contractType.getTypeName();
        String typeCode = contractType.getTypeCode();
        if (StrFuncs.notEmpty(typeName) && StrFuncs.notEmpty(typeCode)){
            String sql = "insert into t_contract_type (type_name,type_code) values (?,?)";
            String sql2 ="update t_contract_type set type_name=?,type_code=? where id=?";
            if (id ==null || id==0){
                generalDao.execute(sql,typeName,typeCode);
            }else {
                generalDao.execute(sql2,typeName,typeCode,id);
            }
            return true;
        }
        return false;
    }

    @Override
    public ContractType get(Integer id) {
        String sql ="select * from t_contract_type where id =? ";
        ContractType con = generalDao.queryValue(ContractType.class,sql,id);
        return con;
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from t_contract_type where id=?";
       generalDao.execute(sql,id);
    }


}
