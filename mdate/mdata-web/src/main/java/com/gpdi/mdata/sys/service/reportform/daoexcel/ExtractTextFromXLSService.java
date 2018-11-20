package com.gpdi.mdata.sys.service.reportform.daoexcel;

import com.gpdi.mdata.sys.entity.report.CleanInventory;
import com.gpdi.mdata.sys.entity.report.MachineBill;
import com.gpdi.mdata.sys.entity.report.MachineTitleBill;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

import java.util.List;

/**
 * @description:保存导入后的数据
 * @author: WangXiaoGang
 * @data: Created in 2018/6/22 11:07
 * @modifier:
 */
public interface ExtractTextFromXLSService {
    boolean savePurchaseContract(List<PurchaseContract> list);//保存导入后的采购台账表数据
    boolean saveCleanlneventory(List<CleanInventory> list);//保存导入后的采购台账表数据
    String getFileName(String name);//根据文件名称查询文件是否存在
    QueryResult queryPurchaseNote(QueryData queryData, PageSettings settings);//查询导入采购合同台账表日志记录
    QueryResult queryAccountNote(QueryData queryData, PageSettings settings);//查询导入合同台账表日志记录
    QueryResult queryKinsfolkNote(QueryData queryData, PageSettings settings);//查询导入领导亲属经商表日志记录
    QueryResult queryBidCompanyNote(QueryData queryData, PageSettings settings);//查询导入公开采购投标公司信息日志记录
    void save(String startTime, String endTime);//根据日期更新基础数据
    boolean insertData(String resultCode);//插入模板二数据
    QueryResult queryICCard(QueryData queryData, PageSettings settings);//查看IC卡加油站台账数据

    boolean savePurchaseBill(List<MachineBill> list);//保存导入后的ic加油站账表数据
    boolean savePurchaseTitleBill(List<MachineTitleBill> list);//保存导入后的ic加油站账表数据
}
