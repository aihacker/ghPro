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
    <title>工作坊</title>
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

<div class="mui-content" style="height: 100%;">
    <div id="slider" class="mui-slider" style="height: 100%;">
        <div disabled="disabled" id="sliderSegmentedControl"
             class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted"
             style="position: fixed;z-index: 99;background-color: #efeff4;">
            <a class="mui-control-item" href="#item1mobile">
                工作坊
            </a>
        </div>
        <div id="sliderProgressBar" class="mui-slider-progress-bar "
             style="position: fixed;top: 37px;z-index: 9999;"></div>
        <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
            <div class="mui-scroll">
                <ul class="mui-table-view">
                    <c:choose>
                        <c:when test="${empty results}">
                            <div class="mui-content-padded mui-text-center"
                                 style="background-color: #efeff4;margin: 0;">
                                暂无
                            </div>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${results}" var="item">
                                <li class="mui-table-view-cell mui-media">
                                    <%--<a href="${home}/wx/union/innovation/work/detail.html?id=${item.miId}&workType=${item.workType}">--%>
                                        <%--<c:choose>--%>
                                            <%--<c:when test="${item.imgAvatar != null && item.imgAvatar != ''}">--%>
                                                <%--<img style="width: 37px;" class="mui-media-object mui-pull-left"--%>
                                                     <%--src="${item.imgAvatar}">--%>
                                            <%--</c:when>--%>
                                            <%--<c:otherwise>--%>
                                                <%--<img style="width: 37px;" class="mui-media-object mui-pull-left"--%>
                                                     <%--src="${home}/image/common/nopic.gif">--%>
                                            <%--</c:otherwise>--%>
                                        <%--</c:choose>--%>
                                        <div class="mui-media-body">项目名：${item.itemName}
                                            <span class="mui-right mui-pull-right"> </span>

                                            <p class="mui-ellipsis">
                                                <fmt:formatDate value="${item.addTime}"
                                                                pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                                                <span class="mui-right mui-pull-right"></span>
                                            </p>
                                        </div>
                                    <%--</a>--%>
                                </li>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
        <%--<div class="mui-slider-group" style="margin-top: 37px;">--%>
            <%--<div id="item1mobile" class="mui-slider-item mui-control-content mui-active" style="border-bottom: none;">--%>
                <%--<div id="scroll1" class="mui-scroll-wrapper" style="overflow: scroll;height:100%">--%>
                    <%--<div class="mui-scroll">--%>
                        <%--<ul class="mui-table-view">--%>
                            <%--<c:choose>--%>
                                <%--<c:when test="${empty results}">--%>
                                    <%--<div class="mui-content-padded mui-text-center"--%>
                                         <%--style="background-color: #efeff4;margin: 0;">--%>
                                        <%--暂无--%>
                                    <%--</div>--%>
                                <%--</c:when>--%>
                                <%--<c:otherwise>--%>
                                    <%--<c:forEach items="${results}" var="item">--%>
                                        <%--<li class="mui-table-view-cell mui-media">--%>
                                            <%--<a href="${home}/wx/union/innovation/work/detail.html?id=${item.miId}&workType=${item.workType}">--%>
                                                <%--<c:choose>--%>
                                                    <%--<c:when test="${item.imgAvatar != null && item.imgAvatar != ''}">--%>
                                                        <%--<img style="width: 37px;" class="mui-media-object mui-pull-left"--%>
                                                             <%--src="${item.imgAvatar}">--%>
                                                    <%--</c:when>--%>
                                                    <%--<c:otherwise>--%>
                                                        <%--<img style="width: 37px;" class="mui-media-object mui-pull-left"--%>
                                                             <%--src="${home}/image/common/nopic.gif">--%>
                                                    <%--</c:otherwise>--%>
                                                <%--</c:choose>--%>
                                                <%--<div class="mui-media-body">项目名：${item.itemName}--%>
                                                    <%--<span class="mui-right mui-pull-right"> </span>--%>

                                                    <%--<p class="mui-ellipsis">--%>
                                                        <%--<fmt:formatDate value="${item.addTime}"--%>
                                                                        <%--pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>--%>
                                                        <%--<span class="mui-right mui-pull-right"></span>--%>
                                                    <%--</p>--%>
                                                <%--</div>--%>
                                            <%--</a>--%>
                                        <%--</li>--%>
                                    <%--</c:forEach>--%>
                                <%--</c:otherwise>--%>
                            <%--</c:choose>--%>
                        <%--</ul>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
            <%--<div id="item2mobile" class="mui-slider-item mui-control-content" style="border-bottom: none;">--%>
                <%--<div id="scroll2" class="mui-scroll-wrapper">--%>
                    <%--<div class="mui-scroll">--%>
                        <%--<div class="mui-loading">--%>
                            <%--<div class="mui-spinner">--%>
                            <%--</div>--%>
                        <%--</div>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>
    </div>

</div>


<jsp:include page="/comm/mobile/scripts.jsp"/>
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

</body>

</html>
