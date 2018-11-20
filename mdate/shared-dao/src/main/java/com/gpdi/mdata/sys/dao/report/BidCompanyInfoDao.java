package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.BidCompanyInfo;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/7/24 17:36
 * @description:公开采购投标公司信息
 */
@Repository
public class BidCompanyInfoDao extends MyBatisDao<BidCompanyInfo> {

    public BidCompanyInfoDao() {
        super(BidCompanyInfo.class);
    }
}
