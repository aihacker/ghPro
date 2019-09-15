<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/21
  Time: 14:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${n.title}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>

    </style>
</head>
<body class="html">
<div class="mui-content">
    <div class="ui-card">
        <div class="ui-card-header">
            <h5 class="ui-title">${n.title}</h5>
            <small class="ui-tip">${n.author} ${n.timeStr}</small>
        </div>
        <div class="ui-card-content ui-html">
            <p>${n.content}</p>
        </div>
        <c:if test="${!empty n.link}">
            <div class="ui-card-footer">
                <a href="${n.link}">阅读原文</a>
            </div>
        </c:if>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    $(function () {

    })
</script>
</body>
</html>