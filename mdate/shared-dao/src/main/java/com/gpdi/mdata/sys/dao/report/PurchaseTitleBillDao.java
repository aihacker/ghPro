package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.MachineBill;
import com.gpdi.mdata.sys.entity.report.MachineTitleBill;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @description:采购合同报表
 * @author: WangXiaoGang
 * @data: Created in 2018/6/28 23:41
 * @modifier:
 */
@Repository
public class PurchaseTitleBillDao extends MyBatisDao<MachineTitleBill> {

    public PurchaseTitleBillDao() {
        super(MachineTitleBill.class);
    }
}



