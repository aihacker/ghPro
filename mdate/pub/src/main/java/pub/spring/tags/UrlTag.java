package pub.spring.tags;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.springframework.web.servlet.tags.Param;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-3-7
 * Time: 4:18:33
 */
public class UrlTag extends org.springframework.web.servlet.tags.UrlTag {

	private static final long serialVersionUID = 1L;

	@Override
	protected String createQueryString(List<Param> params, Set<String> usedParams, boolean includeQueryStringDelimiter) throws
																														JspException {
		String qs = super.createQueryString(params, usedParams, includeQueryStringDelimiter);

		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		Object pageKey = request.getAttribute("pageKey");
		if (pageKey != null) {
			if (includeQueryStringDelimiter && qs.length() == 0) {
				qs += "?";
			}
			else {
				qs += "&";
			}
			qs += "pageKey=" + pageKey;
		}

		return qs;
	}
}
