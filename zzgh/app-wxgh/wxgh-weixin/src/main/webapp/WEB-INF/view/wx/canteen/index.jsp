<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/20
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的饭堂</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view .mui-media-object {
            width: 60px;
            height: 60px;
            max-width: 60px;
            -webkit-border-radius: 50%;
            border-radius: 50%;
            border: 1px solid gainsboro;
            padding: 1px;
        }

        .mui-media-body {
            margin-top: 5px;
        }

        .mui-media-body p {
            margin-top: 10px;
        }

        .mui-table-view:before {
            height: 0;
        }
    </style>
</head>
<body>
<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view">
        </ul>
    </div>
</div>

<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <img class="mui-media-object mui-pull-left" src="${home}{{=it.avatar}}"/>
            <div class="mui-media-body">
                <div class="mui-ellipsis">{{=it.name}}</div>
                <p class="mui-ellipsis">{{=it.info}}</p>
            </div>
        </a>
    <li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            data: {status: 1},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '你未加入任何饭堂',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['info'] = d.info ? d.info : '暂无简介'
            d['avatar'] = d.path ? d.path : '/image/default/chat.png'
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow(homePath + '/wx/canteen/show.html?id=' + d.id + '&type=1')
            })
            return $item[0];
        }
    })
</script>
</body>
</html>
