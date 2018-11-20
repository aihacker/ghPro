package com.gpdi.mdata.sys.service.reportform.info;

import com.gpdi.mdata.sys.entity.report.FileRulers;
import com.gpdi.mdata.sys.entity.report.Legal;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.entity.report.TenderingrulesWay;
import com.gpdi.mdata.web.reportform.data.QueryData;
import com.gpdi.mdata.web.reportform.data.YtcardData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/7/4 21:21
 * @description:获取公开采购投标公司信息
 */
public interface CompanyInfoService {

    /**
     * 查询操作
     * @param queryData
     * @param settings
     * @return
     */
    QueryResult query(QueryData queryData, QuerySettings settings);//查询所有的公开采购投标公司信息

    QueryResult queryrulers(QueryData queryData, QuerySettings settings);//应招标规则文件

    QueryResult querylegal(YtcardData queryData, QuerySettings settings);//查询公司信息

    QueryResult queryTenderWay(YtcardData ytcardData, QuerySettings settings);//采购方式适用列表

    List<Legal> queryTenderlegal(YtcardData ytcardData);//采购方式适用列表

    List<FileRulers> queryFileRules(YtcardData ytcardData);//获取招标规则说明文档

    String queryRulesMaxId();//查询最大的规则编码

    boolean saveFileRulers(FileRulers fileRulers,Map map,List<TenderingrulesWay> listWay,Integer number);//保存规则文件名



}
