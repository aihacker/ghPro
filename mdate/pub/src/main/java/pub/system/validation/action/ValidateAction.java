package pub.system.validation.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.view.JsonView;
import pub.system.validation.ClientValidator;
import pub.system.validation.CustomValidationRule;

import java.io.IOException;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-4-15
 */
@Controller
@RequestMapping("/common/validate")
public class ValidateAction {

    @RequestMapping
    @ModelAttribute(JsonView.directResult)
    public Object execute(String ruleClass, String ruleData, String valueToValidate) throws IOException {
        CustomValidationRule rule = ClientValidator.deserializeCustomRule(ruleClass, ruleData);
        String errorMsg = rule.validate(valueToValidate);
        return errorMsg == null? true: errorMsg;
    }

}
