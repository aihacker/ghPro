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
        .ui-content {
            position: relative;
        }

        .ui-content:after {
            position: absolute;
            right: 10px;
            left: 10px;
            height: 1px;
            content: '';
            -webkit-transform: scaleY(.6);
            transform: scaleY(.6);
            background-color: #c8c7cc;
            top: -1px;
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
    <ul class="mui-table-view no">
        <li class="mui-table-view-cell">
            <a class="ui-flex">
                <span>活动名称</span>
                <small class="ui-right">${g.name}</small>
            </a>
        </li>
    </ul>
    <div class="ui-content">
        <h5 class="ui-title">活动时间</h5>
        <p>
            ${g.time}
        </p>
    </div>
    <div class="ui-content">
        <h5 class="ui-title">活动介绍</h5>
        <p>
            ${g.info}
        </p>
    </div>
    <div class="ui-content">
        <h5 class="ui-title">活动地点</h5>
        <p>
            ${g.address}
        </p>
    </div>

    <ul class="mui-table-view ui-margin-top-10 no">
        <li class="mui-table-view-cell no">
            <a class="ui-flex">
                <span>是否有积分</span>
                <small class="ui-right">${g.hasScore eq 1?'有积分':'无积分'}</small>
            </a>
        </li>
    </ul>

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
            var $self = $(this);
            ui.confirm('是否' + $.trim($self.text()) + '？', function () {
                var status = $self.data('status')
                wxgh.request.post('api/apply.json', {id: groupId, status: status}, function () {
                    ui.showToast('审核成功', function () {
                        window.location.reload(true);
                    })
                })
            });
        })
    })
</script>
</body>
</html>