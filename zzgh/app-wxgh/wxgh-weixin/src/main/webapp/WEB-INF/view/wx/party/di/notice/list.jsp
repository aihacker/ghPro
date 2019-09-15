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
    <title>公告列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .ui-empty {
            background-color: #fff;
            padding: 10px 0;
        }
    </style>
</head>
<body>
<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <div id="noticeList">
        </div>
    </div>
</div>
<script type="text/template" id="itemTpl">
    <div class="ui-card">
        <a href="javascript:;">
            <div class="ui-card-header">
                <h5 class="ui-title">{{=it.title}}</h5>
                <small class="ui-tip">{{=it.addTime}}</small>
            </div>
            <div class="ui-card-content">
                <p class="mui-ellipsis-2">{{=it.content}}</p>
            </div>
        </a>
    </div>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var groupId = '${param.id}';
    $(function () {
        var refresh = window.refresh('#pullrefresh', {
            url: 'api/list.json',
            data: {groupId: groupId},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            dataEl: '#noticeList',
            emptyHtml: '<div class="ui-empty">暂无公告活动</div>',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['addTime'] = ui.timeAgo(d.addTime)
            d['content'] = $(d.content).text()
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