<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/17
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="date" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>“四小”建设</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .xlk-head {
            background: -webkit-gradient(linear, 0 0, 0 bottom, from(#08A8F4), to(#1088C0));
        }

        .xlk-head .mui-icon-left-nav {
            color: #fff;
        }

        .xlk-head .mui-title {
            color: #fff;
        }

        #slider img {
            height: 260px;
        }

        .index-body {
            font-size: .5em;
            margin-left: 10px;
        }

        .index-demand-title {
            position: relative;
            padding-top: 10px;
            margin-bottom: 10px;
        }

        .index-demand-title span {
            z-index: 99;
            position: relative;
            padding: 8px 25px;
        }

        body {
            font-family: "微软雅黑";
            background-color: #fff;
        }

        .mui-grid-view.mui-grid-9,
        .mui-content {
            background-color: #fff;
        }

        .mui-grid-view.mui-grid-9 {
            border: none;
        }

        .mui-grid-view.mui-grid-9 .mui-table-view-cell {
            border-bottom: none;
        }

        .left-img {
            width: 40px;
            height: 40px;
            display: block;
            border-radius: 50%;
            color: #fff;
            margin-top: 12px;
            margin-right: 10px;
            text-align: center;
        }

        .buytime-span {
            font-size: 14px;
            color: #8f8f94;
        }

        .mui-media .mui-ellipsis {
            color: #000;
            padding-right: 20px;
            padding-top: 4px;
        }

        .left-color-zhise {
            background-color: #d0659b;
        }

        .left-color-huang {
            background-color: #ff972d;
        }

        .left-color-green {
            background-color: #5ed198;
        }

        .left-color-blue {
            background-color: #669aff;
        }
    </style>
</head>

<body>
<header class="mui-bar mui-bar-nav xlk-head">
    <h1 class="mui-title">“四小”建设</h1>
</header>

<div class="mui-content">
    <div id="slider" class="mui-slider">
        <div class="mui-slider-group mui-slider-loop">
            <!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
            <div class="mui-slider-item mui-slider-item-duplicate">
                <a href="#">
                    <img src="${home}/weixin/image/home/example-slide-4.jpg">

                    <p class="mui-slider-title">静静看这世界</p>
                </a>
            </div>
            <div class="mui-slider-item">
                <a href="#">
                    <img src="${home}/weixin/image/home/example-slide-1.jpg">

                    <p class="mui-slider-title">幸福就是可以一起睡觉</p>
                </a>
            </div>
            <div class="mui-slider-item">
                    <a href="#">
                        <img src="${home}/weixin/image/home/example-slide-2.jpg">

                    <p class="mui-slider-title">想要一间这样的木屋，静静的喝咖啡</p>
                </a>
            </div>
            <div class="mui-slider-item">
                <a href="#">
                    <img src="${home}/weixin/image/home/example-slide-3.jpg">

                    <p class="mui-slider-title">Color of SIP CBD</p>
                </a>
            </div>
            <div class="mui-slider-item">
                <a href="#">
                    <img src="${home}/weixin/image/home/example-slide-4.jpg">

                    <p class="mui-slider-title">静静看这世界</p>
                </a>
            </div>
            <!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
            <div class="mui-slider-item mui-slider-item-duplicate">
                <a href="#">
                    <img src="${home}/weixin/image/home/example-slide-1.jpg">

                    <p class="mui-slider-title">幸福就是可以一起睡觉</p>
                </a>
            </div>
        </div>
        <div class="mui-slider-indicator mui-text-right">
            <div class="mui-indicator mui-active"></div>
            <div class="mui-indicator"></div>
            <div class="mui-indicator"></div>
            <div class="mui-indicator"></div>
        </div>
    </div>

    <ul class="mui-table-view mui-grid-view mui-grid-9">
        <li class="mui-table-view-cell mui-media mui-col-xs-6 mui-col-sm-6">
            <a href="${home}/wx/four/demand.html">
                <span class="mui-icon mui-icon-compose"><span class="index-body">四小需求</span></span>
            </a>
        </li>
        <li class="mui-table-view-cell mui-media mui-col-xs-6 mui-col-sm-6">
            <a href="${home}/wx/four/detailed.html">
                <span class="mui-icon mui-icon-flag"><span class="index-body">台账明细</span></span>
            </a>
        </li>
    </ul>

    <div class="mui-content-padded">
        <div class="index-demand-title mui-text-center">
            <span class="mui-badge">最新台账</span>
        </div>

        <c:choose>
            <c:when test="${empty fours}">
                <hr />
                <div class="mui-content-padded mui-text-center">
                    暂无最新台账哦
                </div>
            </c:when>
            <c:otherwise>
                <ul class="mui-table-view">
                    <c:forEach items="${fours}" var="d" varStatus="i`">
                        <li class="mui-table-view-cell mui-media">
                            <a href="${home}/wx/four/show.html?id=${d.id}&type=2" class="mui-navigate-right">
                                <div class="mui-media-object mui-pull-left"><span
                                        class="left-img left-color-green">${i.index+1}</span></div>
                                <div class="mui-media-body">
                                        ${d.deptName}
                                    <p class="mui-ellipsis">${d.content}</p>
                                <span class="buytime-span">采购时间：<date:formatDate value="${d.buyTime}"
                                                                                 pattern="yyyy年MM月"/></span>
                                </div>
                            </a>
                        </li>
                    </c:forEach>
                </ul>
            </c:otherwise>
        </c:choose>

    </div>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init({
        swipeBack: true
    })
    var slider = mui('#slider');
    slider.slider({
        interval: 5000
    });
</script>
</body>

</html>
