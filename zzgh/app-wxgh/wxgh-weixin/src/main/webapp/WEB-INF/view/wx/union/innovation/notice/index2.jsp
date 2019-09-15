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
</style>
<body>

<div class="mui-content">
    <div id="slider" class="mui-slider">
        <div id="sliderSegmentedControl"
             class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted"
             style="position: fixed;z-index: 99;background-color: #efeff4;">
            <a class="mui-control-item" href="#item1mobile">
                活动公告
            </a>
            <a class="mui-control-item" href="#item2mobile">
                招募公告
            </a>
        </div>
        <div id="sliderProgressBar" class="mui-slider-progress-bar mui-col-xs-6"
             style="position: fixed;top: 40px;z-index: 9999;"></div>
        <div class="mui-slider-group" style="margin-top: 37px;">
            <div id="item1mobile" class="mui-slider-item mui-control-content mui-active" style="border-bottom: none;">
                <div id="scroll1" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <ul class="mui-table-view" style="padding-top: 18px;background-color: #efeff4;">
                            <c:choose>
                                <c:when test="${empty list}">
                                    <div class="mui-content-padded mui-text-center"
                                         style="background-color: #efeff4;margin: 0;">
                                        暂无
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <c:forEach items="${list}" var="item">
                                        <li class="mui-table-view-cell"
                                            style="border-radius: 3%;margin:auto;width: 90%;background-color: white; margin-bottom: 30px;padding-right:15px;">

                                            <a href="${home}/wx/union/innovation/notice/detail.html?id=${item.id}&type=1"
                                               style="    text-decoration: none;color: #000; margin-right:-16px;">
                                                <p class="mui-ellipsis"
                                                   style="font-family: '微软雅黑';font-size: 20px;color: #000;text-align: center;">${item.title}</p>
                                                <p style="font-family: '微软雅黑';font-size: 14px;color: #000;">
                                                    <fmt:formatDate
                                                            value="${item.addTime}"
                                                            pattern="yyyy-MM-dd HH:mm"></fmt:formatDate></p>
                                                <div class="ui-img-div" style="height: 160px;">
                                                    <c:choose>
                                                        <c:when test="${!empty item.img}">
                                                            <img style="height: 100%;width: 100%;"
                                                                 src="${item.img}">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <img style="height: 100%;width: 100%;"
                                                                 src="${home}/weixin/image/union/innovation/notice.png">
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                                <p style="font-family: '微软雅黑';font-size: 14px;color: #000;"
                                                   class="mui-ellipsis">
                                                        ${item.content}
                                                </p>
                                                <p style="font-family: '微软雅黑';font-size: 17px;color: #000;">阅读全文<span
                                                        class="mui-icon mui-icon-arrowright mui-pull-right"></span></p>
                                            </a>
                                        </li>
                                    </c:forEach>
                                </c:otherwise>
                            </c:choose>

                        </ul>
                    </div>
                </div>
            </div>
            <div id="item2mobile" class="mui-slider-item mui-control-content" style="border-bottom: none;">
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
<script type="text/javascript">
    mui.init()
</script>
<script>
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
</script>
<script>
    $(document).ready(function () {
        var html_hright = $("body").height() - 40;
        $("#item1mobile").css("height", html_hright);
        $("#item2mobile").css("height", html_hright);

        var html2 = "";
        $.ajax({
            type: "get",
            dataType: "json",
            url: "${home}/wx/union/innovation/notice/get_list.json",
            data: {
//                action: "getList",
                type: 2
            },
            success: function (result) {
                callbackFunc2(result);
            }
        });
        function callbackFunc2(result) {
            var data = result.data;
            var ps = "";
            if (data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    var img = data[i].img;
                    if (img == null || img == "") {
                        img = "${home}/image/common/nopic.gif";
                    } else {
                        img = wxgh.get_image(img);
                    }
                    ps = ps + '<li class="mui-table-view-cell"style="border-radius: 3%;margin:auto;width: 90%;background-color: white; margin-bottom: 30px;padding-right:15px;"><a href="${home}/wx/union/innovation/notice/detail.html?id=' + data[i].id + '&type=2" style="    text-decoration: none;color: #000; margin-right:-16px;"><p class="mui-ellipsis"style=";font-size: 20px;color: #000;text-align: center;">' + data[i].title + '</p><p style="font-size: 14px;color: #000;">' + formatTime(data[i].addTime) + '</p><div class="ui-img-div"style="height: 160px;"><img style="height: 100%;width: 100%;"src="' + img + '"></div><p style="font-size: 14px;color: #000;"class="mui-ellipsis">1111</p><p style="font-size: 17px;color: #000;">阅读全文<span class="mui-icon mui-icon-arrowright mui-pull-right"></span></p></a></li>';
                }
            } else {
                ps = '<div class="mui-content-padded mui-text-center"style="background-color: #efeff4;margin: 0;">暂无</div>';
            }
            html2 = '<ul class="mui-table-view" style="padding-top: 18px;background-color: #efeff4;">' + ps + '</ul>';
        }


        var item1 = document.getElementById('item1mobile');
        var item2 = document.getElementById('item2mobile');
        document.getElementById('slider').addEventListener('slide', function (e) {
            if (e.detail.slideNumber === 1) {
                if (item2.querySelector('.mui-loading')) {
                    setTimeout(function () {
                        item2.querySelector('.mui-scroll').innerHTML = html2;
                    }, 500);
                }
            } else if (e.detail.slideNumber === 0) {

            }
        });
    });
</script>
</body>

</html>
