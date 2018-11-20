package com.gpdi.mdata.sys.serviceimpl.reportform.mainriskexclute;

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
public class BidderExcludeServiceImpl implements BidderExcludeService{
    @Autowired
    GeneralDao generalDao;

    @Override
    public QueryResult queryBidders(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);

        String sql = "{call bidders()}";
        generalDao.execute(sql);

        query.select(" DISTINCT a.contract_name,a.contract_code,a.purchase_way,a.purchase_time,b.company_name,g.legal_representative,g.shareholder_one,g.shareholder_two,g.senior_admin_one,g.senior_admin_two ,c.s AS relName")
                .from("t_purchase_contract as a, t_candidater as b,t_supplier_info_online as g,( SELECT x.project_code,x.s,x.total FROM (select  project_code, s, count(s) as total from temp3  group by project_code, s having count(s)>1  ) x GROUP BY x.project_code  ) c ")
                .where("a.purchase_result_code = b.pur_result_code and b.company_name = g.company_name AND a.purchase_result_code=c.project_code")
                .orderBy("a.contract_name desc");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();

        return queryResult;
    }
}
