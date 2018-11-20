package com.gpdi.mdata.web.reportform.exclude.source.proportion.action;


import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.SingleSourceService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 各采购类型所占比例
 */
@Controller
public class QueryAction {
    @Autowired
    private SingleSourceService singleSourceService;

   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
       QueryResult queryResult=singleSourceService.queryProportion(queryData,settings);
       List<TempData> list=new ArrayList<TempData>();
       if(queryData.getIsExport()!=null && queryData.getIsExport()==1){
           if(queryResult.getRows().size()>0){
               for(int i = 0; i<queryResult.getRows().size(); i++){
                   LinkedCaseInsensitiveMap map= (LinkedCaseInsensitiveMap) queryResult.getRows().get(i);
                   TempData data=new TempData();
                   data.setAa((String)map.get("aa"));
                   data.setBb((Long)map.get("bb"));
                   data.setCc(map.get("cc").toString());
                   data.setDd(map.get("dd").toString());
                   data.setEe(map.get("ee").toString());
                   data.setFf(map.get("ff").toString());
                   data.setGg(map.get("gg").toString());
                   list.add(data);
               }
               ProportionWriteApi proportion=new ProportionWriteApi();
               proportion.toExcel(list);
               HttpServletResponse response= ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
               proportion.response(response);
           }
       }
       return queryResult;
   }

    @RequestMapping
    public ActionResult getModal(QueryData queryData) {
        ActionResult result = ActionResult.ok();
        List<PurchaseContract> list = singleSourceService.getCount(queryData);
        result.setData(list);
        return result;
    }

}
