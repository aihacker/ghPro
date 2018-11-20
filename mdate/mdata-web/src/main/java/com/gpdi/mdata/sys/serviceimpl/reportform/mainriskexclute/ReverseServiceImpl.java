package com.gpdi.mdata.sys.serviceimpl.reportform.mainriskexclute;

import com.gpdi.mdata.sys.service.reportform.mainriskexclude.ReverseService;
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

@Service
@Transactional(readOnly = false)
public class ReverseServiceImpl  implements ReverseService{
    @Autowired
    GeneralDao generalDao;

    @Override
    public QueryResult queryReverseDate(QueryData queryData, QuerySettings settings) {
        Query query =new PagedQuery<>(settings);
        String code =queryData.getCode();
        if (StrFuncs.notEmpty(code) && !code.equals("0") ){
            query.select("contract_name,supplier_name,finalize_date,performance_begin,receipt_pay_type,ABS(TO_DAYS(finalize_date)- TO_DAYS(performance_begin)) tim").from("t_machine_account").where("finalize_date > performance_begin\tAND receipt_pay_type = '支出类' AND ABS(TO_DAYS(finalize_date)- TO_DAYS(performance_begin)) <='"+code+"'").orderBy("tim desc");
        }else if (StrFuncs.notEmpty(code) && code.equals("0")){
            query.select("contract_name,supplier_name,finalize_date,performance_begin,receipt_pay_type,ABS(TO_DAYS(finalize_date)- TO_DAYS(performance_begin)) tim").from("t_machine_account").where("finalize_date > performance_begin\tAND receipt_pay_type = '支出类' AND ABS(TO_DAYS(finalize_date)- TO_DAYS(performance_begin)) >30").orderBy("tim desc");
        }else{
            query.select("contract_name,supplier_name,finalize_date,performance_begin,receipt_pay_type,ABS(TO_DAYS(finalize_date)- TO_DAYS(performance_begin)) tim").from("t_machine_account").where("finalize_date > performance_begin\tAND receipt_pay_type = '支出类' AND ABS(TO_DAYS(finalize_date)- TO_DAYS(performance_begin))<8").orderBy("tim desc");
        }
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }
















}
