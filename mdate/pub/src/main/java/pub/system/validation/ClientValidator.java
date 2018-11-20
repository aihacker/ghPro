package pub.system.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.core.Conventions;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import pub.system.validation.model.FieldInfo;
import pub.system.validation.model.MessageModel;
import pub.system.validation.model.RuleInfo;
import pub.types.Desc;
import pub.types.NotImplementedException;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.*;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-4-15
 */
public class ClientValidator extends AbstractValidationRules {

    private Object bean;
    private String beanName;

    private String nestedPath = "";
    private List<String> orderedFields;
    private Map<String, List<RuleInfo>> fieldRulesMap;
    private List<RuleInfo> fieldRules;
    private String fieldName;

    public ClientValidator(Object bean) {
        this(bean, Conventions.getVariableName(bean));
    }

    public ClientValidator(Object bean, String beanName) {
        this.bean = bean;
        this.beanName = beanName;
        orderedFields = new ArrayList<String>();
        fieldRulesMap = new HashMap<String, List<RuleInfo>>();
    }

    public ClientValidator(ClientValidator parentValidator, Object bean, String path) {
        this.nestedPath = parentValidator.nestedPath + path + ".";
        this.bean = bean;
        this.orderedFields = parentValidator.orderedFields;
        this.fieldRulesMap = parentValidator.fieldRulesMap;
        this.beanName = null;
    }

    @Override
    public ClientValidator getNestedRules(String path) {
        Object childBean = null;
        try {
            if (bean != null) {
                childBean = PropertyUtils.getProperty(bean, path);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        ClientValidator validator = new ClientValidator(this, childBean, path);
        return validator;
    }

    @Override
    public ClientValidator on(String field) {
//		sb.append(field).append(" {")
        field = nestedPath + field;
        List<RuleInfo> rules = fieldRulesMap.get(field);
        if (rules == null) {
            rules = new ArrayList<RuleInfo>();
            fieldRulesMap.put(field, rules);
            orderedFields.add(field);
        }
        fieldRules = rules;
        fieldName = field;
        return this;
    }

    @Override
    public ClientValidator required(String errorMsg) {
        return addRule(RuleNames.required, true, errorMsg, DefaultMessages.get("required"));
    }

    private ClientValidator addRule(String ruleName, Object ruleParams, String errorMsg, String defaultMsg) {
        if (errorMsg == null || errorMsg.length() == 0) {
            errorMsg = defaultMsg;
        }
        fieldRules.add(new RuleInfo(ruleName, ruleParams, errorMsg));
        return this;
    }

    @Override
    public ClientValidator min(Object valueToCompare, String errorMsg) {
        return addRule(RuleNames.min, valueToCompare, errorMsg, DefaultMessages.get("min"));
    }

    @Override
    public ClientValidator max(Object valueToCompare, String errorMsg) {
        return addRule(RuleNames.max, valueToCompare, errorMsg, DefaultMessages.get("max"));
    }

    @Override
    public ClientValidator url(String errorMsg) {
        return addRule(RuleNames.url, true, errorMsg, DefaultMessages.get("url"));
    }

    @Override
    public ValidationRules regex(String regex, String errorMsg) {
        throw new NotImplementedException();
    }

    @Override
    public ClientValidator telno(String errorMsg) {
        return addRule(RuleNames.telno, true, errorMsg, DefaultMessages.get("telno"));
    }

    @Override
    public ClientValidator mobile(String errorMsg) {
        return addRule(RuleNames.mobile, true, errorMsg, DefaultMessages.get("mobile"));
    }

    @Override
    public ClientValidator minLength(int minLength, String errorMsg) {
        return addRule(RuleNames.minLength, minLength, errorMsg, DefaultMessages.get("minLength"));
    }

    @Override
    public ClientValidator maxLength(int maxLength, String errorMsg) {
        return addRule(RuleNames.maxLength, maxLength, errorMsg, DefaultMessages.get("maxLength"));
    }

    @Override
    public ValidationRules custom(CustomValidationRule rule) {
        String ruleClass = rule.getClass().getName();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("clazz", ruleClass);
        params.put("data", rule);
        return addRule("remote", params, null, DefaultMessages.get("custom"));
    }

    @Override
    public ClientValidator number(String errorMsg) {
        return addRule(RuleNames.number, true, errorMsg, DefaultMessages.get("number"));
    }

    @Override
    public ClientValidator integer(String errorMsg) {
        return addRule(RuleNames.integer, true, errorMsg, DefaultMessages.get("integer"));
    }

    @Override
    public ClientValidator email(String errorMsg) {
        return addRule(RuleNames.email, true, errorMsg, DefaultMessages.get("email"));
    }

    @Override
    public ClientValidator digits(String errorMsg) {
        return addRule(RuleNames.digits, true, errorMsg, DefaultMessages.get("digits"));
    }

    private String resolveMessage(String field, RuleInfo ruleInfo) {
        String fieldDesc = "";
        try {
            PropertyDescriptor propDesc = PropertyUtils.getPropertyDescriptor(bean, field);
            Desc desc = propDesc.getReadMethod().getAnnotation(Desc.class);
            if (desc != null) {
                fieldDesc = desc.value();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String message = ruleInfo.message;

        if (message.indexOf('{') != -1) {
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setName(field);
            fieldInfo.setDesc(fieldDesc);
            fieldInfo.setValue("");

            MessageModel model = new MessageModel();
            model.setField(fieldInfo);
            model.setRule(ruleInfo);

            ExpressionParser parser = new SpelExpressionParser();
            TemplateParserContext context = new TemplateParserContext("{", "}");
            message = parser.parseExpression(message, context).getValue(model, String.class);
        }
        return message;
    }

    @SuppressWarnings("unchecked")
    public static CustomValidationRule deserializeCustomRule(String ruleClassName, String ruleData) {
        CustomValidationRule rule = null;
        Class<? extends CustomValidationRule> ruleClass = null;
        try {
            ruleClass = (Class<CustomValidationRule>) Class.forName(ruleClassName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            rule = mapper.readValue(ruleData, ruleClass);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return rule;
    }

    public String getBean() {
        return beanName;
    }

    private RuleInfo makeOutputRuleInfo(String fieldName, RuleInfo ruleInfo) {
        RuleInfo resultInfo = new RuleInfo(ruleInfo.name, ruleInfo.param, ruleInfo.message);
        resultInfo.message = resolveMessage(fieldName, ruleInfo);
        return resultInfo;
    }

    public Object getRules() {
        SortedMap<String, List<RuleInfo>> rules = new TreeMap<String, List<RuleInfo>>(new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return orderedFields.indexOf(s1) - orderedFields.indexOf(s2);
            }
        });
        RuleInfo outputRuleInfo;
        for (int n = 0; n < orderedFields.size(); n++) {
            String field = orderedFields.get(n);
            List<RuleInfo> ruleInfos = fieldRulesMap.get(field);
            for (int m = 0; m < ruleInfos.size(); m++) {
                ruleInfos.set(m, makeOutputRuleInfo(field, ruleInfos.get(m)));
            }
            rules.put(field, ruleInfos);
        }
        return rules;
    }
}
