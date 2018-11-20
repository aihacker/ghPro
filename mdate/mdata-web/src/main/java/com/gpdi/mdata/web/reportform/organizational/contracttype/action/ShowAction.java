package com.gpdi.mdata.web.reportform.organizational.contracttype.action;
import com.gpdi.mdata.sys.entity.report.ContractType;
import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.sys.entity.system.SysVar;
import com.gpdi.mdata.sys.service.reportform.organizational.ContracttypeService;
import com.gpdi.mdata.sys.service.system.role.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.bind.XData;
import pub.spring.web.mvc.model.Model;
import pub.types.RefData;

/**
 *
 */
@Controller
@XData(types = SysVar.class)
public class ShowAction {
    @Autowired
    private ContracttypeService typeservice;
    @RequestMapping
    public void execute(Integer id, Model model) {
        ContractType contractType = null;
        if (id == null) {
            contractType = new ContractType();
            model.put(contractType);
        } else {
            contractType = typeservice.get(id);
            model.put(contractType);
        }

    }

}
