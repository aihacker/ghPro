package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import pub.dao.Dao;

import java.io.Serializable;
import java.util.List;

/**
 * 采购合同
 */
public interface RepPurchaseDao extends Dao<PurchaseContract> {


    List<PurchaseContract> getContractType();//查询所有合同类型
    List<PurchaseContract> getAllDept();//查询所有的经办部门
    List<String> getAllPurchaseWay();//查询所有的采购方式
    List<String> getAllRelatives();//查询所有的领导亲属名称

}
