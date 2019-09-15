<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <title>荣誉墙</title>

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
    .p-shenhezhong {
        width:100px;
        height:20px;
        color:white;
        text-align:center;
        background-color:#0398E6;
        /* Rotate div */
        transform:rotate(45deg);
        -ms-transform:rotate(45deg); /* Internet Explorer */
        -moz-transform:rotate(45deg); /* Firefox */
        -webkit-transform:rotate(45deg); /* Safari 和 Chrome */
        -o-transform:rotate(45deg); /* Opera */
    }
    ul {
        background-color: #efeff4 !important;
    }
</style>
<body>

<div class="mui-content">
    <div id="slider" class="mui-slider">
        <div id="sliderSegmentedControl"
             class="mui-slider-indicator mui-segmented-control mui-segmented-control-inverted"
             style="position: fixed;top: 0px;z-index: 99;background-color: #efeff4;">
            <a class="mui-control-item mui-active" href="#item1">
                荣誉墙列表
            </a>
        </div>
        <div id="sliderProgressBar" class="mui-slider-progress-bar"
             style="position: fixed;top: 36px;z-index: 9999;"></div>
        <div class="mui-slider-group" style="margin-top: 37px;">

            <div id="item1" class="mui-slider-item mui-control-content mui-active" style="border-bottom: none;">
                <div id="scroll" class="mui-scroll-wrapper">
                    <div class="mui-scroll">
                        <ul class="mui-table-view" style="background-color: #efeff4;padding-top: 7px;">
                            <c:choose>
                                <c:when test="${fn:length(results) != 0 && results != null}">
                                    <c:forEach items="${results}" var="item">
                                        <li class="mui-table-view-cell"
                                            style="background-color: white; margin-bottom: 7px;">
                                            <a href="javascript:void(0);" style="color: #000;">
                                                <img src="${item.avatar}" style="float: left;width: 14%;height: 49px;">
                                                <div style="float: left;">
                                                    <div style="padding-left:10px;">
                                                        <p class="mui-ellipsis" style="color: black;font-size: 18px;">
                                                                ${item.username}
                                                        </p>
                                                        <p class="mui-ellipsis">
                                                            上传时间：<fmt:formatDate value="${item.applyTime}"
                                                                                 pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                                                        </p>
                                                    </div>
                                                    <h4>名称：${item.name}</h4>
                                                    <h4>奖项：${item.awardGrade}</h4>
                                                    <h4>单位/个人：${item.people}</h4>
                                                </div>
                                            </a>
                                        </li>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <div class="mui-content-padded mui-text-center"
                                         style="background-color: #efeff4;margin: 0;">
                                        暂无
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
    </div>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script>

    mui('.mui-scroll-wrapper').scroll({
        indicators: true,
        deceleration: 0.0005//是否显示滚动条
    });
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
