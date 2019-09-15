<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/30
  Time: 17:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>签到记录</title>
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
                {{=it.username}}
                <small class="mui-pull-right distan">{{=it.distan}}</small>
                <p>签到时间：{{=it.addTime}}</p>
                <p class="mui-ellipsis"><span class="fa fa-map-marker"></span> {{=it.address}}</p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var actId = '${param.id}';
    var dateid = '${param.dateid}';
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: 'list_api.json',
            data: {actId: actId, dateid: dateid},
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
            d['distan'] = d.distan ? get_distan(d.distan) : '未知'
            var $item = refresh.getItem(d)
            return $item[0]
        }

        function get_distan(distan) {
            if (distan > 1000) {
                distan = parseFloat(distan / 1000).toFixed(1) + 'km'
            } else {
                distan = distan + 'm';
            }
            return distan;
        }
    })
</script>
</body>
</html>