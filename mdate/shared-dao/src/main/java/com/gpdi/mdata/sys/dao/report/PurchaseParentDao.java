package com.gpdi.mdata.sys.dao.report;

import com.gpdi.mdata.sys.entity.report.PurchaseParent;
import pub.dao.Dao;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/7/4 11:23
 * @modifier:
 */
public interface PurchaseParentDao extends Dao<PurchaseParent> {

    void updateCodeById(Integer id, String code);

    PurchaseParent getByMd5(String MD5);
    String getFileName(String name);

}