<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/27
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view .mui-media-object {
            max-width: 65px;
            width: 65px;
            height: 68px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <%--<a data-status="0" class="mui-control-item mui-active" id="0">意见征集</a>--%>
        <a data-status="1" class="mui-control-item" id="1">问卷调查</a>
    </div>
    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view" id="ul1">
            </ul>
        </div>
    </div>
</div>

<script type="text/html" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <%--<img class="mui-media-object mui-pull-left" src="{{=it.path}}">--%>
            <div class="mui-media-body">
                {{=it.title}}
                <p class="mui-ellipsis">{{=it.info}}</p>
                <p>
                    <small>{{=it.addTime}}</small>
                </p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/comm/mobile/js/refresh.js"></script>
<script type="text/javascript">

    $(function () {
        var status;
        var refresh = window.refresh('#refreshContainer', {
            url: 'api/list.json',
            data:{},
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
            d['addTime'] = ui.timeAgo(d.addTime)
            var $item = refresh.getItem(d)
            $item.on("tap",function () {
                wxgh.openUrl("${home}/wx/party/opinion/info.html?id=" + d.id)
            })
            return $item[0]
        }

        $('#segmentedControl').on('tap', '.mui-control-item', function () {
            var status = $(this).data('status')
            if(status == 1)
                wxgh.openUrl("${home}/wx/party/comment/index.html")
            else
                refresh.refresh()
        })

    })
</script>
</body>
</html>

