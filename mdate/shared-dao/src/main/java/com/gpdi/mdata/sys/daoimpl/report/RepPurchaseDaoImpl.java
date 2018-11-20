package com.gpdi.mdata.sys.daoimpl.report;

import com.gpdi.mdata.sys.dao.report.RepPurchaseDao;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

import javax.annotation.Resource;
import java.sql.Statement;
import java.util.List;

/**
 * 采购合同
 */
@Repository     //用于标注数据访问组件
public class RepPurchaseDaoImpl extends MyBatisDao<PurchaseContract> implements RepPurchaseDao{
    public RepPurchaseDaoImpl() { super(PurchaseContract.class); }

    @Override
    public List<PurchaseContract> getContractType() {   //获取合同类型
        return getSqlSession().selectList("com.gpdi.mdata.sys.entity.report.PurchaseContract.getContractType");
    }
    @Override
    public List<PurchaseContract> getAllDept(){//查询所有的经办部门
        return getSqlSession().selectList("com.gpdi.mdata.sys.entity.report.PurchaseContract.getAllDept");
    }

    @Override
    public List<String> getAllPurchaseWay() {//查询所有的采购方式
        return getSqlSession().selectList("com.gpdi.mdata.sys.entity.report.PurchaseContract.getAllPurchaseWay");
    }

    @Override
    public List<String> getAllRelatives() {//查询所有的领导亲属
        return getSqlSession().selectList("com.gpdi.mdata.sys.entity.report.PurchaseContract.getAllRelatives");
    }


}
