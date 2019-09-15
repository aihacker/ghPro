<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/2
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>
        <c:if test="${param.status eq 1}">
            比赛进行中
        </c:if>
        <c:if test="${param.status eq 2}">
            历史赛事
        </c:if>
        <c:if test="${empty param.status}">
            我要报名
        </c:if>
    </title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body,
        .ui-race-list {
            background-color: #efeff4;
        }

        .ui-race-list.mui-table-view:after,
        .ui-race-list.mui-table-view:before {
            height: 0;
        }

        .ui-race-list .mui-table-view-cell:first-child {
            margin: 0;
        }

        .ui-race-list .mui-table-view-cell {
            margin: 8px 0;
            background-color: #fff;
        }

        .ui-race-list .mui-table-view-cell:after {
            height: 0;
        }

        .ui-race-list .ui-img-div {
            max-width: 100px;
            width: 90px;
            height: 70px;
            border: 1px solid #ddd;
            padding: 1px;
        }

        .ui-race-list .mui-media-body {
            min-height: 80px;
        }

        .ui-race-list .ui-bottom {
            border-top: 1px solid #e2e2e2;
        }

        .ui-race-list .ui-bottom .fa-clock-o {
            color: #0099e9;
        }
    </style>
</head>

<body>

<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view ui-race-list">
        </ul>
    </div>
</div>

<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <div class="ui-img-div mui-pull-left mui-media-object"><img class="" src="{{=it.img}}"></div>
            <div class="mui-media-body">
                <span class="mui-ellipsis">{{=it.name}}</span>
                <p class="mui-ellipsis-2">{{=it.info}}</p>
            </div>
            <div class="ui-bottom">
                <span class="fa fa-clock-o"></span>
                <small class="ui-time">{{=it.timeStr}}</small>
            </div>
        </a>
    <li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script type="text/javascript">

    var status = '${param.status}'

    var info = {action: 'list'}
    if (status && status == 1) {
        info['ingIs'] = true //进行中
    } else if (status && status == 2) {
        info['endIs'] = true //已结束
    } else {
        info['baoming'] = true //报名中
    }

    var refresh = window.refresh('#refreshContainer', {
        url: homePath + '/wx/union/race/api/list.json',
        data: info,
        ispage: true,
        isBind: true,
        tplId: '#itemTpl',
        emptyText: '暂无比赛',
        bindFn: bindfn,
        errorFn: function (type) {
            console.log(type)
            ui.alert('获取失败...')
        }
    })

    function bindfn(d) {
        var img = d.img ? wxgh.get_image(d.img) : homePath + '/image/common/nopic.gif';
        d['img'] = img;
        d['timeStr'] = new Date(d.startTime).format('yyyy-MM-dd hh:mm') + ' 至 ' + new Date(d.endTime).format('yyyy-MM-dd hh:mm');
        var $item = refresh.getItem(d);
        $item.on('tap', function () {
            mui.openWindow(homePath + '/wx/union/race/show.html?id=' + d.id)
        });
        return $item[0];
    }
</script>
</body>

</html>
