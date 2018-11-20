package com.gpdi.mdata.web.reportform.organizational.contracttype.action;
import com.gpdi.mdata.sys.service.reportform.organizational.ContracttypeService;
import com.gpdi.mdata.sys.service.system.role.SysRoleService;
import com.gpdi.mdata.web.system.data.QueryData;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/11/8 18:49
 * @description:合同类型大类
 */
@Controller
public class QueryAction {

    @Autowired
    private ContracttypeService contracttypeService;


    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings) {
        return contracttypeService.queryType(queryData, settings);
    }




}
