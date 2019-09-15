<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/8
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>通知设置</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>

    </style>
</head>
<body>
<div class="mui-content">
    <ul class="mui-table-view ui-margin-top-10">
        <li id="sportLi" class="mui-table-view-cell">
            <a class="mui-navigate-right ui-flex">
                <span>工会运动通知</span>
                <small class="ui-right">${status eq 1?'通知':'不通知'}</small>
            </a>
        </li>
    </ul>
</div>

<div id="sheet1" class="mui-popover mui-popover-bottom mui-popover-action">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a data-status="1" href="javascript:;">通知</a>
        </li>
        <li class="mui-table-view-cell">
            <a data-status="0" href="javascript:;">不通知</a>
        </li>
    </ul>
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a style="color: #FF3B30;" href="#sheet1"><b>取消</b></a>
        </li>
    </ul>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    $(function () {
        $('#sportLi').on('tap', function () {
            mui('#sheet1').popover('show');
        });

        $('#sheet1').on('tap', 'a[data-status]', function () {
            var $self = $(this),
                status = $self.data("status");
            wxgh.request.get('api/edit_sport.json', {status: status}, function () {
                ui.showToast('修改成功', function () {
                    window.location.reload();
                });
            });
        });
    })
</script>
</body>
</html>