package com.gpdi.mdata.sys.service.reportform.organizational;

import com.gpdi.mdata.sys.entity.report.ContractType;
import com.gpdi.mdata.sys.entity.report.ContractTypeInfo;
import com.gpdi.mdata.web.system.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

/**
 * @description:合同小类接口
 * @author: WangXiaoGang
 * @data: Created in 2018/11/7 10:22
 * @modifier:
 */
public interface ContracttypeInfoService {
    /**
     * 根据合同大类查询该类下的所有小类
     * @param queryData
     * @param settings
     * @return
     */
    QueryResult queryTypeInfo(QueryData queryData, QuerySettings settings,String typecode);

    /**
     * 保存合同类型小类
     * @param contractTypeInfo
     * @return
     */
    Boolean saveLittleType(ContractTypeInfo contractTypeInfo);

    /**
     * 删除操作
     * @param id
     */
    void delete(Integer id);

}
