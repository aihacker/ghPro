package com.gpdi.mdata.sys.service.reportform.cleanInventory;


import com.gpdi.mdata.sys.entity.report.CleanInventory;
import com.gpdi.mdata.sys.entity.report.CleanInventoryRel;
import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 * @description:
 * @author: zzy
 * @data: Created in 2018/7/17 20:55
 * @modifier:
 */
public interface InventoryExamineService {
        /**
         * 即时清结数据查看
         * @param queryData
         * @param settings
         * @return
         */
        QueryResult queryInventory(QueryData queryData, QuerySettings settings);//查询数据

        void save(String startTime,String endTime);//根据日期更新基础数据(触发机器人模板一)

        boolean insertData(String cleanCode,String ngDateTime);//插入模板二数据,根据时间和编号

   //     List<CleanInventory> cleanAll ();//获取即时清结导入后的数据

        boolean saveClean( List<CleanInventory> list);//对取数进行清理和去重


}
