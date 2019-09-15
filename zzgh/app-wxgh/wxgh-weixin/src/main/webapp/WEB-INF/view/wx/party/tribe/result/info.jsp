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
    <title>成果详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"></jsp:include>
    <style>
        .mui-content-padded, .mui-content, body {
            background-color: #fff;
        }
    </style>
</head>

<body class="ui-html">

<div class="mui-content">
    ${info.content}
</div>

<jsp:include page="/comm/mobile/scripts.jsp"></jsp:include>
</body>

</html>
