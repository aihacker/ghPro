package com.gpdi.mdata.sys.service.reportform.organizational;

import com.gpdi.mdata.sys.entity.report.ContractType;
import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.web.system.data.QueryData;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

/**
 * @description:组织架构接口
 * @author: WangXiaoGang
 * @data: Created in 2018/11/7 10:22
 * @modifier:
 */
public interface ContracttypeService {
    /**
     * 查询所有的合同大类
     * @param queryData
     * @param settings
     * @return
     */
    QueryResult queryType(QueryData queryData, QuerySettings settings);

    /**
     * 保存合同类型大类
     * @param contractType
     * @return
     */
    Boolean saveBigType(ContractType contractType);

    /**
     * 根据id查询操作
     * @param id
     * @return
     */
    ContractType get(Integer id);

    /**
     * 删除操作
     * @param id
     */
    void delete(Integer id);

}
