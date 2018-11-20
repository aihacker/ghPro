package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.ContractTypeWay;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @description:保存招标规则采购方式
 * @author: WangXiaoGang
 * @data: Created in 2018/10/13 11:24
 * @modifier:
 */
@Repository
public class ContractTypeWayDao extends MyBatisDao<ContractTypeWay>{
    public ContractTypeWayDao() {
        super(ContractTypeWay.class);
    }
}
