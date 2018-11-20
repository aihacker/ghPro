package com.gpdi.mdata.sys.service.system;

import com.gpdi.mdata.sys.entity.system.SysModule;
import com.gpdi.mdata.sys.entity.system.SysVar;
import com.gpdi.mdata.web.system.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 *
 */
public interface SysAuthorityService {

    /**
     * 删除操作
     * @param id
     */
    void delete(Integer id);

    void delModuleByModuleId(Integer id);

    /**
     * 保存操作
     * @para
     * @return
     */
    Integer save(SysModule sysModule);

    /**
     * 根据id查询操作
     * @param id
     * @return
     */
    SysModule get(Integer id);

    /**
     * 查询操作
     * @param queryData
     * @param settings
     * @return
     */
    QueryResult query(QueryData queryData, QuerySettings settings);

    Boolean testingCode(String code);

    List<SysModule> getAllCode();
}
