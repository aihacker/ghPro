package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.CleanResult;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @description:候选供应商
 * @author: WangXiaoGang
 * @data: Created in 2018/6/28 23:41
 * @modifier:
 */
@Repository
public class CleanResultDao extends MyBatisDao<CleanResult> {

    public CleanResultDao() {
        super(CleanResult.class);
    }
}
