package pub.spring.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.form.FormTag;
import org.springframework.web.servlet.tags.form.TagWriter;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-2-12
 * Time: 16:24:25
 */
public class ErrorsTag extends org.springframework.web.servlet.tags.form.ErrorsTag {

	private static final long serialVersionUID = 1L;

	public ErrorsTag() {
		setElement("label");
		setCssClass("error");
	}

	@Override
	protected void writeDefaultAttributes(TagWriter tagWriter) throws JspException {
//		super.writeDefaultAttributes(tagWriter);

		String forId = getPropertyPath();
		if ("".equals(forId)) {
			forId = (String) this.pageContext.getAttribute(
				FormTag.MODEL_ATTRIBUTE_VARIABLE_NAME, PageContext.REQUEST_SCOPE);
		}
		forId = StringUtils.deleteAny(forId, "[]");

		writeOptionalAttribute(tagWriter, "for", forId);
//////		writeOptionalAttribute(tagWriter, "name", getName());
		writeOptionalAttributes(tagWriter);
		tagWriter.writeAttribute("generated", "true");
	}

//	@Override
//	protected String autogenerateId() throws JspException {
////		return super.autogenerateId();
//		return StringUtils.deleteAny(getPropertyPath(), "[]");
//	}
}
