<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/18
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <title>${(empty param.status or param.status eq 1)?'活动预告':'正在众筹'}</title>
    <jsp:include page="/comm/mobile/styles.jsp"></jsp:include>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css"/>
    <style>
        html, body {
            height: 100%;
        }

        .mui-content {
            height: 100%;
        }

        .mui-control-content {
            background-color: #fff;
        }

        .mui-loading {
            margin-top: 15px;
        }

        .mui-slider .mui-slider-group .mui-slider-item img {
            width: 100px;
        }

        .ui-act-item {
            position: relative;
        }

        .ui-act-item a {
            display: flex;
        }

        .ui-act-item .ui-act-img {
            flex: 3;
            height: 142px;
            padding: 10px;
        }

        .ui-act-body {
            flex: 7;
            padding: 8px;
        }

        .ui-act-time {
            position: absolute;
            right: 10px;
            top: 8px;
            font-size: 13px;
        }

        .ui-act-body h5 {
            font-weight: 500;
            color: #000;
            padding-right: 64px;
            line-height: 18px;
            font-size: 16px;
        }

        .ui-act-body div {
            font-size: 14px;
            line-height: 20px;
            color: #777;
        }

        .ui-ellipsis {
            display: -webkit-box;
            overflow: hidden;
            text-overflow: ellipsis;
            -webkit-box-orient: vertical;
            -webkit-line-clamp: 1;
        }

        .ui-act-div > .ui-act-item:not(:last-child):after {
            position: absolute;
            bottom: -5px;
            left: 4px;
            width: 100%;
            height: 4px;
            content: '';
            background-color: #efeff4;
        }

        .ui-act-div > .ui-act-item:not(:first-child) {
            margin-top: 4px;
        }

        .mui-slider-group .mui-slider-item {
            height: initial !important;
        }

        #sliderSegmentedControl {
            position: fixed;
            z-index: 999;
            background: #efeff4;
        }

        #sliderProgressBar {
            margin-top: 35px;
        }

        .mui-control-content {
            background-color: white;
            min-height: 215px;
        }

        .mui-control-content .mui-loading {
            margin-top: 50px;
        }
    </style>
</head>

<body>

<div class="mui-content">

    <div id="refreshContainer" class="mui-scroll-wrapper" style="padding-top: 0px;">
        <%-- <ul class="mui-table-view" style="background-color: #efeff4;" id="refreshContainer">

         </ul>--%>
        <div class="mui-scroll">
            <ul class="mui-table-view" style="background-color: #efeff4;">
            </ul>
        </div>
    </div>


</div>
<jsp:include page="/comm/mobile/scripts.jsp"></jsp:include>
<script type="text/javascript" src="${home}/comm/js/refresh.js"></script>
<script>
    var status = '${param.status}'

    $(document).ready(function () {
        var body_height = $("body").height();
        $("#item1mobile").attr("style", "height:" + body_height + "px !important;");
//        $("#item2mobile").css("height", body_height+"px !important");
        $("#item2mobile").attr("style", "height:" + body_height + "px !important;");
    });

    var homePath = '${home}';
    var ifno = {action: 'get'}
    if (status) {
        ifno['status'] = status
    }
    var refresh = refresh('#refreshContainer', {
        url: 'api/list_act.json',
        data: ifno,
        ispage: true,
        bindFn: bindfn,
        errorFn: function (type) {
            console.log(type)
        }
    })

    function bindfn(data) {
        var list = data.list;
        if (list && list.length > 0) {
            for (var i = 0; i < list.length; i++) {
                var li = document.createElement('li');
                li.className = 'mui-table-view-cell';
                li.style = 'background-color: white;margin-bottom: 10px;';
                li.innerHTML = '<a href="act/show.html?id=' + list[i].id + '" style="color: #000;"><img src="' + list[i].coverImg + '" style="float: left;width: 25%;height: 80px;"><div style="float: left;width: 75%;"><div style="padding-left:10px;"><p class="mui-ellipsis-2" style="font-weight: 600;color: black;font-size: 15px;">' + list[i].theme + '</p><p>' + new Date(list[i].startTime).format('MM-dd hh:mm') + '&nbsp;&nbsp;&nbsp;开始</p><p class="mui-ellipsis">' + list[i].address + '</p></div></div></a>';
                refresh.addItem(li)
            }
        } else {
            refresh.addItem('<li class="mui-table-view-cell"><div class="mui-text-center ui-text-info">暂无众筹活动</div></li>')
        }
    }
    $(function () {
        $("#refreshContainer").on("tap", "li", function () {
            var href = $(this).children("a").attr("href");
            if (href) {
                mui.openWindow(href)
            }
        });
    })

</script>
</body>

</html>

