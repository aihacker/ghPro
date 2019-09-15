<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/25
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>活动介绍详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>

    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-content">
        <h5 class="ui-title">活动介绍</h5>
        <p>${info}</p>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    $(function () {

    })
</script>
</body>
</html>