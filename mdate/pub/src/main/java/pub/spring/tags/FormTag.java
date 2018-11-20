package pub.spring.tags;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.TagWriter;

import pub.functions.VarFuncs;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-2-18
 * Time: 19:43:32
 */
@SuppressWarnings("unchecked")
public class FormTag extends org.springframework.web.servlet.tags.form.FormTag {

    private static final long serialVersionUID = 1L;

    private Boolean outputXDatas = true;
    private Boolean showErrors = false;

    protected boolean isOutputXDatas() {
        if (this.outputXDatas != null) {
            return this.outputXDatas;
        } else {
            return true;
        }
    }

    public void setOutputXDatas(Boolean outputXDatas) throws JspException {
        this.outputXDatas = outputXDatas;
    }

    protected boolean isShowErrors() {
        return this.showErrors != null && this.showErrors == true;
    }

    public void setShowErrors(Boolean showErrors) throws JspException {
        this.showErrors = showErrors;
    }

    @Override
    protected int writeTagContent(TagWriter tagWriter) throws JspException {
        int result = super.writeTagContent(tagWriter);
        if (isOutputXDatas()) {
            _outputXDatas(tagWriter);
        }
        pageContext.setAttribute("_FORM_TAG_SHOW_ERRORS", isShowErrors());
        return result;
    }

    @Override
    protected String resolveAction() throws JspException {
        String action = super.resolveAction();
        ServletRequest request = this.pageContext.getRequest();
        Object pageKey = request.getAttribute("pageKey");
        if (pageKey == null || action.indexOf("pageKey=") != -1) {
            return action;
        }
        if (action.indexOf('?') == -1) {
            action += '?';
        } else {
            action += '&';
        }
        action += "pageKey=" + pageKey;
        return action;
    }

    private void _outputXDatas(TagWriter tw) throws JspException {
        Map<String, Object> xDataMap = (Map<String, Object>) this.pageContext.getRequest().getAttribute("xDataMap");
        if (xDataMap == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : xDataMap.entrySet()) {
            tw.startTag("input");
            tw.writeAttribute("type", "hidden");
            tw.writeAttribute("name", entry.getKey());
            String xData = VarFuncs.serializeToXData((Serializable) entry.getValue());
            tw.writeAttribute("value", xData);
            tw.endTag();
        }
    }
}
