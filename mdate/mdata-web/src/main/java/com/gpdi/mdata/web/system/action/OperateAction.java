package com.gpdi.mdata.web.system.action;

import com.gpdi.mdata.sys.entity.system.SysVar;
import com.gpdi.mdata.sys.service.system.SysVarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.bind.XData;

import java.util.Date;

/**
 *
 */
@Controller
@XData(types = SysVar.class)
public class OperateAction {
    @Autowired
    private SysVarService sysVarService;

    @RequestMapping
    public String new_() {
        return "redirect:show.html";
    }

    @RequestMapping
    public String edit(Integer id) {
        return "redirect:show.html?edit=1&id=" + id;
    }

    @RequestMapping
    public ActionResult delete(Integer[] ids) {
        try {
            for(Integer id: ids) {
                sysVarService.delete(id);
            }
            return ActionResult.ok();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ActionResult.error(e);
        }
    }

    @RequestMapping
    public ActionResult save(SysVar sysVar, Errors errors) {
        try {
            Integer id = sysVarService.save(sysVar);
            return ActionResult.ok();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ActionResult.error(e);
        }
    }
}
