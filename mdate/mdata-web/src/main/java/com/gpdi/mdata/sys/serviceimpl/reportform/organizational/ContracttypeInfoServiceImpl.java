package com.gpdi.mdata.sys.serviceimpl.reportform.organizational;

import com.gpdi.mdata.sys.entity.report.ContractType;
import com.gpdi.mdata.sys.entity.report.ContractTypeInfo;
import com.gpdi.mdata.sys.service.reportform.organizational.ContracttypeInfoService;
import com.gpdi.mdata.sys.service.reportform.organizational.ContracttypeService;
import com.gpdi.mdata.web.system.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;

/**
 * @description:合同类型小类实现类
 * @author: WangXiaoGang
 * @data: Created in 2018/11/7 10:22
 * @modifier:
 */
@Service
public class ContracttypeInfoServiceImpl implements ContracttypeInfoService {

    @Autowired
    private GeneralDao generalDao;

    /**
     * 查询所有的合同类型大类
     * @param queryData
     * @param settings
     * @return
     */
    @Override
      public QueryResult queryTypeInfo(QueryData queryData, QuerySettings settings,String typecode) {
        Query query = new PagedQuery(settings);
        query.select("*").from("t_contract_type_child").where("parent_num='"+typecode+"'");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public Boolean saveLittleType(ContractTypeInfo contractTypeInfo) {
        String typeName = contractTypeInfo.getTypeName();
        String typeCode = contractTypeInfo.getTypeCode();
        String parentNum = contractTypeInfo.getParentNum();
        if (StrFuncs.notEmpty(typeName) && StrFuncs.notEmpty(typeCode) && StrFuncs.notEmpty(parentNum)){
            String sql = "insert into t_contract_type_child (type_name,type_code,parent_num) values (?,?,?)";
            generalDao.execute(sql,typeName,typeCode,parentNum);
            return true;
        }
        return false;
    }

    @Override
    public void delete(Integer id) {
        String sql = "delete from t_contract_type_child where id=?";
        generalDao.execute(sql,id);
    }


}
