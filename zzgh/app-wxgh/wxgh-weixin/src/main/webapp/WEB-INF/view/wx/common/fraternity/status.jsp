<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/27
  Time: 17:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>互助会入会申请</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>

<body>
<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--&lt;%&ndash;<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>&ndash;%&gt;--%>

    <%--<h1 class="mui-title">互助会入会申请</h1>--%>
<%--</header>--%>
<div class="mui-content">
    <div class="mui-content-padded mui-text-center">
        当前申请状态状态为：${msg}
    </div>
</div>


<jsp:include page="/comm/mobile/scripts.jsp"/>

</body>

</html>
