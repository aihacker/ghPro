package com.gpdi.mdata.web.reportform.organizational.contracttypeinfo.action;

import com.gpdi.mdata.sys.entity.report.ContractType;
import com.gpdi.mdata.sys.entity.report.ContractTypeInfo;
import com.gpdi.mdata.sys.service.reportform.organizational.ContracttypeInfoService;
import com.gpdi.mdata.sys.service.reportform.organizational.ContracttypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/11/7 16:46
 * @description:新增合同类型小类
 */
@Controller
public class OperateAction {

    @Autowired
    private ContracttypeInfoService contractTypeInfoService;

    /**
     * 新增合同类型小类
     * @return
     */
    @RequestMapping
    public String new_(String parent) {
        return "redirect:show.html?parent="+parent;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping
    public ActionResult delete(Integer[] ids) {

        try {
            for(Integer id: ids) {
                contractTypeInfoService.delete(id);
            }
            return ActionResult.ok("删除成功");
        }
        catch (Exception e) {
            e.printStackTrace();
            return ActionResult.error(e);
        }
    }

    /**
     * 保存合同类型小类
     * @param contractTypeInfo
     * @param errors
     * @return
     */
    @RequestMapping
    public ActionResult save(ContractTypeInfo contractTypeInfo, Errors errors) {
        try {

            Boolean flag = contractTypeInfoService.saveLittleType(contractTypeInfo);
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
