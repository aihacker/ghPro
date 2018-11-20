package com.gpdi.mdata.sys.daoimpl.report;

import com.gpdi.mdata.sys.dao.report.PurchaseParentDao;
import com.gpdi.mdata.sys.entity.report.PurchaseParent;
import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;

import java.util.List;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/7/4 11:25
 * @modifier:
 */
@Repository
public class PurchaseParentDaoImpl extends MyBatisDao<PurchaseParent> implements PurchaseParentDao {
    public PurchaseParentDaoImpl() {
        super(PurchaseParent.class);
    }

    @Override
    public void updateCodeById(Integer id, String code) {

    }

    @Override
    public PurchaseParent getByMd5(String MD5) {
        PurchaseParent purchaseParent = getSqlSession().selectOne("com.gpdi.mdata.sys.entity.report.PurchaseParent.getByMd5",MD5);
        return purchaseParent;
    }

    @Override
    public String getFileName(String name) {
        String fileName = getSqlSession().selectOne("com.gpdi.mdata.sys.entity.report.PurchaseParent.getFileName",name);
        return fileName;
    }

}
