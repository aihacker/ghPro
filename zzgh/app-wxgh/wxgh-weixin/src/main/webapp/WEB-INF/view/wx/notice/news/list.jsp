<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: cby
  Date: 2017/8/28
  Time: 11:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:choose>
        <c:when test="${type == 1}">企业新闻</c:when>
        <c:when test="${type == 2}">重点工作</c:when>
        <c:otherwise>工会活动</c:otherwise>
    </c:choose></title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
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
        <a class="a-list">
            <img class="mui-media-object mui-pull-left" src=${home}{{=it.path}}>
            {{=it.title}}
            <div class="mui-media-body">
                <p class="mui-ellipsis">{{=it.addTime}}</p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var status = '${param.type}'
        var refresh = window.refresh('#refreshContainer', {
            url: 'api/get_news.json',
            data: {status: status},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyHtml: '<div class="ui-empty">暂无新闻</div>',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['addTime'] = ui.timeAgo(d.addTime)
            d['image'] = d.image ? d.image : '/image/default/bbs.png'
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow('show.html?id=' + d.id)
            })
            return $item[0];
        }
    })
</script>
</body>
</html>
