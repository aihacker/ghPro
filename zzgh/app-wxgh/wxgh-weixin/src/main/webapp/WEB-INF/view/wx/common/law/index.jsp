<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/30
  Time: 19:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>法律法规</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--&lt;%&ndash;<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>&ndash;%&gt;--%>

    <%--<h1 class="mui-title">法律法规</h1>--%>
<%--</header>--%>

<div class="mui-content">

    <c:choose>
        <c:when test="${empty laws}">
            <div class="mui-content-padded">暂未发布法律法规哦</div>
        </c:when>
        <c:otherwise>
            <ul class="mui-table-view">
                <c:forEach items="${laws}" var="l">
                    <li class="mui-table-view-cell">
                        <a href="${home}/wx/common/law/detail.html?id=${l.id}" class="mui-navigate-right">
                            《${l.name}》
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
</body>

</html>
