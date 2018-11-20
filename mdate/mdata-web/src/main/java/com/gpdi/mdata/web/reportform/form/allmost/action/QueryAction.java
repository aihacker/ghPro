package com.gpdi.mdata.web.reportform.form.allmost.action;


import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.form.QuerySupByDeptService;
import com.gpdi.mdata.sys.service.reportform.form.RepPurchaseService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * 签订合同最多的供应商
 */
@Controller
public class QueryAction {

    @Autowired
    private RepPurchaseService repPurchaseService;

    @Autowired
    private QuerySupByDeptService querySupByDeptService;

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings, Model model) {
        List<PurchaseContract> contracts = repPurchaseService.getContractType();
        model.put("types", contracts);
        List<PurchaseContract> deptList = querySupByDeptService.getAllDept();//获取所有部门
        model.put("depts", deptList);

        QueryResult queryResult = repPurchaseService.queryForm(queryData, settings);

        List<AllmostTempData> list = new ArrayList<AllmostTempData>();
        if (queryData.getIsExport() != null && queryData.getIsExport() == 1) {
            if (queryResult.getRows().size() > 0) {
                for (int i = 0; i < queryResult.getRows().size(); i++) {
                    LinkedCaseInsensitiveMap map = (LinkedCaseInsensitiveMap) queryResult.getRows().get(i);
                    AllmostTempData data = new AllmostTempData();
                    data.setSupplier_name((String) map.get("supplier_name"));
                    data.setCc(map.get("cc").toString());
                    data.setDd(map.get("dd").toString());
                    data.setContract_type_name(map.get("contract_type_name").toString());
                    list.add(data);
                }
                AllmostWriteApi proportion = new AllmostWriteApi();
                proportion.toExcel(list);
                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                proportion.response(response);
            }

        }
        return queryResult;
    }
}
