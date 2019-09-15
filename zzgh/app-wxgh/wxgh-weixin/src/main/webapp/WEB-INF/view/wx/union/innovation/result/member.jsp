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
    <title>成员列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
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

    <div  class="mui-scroll-wrapper" style="padding-top: 0px;">
        <div class="mui-scroll">
            <ul class="mui-table-view" style="background-color: #efeff4;" id="refreshContainer">
                <c:forEach items="${users}" var="item">
                    <li class="mui-table-view-cell" style="background-color: white; margin-bottom: 5px;">
                        <c:choose>
                            <c:when test="${empty item.userImg}">
                                <img style="border-radius: 50%;" class="mui-media-object mui-pull-left"
                                     src="${home}/image/common/nopic.gif">
                            </c:when>
                            <c:otherwise>
                                <img style="border-radius: 50%;" class="mui-media-object mui-pull-left"
                                     src="${item.userImg}">
                            </c:otherwise>
                        </c:choose>
                        <div class="mui-media-body mui-ellipsis" style="margin-top: 10px;">
                                ${item.name}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${item.typeName}
                            <span class="mui-ellipsis mui-right mui-pull-right">${item.deptname}</span>
                        </div>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>


</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    mui.init({
        swipeBack: true
    });
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });
</script>
</body>

</html>
