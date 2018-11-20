package pub.system.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.core.Conventions;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import pub.types.Desc;
import pub.types.NotImplementedException;
import pub.web.ServletUtils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-2-15
 * Time: 2:54:28
 */
@SuppressWarnings("unchecked")
public class JsValidator extends AbstractValidationRules {

	private static class RuleInfo {
		String ruleName;
		Object ruleValue;
		String message;

		public RuleInfo(String ruleName, Object ruleValue) {
			this(ruleName, ruleValue, null);
		}

		public RuleInfo(String ruleName, Object ruleValue, String message) {
			this.ruleName = ruleName;
			this.ruleValue = ruleValue;
			this.message = message;
		}
	}

	private Object bean;
	private String formId;

	private String nestedPath = "";
	private List<String> orderedFields;
	private Map<String, List<RuleInfo>> fieldRulesMap;
	private List<RuleInfo> fieldRules;
	private String fieldName;

	public JsValidator(Object bean) {
		this(bean, Conventions.getVariableName(bean));
	}

	public JsValidator(Object bean, String formId) {
		this.bean = bean;
		this.formId = formId;
		orderedFields = new ArrayList<String>();
		fieldRulesMap = new HashMap<String, List<RuleInfo>>();
	}

	public JsValidator(JsValidator parentValidator, Object bean, String path) {
		this.nestedPath = parentValidator.nestedPath + path + ".";
		this.bean = bean;
		this.orderedFields = parentValidator.orderedFields;
		this.fieldRulesMap = parentValidator.fieldRulesMap;
		this.formId = null;
	}

	public Object getBean() {
		return bean;
	}

