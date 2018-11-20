package com.gpdi.mdata.web.reportform.cleanInventory.sort.company.action;


import com.gpdi.mdata.sys.entity.report.CleanTemps;
import com.gpdi.mdata.sys.service.reportform.cleanInventory.CleanSortService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

import java.util.HashMap;
import java.util.List;


/**
 * @author: WangXiaoGang
 * @data: Created in 2018/9/7 15:23
 * @description:即时清结数据更新
 */
@Controller
public class QueryAction {
    @Autowired
    CleanSortService cleanSortService;

    @RequestMapping
    public HashMap<String, Integer> execute(QueryData queryData, PageSettings settings, Model model) {
        HashMap<String, Integer> hashMap = null;
        if(queryData.getDept() != null && queryData.getDept() != "") {
            List<CleanTemps> temps = cleanSortService.getAllByDept(queryData.getDept());
           // QueryResult queryResult = cleanSortService.getAllByDept(queryData.getDept());
            model.put("temps", temps);
            model.put("queryType", 0);
        } else {
            hashMap = cleanSortService.doSortComb(queryData, settings);
            if (queryData.getCode() != null && queryData.getCode() != "") {
                model.put("cname", cleanSortService.getCompanyName(queryData.getCode()));
            }
            model.put("queryType", 1);
        }
        if(hashMap == null){
            return null;
        }
        model.put("hashMap", hashMap);
        return hashMap;
    }


}