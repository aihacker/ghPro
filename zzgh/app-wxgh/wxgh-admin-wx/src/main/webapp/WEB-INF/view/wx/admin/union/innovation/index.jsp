<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/8
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>岗位创新审核中心</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>

<div class="mui-content">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a data-url="${home}/wx/admin/union/work/index.html" class="mui-navigate-right">
                工作坊管理中心
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a data-url="${home}/wx/admin/union/advice/index.html" class="mui-navigate-right">
                创新项目管理中心
            </a>
        </li>
    </ul>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    $(function () {
        $('.mui-table-view').on('tap', 'a[data-url]', function () {
            mui.openWindow($(this).data('url'))
        })
    })
</script>
</body>
</html>
