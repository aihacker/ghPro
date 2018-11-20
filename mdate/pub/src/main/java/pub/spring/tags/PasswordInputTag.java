package pub.spring.tags;

import org.springframework.util.StringUtils;

import javax.servlet.jsp.JspException;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-2-25
 * Time: 11:06:53
 */
public class PasswordInputTag extends org.springframework.web.servlet.tags.form.PasswordInputTag {

    private static final long serialVersionUID = 1L;

    private ErrorsTag errorsTag;

    @Override
    protected String getName() throws JspException {
        return getPropertyPath();
    }

    @Override
    protected String autogenerateId() throws JspException {
        //return super.autogenerateId();
        return StringUtils.deleteAny(getPropertyPath(), "[]");
    }

    private boolean hasDynamicTypeAttribute() {
        return getDynamicAttributes() != null && getDynamicAttributes().containsKey("type");
    }

    @Override
    public int doEndTag() throws JspException {
        int result = super.doEndTag();

        if (Boolean.TRUE.equals(pageContext.getAttribute("_FORM_TAG_SHOW_ERRORS"))) {
            if (errorsTag == null) {
                errorsTag = new ErrorsTag();
            }
            errorsTag.setPageContext(this.pageContext);
            errorsTag.setParent(getParent());
            errorsTag.setPath(getPath());
            try {
                errorsTag.doStartTag();
                errorsTag.doEndTag();
            }
            finally {
                errorsTag.doFinally();
            }
        }
        //
        return result;
    }
}
