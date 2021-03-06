package com.gpdi.mdata.web.system.user.action;

import com.gpdi.mdata.sys.entity.system.SysVar;
import com.gpdi.mdata.sys.service.system.role.SysRoleService;
import com.gpdi.mdata.sys.service.system.user.SysUserService;
import com.gpdi.mdata.web.system.data.UserData;
import com.gpdi.mdata.web.system.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.bind.XData;
import pub.spring.web.mvc.model.Model;
import pub.system.validation.JsValidator;
import pub.types.RefData;

/**
 *
 */
@Controller
@XData(types = SysVar.class)
public class Show2Action {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping
    public void execute(Integer id, Model model) {
        UserData userData= sysUserService.getUserData(id);
        userData.setUserId(id);
        model.put("userData",userData);
    }

}
