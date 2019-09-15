<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/30
  Time: 19:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${law.name}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>

<body class="html">
<%--<header class="mui-bar mui-bar-nav ui-head">--%>
<%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

<%--<h1 class="mui-title">${law.name}</h1>--%>
<%--</header>--%>

<div class="ui-html">
    ${law.content}
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
</body>

</html>
