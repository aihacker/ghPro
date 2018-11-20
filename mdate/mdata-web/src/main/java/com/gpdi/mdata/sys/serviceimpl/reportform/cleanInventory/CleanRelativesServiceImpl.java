package com.gpdi.mdata.sys.serviceimpl.reportform.cleanInventory;

import com.gpdi.mdata.sys.dao.report.RepPurchaseDao;
import com.gpdi.mdata.sys.service.reportform.cleanInventory.CleanRelativesService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class CleanRelativesServiceImpl implements CleanRelativesService {
    @Autowired
    GeneralDao generalDao;
    @Autowired
    RepPurchaseDao repPurchaseDao;
    @Override
    public QueryResult queryRelatives(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        query.select("DISTINCT c.kinsfolk_name,c.enterprise_name,c.duty,(SELECT COUNT(supplier_name) sname FROM t_clean_list_immediately_rel WHERE supplier_name=a.supplier_name) as sname,(SELECT COUNT(enterprise_name) etcount FROM t_kinsfolk_business WHERE enterprise_name = a.supplier_name and kinsfolk_name = c.kinsfolk_name) as etname,a.supplier_name").from("t_clean_list_immediately_rel as a,t_supplier_info_online as b, t_kinsfolk_business as c").where("a.supplier_name=b.company_name AND (b.legal_representative = c.kinsfolk_name OR b.shareholder_one = c.kinsfolk_name OR b.shareholder_two = c.kinsfolk_name OR b.senior_admin_one = c.kinsfolk_name OR b.senior_admin_two = c.kinsfolk_name )");
        generalDao.execute(query);
        QueryResult queryResult =query.getResult();
        return queryResult;
    }

    /**
     * 查询即时清结领导亲属排查
     * @param queryData
     * @param settings
     * @return
     */
    @Override
    public QueryResult queryRelativesRel(QueryData queryData, QuerySettings settings) {
        String suname = queryData.getSupperName();
        Query query = new PagedQuery<>(settings);
        if (suname!=null) {
            query.select("contract_code,contract_name,supplier_name,purchase_time,contract_amount,purchase_way,contract_type_name,purchase_result_code").from("t_purchase_contract").where("supplier_name='"+suname+"'");
        }
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public List<String> getAllRelatives() {
        return repPurchaseDao.getAllRelatives();
    }

}
