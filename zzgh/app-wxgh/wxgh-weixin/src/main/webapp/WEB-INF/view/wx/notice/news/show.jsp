<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 蔡炳炎
  Date: 2017/8/28
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${detail.title}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>
<div class="mui-content">
    <div class="ui-card">
        <div class="ui-card-header">
            <h5 class="ui-title">${detail.title}</h5>
            <small class="ui-tip">${time}</small>
        </div>
        <div class="ui-card-content ui-html">
            <p>${detail.content}</p>
        </div>
        <c:if test="${!empty detail.link}">
            <div class="ui-card-footer">
                <a href="${detail.link}">阅读原文</a>
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
