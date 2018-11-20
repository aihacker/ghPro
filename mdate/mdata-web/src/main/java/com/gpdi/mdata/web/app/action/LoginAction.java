package com.gpdi.mdata.web.app.action;


import com.gpdi.mdata.sys.entity.system.QueryData;
import com.gpdi.mdata.sys.entity.system.SysModule;
import com.gpdi.mdata.sys.entity.system.SysUser;
import com.gpdi.mdata.sys.service.system.role.SysRoleService;
import com.gpdi.mdata.sys.service.system.user.SysUserService;
import com.gpdi.mdata.web.app.utils.SessionUtils;
import com.gpdi.mdata.web.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.web.ServletUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zzl on 2015/11/14.
 */
@Controller
public class LoginAction {

    @RequestMapping
    public void execute() {
        //
    }

    @RequestMapping
    public ActionResult login(HttpServletRequest request, HttpServletResponse response,Model model) {
        ActionResult result;
        String account = request.getParameter("account");
        String pwd =request.getParameter("password");
        SysUser sysUser=new SysUser();
        sysUser.setPwd(pwd);
        sysUser.setUserCode(account);
        SysUser user1 = loginUser(sysUser);
        //User user = null;
        if (user1 == null) {//登录失败
            result = ActionResult.fail("帐号/密码错误");
            model.put("errorMsg","帐号/密码错误");
        }  else {
            QueryData queryData=new QueryData();
            queryData.setParentId(user1.getUserId());
            queryData.setModuleCode("00");
            List<Integer> roleIds=sysUserService.getRoleByUser(user1.getUserId());
            if(roleIds!=null&&roleIds.size()>0) {
                //queryData.setList(roleIds);
                List<String> modeleCode = sysUserService.getModuleByRoleSAndParent(queryData);
                SessionUtils.loginUserInfo(user1, modeleCode);
                HttpSession session = ServletUtils.getSession();
                session.setAttribute("userName", user1.getUserName());

                //session.setAttribute("ReportType", 1);

                //sys
                //queryData.setParentId(100);
                queryData.setModuleCode("01");
                session.setAttribute("system", sysUserService.getModuleByRoleSAndParent(queryData));
                //queryData.setParentId(101);
                queryData.setModuleCode("02");
                session.setAttribute("medata", sysUserService.getModuleByRoleSAndParent(queryData));
                //queryData.setParentId(102);
                queryData.setModuleCode("03");
                session.setAttribute("datasyn", sysUserService.getModuleByRoleSAndParent(queryData));
                //queryData.setParentId(103);
                queryData.setModuleCode("04");
                session.setAttribute("dataoperate",sysUserService.getModuleByRoleSAndParent(queryData));
                //queryData.setParentId(104);
                queryData.setModuleCode("05");
                session.setAttribute("monitor",sysUserService.getModuleByRoleSAndParent(queryData));
            }
            else{
                SessionUtils.loginUser(user1);
            }


            HttpSession session = ServletUtils.getSession();

            result = ActionResult.ok();
            result.setData(user1);
            try {
                response.sendRedirect(request.getContextPath() + "/");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private boolean loginAdmin(HttpServletRequest request, HttpServletResponse response) {
        if (!"admin".equals(request.getParameter("account"))) {
            return false;
        }
        String password = request.getParameter("password");
        //String adminPass = sysVarService.getValue("admin_pass");
        String adminPass = "admin";
        if (!adminPass.equals(password)) {
            return false;
        }
        SessionUtils.loginAdmin();
        try {
            response.sendRedirect(request.getContextPath() + "/");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private SysUser loginUser(SysUser user) {
        String user_code =user.getUserCode();
        SysUser dbUser = sysUserService.getUser(user_code);
        /*String pw = dbUser.getPwd();//数据库里的
        String pw2 = MD5Util.MD5(user.getPwd());//录入的
        System.out.println(pw+";;;"+pw2);*/
        if (dbUser == null || !dbUser.getPwd().equals(MD5Util.MD5(user.getPwd()))) {
            return null;
        }
        return dbUser;
    }

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

}
