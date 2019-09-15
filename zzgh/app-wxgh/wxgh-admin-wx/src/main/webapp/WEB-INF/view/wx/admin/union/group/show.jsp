<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/8
  Time: 9:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>${g.name}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .head {
            text-align: center;
            padding: 5px 0;
            margin-top: 10px;
        }

        .head img {
            width: 60px;
            height: 60px;
            border: 1px solid #fff;
        }
    </style>
</head>
<body>
<div class="ui-fixed-bottom ui-flex">
    <button data-status="2" type="button" class="mui-btn mui-btn-danger ui-flex-1${g.status eq 2?' mui-disabled':''}">
        ${g.status eq 2?'已不通过':'不通过'}
    </button>
    <button data-status="1" type="button" class="mui-btn mui-btn-primary ui-flex-2${g.status eq 1?' mui-disabled':''}">
        ${g.status eq 1?'已通过':'通过'}
    </button>
</div>
<div class="mui-content">
    <div class="head">
        <img class="ui-circe" src="${home}${g.path}"/>
    </div>
    <ul class="mui-table-view">
        <li class="mui-table-view-cell no">
            <a class="ui-flex">
                <span>协会名称</span>
                <small class="ui-right">${g.name}</small>
            </a>
        </li>
    </ul>
    <div class="ui-content">
        <h5 class="ui-title">活动介绍</h5>
        <p>
            ${g.info}
        </p>
    </div>
    <ul class="mui-table-view ui-margin-top-10 no">
        <li class="mui-table-view-cell no">
            <a class="ui-flex">
                <span>申请时间</span>
                <small class="ui-right"><fmt:formatDate value="${g.addTime}" pattern="yyyy年MM月dd日 HH:mm"/></small>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a href="${home}/wx/pub/user/show.html?userid=${g.userid}" class="mui-navigate-right ui-flex">
                <span>申请人</span>
                <small class="ui-right">${g.username}</small>
            </a>
        </li>
    </ul>
    <ul class="mui-table-view ui-margin-top-10 no">
        <li class="mui-table-view-cell no">
            <a class="ui-flex">
                <span>审核状态</span>
                <small class="ui-right">${g.status eq 0?'未审核':(g.status eq 1 ? '已通过':'未通过')}</small>
            </a>
        </li>
    </ul>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    var groupId = '${g.id}';
    $(function () {
        $('.ui-fixed-bottom').on('tap', 'button', function () {
            var status = $(this).data('status')
            wxgh.request.post('/wx/admin/union/group/api/apply.json', {id: groupId, status: status}, function () {
                ui.showToast('审核成功', function () {
                    window.location.reload()
                })
            })
        })
    })
</script>
</body>
</html>