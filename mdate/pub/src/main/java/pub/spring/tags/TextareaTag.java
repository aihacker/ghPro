package pub.spring.tags;

import javax.servlet.jsp.JspException;

import org.springframework.util.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-8-20
 * Time: 10:27:11
 */
public class TextareaTag extends org.springframework.web.servlet.tags.form.TextareaTag {

	private ErrorsTag errorsTag;

	@Override
	protected String getName() throws JspException {
		//return super.getName();
		return getPropertyPath();
	}

	@Override
	protected String autogenerateId() throws JspException {
		//return super.autogenerateId();
		return StringUtils.deleteAny(getPropertyPath(), "[]");
	}

}
