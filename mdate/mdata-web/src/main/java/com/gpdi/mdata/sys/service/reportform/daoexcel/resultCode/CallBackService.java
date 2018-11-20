package com.gpdi.mdata.sys.service.reportform.daoexcel.resultCode;

import com.gpdi.mdata.sys.entity.report.ResultCode;
import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 *根据采购结果编号获取数据
 */
public interface CallBackService {

    /**
     * 查询操作
     * @param code
     * @return
     */
    List<ResultCode> getCompanys(String code);//根据采购结果编号获取数据

    boolean  saveRsult(List<ResultCode> resultCodes);//保存候选供应商数据

    boolean saver();//取领导审批人

}
