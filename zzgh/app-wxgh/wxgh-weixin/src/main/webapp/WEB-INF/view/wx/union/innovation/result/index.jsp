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
    <title>成果展示</title>
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

    li span.ui-title {
        display: inline-block;
        max-width: 100%;
    }

    li .mui-media-object {
        width: 50px;
        height: 50px;
        line-height: 50px;
        max-width: 50px;
        /*margin-top: 22px;*/
    }

    li span.ui-badge {
        text-align: center;
        -webkit-border-radius: 50%;
        border-radius: 50%;
        display: block;
        width: 100%;
        height: 100%;
    }

    li small.ui-time {
        font-size: 12px;
        float: right;
    }
</style>
<body>

<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view">
        </ul>
    </div>
</div>


<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="{{=it.url}}">
            <img style="width: 37px;" class="mui-media-object mui-pull-left"
                         src="{{=it.imgAvatar}}"/>
            <div class="mui-media-body">项目名：{{=it.itemName}}
                <span class="mui-right mui-pull-right"> </span>
                <p class="mui-ellipsis">{{=it.addTime}}
                    <span class="mui-right mui-pull-right"></span>
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
            url: homePath + "/wx/union/innovation/result/list.json",
            data: {status: 1, workType: 3},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '暂无信息',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['addTime'] = formatTime(d.addTime)
            d['url'] = homePath + '/wx/union/innovation/result/detail.html?id=' + d['miId'] + '&workType=' + d['workType']
            d['imgAvatar'] = d.avatar ? (wxgh.get_image(d.imgAvatar)) : homePath + '/image/default/chat.png'
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow(d['url'])
            })
            return $item[0];
        }

        mui.init({
            swipeBack: false
        });
        (function ($) {
            $('.mui-scroll-wrapper').scroll({
                indicators: true,
                deceleration: 0.0005//是否显示滚动条
            });
        })(mui);
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

    });
</script>

</body>

</html>
