<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/6
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>赛事列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>

<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-status="1" class="mui-control-item mui-active">羽毛球
            <small></small>
        </a>
        <a data-status="2" class="mui-control-item">乒乓球
            <small></small>
        </a>
        <a data-status="3" class="mui-control-item">篮球
            <small></small>
        </a>
        <a data-status="4" class="mui-control-item">足球
            <small></small>
        </a>
    </div>

    <div id="refreshContainer">
    </div>

    <%--<ul class="mui-table-view">--%>
        <%--<c:choose>--%>
            <%--<c:when test1="${empty events}">--%>
                <%--<li class="mui-table-view-cell">--%>
                    <%--<div class="mui-content-padded mui-text-center ui-text-info">--%>
                        <%--暂无赛事哦--%>
                    <%--</div>--%>
                <%--</li>--%>
            <%--</c:when>--%>
            <%--<c:otherwise>--%>
                <%--<c:forEach items="${events}" var="e">--%>
                    <%--<li class="mui-table-view-cell">--%>
                        <%--<a href="show.html?id=${e.id}" class="mui-navigate-right">--%>
                                <%--${e.name}--%>
                            <%--<small>（${e.simpleName}）</small>--%>
                            <%--- ${e.typeText}--%>
                            <%--<p>创建时间：${e.timeStr}</p>--%>
                            <%--<p>--%>
                                    <%--${empty e.remark?'暂无简介':e.remark}--%>
                            <%--</p>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                <%--</c:forEach>--%>
            <%--</c:otherwise>--%>
        <%--</c:choose>--%>
    <%--</ul>--%>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    $(function () {
        var $segment = $('#segmentedControl')
        $segment.data('status', 1)
        $segment.on('tap', 'a.mui-control-item', function () {
            var status = $(this).attr('data-status')
            getjson(status);
        })
        getjson(1);

        function getjson(status) {
            mui.getJSON("all_event.json", {status:status}, function (res) {
                if (res.ok) {
                    var result = res.data;
                    if(result.length>0){
                        var $rec = $('#refreshContainer');
                        $rec.html('')
                        var ehtml = '<ul class="mui-table-view">';
                        debugger
                        for(var i = 0;i < result.length; i++) {
                            ehtml += '<li class="mui-table-view-cell">' +
                                '<a href="show.html?id=' + result[i].id + '&species=' + status +'" class="mui-navigate-right">' +
                                result[i].name;
                            if(status == 1 || status ==2 ){
                                ehtml += '<small>' + result[i].simple_name + '</small> - ' + result[i].type_text;
                            }
                            ehtml += '<p>创建时间：' + result[i].timeStr + '</p>' +
                                '<p>' + result[i].remark +
                                '</p>' +
                                '</a>' +
                                '</li>';
                            debugger
                        }
                        ehtml += '</ul>';
                        $rec.append(ehtml);
                    } else {
                        var $rec = $('#refreshContainer');
                        $rec.html('<ul class="mui-table-view">' +
                            '<li class="mui-table-view-cell">' +
                            '<div class="mui-content-padded mui-text-center ui-text-info">暂无赛事哦</div>' +
                            '</li>' +
                            '</ul>')
                    }
                } else {
                    alert(res.msg)
                }
            })
        }
    })
</script>
</body>
</html>
