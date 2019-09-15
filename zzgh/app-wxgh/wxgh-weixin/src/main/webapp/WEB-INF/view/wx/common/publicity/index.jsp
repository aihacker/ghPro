<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/30
  Time: 20:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title><c:choose>
        <c:when test="${empty param.type}">
            宣传
        </c:when>
        <c:otherwise>
            公告列表
        </c:otherwise>
    </c:choose></title>

    <jsp:include page="/comm/mobile/styles.jsp"/>

</head>

<body>

<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view">
        </ul>
    </div>
</div>

<script type="text/html" id="itemTpl">
    <li class="mui-table-view-cell">
        <a href="javascript:;">
            <div class="mui-media-body">
                <span class="mui-ellipsis-2" style="font-weight: bold;color: black">
                    {{? it.top == 1}}
                    <span style="color:red;">【<span style="color:red;font-weight: bold">顶</span>】</span>
                    {{?}}
                    {{=it.title}}
                </span>
                <p class="mui-ellipsis">
                    发布时间：{{=it.addTime}}
                </p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
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
            d['addTime'] = ui.timeAgo(d.addTime, true);
            if (d.type != 'url') {
                d['content'] = 'info.html?id=' + d.id;
            }
            var $item = refresh.getItem(d)

            $item.on('tap', function () {
                mui.openWindow(d.content);
            })

            return $item[0]
        }
    })
</script>
</body>

</html>
