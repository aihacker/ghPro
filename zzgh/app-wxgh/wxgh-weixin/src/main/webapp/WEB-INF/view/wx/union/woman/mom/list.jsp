<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/14
  Time: 11:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>小屋列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .div-time {
            background-color: #DCDCDC;
        }

        .div-time-selected {
            background-color: #00A0E9;
        }

        .list.mui-table-view .mui-media-object {
            width: 100px;
            max-width: 100px;
            height: 80px;
        }
    </style>
</head>
<body>
<div style="height:70px;background-color: white;">

    <c:forEach items="${dateList}" var="item" varStatus="status">
        <div style="height: 100%;float: left; width: 20%;">
            <div data-week="${item.weekInt}"
                 class="div-time <c:if test='${status.index == 0}'>div-time-selected</c:if> "
                 style="height: 50px;width: 50px;border-radius: 50%;margin: auto;text-align: center;padding-top: 7px;margin-top: 10px;">
                <p style="margin-bottom: 0px;font-size: 12px;color: white;">${item.dateStr}</p>
                <p style="margin-bottom: 0px;font-size: 12px;color: white;">${item.week}</p>
            </div>
        </div>
    </c:forEach>
</div>

<div id="refreshContainer" class="mui-content mui-scroll-wrapper" style="top: 70px;">
    <div class="mui-scroll">
        <ul class="mui-table-view list">
        </ul>
    </div>
</div>

<script type="text/html" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <img class="mui-media-object mui-pull-left" src="{{=it.path}}">
            <div class="mui-media-body">
                {{=it.name}}
                <p class="mui-ellipsis-2">{{=it.info}}</p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: 'api/list.json',
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '今日没有小屋可以预约',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                mui.toast('加载失败...');
            }
        });

        function bindfn(d) {
            d['path'] = wxgh.get_thumb(d);
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow('show.html?id=' + d.id);
            })
            return $item[0];
        }

        $(".div-time").on('tap', function () {
            var $self = $(this);
            if (!$self.hasClass('div-time-selected')) {
                $(".div-time").removeClass("div-time-selected");
                $(this).addClass("div-time-selected");
                refresh.refresh({week: $self.data('week')});
            }
        });
    })
</script>
</body>
</html>