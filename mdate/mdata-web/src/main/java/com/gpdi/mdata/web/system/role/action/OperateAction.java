package com.gpdi.mdata.web.system.role.action;

import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.sys.service.system.role.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;

/**
 *
 */
@Controller
//@XData(types = SysVar.class)
public class OperateAction {
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping
    public String new_() {
        return "redirect:show.html";
    }

    @RequestMapping
    public String edit(Integer id) {
        System.out.println("role id : " + id);
        return "redirect:show.html?edit=1&id=" + id;
    }

    @RequestMapping
    public ActionResult delete(Integer[] ids) {
        try {
            for(Integer id: ids) {
                sysRoleService.delete(id);
            }
            return ActionResult.ok();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ActionResult.error(e);
        }
    }

    @RequestMapping
    public ActionResult save(SysRole sysRole, Errors errors) {
        try {
            if(sysRole.getIsEnable()==null){
                sysRole.setIsEnable(0);
            }
            Integer id = sysRoleService.save(sysRole);
            return ActionResult.ok();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ActionResult.error(e);
        }
    }
}
