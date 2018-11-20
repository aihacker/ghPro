package com.gpdi.mdata.web.reportform.organizational.contracttype.action;

import com.gpdi.mdata.sys.entity.report.ContractType;
import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.sys.service.reportform.organizational.ContracttypeService;
import com.gpdi.mdata.sys.service.system.role.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/11/7 16:46
 * @description:新增合同类型大类
 */
@Controller
public class OperateAction {
    @Autowired
   private ContracttypeService contracttypeService;

    /**
     * 新增合同类型大类
     * @return
     */
    @RequestMapping
    public String new_() {
        return "redirect:show.html";
    }

    @RequestMapping
    public String edit(String id) {
        System.out.println("type_code : " + id);
        return "redirect:show.html?edit=1&id=" + id;
    }

    @RequestMapping
    public ActionResult delete(Integer[] ids) {

        try {
            for(Integer id: ids) {
                contracttypeService.delete(id);
            }
            return ActionResult.ok("删除成功");
        }
        catch (Exception e) {
            e.printStackTrace();
            return ActionResult.error(e);
        }
    }

    /**
     * 保存合同类型大类
     * @param contractType
     * @param errors
     * @return
     */
    @RequestMapping
    public ActionResult save(ContractType contractType, Errors errors) {
        try {

            Boolean flag = contracttypeService.saveBigType(contractType);
            if (flag ==true){
                return ActionResult.ok("新增成功:"+flag);
            }else {
                return ActionResult.error("保存失败");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            return ActionResult.error(e);
        }
    }
}
