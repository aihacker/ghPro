<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/21
  Time: 14:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的群体</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>

    </style>
</head>
<body>
<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view"></ul>
    </div>
</div>
<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <img class="mui-media-object ui-circle mui-pull-left"
                 src="{{=it.path}}">
            <div class="mui-media-body">
                {{=it.name}}
                <p class="mui-ellipsis">{{=it.info}}</p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var refresh = window.refresh('#pullrefresh', {
            url: 'api/list.json',
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '您未加入任何群体哦',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['info'] = d.info ? d.info : '暂无简介';
            d['path'] = d.path ? d.path : (homePath + '/image/default/chat.png')
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow(homePath + '/wx/chat/chat.html?id=' + d.id);
            })
            return $item[0]
        }
    })
</script>
</body>
</html>