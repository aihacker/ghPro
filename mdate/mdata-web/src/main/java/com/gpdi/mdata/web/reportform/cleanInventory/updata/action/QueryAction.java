package com.gpdi.mdata.web.reportform.cleanInventory.updata.action;


import com.gpdi.mdata.sys.service.reportform.cleanInventory.InventoryExamineService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;


/**
 * @author: WangXiaoGang
 * @data: Created in 2018/9/7 15:23
 * @description:即时清结数据更新
 */
@Controller
public class QueryAction {

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
        return null;
    }


}