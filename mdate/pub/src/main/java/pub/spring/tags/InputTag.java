package pub.spring.tags;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.tags.form.TagWriter;

import javax.servlet.jsp.JspException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-2-25
 * Time: 11:06:53
 */
public class InputTag extends org.springframework.web.servlet.tags.form.InputTag {

    private static final long serialVersionUID = 1L;

    private ErrorsTag errorsTag;

    private Object format;

    public Object getFormat() {
        return format;
    }

    public void setFormat(Object format) {
        this.format = format;
    }

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

    private boolean hasDynamicTypeAttribute() {
        return getDynamicAttributes() != null && getDynamicAttributes().containsKey("type");
    }

    @Override
    protected void writeValue(TagWriter tagWriter) throws JspException {
        Object boundValue = getBoundValue();

        Format fmt = null;
        if (boundValue instanceof String || boundValue == null) {
            // nothing to do
        } else if (format == null) {
            if (boundValue instanceof Number) {
                DecimalFormat numberFmt = new DecimalFormat();
                numberFmt.setMinimumFractionDigits(0);
                numberFmt.setGroupingUsed(false);
                fmt = numberFmt;
            }
        } else if (format instanceof Format) {
            fmt = (Format) format;
        } else if (format instanceof String) {
            if (boundValue instanceof Date) {
                fmt = new SimpleDateFormat((String) format);
            } else if (boundValue instanceof Number) {
                DecimalFormat numberFmt = new DecimalFormat((String) format);
                fmt = numberFmt;
            } else {
                //???
            }
        }
        if (fmt == null) {
            boundValue = getBoundValue();
        } else {
            boundValue = fmt.format(boundValue);
        }

        String value = getDisplayString(boundValue, getPropertyEditor());
        String type = hasDynamicTypeAttribute() ? (String) getDynamicAttributes().get("type") : getType();
        tagWriter.writeAttribute("value", processFieldValue(getName(), value, type));
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
            } finally {
                errorsTag.doFinally();
            }
        }
        //
        return result;
    }
}
