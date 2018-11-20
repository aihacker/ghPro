package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.CleanInventory;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @description:及时清洁
 * @author: WangXiaoGang
 * @data: Created in 2018/6/28 23:41
 * @modifier:
 */
@Repository
public class CleanDao extends MyBatisDao<CleanInventory> {

    public CleanDao() {
        super(CleanInventory.class);
    }
}
