<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/31
  Time: 9:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>活动成果</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view .mui-media-object {
            line-height: 54px;
            max-width: 54px;
            height: 54px;
        }

        .distan {
            color: #777;
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

<script type="text/html" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <img class="mui-media-object ui-circle mui-pull-left" src="{{=it.avatar}}">
            <div class="mui-media-body">
                {{=it.title}}
                <small class="mui-pull-right distan">{{=it.addTime}}</small>
                <p>发布人：{{=it.username}}</p>
                <p class="mui-ellipsis">{{=it.info}}</p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var actId = '${param.id}';
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: 'api_list.json',
            data: {actId: actId},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['avatar'] = wxgh.get_avatar(d.avatar);
            d['addTime'] = ui.timeAgo(d.addTime);
            var $item = refresh.getItem(d)

            $item.on('tap', function () {
                mui.openWindow('show.html?id=' + d.id);
            })
            return $item[0]
        }
    })
</script>
</body>
</html>