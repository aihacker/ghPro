<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/6
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>赛事列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>

<div class="mui-content">
    <ul class="mui-table-view">
        <c:choose>
            <c:when test="${empty events}">
                <li class="mui-table-view-cell">
                    <div class="mui-content-padded mui-text-center ui-text-info">
                        暂无赛事哦
                    </div>
                </li>
            </c:when>
            <c:otherwise>
                <c:forEach items="${events}" var="e">
                    <li class="mui-table-view-cell">
                        <a href="show.html?id=${e.id}" class="mui-navigate-right">
                                ${e.name}
                            <small>（${e.simpleName}）</small>
                            - ${e.typeText}
                            <p>创建时间：${e.timeStr}</p>
                            <p>
                                    ${empty e.remark?'暂无简介':e.remark}
                            </p>
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
