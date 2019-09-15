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
    <meta charset="UTF-8">
    <title>项目团队</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">


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

<%--<div class="mui-content">
    <div id="refreshContainer" class="mui-scroll-wrapper ui-scroll-wrapper" style="padding-top: 0px;">

        <div class="mui-scroll">
            <ul class="mui-table-view mui-table-view-chevron" style="background-color: #efeff4;">

                <c:forEach items="${list}" var="item">
                    <li data-your="${item.isYourGroup}"
                        data-href="${home}/chat2/chat.html?dept=${item.name}&id=${item.id}&type=team"
                        class="mui-table-view-cell" style="background-color: white; margin-bottom: 7px;">
                        <a style="color: #000;">
                            <img src="${item.avatar}" style="float: left;width: 25%;height: 65px;">
                            <div style="float: left;width: 75%;">
                                <div style="padding-left:10px;">
                                    <p class="mui-ellipsis" style="font-weight: 600;color: black;font-size: 15px;">
                                            ${item.name}</p>
                                    <p class="mui-ellipsis-2">
                                            ${item.briefIntro}
                                    </p>
                                </div>
                            </div>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>--%>
<div class="mui-content">
    <div id="slider" class="mui-slider">
        <div id="sliderSegmentedControl"
             class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
            <a class="mui-control-item mui-active" href="#item1mobile">
                所有团队
            </a>
            <a class="mui-control-item" href="#item2mobile">
                我的团队
            </a>
        </div>
        <div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-6" style="z-index: 999;"></div>
        <div class="mui-slider-group">
            <div id="item1mobile" class="mui-slider-item mui-control-content mui-active">
                <div id="scroll1" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <ul class="mui-table-view mui-table-view-chevron" style="background-color: #efeff4;">

                            <c:forEach items="${list}" var="item">
                                <li data-href="${home}/wx/union/innovation/team/show.html?id=${item.id}"
                                    class="mui-table-view-cell" style="background-color: white; margin-bottom: 7px;">
                                    <a style="color: #000;">
                                        <img src="${item.avatar}" style="float: left;width: 25%;height: 65px;">
                                        <div style="float: left;width: 75%;">
                                            <div style="padding-left:10px;">
                                                <p class="mui-ellipsis"
                                                   style="font-weight: 600;color: black;font-size: 15px;">
                                                        ${item.name}</p>
                                                <p class="mui-ellipsis-2">
                                                        ${item.info}
                                                </p>
                                            </div>
                                        </div>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
            <div id="item2mobile" class="mui-slider-item mui-control-content">
                <div id="scroll2" class="mui-scroll-wrapper">
                    <div class="mui-scroll">

                        <div class="mui-loading">
                            <div class="mui-spinner">
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </div>
    </div>

</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>

<script src="${home}/comm/mobile/js/refresh.js"></script>

<script>
    mui.init({
        swipeBack: true
    });


    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    $(document).ready(function () {

        $.each($('#item1mobile .mui-table-view-cell img'), function () {
            var src = $(this).attr('src')
            $(this).attr('src', wxgh.get_image(src))
        })

        var body_height = $("body").height()-40;
        $("#item1mobile").attr("style", "height:" + body_height + "px !important;");
//        $("#item2mobile").css("height", body_height+"px !important");
        $("#item2mobile").attr("style", "height:" + body_height + "px !important;");

        $('#item1mobile').on("click", "li[data-href]", function () {
            var href = $(this).attr("data-href");
            mui.openWindow(href)
        });

        $.ajax({
            url: "${home}/wx/union/innovation/team/get_list.json",
            data: {
//                action: "getList"
            },
            success: function (rs) {
                callback(rs);
            }
        });

        var html2 = "";
        var item2 = document.getElementById('item2mobile');

        function callback(rs) {

            var ps = "";
            var data = rs.data;
            for (var i = 0; i < data.length; i++) {
                ps += '<li data-href="${home}/wx/union/innovation/team/show.html?id=' + data[i].id + '" class="mui-table-view-cell" style="background-color: white; margin-bottom: 7px;"><a style="color: #000;"><img src="' + wxgh.get_image(data[i].avatar) + '" style="float: left;width: 25%;height: 65px;"><div style="float: left;width: 75%;"><div style="padding-left:10px;"><p class="mui-ellipsis"style="font-weight: 600;color: black;font-size: 15px;">' + data[i].name + '</p><p class="mui-ellipsis-2">' + data[i].briefIntro + '</p></div></div></a></li>';
            }
            html2 = '<ul class="mui-table-view mui-table-view-chevron" style="background-color: #efeff4;">' + ps + '</ul>';
        }

        var isEvent = false

        document.getElementById('slider').addEventListener('slide', function (e) {
            if (e.detail.slideNumber === 1) {
                if (item2.querySelector('.mui-loading')) {
                    setTimeout(function () {
                        item2.querySelector('.mui-scroll').innerHTML = html2;
                        if(!isEvent){
                            $(item2).on("click", "li[data-href]", function () {
                                var href = $(this).attr("data-href");
                                mui.openWindow(href)
                            });
                            isEvent = true
                        }
                    }, 500);
                }
            }
        });

    });

    function formatTime(time) {
        //   格式：yyyy-MM-dd hh:mm:ss
        var date = new Date(time);
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
        h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
        m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes() + '';
        s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();

        return M + D + h + m;
    }
</script>
</body>

</html>
