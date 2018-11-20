package com.gpdi.mdata.web.reportform.ytcard.platenumber.action;

import com.gpdi.mdata.sys.service.reportform.ytcard.MainYtcardService;
import com.gpdi.mdata.web.reportform.data.YtcardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

/**
 * @description:车牌号和卡号对应关系表
 * @author: WangXiaoGang
 * @data: Created in 2018/10/9 16:19
 * @modifier:
 */
@Controller
public class QueryAction {
    @Autowired
    MainYtcardService ytcardService;
    @RequestMapping
    public QueryResult execute(YtcardData ytcardData, PageSettings settings){
        return ytcardService.queryPlatenumber(ytcardData,settings);
    }
}
