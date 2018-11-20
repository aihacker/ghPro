package com.gpdi.mdata.web.system.action;

import com.gpdi.mdata.sys.entity.system.SysVar;
import com.gpdi.mdata.sys.service.system.SysVarService;
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
    private SysVarService sysVarService;

    @RequestMapping
    public void execute(Integer id, Model model) {
        SysVar sysVar = null;
        if(id == null) {
            sysVar = new SysVar();
        }
        else {
            sysVar = sysVarService.get(id);
        }
        model.put(sysVar);
        model.put(createRefData());
        model.put(createJsValidator(sysVar));
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
