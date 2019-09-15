<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/18
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

<head>
    <title>报名列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"></jsp:include>
    <style>

    </style>
</head>

<body>

<div id="refreshContainer" class="mui-scroll-wrapper mui-content">
    <div class="mui-scroll">
        <ul class="mui-table-view">
        </ul>
    </div>
</div>

<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <img class="mui-media-object ui-circle mui-pull-left" src="{{=it.avatar}}">
            <div class="mui-media-body">
                {{=it.name}}
                <p class="mui-ellipsis">{{=it.deptname}}</p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var actId = '${param.id}';

    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: '../commit/api/join_list.json',
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
            d['avatar'] = wxgh.get_avatar(d.avatar)
            var $item = refresh.getItem(d)

            return $item[0]
        }
    });
</script>
</body>

</html>