	@Override
	public JsValidator getNestedRules(String path) {
		Object childBean = null;
		try {
			if (bean != null) {
				childBean = PropertyUtils.getProperty(bean, path);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		JsValidator validator = new JsValidator(this, childBean, path);
		return validator;
	}

	@Override
	public JsValidator on(String field) {
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
	public ValidationRules regex(String regex, String errorMsg) {
        throw new NotImplementedException();
//		return this;
	}

	@Override
	public JsValidator required(String errorMsg) {
		fieldRules.add(new RuleInfo(RuleNames.required, true, errorMsg));
		return this;
	}

	@Override
	public JsValidator min(Object valueToCompare, String errorMsg) {
		fieldRules.add(new RuleInfo(RuleNames.min, valueToCompare, errorMsg));
		return this;
	}

	@Override
	public JsValidator max(Object valueToCompare, String errorMsg) {
		fieldRules.add(new RuleInfo(RuleNames.max, valueToCompare, errorMsg));
		return this;
	}
	
	@Override
	public JsValidator url(String errorMsg) {
		fieldRules.add(new RuleInfo(RuleNames.url, true, errorMsg));
		return this;
	}	

	@Override
	public JsValidator telno(String errorMsg) {
		fieldRules.add(new RuleInfo(RuleNames.telno, true, errorMsg));
		return this;
	}

	@Override
	public JsValidator mobile(String errorMsg) {
		fieldRules.add(new RuleInfo(RuleNames.mobile, true, errorMsg));
		return this;
	}

	@Override
	public JsValidator minLength(int minLength, String errorMsg) {
		fieldRules.add(new RuleInfo(RuleNames.minLength, minLength, errorMsg));
		return this;
	}

	@Override
	public JsValidator maxLength(int maxLength, String errorMsg) {
		fieldRules.add(new RuleInfo(RuleNames.maxLength, maxLength, errorMsg));
		return this;
	}

	@Override
	public ValidationRules custom(CustomValidationRule rule) {

		String ruleValue = null;

		Map remoteOptions = new HashMap();

		String home = ServletUtils.getRequest().getContextPath();
		String remoteUrl = home + "/common/validate.json";
		remoteOptions.put("url", remoteUrl);
		remoteOptions.put("type", "POST");

		Map postData = new HashMap();
		String ruleClass = rule.getClass().getName();
		postData.put("ruleClass", ruleClass);

		ObjectMapper mapper = new ObjectMapper();
//		mapper.getSerializationConfig().set(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		String ruleData;
		StringWriter sw = new StringWriter();
		try {
			mapper.writeValue(sw, rule);
			sw.flush();
			ruleData = sw.toString();
			sw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		postData.put("ruleData", ruleData);
		remoteOptions.put("data", postData);

		sw = new StringWriter();
		try {
			mapper.writeValue(sw, remoteOptions);
			sw.flush();
			ruleValue = sw.toString();
			sw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		fieldRules.add(new RuleInfo("remote", ruleValue));
		return this;
	}

	@Override
	public JsValidator number(String errorMsg) {
		fieldRules.add(new RuleInfo(RuleNames.number, true, errorMsg));
		return this;
	}
	
	@Override
	public JsValidator integer(String errorMsg) {
		fieldRules.add(new RuleInfo(RuleNames.integer, true, errorMsg));
		return this;
	}

	@Override
	public JsValidator email(String errorMsg) {
		fieldRules.add(new RuleInfo(RuleNames.email, true, errorMsg));
		return this;
	}

	@Override
	public JsValidator digits(String errorMsg) {
		fieldRules.add(new RuleInfo(RuleNames.digits, true, errorMsg));
		return this;
	}

	private String resolveMessage(String field, RuleInfo ruleInfo) {
		Map<String, Object> argumentModel = new HashMap<String, Object>();

		String fieldDesc = "";
		try {
			PropertyDescriptor propDesc = PropertyUtils.getPropertyDescriptor(bean, field);
			Desc desc = propDesc.getReadMethod().getAnnotation(Desc.class);
			if (desc != null) {
				fieldDesc = desc.value();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, Object> fieldInfo = new HashMap<String, Object>();
		fieldInfo.put("desc", fieldDesc);
		fieldInfo.put("value", "");
		argumentModel.put("field", fieldInfo);

		Map<String, Object> ruleInfoMap = new HashMap<String, Object>();
		ruleInfoMap.put(ruleInfo.ruleName, ruleInfo.ruleValue);
		argumentModel.put("rule", ruleInfoMap);

//		TemplateRenderer templateRenderer = new TemplateRenderer(ruleInfo.message);
//		String message = templateRenderer.putAll(argumentModel).evaluate();
		ExpressionParser parser = new SpelExpressionParser();
		Expression expression = parser.parseExpression(ruleInfo.message, new TemplateParserContext());

		StandardEvaluationContext context = new StandardEvaluationContext();
		context.setVariables(argumentModel);
		String message = expression.getValue(context, String.class);

		return message;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
//		sb.append("$('#").append(formId).append("').validate({\n");
        sb.append("window.validateOptions = {\n");
		sb.append("\trules: {\n");
		boolean firstField = true;
		for (int n = 0; n < orderedFields.size(); n++) {
			String field = orderedFields.get(n);
			if (n > 0) {
				sb.append(",\n");
			}
			sb.append("\t\t'").append(field).append("': {\n");
			List<RuleInfo> ruleInfos = fieldRulesMap.get(field);
			for (int i = 0; i < ruleInfos.size(); i++) {
				RuleInfo ruleInfo = ruleInfos.get(i);
				if (i > 0) {
					sb.append(",\n");
				}
				sb.append("\t\t\t").append(ruleInfo.ruleName)
					.append(": ").append(ruleInfo.ruleValue);
			}
			sb.append("\n\t\t}");
		}
		sb.append("\n\t},\n");
		sb.append("\tmessages: {\n");
		for (int n = 0; n < orderedFields.size(); n++) {
			String field = orderedFields.get(n);
			if (n > 0) {
				sb.append(",\n");
			}
			sb.append("\t\t'").append(field).append("': {\n");
			List<RuleInfo> ruleInfos = fieldRulesMap.get(field);
			boolean firstIter = true;
			for (RuleInfo ruleInfo: ruleInfos) {
				String message = ruleInfo.message;
				if (message == null || message.length() == 0) {
					continue;
				}
				if (!firstIter) {
					sb.append(",\n");
				}
				message = resolveMessage(field, ruleInfo);
				sb.append("\t\t\t").append(ruleInfo.ruleName)
					.append(": '").append(message).append("'");
				firstIter = false;
			}
			sb.append("\n\t\t}");
		}
		sb.append("\n\t}\n");

//		sb.append("});\n");
        sb.append("};\n");
		return sb.toString();
	}

	public static CustomValidationRule deserialize(String ruleString) {
		CustomValidationRule rule = null;
		int pos = ruleString.indexOf(':');
		String ruleClassName = ruleString.substring(0, pos);
		String ruleData = ruleString.substring(pos + 1);
		Class<? extends CustomValidationRule> ruleClass = null;
		try {
			ruleClass = (Class<CustomValidationRule>) Class.forName(ruleClassName);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		if (ruleData == null || ruleData.length() == 0) {
			try {
				return (CustomValidationRule) ruleClass.newInstance();
			}
			catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			rule = mapper.readValue(ruleData, ruleClass);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return rule;
	}

	public static CustomValidationRule deserialize(String ruleClassName, String ruleData) {
		CustomValidationRule rule = null;
		Class<? extends CustomValidationRule> ruleClass = null;
		try {
			ruleClass = (Class<CustomValidationRule>) Class.forName(ruleClassName);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			rule = mapper.readValue(ruleData, ruleClass);
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return rule;
	}

}
