package com.gpdi.mdata.web.system.role.action;
import com.gpdi.mdata.sys.entity.system.SysRole;
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
public class ShowAction {
    @Autowired
    private SysRoleService sysRoleService;
    @RequestMapping
    public void execute(Integer id, Model model) {
        SysRole sysRole = null;
        if (id == null) {
            sysRole = new SysRole();
            model.put(sysRole);
            model.put(createRefData(0));
        } else {
            sysRole = sysRoleService.get(id);
            model.put(createRefData(sysRole.getIsEnable()));
            model.put(sysRole);
        }

    }

    private RefData createRefData(Integer status) {
        RefData refData = new RefData();
        refData.put("refData", status);
        return refData;
    }


}
