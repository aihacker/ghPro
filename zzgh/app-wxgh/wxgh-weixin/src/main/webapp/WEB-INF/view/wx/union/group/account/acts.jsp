<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/16
  Time: 9:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>积分结算</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>

    </style>
</head>
<body>
<div id="refreshContainer" class="mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view"></ul>
    </div>
</div>
<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell">
        <div class="mui-slider-right mui-disabled">
            <a class="mui-btn mui-btn-red">不结算</a>
        </div>
        <div class="mui-slider-handle">
            <img class="mui-media-object mui-pull-left ui-circle" src="{{=it.path}}">
            <div class="mui-media-body">
                {{=it.info}}
                <p class="mui-ellipsis">{{=it.name}}</p>
            </div>
        </div>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var groupId = '${param.id}';
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: 'list_act.json',
            data: {groupId: groupId},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '暂无未结算活动',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['path'] = wxgh.get_thumb(d)
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow(homePath + '/wx/union/group/account.html?id=' + d.id)
            })
            return $item[0]
        }
    })
</script>
</body>
</html>