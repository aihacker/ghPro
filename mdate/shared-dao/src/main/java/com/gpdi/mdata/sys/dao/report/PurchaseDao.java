package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @description:采购合同报表
 * @author: WangXiaoGang
 * @data: Created in 2018/6/28 23:41
 * @modifier:
 */
@Repository
public class PurchaseDao extends MyBatisDao<PurchaseContract> {

    public PurchaseDao() {
        super(PurchaseContract.class);
    }
}
