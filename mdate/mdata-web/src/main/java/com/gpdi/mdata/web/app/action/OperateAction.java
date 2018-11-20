package com.gpdi.mdata.web.app.action;

import com.gpdi.mdata.sys.entity.system.SysUser;
import com.gpdi.mdata.sys.service.system.user.SysUserService;
import com.gpdi.mdata.web.system.data.UserData;
import com.gpdi.mdata.web.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.bind.XData;
import pub.spring.web.mvc.model.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Controller
@XData(types = UserData.class)
public class OperateAction {
    @Autowired
    private SysUserService sysUserService;

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
                sysUserService.delete(id);
            }
            return ActionResult.ok();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ActionResult.error(e);
        }
    }

    @RequestMapping
    public ActionResult save(UserData userData) {
        try {
            SysUser sysUser=sysUserService.getUser(userData.getUserId());
            if(sysUser.getPwd().equals(MD5Util.MD5(userData.getOldPass()))){
                sysUser.setPwd(MD5Util.MD5(userData.getNewPass()));
                sysUserService.save(sysUser);
            }else{
                return ActionResult.error("密码不正确");
            }
/*            userData.getRegions();
            sysUserService.saveAndToJOB(userData);*/
            return ActionResult.ok();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ActionResult.error(e);
        }
    }



}
