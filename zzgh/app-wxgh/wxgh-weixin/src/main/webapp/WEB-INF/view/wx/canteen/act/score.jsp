<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/25
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>活动积分详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>

    </style>
</head>
<body>
<div class="mui-content">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell no">
            <a class="ui-flex">
                <span>参加积分</span>
                <small class="ui-right">${rule.score} 分</small>
            </a>
        </li>
        <li class="mui-table-view-cell no">
            <a class="ui-flex">
                <span>请假积分</span>
                <small class="ui-right">${rule.leaveScore} 分</small>
            </a>
        </li>
        <li class="mui-table-view-cell no">
            <a class="ui-flex">
                <span>缺席积分</span>
                <small class="ui-right">${rule.outScore} 分</small>
            </a>
        </li>
    </ul>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    $(function () {

    })
</script>
</body>
</html>