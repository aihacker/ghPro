package pub.system.validation;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.validation.Errors;
import pub.functions.ValidateFuncs;
import pub.system.validation.model.FieldInfo;
import pub.system.validation.model.MessageModel;
import pub.system.validation.model.RuleInfo;
import pub.types.Desc;
import pub.types.NotImplementedException;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-2-12
 */
@SuppressWarnings("unchecked")
public class Validator extends AbstractValidationRules {

	public static org.springframework.validation.Validator staticValidator;

	private Object bean;
	private Errors errors;

	private String nestedPath = "";

	private String fieldName;
	private Object fieldValue;
	private boolean noValue;
	private boolean hasError;
	private static BeanComparator beanComparator = new BeanComparator();

	public Validator(Object bean, Errors errors) {
		this.bean = bean;
		this.errors = errors;
	}

	public static Validator of(Object bean, Errors errors) {
		return new Validator(bean, errors);
	}

	public Validator staticValidate() {
		if (staticValidator != null) {
			staticValidator.validate(bean, errors);
		}
		return this;
	}

	@Override
	public Object getBean() {
		return bean;
	}
	
	@Override
	public Validator getNestedRules(String path) {
		Object childBean;
		try {
			childBean = PropertyUtils.getProperty(bean, path);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		Validator validator = new Validator(childBean, errors);
		validator.nestedPath = this.nestedPath + path + ".";
		return validator;
	}

	@Override
	public Validator on(String field) {
		fieldName = field;
		try {
			fieldValue = PropertyUtils.getProperty(bean, field);
			String fullField = nestedPath + fieldName;
			hasError = errors.hasFieldErrors(fullField);
			noValue = fieldValue == null || fieldValue.toString().length() == 0;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return this;
	}

	@Override
	public Validator required(String errorMsg) {
		if (hasError) return this;
		if(noValue) {
			reject(RuleNames.required, errorMsg, DefaultMessages.get("required"));
		}
		return this;
	}

	@Override
	public Validator min(Object valueToCompare, String errorMsg) {
		if (hasError || noValue) return this;

		Comparable comparable1 = (Comparable) fieldValue;
		Comparable comparable2 = (Comparable) ConvertUtils.convert(
			valueToCompare.toString(), fieldValue.getClass());

		if (comparable1.compareTo(comparable2) < 0) {
			reject(RuleNames.min, errorMsg, DefaultMessages.get("min"), valueToCompare);
		}
		return this;
	}

	@Override
	public Validator max(Object valueToCompare, String errorMsg) {
		if (hasError || noValue) return this;

		Comparable comparable1 = (Comparable) fieldValue;
		Comparable comparable2 = (Comparable) ConvertUtils.convert(
			valueToCompare.toString(), fieldValue.getClass());

		if (comparable1.compareTo(comparable2) > 0) {
			reject(RuleNames.max, errorMsg, DefaultMessages.get("max"), valueToCompare);
		}
		return this;
	}

	@Override
	public Validator telno(String errorMsg) {
		if (hasError || noValue) return this;
		String value = fieldValue.toString();
		if (!ValidateFuncs.validateTelno(value)) {
			reject(RuleNames.telno, errorMsg, DefaultMessages.get("telno"));
		}
		return this;
	}

	@Override
	public Validator mobile(String errorMsg) {
		if (hasError || noValue) return this;
		String value = fieldValue.toString();
		if (!ValidateFuncs.validateMobile(value)) {
			reject(RuleNames.telno, errorMsg, DefaultMessages.get("mobile"));
		}
		return this;
	}

	@Override
	public Validator minLength(int minLength, String errorMsg) {
		if (hasError) return this;
		if (!noValue && fieldValue.toString().length() < minLength) {
			reject(RuleNames.minLength, errorMsg, DefaultMessages.get("minLength"), minLength);
		}
		return this;
	}

	@Override
	public Validator maxLength(int maxLength, String errorMsg) {
		if(hasError || noValue) return this;
		if (fieldValue.toString().length() > maxLength) {
			String defaultMsg = DefaultMessages.get("maxLength");
			reject(RuleNames.maxLength, errorMsg, defaultMsg, maxLength);
		}
		return this;
	}

	@Override
	public Validator custom(CustomValidationRule rule) {
		if(hasError || noValue) return this;
		String errorMsg = rule.validate(fieldValue);
		if (errorMsg != null) {
			reject(RuleNames.general, errorMsg, DefaultMessages.get("custom"));
		}
		return this;
	}

	@Override
	public ValidationRules number(String errorMsg) {
		if(hasError || noValue) return this;
		try {
			Double.valueOf(fieldValue.toString());
		}
		catch(Exception e) {
			String defaultMsg = DefaultMessages.get("number");
			reject(RuleNames.number, errorMsg, defaultMsg);
		}
		return this;
	}

	@Override
	public ValidationRules integer(String errorMsg) {
		if(hasError || noValue) return this;
		try {
			Integer.valueOf(fieldValue.toString());
		}
		catch(Exception e) {
			String defaultMsg = DefaultMessages.get("integer");
			reject(RuleNames.integer, errorMsg, defaultMsg);
		}
		return this;
	}

	@Override
	public ValidationRules email(String errorMsg) {
		if(hasError || noValue) return this;
		String s = fieldValue.toString();
		int atPos = s.indexOf('@');
		if(atPos < 1 || atPos == s.length() - 1) {
			String defaultMsg = DefaultMessages.get("email");
			reject(RuleNames.integer, errorMsg, defaultMsg);
		}
		return this;
	}
	
	@Override
	public ValidationRules url(String errorMsg) {
		if(hasError || noValue) return this;
		String s = fieldValue.toString();
		Pattern pattern = Pattern.compile("^[a-zA-z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(s);
		if(!matcher.matches()) {
			String defaultMsg = DefaultMessages.get("url");
			reject(RuleNames.url, errorMsg, defaultMsg);
		}
		return this;
	}

	@Override
	public ValidationRules regex(String regex, String errorMsg) {
		throw new NotImplementedException();
	}

	@Override
	public ValidationRules digits(String errorMsg) {
		if(hasError || noValue) return this;
		String s = fieldValue.toString();
		for (int n = 0; n < s.length(); n++) {
			char c = s.charAt(n);
			if (!Character.isDigit(c)) {
				String defaultMsg = DefaultMessages.get("digits");
				reject(RuleNames.digits, errorMsg, defaultMsg);
				break;
			}
		}
		return this;
	}

    public void reject(String message) {
        reject(RuleNames.general, null, message);
    }

	private void reject(String ruleName, String message, String defaultMessage) {
		reject(ruleName, message, defaultMessage, null);
	}
	private void reject(String ruleName, String message, String defaultMessage, Object ruleAttributes) {
		hasError = true;
		String field = nestedPath + (fieldName == null? "": fieldName);

		String code = "invalid." + ruleName;
		if (message != null) {
			//xxRequestScopeMessageSource.setMessage(code + "." + field, message);
		}
		String fieldDesc = "";
		try {
			PropertyDescriptor propDesc = PropertyUtils.getPropertyDescriptor(bean, fieldName);
			Desc desc = propDesc.getReadMethod().getAnnotation(Desc.class);
			if (desc != null) {
				fieldDesc = desc.value();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> argumentModel = new HashMap<String, Object>();

        RuleInfo ruleInfo = new RuleInfo(ruleName, ruleAttributes);
        MessageModel model = new MessageModel();
        model.setRule(ruleInfo);
        FieldInfo fieldInfo = new FieldInfo();

        fieldInfo.setName(field);
        fieldInfo.setDesc(fieldDesc);
        fieldInfo.setValue(fieldValue);

        model.setField(fieldInfo);

		argumentModel.put("rule", ruleInfo);
		//mod by wujian on 13-6-27 for show the message begin  
		if(message!=null && !message.equals("")){
			errors.rejectValue(field, code, new Object[] {argumentModel}, message);
		}else{
			errors.rejectValue(field, code, new Object[] {argumentModel}, defaultMessage);
		}
		//mod by wujian on 13-6-27 end 
	}
}
