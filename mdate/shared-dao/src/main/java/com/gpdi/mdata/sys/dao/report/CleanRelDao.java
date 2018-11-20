package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.CleanInventory;
import com.gpdi.mdata.sys.entity.report.CleanInventoryRel;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @description:及时清洁清理数据
 * @author: WangXiaoGang
 * @data: Created in 2018/6/28 23:41
 * @modifier:
 */
@Repository
public class CleanRelDao extends MyBatisDao<CleanInventoryRel> {

    public CleanRelDao() {
        super(CleanInventoryRel.class);
    }
}
