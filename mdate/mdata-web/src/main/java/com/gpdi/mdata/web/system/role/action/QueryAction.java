package com.gpdi.mdata.web.system.role.action;
import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.sys.service.system.role.SysRoleService;
import com.gpdi.mdata.sys.service.system.user.SysUserService;
import com.gpdi.mdata.web.system.data.QueryData;
import net.sf.json.JSONArray;
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


@Controller
public class QueryAction {

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings) {
        return sysRoleService.query(queryData, settings);
    }

    @RequestMapping
    public void add(HttpServletResponse response) throws IOException {
        response.sendRedirect("");
    }

    @RequestMapping
    public ActionResult getModule(HttpServletRequest request,Model model) {
        String roleId=request.getParameter("roleId");
        ActionResult result=ActionResult.ok();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("tree",sysRoleService.formTree(Integer.parseInt(roleId)));
        map.put("roleId",roleId);
        result.setData(map);
        return  result;
    }

    @RequestMapping
    public ActionResult saveTree(HttpServletRequest request) {
        String datas=request.getParameter("datas");
        JSONObject jsonObject=JSONObject.fromObject(datas);
        Integer roleid=Integer.valueOf(jsonObject.getString("rid"));
        String ids=jsonObject.getString("ids");
        String array[]=ids.replace("[","").replace("]","").split(",");
        System.out.println(array.toString());
        ActionResult result=ActionResult.ok();
        sysRoleService.saveTree(roleid,array);
//        result.setData(sysRoleService.formTree());
        return  result;
    }
    @Autowired
    private SysRoleService sysRoleService;

}
