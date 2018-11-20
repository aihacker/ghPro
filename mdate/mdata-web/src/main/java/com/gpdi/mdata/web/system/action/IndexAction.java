package com.gpdi.mdata.web.system.action;

import com.gpdi.mdata.sys.entity.system.QueryData;
import com.gpdi.mdata.sys.entity.system.SysUser;
import com.gpdi.mdata.sys.service.system.user.SysUserService;
import com.gpdi.mdata.web.app.data.SessionData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import pub.web.ServletUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zoe on 16/8/7.
 */
@Controller
public class IndexAction {

    @RequestMapping
    public void execute(Model model) {
        HttpSession session= ServletUtils.getSession();
        SessionData  session_data=(SessionData)session.getAttribute("SESSION_DATA");
        Integer userId=session_data.getUserId();
        QueryData queryData=new QueryData();
        queryData.setParentId(100);
        queryData.setList(sysUserService.getRoleByUser(userId));
        List<String> code=sysUserService.getModuleByRoleSAndParent(queryData);
        model.put("code",code);
        model.put("name",session_data.getUserName());
    }
    @Autowired
    private SysUserService sysUserService;
}
