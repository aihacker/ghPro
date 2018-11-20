package com.gpdi.mdata.sys.serviceimpl.reportform.mainriskexclute;

import com.gpdi.mdata.sys.service.reportform.mainriskexclude.ReverseService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.SingleService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;

import static org.apache.commons.lang.StringUtils.trim;

@Service
@Transactional(readOnly = false)
public class SingleServiceImpl implements SingleService{
    @Autowired
    GeneralDao generalDao;

    @Override
    public QueryResult queryReverseDate(QueryData queryData, QuerySettings settings) {
        Query query =new PagedQuery<>(settings);
        String contractNum =trim(queryData.getContractNum());//获取项目编号
        /*String contractName = trim(queryData.getContractName());//获取项目名称
        String supplier = trim(queryData.getSupperName());//获取供应商名称*/
        String where = "";
        if (StrFuncs.notEmpty(contractNum)){
            where += " and i.contract_code like :contractNum";
            query.put("contractNum",StrFuncs.anyLike(contractNum));
        }else{
            where += " and i.contract_code ='GDFSA1700155CGN0F'";
        }
       /* if(StrFuncs.notEmpty(contractName)){
            where += " and i.contract_name like:contractName";
            query.put("contractName",StrFuncs.anyLike(contractName));
        }
        if(StrFuncs.notEmpty(supplier)){
            where += " and i.supplier_name like:supplier";
            query.put("supplier",StrFuncs.anyLike(supplier));
        }*/

       /* if (StrFuncs.notEmpty(contractNum)|| StrFuncs.notEmpty(contractName) || StrFuncs.notEmpty(supplier)) {//判断不为空其长度大于0
            //where += " and i.contract_code like :name or i.contract_name like:name or i.supplier_name like :name or i.undertake_dept like :name";

            query.put("name", StrFuncs.anyLike(name));
        }*/
        query.select("i.*")
                .from("t_purchase_contract i")
                .where(where);
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        /*if(queryResult ==null && contractNum !=null  ){
            String sql = "update tb_staff set state = " +
                    "(CASE WHEN state = 1 THEN 0 ELSE 1 END)," +
                    "update_date = (CASE WHEN state = 1 and update_date is null THEN" +
                    " curdate() ELSE update_date END)" +
                    " where id = ?";
            //generalDao.execute(sql, id);
        }*/
        return queryResult;
    }
















}
