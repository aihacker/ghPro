package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.KinsfolkBusiness;
import com.gpdi.mdata.sys.entity.report.MachineBill;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @description:采购合同报表
 * @author: WangXiaoGang
 * @data: Created in 2018/6/28 23:41
 * @modifier:
 */
@Repository
public class PurchaseBillDao extends MyBatisDao<MachineBill> {

    public PurchaseBillDao() {
        super(MachineBill.class);
    }
}



