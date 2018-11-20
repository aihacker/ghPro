package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.TenderingrulesWay;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @description:保存招标规则方式
 * @author: WangXiaoGang
 * @data: Created in 2018/10/13 11:24
 * @modifier:
 */
@Repository
public class TenderingrulesDao extends MyBatisDao<TenderingrulesWay>{
    public TenderingrulesDao() {
        super(TenderingrulesWay.class);
    }
}
