package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.MachineAccount;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

/**
 * @description:合同台账报表
 * @author: WangXiaoGang
 * @data: Created in 2018/6/28 23:41
 * @modifier:
 */
@Repository
public class MachineDao extends MyBatisDao<MachineAccount> {

    public MachineDao() {
        super(MachineAccount.class);
    }
}
