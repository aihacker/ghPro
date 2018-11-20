package com.gpdi.mdata.web.reportform.cleanInventory.show.action;


import com.gpdi.mdata.sys.service.reportform.cleanInventory.InventoryExamineService;
import com.gpdi.mdata.sys.service.reportform.daoexcel.pdfscream.PdfExamineService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * @author: WangXiaoGang
 * @data: Created in 2018/9/7 15:23
 * @description:即时清结数据查看
 */
@Controller
public class QueryAction {
   protected static final Logger log = Logger.getLogger(QueryAction.class);
    @Autowired
    private InventoryExamineService inventoryExamineService;

   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){

       QueryResult queryResult=inventoryExamineService.queryInventory(queryData,settings);

       List<ShowTempData> list = new ArrayList<ShowTempData>();
       if (queryData.getIsExport() != null && queryData.getIsExport() == 1) {
           if (queryResult.getRows().size() > 0) {
               for (int i = 0; i < queryResult.getRows().size(); i++) {
                   LinkedCaseInsensitiveMap map = (LinkedCaseInsensitiveMap) queryResult.getRows().get(i);
                   ShowTempData data = new ShowTempData();
                   data.setId(map.get("id").toString());
                   data.setFlow_path(map.get("flow_path").toString());
                   data.setTitle(map.get("title").toString());
                   data.setIscm_number(map.get("iscm_number").toString());
                   data.setFlow_path_number(map.get("flow_path_number").toString());
                   data.setClean_advance(map.get("clean_advance").toString());
                   data.setSupplier_code(map.get("supplier_code").toString());
                   data.setSupplier_name(map.get("supplier_name").toString());
                   data.setMaterial_code(map.get("material_code").toString());
                   data.setMaterial_name(map.get("material_name").toString());
                   data.setUnit_measurement(map.get("unit_measurement").toString());
                   data.setTransaction_price(map.get("transaction_price").toString());
                   data.setTurnover(map.get("turnover").toString());
                   data.setTrading_volume(map.get("trading_volume").toString());
                   data.setDraft_unit(map.get("draft_unit").toString());
                   //data.setUndertake_man(map.get("undertake_man").toString());
                   data.setDraft_department(map.get("draft_department").toString());
                   data.setPurchase_amount(map.get("purchase_amount").toString());
                   data.setDraft_time(map.get("draft_time").toString());
                   data.setProcurement_method(map.get("procurement_method").toString());
                   data.setWhether_open_purchasing(map.get("whether_open_purchasing").toString());
                   data.setProcurement_classification(map.get("procurement_classification").toString());
                   data.setCapital_source(map.get("capital_source").toString());
                   data.setNewest_time(map.get("newest_time").toString());
                   data.setProcurement_submit_time(map.get("procurement_submit_time").toString());
                   data.setCurrent_state(map.get("current_state").toString());
                   data.setCurrent_handler(map.get("current_handler").toString());
                   data.setProduct_service_type(map.get("product_service_type").toString());
                   list.add(data);
               }
               ShowWriteApi show = new ShowWriteApi();
               show.toExcel(list);
               HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
               show.response(response);
           }
       }

        return queryResult;
    }


}
