
<%@ page import="java.util.List" %>
<%@ page import="com.gpdi.mdata.sys.data.OpInfo" %>
<%@ page import="com.gpdi.mdata.web.app.utils.OperationUtils" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<OpInfo> ops = OperationUtils.list();
    request.setAttribute("_ops", ops);
%>
<c:forEach items="${_ops}" var="op">
    <button data-action="${op.action}" class="g-action-btn">
        <img src="${home}/style/icons/${op.icon}.png"/>${op.name}
    </button>
</c:forEach>