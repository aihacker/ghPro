package com.gpdi.mdata.web.app.action;

import com.gpdi.mdata.sys.entity.system.SysVar;
import com.gpdi.mdata.sys.service.system.role.SysRoleService;
import com.gpdi.mdata.sys.service.system.user.SysUserService;
import com.gpdi.mdata.web.system.data.UserData;
import com.gpdi.mdata.web.system.utils.ValidateUtils;
import com.sun.org.apache.xpath.internal.operations.String;
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
public class ShowAction {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @RequestMapping
    public void execute(Integer id, Model model) {
        UserData userData= sysUserService.getUserData(id);
        userData.setUserId(id);
        model.put("userData",userData);
    }

    private RefData createRefData() {
        RefData refData = new RefData();
        return refData;
    }

    private JsValidator createJsValidator(SysVar sysVar) {
        JsValidator validator = new JsValidator(sysVar);
        ValidateUtils.setRules(validator);
        return validator;
    }
}
