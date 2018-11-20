package com.gpdi.mdata.sys.service.reportform.mainriskexclude;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 * @description:在单一来源中,根据各维度做个角度的分析
 * @author: WangXiaoGang
 * @data: Created in 2018/9/18 15:03
 * @modifier:
 */
public interface SingleSourceAnalysisService {

    //1.根据供应商获取部门签的合同数量排序
    QueryResult getDepartBySupplier(QueryData queryData, QuerySettings settings);
    //2.根据供应商获取合同类型数量排序
    List<PurchaseContract> getContractBySupplier(QueryData queryData);//获取所有的合同类型


}
