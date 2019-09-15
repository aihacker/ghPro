<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/18
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <title>总经理直通车</title>
    <jsp:include page="/comm/mobile/styles.jsp"></jsp:include>
    <style>
        .mui-table-view .mui-media-object {
            max-width: 100px;
            width: 100px;
            height: 80px;
        }

        .mui-media-body p:first-child {
            font-weight: 600;
            font-size: 15px;
            color: #000;
        }
    </style>
</head>

<body>

<div id="refreshContainer" class="mui-scroll-wrapper mui-content">
    <div class="mui-scroll">
        <ul class="mui-table-view">
        </ul>
    </div>
</div>
<script type="text/html" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <div class="ui-img-div mui-media-object mui-pull-left">
                <img src="{{=it.path}}" class="">
            </div>
            <div class="mui-media-body">
                <p class="mui-ellipsis-2">{{=it.theme}}</p>
                <p>{{=it.startTime}}&nbsp;&nbsp;开始</p>
                <p class="mui-ellipsis">{{=it.address}}</p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"></jsp:include>
<script type="text/javascript" src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var status = 1

        var ifno = {action: 'get'}
        if (status) {
            ifno['status'] = status
        }
        var refresh = window.refresh('#refreshContainer', {
            url: 'api/list_act.json',
            data: ifno,
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '暂无活动',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
            }
        })

        function bindfn(d) {
            d['path'] = wxgh.get_thumb(d, 'notice.png');
            d['startTime'] = new Date(d.startTime).format('yyyy-MM-dd hh:mm');
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow('act.html?id=' + d.id);
            })
            return $item[0];
        }
    })
</script>
</body>

</html>

