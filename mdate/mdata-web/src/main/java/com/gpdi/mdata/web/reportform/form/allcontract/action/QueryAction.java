package com.gpdi.mdata.web.reportform.form.allcontract.action;
import com.gpdi.mdata.sys.service.reportform.form.RepPurchaseService;
import com.gpdi.mdata.sys.service.system.user.SysUserService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import com.gpdi.mdata.web.reportform.form.allmost.action.AllmostTempData;
import com.gpdi.mdata.web.reportform.form.allmost.action.AllmostWriteApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class QueryAction {
    @Autowired
    private RepPurchaseService repPurchaseService;

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings) {

        QueryResult queryResult = repPurchaseService.query(queryData, settings);

        List<AllcontractTempData> list = new ArrayList<AllcontractTempData>();
        if (queryData.getIsExport() != null && queryData.getIsExport() == 1) {
            if (queryResult.getRows().size() > 0) {
                for (int i = 0; i < queryResult.getRows().size(); i++) {
                    LinkedCaseInsensitiveMap map = (LinkedCaseInsensitiveMap) queryResult.getRows().get(i);
                    AllcontractTempData data = new AllcontractTempData();
                    data.setId(map.get("id").toString());
                    data.setContract_code(map.get("contract_code").toString());
                    data.setPurchase_time(map.get("purchase_time").toString());
                    data.setContract_name(map.get("contract_name").toString());
                    data.setContract_amount(map.get("contract_amount").toString());
                    data.setUndertake_org(map.get("undertake_org").toString());
                    data.setUndertake_dept(map.get("undertake_dept").toString());
                    data.setUndertake_man(map.get("undertake_man").toString());
                    data.setSupplier_name(map.get("supplier_name").toString());
                    data.setPurchase_way(map.get("purchase_way").toString());
                    data.setPurchase_type(map.get("purchase_type").toString());
                    data.setContract_code(map.get("contract_code").toString());
                    data.setPurchase_kind(map.get("purchase_kind").toString());
                    data.setSet_service_type(map.get("set_service_type").toString());
                    data.setSet_type_lv1(map.get("set_type_lv1").toString());
                    data.setSet_type_lv2(map.get("set_type_lv2").toString());
                    data.setSign_type(map.get("sign_type").toString());
                    data.setIs_correlation_trade(map.get("is_correlation_trade").toString());
                    data.setCorrelation_trade_type(map.get("correlation_trade_type").toString());
                    data.setIs_itc_project(map.get("is_itc_project").toString());
                    data.setIs_pivotal_contract(map.get("is_pivotal_contract").toString());
                    data.setReceipt_pay_type(map.get("receipt_pay_type").toString());
                    data.setContract_type(map.get("contract_type").toString());
                    data.setPurchase_result_code(map.get("purchase_result_code").toString());
                    data.setContract_prop(map.get("contract_prop").toString());
                    if(map.get("archive_date")!=null) {
                        data.setArchive_date(map.get("archive_date").toString());
                    }else{
                        data.setArchive_date(" ");
                    }
                    data.setContract_status(map.get("contract_status").toString());
                    list.add(data);
                }
                AllcontractWriteApi allcontract = new AllcontractWriteApi();
                allcontract.toExcel(list);
                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                allcontract.response(response);
            }

        }
        return queryResult;
    }

}
