<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/6
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>参赛队伍</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>
<div class="mui-content">
    <ul class="mui-table-view">
        <c:choose>
            <c:when test="${empty stages}">
                <li class="mui-table-view-cell">
                    <div class="mui-content-padded mui-text-center ui-text-info">
                        暂无参赛队伍哦
                    </div>
                </li>
            </c:when>
            <c:otherwise>
                <c:forEach items="${stages}" var="s">
                    <li class="mui-table-view-cell">
                        <a href="${home}/wx/tv/team_info.html?eventId=${param.id}&id=${s.id}" class="mui-navigate-right">
                                ${s.name}
                        </a>
                    </li>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </ul>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
</body>
</html>
