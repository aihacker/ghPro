package pub.spring.tags;

import java.io.Serializable;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.form.AbstractFormTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import pub.functions.VarFuncs;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-2-17
 * Time: 17:32:07
 */
@SuppressWarnings("unchecked")
public class XDatasTag extends AbstractFormTag {

	@Override
	protected int writeTagContent(TagWriter tagWriter) throws JspException {
		TagWriter tw = tagWriter;
		Map<String, Object> xDataMap = (Map<String, Object>) this.pageContext.getRequest().getAttribute("xDataMap");
		if (xDataMap == null) {
			return 0;
		}
		for(Map.Entry<String, Object> entry: xDataMap.entrySet()) {
			tw.startTag("input");
			tw.writeAttribute("type", "hidden");
			tw.writeAttribute("name", entry.getKey());
			String xData = VarFuncs.serializeToXData((Serializable) entry.getValue());
			tw.writeAttribute("value", xData);
			tw.endTag();
		}
		return 0;
	}
}
