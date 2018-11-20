package pub.spring.web.mvc.bind;

import org.springframework.web.bind.support.DefaultSessionAttributeStore;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import pub.functions.VarFuncs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-2-17
 * Time: 15:46:29
 */
@SuppressWarnings("unchecked")
public class XDataSessionAttributeStore extends DefaultSessionAttributeStore {

    public XDataSessionAttributeStore() {
        setAttributeNamePrefix("xData_");
    }

    @Override
    public void setAttributeNamePrefix(String attributeNamePrefix) {
        super.setAttributeNamePrefix(attributeNamePrefix);
    }

    @Override
    public void storeAttribute(WebRequest request, String attributeName, Object attributeValue) {
        request.setAttribute(attributeName, attributeValue, RequestAttributes.SCOPE_REQUEST);
        String storeAttributeName = getAttributeNameInSession(request, attributeName);
        Map<String, Object> xDataMap = (Map<String, Object>) request.getAttribute("xDataMap", RequestAttributes.SCOPE_REQUEST);
        if (xDataMap == null) {
            xDataMap = new HashMap<String, Object>();
            request.setAttribute("xDataMap", xDataMap, RequestAttributes.SCOPE_REQUEST);
        }
        xDataMap.put(storeAttributeName, attributeValue);
//		super.storeAttribute(request, attributeName, attributeValue);
    }

    @Override
    public Object retrieveAttribute(WebRequest request, String attributeName) {
        Object result = request.getAttribute(attributeName, RequestAttributes.SCOPE_REQUEST);
        if (result != null) {
            return result;
        }
        String storeAttributeName = getAttributeNameInSession(request, attributeName);
        String xData = request.getParameter(storeAttributeName);
        if (xData == null) {
            return null;
        }
        Object attributeValue = VarFuncs.deserializeXData(xData);
        request.setAttribute(attributeName, attributeValue, RequestAttributes.SCOPE_REQUEST);
        return attributeValue;
//		return super.retrieveAttribute(request, attributeName);

    }

    @Override
    public void cleanupAttribute(WebRequest request, String attributeName) {
//		super.cleanupAttribute(request, attributeName);
    }

    @Override
    protected String getAttributeNameInSession(WebRequest request, String attributeName) {
        return super.getAttributeNameInSession(request, attributeName);
    }
}
