package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.KinsfolkBusiness;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/7/24 17:36
 * @description:领导亲属经商报表
 */
@Repository
public class KinsfolkDao extends MyBatisDao<KinsfolkBusiness> {

    public KinsfolkDao() {
        super(KinsfolkBusiness.class);
    }
}
