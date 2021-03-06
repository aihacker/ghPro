<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/25
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>课堂报名情况</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
    </style>
</head>
<body>
<div class="mui-content">
    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view user-list">
            </ul>
        </div>
    </div>
</div>
<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <img class="mui-media-object mui-pull-left" src="{{=it.avatar}}">
            <div class="mui-media-body">
                {{=it.username}}
                <p class="mui-ellipsis">{{=it.deptname}}</p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var teachId = '${param.id}';

    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: 'api/join_list.json',
            data: {id: teachId},
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
            d['avatar'] = wxgh.get_avatar(d.avatar)
            var $item = refresh.getItem(d)
            return $item[0]
        }
    })
</script>
</body>
</html>