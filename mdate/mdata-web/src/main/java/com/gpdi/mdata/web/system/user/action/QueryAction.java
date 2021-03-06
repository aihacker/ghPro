package com.gpdi.mdata.web.system.user.action;
import com.gpdi.mdata.sys.service.system.user.SysUserService;
import com.gpdi.mdata.web.system.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class QueryAction {

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings) {
        return sysUserService.query(queryData, settings);
    }

    @RequestMapping
    public void add(HttpServletResponse response) throws IOException {
        response.sendRedirect("");
    }

    @Autowired
    private SysUserService sysUserService;

}
