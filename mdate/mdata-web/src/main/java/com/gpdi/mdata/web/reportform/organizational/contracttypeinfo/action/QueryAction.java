package com.gpdi.mdata.web.reportform.organizational.contracttypeinfo.action;
import com.gpdi.mdata.sys.service.reportform.organizational.ContracttypeInfoService;
import com.gpdi.mdata.sys.service.reportform.organizational.ContracttypeService;
import com.gpdi.mdata.sys.service.system.role.SysRoleService;
import com.gpdi.mdata.web.system.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/11/8 18:49
 * @description:合同类型小类
 */
@Controller
public class QueryAction {

    @Autowired
    private ContracttypeInfoService infoService;


    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings, String typecode, String typename, Model model) {
        model.put("typename",typename);
        return infoService.queryTypeInfo(queryData, settings,typecode);
    }




}
