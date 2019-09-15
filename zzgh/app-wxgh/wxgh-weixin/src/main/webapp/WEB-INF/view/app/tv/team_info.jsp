<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/30
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>参赛成员</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>
<div class="mui-content">
    <ul class="mui-table-view mui-grid-view mui-grid-9">
        <c:choose>
            <c:when test="${empty teams}">
                <li class="mui-table-view-cell mui-media">
                    <div class="ui-text-info mui-text-center">暂无队员信息</div>
                </li>
            </c:when>
            <c:otherwise>
                <c:forEach items="${teams}" var="t">
                    <li class="mui-table-view-cell mui-media mui-col-xs-6 mui-col-sm-6">
                        <a href="javascript:;">
                            <c:if test="${empty t.sex}">
                                <small class="ui-text-info">未知性别</small>
                            </c:if>
                            <c:if test="${t.sex eq 1}">
                                <span class="fa fa-male ui-text-primary"></span>
                            </c:if>
                            <c:if test="${t.sex eq 0}">
                                <span class="fa fa-female ui-text-danger"></span>
                            </c:if>
                            &nbsp;${empty t.name?'未知姓名':t.name}
                            <p>年龄：${empty t.age?'未知':t.age}</p>
                            <c:if test="${t.type != '领队'}">
                                <p>${empty t.phone?'未知':t.phone}</p>
                            </c:if>
                            <p>单位：${empty t.unit?'未知':t.unit}</p>
                            <p>身份：${t.type}</p>
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
