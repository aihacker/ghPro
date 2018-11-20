package com.gpdi.mdata.sys.service.system;

import com.gpdi.mdata.sys.entity.system.SysVar;
import com.gpdi.mdata.web.system.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 *
 */
public interface SysVarService {

    /**
     * 删除操作
     * @param id
     */
    void delete(Integer id);

    /**
     * 保存操作
     * @param sysVar
     * @return
     */
    Integer save(SysVar sysVar);

    /**
     * 根据id查询操作
     * @param id
     * @return
     */
    SysVar get(Integer id);

    /**
     * 查询操作
     * @param queryData
     * @param settings
     * @return
     */
    QueryResult query(QueryData queryData, QuerySettings settings);

    SysVar getVarByCode(String code);
}
