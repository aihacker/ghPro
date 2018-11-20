package com.gpdi.mdata.web.reportform.cleanInventory.sort.department.action;


import com.gpdi.mdata.sys.service.reportform.cleanInventory.CleanSortService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.spring.web.mvc.model.Model;

import java.util.HashMap;


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

        HashMap<String, Integer> hashMap = cleanSortService.doSortComb(queryData, settings);
        if(hashMap == null) return null;
        model.put("hashMap", hashMap);
        return hashMap;
    }


}