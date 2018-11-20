package com.gpdi.mdata.web.reportform.organizational.contracttypeinfo.action;
import com.gpdi.mdata.sys.entity.report.ContractType;
import com.gpdi.mdata.sys.entity.report.ContractTypeInfo;
import com.gpdi.mdata.sys.entity.system.SysVar;
import com.gpdi.mdata.sys.service.reportform.organizational.ContracttypeInfoService;
import com.gpdi.mdata.sys.service.reportform.organizational.ContracttypeService;
import com.gpdi.mdata.sys.service.system.role.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.bind.XData;
import pub.spring.web.mvc.model.Model;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/11/8 19:31
 * @description:新增合同类型小类
 */
@Controller
@XData(types = SysVar.class)
public class ShowAction {

    @Autowired
    private ContracttypeInfoService infoService;
    @RequestMapping
    public void execute(Integer id, Model model,String parent) {
        ContractTypeInfo contractTypeInfo = null;
        if (id == null) {
            contractTypeInfo = new ContractTypeInfo();
            contractTypeInfo.setParentNum(parent);
            model.put(contractTypeInfo);
        } else {
           // contractTypeInfo = typeservice.get(id);
            model.put(contractTypeInfo);
        }

    }
}
