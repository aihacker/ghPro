package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.DismantTemp;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @description:拆单字符串比较
 * @author: WangXiaoGang
 * @data: Created in 2018/6/28 23:41
 * @modifier:
 */
@Repository
public class DismantDao extends MyBatisDao<DismantTemp> {

    public DismantDao() {
        super(DismantTemp.class);
    }
}
