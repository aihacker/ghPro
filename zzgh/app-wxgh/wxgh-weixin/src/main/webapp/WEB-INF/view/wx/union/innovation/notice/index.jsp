<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/29
  Time: 21:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>公告</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>

<style>
    html, body {
        height: 100%;
    }

    .mui-control-content {
        min-height: 215px;
    }

    .mui-control-content .mui-loading {
        margin-top: 50px;
    }

    .mui-badge-selected {
        color: #fff;
        background-color: #007aff;
    }

    .ui-act-div .mui-table-view-cell {
        list-style: none;
    }
</style>
<body>


<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-type="1" class="mui-control-item mui-active">活动公告
        </a>
        <a data-type="2" class="mui-control-item">招募公告
        </a>
    </div>
    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <div class="ui-act-div"></div>
        </div>
    </div>
</div>


<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>

<script id="itemTpl" type="text/template">
    <li class="mui-table-view-cell"
        style="border-radius: 3%;margin:auto;width: 90%;background-color: white; margin-bottom: 30px;padding-right:15px;">
        <a href="{{=it.url}}"
           style="    text-decoration: none;color: #000; margin-right:-16px;">
            <p class="mui-ellipsis"
               style="font-family: '微软雅黑';font-size: 20px;color: #000;text-align: center;">{{=it.title}}</p>
            <p style="font-family: '微软雅黑';font-size: 14px;color: #000;">
                {{=it.addTime}}</p>
            <%--<div class="ui-img-div" style="height: 160px;">--%>
                <%--<img style="height: 100%;width: 100%;"--%>
                     <%--src="{{=it.img}}">--%>
            <%--</div>--%>
            <p style="font-family: '微软雅黑';font-size: 14px;color: #000;"
               class="mui-ellipsis">
                ${item.content}
            </p>
            <p style="font-family: '微软雅黑';font-size: 17px;color: #000;">阅读全文<span
                    class="mui-icon mui-icon-arrowright mui-pull-right"></span></p>
        </a>
    </li>
</script>

<script type="text/javascript">
    mui.init()
</script>
<script>
    //    mui.init({
    //        swipeBack: false
    //    });
    //    (function ($) {
    //        $('.mui-scroll-wrapper').scroll({
    //            indicators: true,
    //            deceleration: 0.0005//是否显示滚动条
    //        });
    //    })(mui);

    var refresh = window.refresh('#refreshContainer', {
        url: 'list.json',
        data: {type: 1},
        ispage: true,
        isBind: true,
        tplId: '#itemTpl',
        dataEl: '.ui-act-div',
        emptyHtml: '<div class="mui-content-padded ui-empty">暂无公告哦</div>',
        bindFn: bindfn,
        errorFn: function (type) {
            console.log(type)
            ui.alert('获取失败...')
        }
    })

    function bindfn(d) {
        d['url'] = homePath + '/wx/union/innovation/notice/detail2.html?id=' + d.id + '&type=' + d.type;
        d['addTime'] = formatTime(d.addTime);
        if (!d.img) {
            d['img'] = homePath + '/weixin/image/union/innovation/notice.png';
        } else
            d['img'] = homePath + d.img;
        var $item = refresh.getItem(d)
        $item.on('tap', function () {
            mui.openWindow(d['url']);
        })
        return $item[0]
    }

    $('#segmentedControl').on('tap', '.mui-control-item', function () {
        var type = $(this).data('type')
        refresh.refresh({type: type})
    })

    function formatTime(time) {
        //   格式：yyyy-MM-dd hh:mm:ss
        var date = new Date(time);
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
        h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
        m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes();
        return Y + M + D + h + m;
    }
</script>

</body>

</html>
