<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/26
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>活动列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .ui-item:first-child {
            margin: 0;
        }

        .ui-item {
            background-color: #fff;
            padding: 5px 15px;
            margin: 15px 0;
        }

        .ui-item .ui-img-div {
            height: 180px;
        }

        .ui-item .ui-info {
            padding-top: 5px;
        }

        .ui-item .ui-info p {
            margin: 0;
        }

        .ui-item .mui-card-header.mui-card-media .mui-media-body {
            font-size: 16px;
            color: #333;
        }

        .ui-item .mui-card-media img {
            border-radius: 50%;
            margin-right: 10px;
            position: relative;
            top: -6px;
        }

        .ui-item .ui-name {
            font-size: 15px;
            color: #333;
        }

        .ui-empty {
            background-color: #fff;
            padding: 10px 0;
        }
    </style>
</head>
<body>
<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <div id="mainDiv">
        </div>
    </div>
</div>

<div class="ui-fixed-btn right">
    <a href="add.html?id=${param.id}"><span class="fa fa-plus"
                                            style="line-height: 50px;font-size: 28px;"></span></a>
</div>

<script type="text/html" id="itemTpl">
    <div class="ui-item">
        <div class="mui-card-header mui-card-media">
            <img src="{{=it.avatar}}"/>
            <div class="mui-media-body">{{=it.username}}</div>
        </div>
        <div class="mui-card-content">
            <div class="ui-img-div">
                <img src="${home}{{=it.path}}"/></div>
            <div class="ui-info">
                <span class="ui-name">{{=it.name}}</span>
                <p><span class="fa fa-clock-o"></span>&nbsp;&nbsp;{{=it.timeStr}}</p>
                <p><span class="fa fa-map-marker"></span>&nbsp;&nbsp;{{=it.address}}</p>
            </div>
        </div>
    </div>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var groupId = '${param.id}'

        var refresh = window.refresh('#refreshContainer', {
            data: {groupId: groupId},
            url: 'api/act_list.json',
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            dataEl: '#mainDiv',
            emptyHtml: '<div class="ui-empty">暂无活动哦</div>',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['path'] = d.path ? d.path : '/image/default/act.png';
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow('show.html?id=' + d.id)
            })
            return $item[0]
        }
    })
</script>
</body>
</html>
