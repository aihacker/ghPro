package com.gpdi.mdata.sys.serviceimpl.reportform.cleanInventory;

import com.gpdi.mdata.sys.service.reportform.cleanInventory.CleanBidderExcludeService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.BidderExcludeService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

@Service
@Transactional(readOnly = false)
public class CleanBidderExcludeServiceImpl implements CleanBidderExcludeService {
    @Autowired
    GeneralDao generalDao;

    @Override
    public QueryResult cleanQueryBidders(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String way = queryData.getName();

//        String sql = "{call clean_purcahse()}";
//        generalDao.execute(sql);
        /*query.select("a.contract_name,\n" +
                " a.contract_code,\n" +
                " b.company_name,\n" +
                " a.purchase_time,\n" +
                " g.legal_representative,\n" +
                " g.shareholder_one,\n" +
                " g.credit_code,\n" +
                " g.shareholder_two,\n" +
                " g.senior_admin_one,\n" +
                " g.senior_admin_two,\n" +
                " c.s AS relName").from("t_clean_list_immediately_rel a,\n" +
                "t_clean_candidater b,\n" +
                "\tt_supplier_info_online AS g,\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tproject_code,\n" +
                "\t\t\ts,\n" +
                "\t\t\tcount(s) AS total\n" +
                "\t\tFROM\n" +
                "\t\t\ttemp2\n" +
                "\t\tGROUP BY\n" +
                "\t\t\tproject_code,\n" +
                "\t\t\ts\n" +
                "\t\tHAVING\n" +
                "\t\t\tcount(s) > 1\n" +
                "\t) c").where("\ta.contract_code = b.contract_code\n" +
                "AND b.company_name = g.company_name\n" +
                "AND a.contract_code = c.project_code").orderBy("a.contract_name DESC");*/
        query.select("\ta.contract_name,\n" +
                "\ta.contract_code,\n" +
                "\tb.company_name,\n" +
                "\ta.purchase_time,\n" +
                "\tg.legal_representative,\n" +
                "\tg.shareholder_one,\n" +
                "\tg.credit_code,\n" +
                "\tg.shareholder_two,\n" +
                "\tg.senior_admin_one,\n" +
                "\tg.senior_admin_two,\n" +
                "\tc.p AS relName").from("t_clean_list_immediately_rel a,\n" +
                "\tt_clean_candidater b,\n" +
                "\tt_supplier_info_online AS g,\t\n" +
                "(\n" +
                "\t\tSELECT\n" +
                "\t\t\tproject_code,\n" +
                "\t\t\tp,\n" +
                "\t\t\tcount(p) AS total\n" +
                "\t\tFROM\n" +
                "\t\t\t(select b.contract_code as project_code, b.company_name,c.shareholder_one as p \n" +
                "  from t_clean_candidater b,  t_supplier_info_online C\n" +
                " where b.company_name = C.company_name  AND c.shareholder_one !=''      \n" +
                "union\n" +
                "select b.contract_code as project_code, b.company_name,c.shareholder_two as p \n" +
                "  from t_clean_candidater b,  t_supplier_info_online C\n" +
                " where b.company_name = C.company_name  AND c.shareholder_two !='' \n" +
                "UNION\n" +
                "select b.contract_code as project_code, b.company_name,c.senior_admin_one as p \n" +
                "  from t_clean_candidater b,  t_supplier_info_online C\n" +
                " where b.company_name = C.company_name  AND c.senior_admin_one !=''       \n" +
                "union\n" +
                "select b.contract_code as project_code, b.company_name, c.senior_admin_two as p \n" +
                "  from t_clean_candidater b,  t_supplier_info_online C\n" +
                "where b.company_name = C.company_name\t\tAND c.senior_admin_two !='' \n) c\n" +
                "\t\tGROUP BY\n" +
                "\t\t\tproject_code,\n" +
                "\t\t\tp\n" +
                "\t\tHAVING\n" +
                "\t\t\tcount(p) > 1\n" +
                "\t) c ").where("a.contract_code = b.contract_code\n" +
                "\t\tAND b.company_name = g.company_name\n" +
                "\t\tAND a.contract_code = c.project_code\t").orderBy("a.contract_name DESC");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }
}
