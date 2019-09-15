<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/8
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>提案详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>

    </style>
</head>
<body>
<div class="ui-fixed-bottom ui-flex">
    <button data-status="2" type="button" class="mui-btn mui-btn-danger ui-flex-1${s.status eq 2?' mui-disabled':''}">
        ${s.status eq 2?'已不通过':'不通过'}
    </button>
    <button data-status="1" type="button" class="mui-btn mui-btn-primary ui-flex-2${s.status eq 1?' mui-disabled':''}">
        ${s.status eq 1?'已通过':'通过'}
    </button>
</div>
<div class="mui-content">
    <div class="ui-content">
        <h5 class="ui-title">提案标题</h5>
        <p>
            ${s.title}
        </p>
    </div>
    <div class="ui-content">
        <h5 class="ui-title">提案内容</h5>
        <p>
            ${s.content}
        </p>
    </div>

    <ul class="mui-table-view ui-margin-top-10 no">
        <li class="mui-table-view-cell no">
            <a class="ui-flex">
                <span>申请时间</span>
                <small class="ui-right"><fmt:formatDate value="${s.createTime}" pattern="yyyy年MM月dd日 HH:mm"/></small>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a href="${home}/wx/pub/user/show.html?userid=${s.userid}" class="mui-navigate-right ui-flex">
                <span>申请人</span>
                <small class="ui-right">${s.username}</small>
            </a>
        </li>
    </ul>
    <ul class="mui-table-view ui-margin-top-10 no">
        <li class="mui-table-view-cell no">
            <a class="ui-flex">
                <span>审核状态</span>
                <small class="ui-right">${s.status eq 0?'未审核':(s.status eq 1 ? '已通过':'未通过')}</small>
            </a>
        </li>
    </ul>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    var suggestId = '${s.id}';
    $(function () {
        $('.ui-fixed-bottom').on('tap', 'button', function () {
            var status = $(this).data('status')
            wxgh.request.post('api/apply.json', {id: suggestId, status: status}, function () {
                ui.showToast('审核成功', function () {
                    window.location.reload()
                })
            })
        })
    })
</script>
</body>
</html>