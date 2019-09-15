<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/22
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>场馆详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <link rel="stylesheet" href="${home}/libs/font-awesome-4.7.0/css/font-awesome.min.css"/>
    <style>
        .ui-img-div {
            width: 100%;
            display: flex;
            max-height: 180px;
            justify-content: center;
            align-items: center;
            overflow: hidden;
        }

        .ui-img-div img {
            width: 100%;
        }

        .ui-place-main {
        }

        .ui-bottom-border:after {
            position: absolute;
            right: 0;
            bottom: 0px;
            left: 0;
            height: 1px;
            content: '';
            width: 90%;
            left: 5%;
            background-color: #c8c7cc;
            -webkit-transform: scaleY(.5);
            transform: scaleY(.5);
        }

        .ui-place-head {
            padding: 5px 20px;
            position: relative;
            background-color: #fff;
        }

        .ui-place-head h5 {
            color: #000;
            font-size: 17px;
        }

        .ui-place-head small {
            color: #777;
        }

        #orderProgame .mui-media-body {
            height: 40px;
            margin-top: 0;
        }

        #orderProgame .fa {
            font-size: 30px;
        }

        #orderProgame button {
            display: block;
            padding: 0px 5px;
            font-size: 12px;
            margin-left: auto;
            margin-right: auto;
            margin-top: 2px;
        }

        #orderProgame .mui-table-view:before,
        #orderProgame .mui-table-view:after {
            height: 0;
        }

        #orderProgame .mui-table-view:first-child {
            padding-bottom: 0px;
        }

        #orderProgame .ui-angle-down-div {
            background-color: #fff;
            color: rgba(0, 0, 0, .2);
            font-weight: 100;
            line-height: 12px;
        }

        .ui-order-title {
            padding: 5px 20px 0 20px;
            background-color: #fff;
        }

        .ui-info {
            padding: 5px 20px;
            background-color: #fff;
            text-indent: 25px;
        }

        #placeInfoDiv,
        .ui-place-info,
        .ui-order-title {
            position: relative;
        }

        #placeInfoDiv .ui-place-info {
            display: flex;
            padding: 5px 20px;
            background-color: #fff;
        }

        .ui-place-info .ui-place-addr {
            flex: 1;
        }

        .ui-place-addr h5 {
            color: #000;
            font-size: 16px;
        }

        .ui-place-addr small {
            color: #777;
        }

        .ui-place-info .ui-place-phone {
            font-size: 30px;
            width: 40px;
            text-align: center;
            justify-content: center;
            align-items: center;
            display: flex;
            border-left: 2px solid gainsboro;
        }

        /*.mui-bar-transparent {
            background-color: RGBA(8,168,244,1);
        }*/
        .ui-icon {
            width: 35px;
            height: 35px;
            display: inline-block;
            background-repeat: no-repeat;
            background-position: center;
            background-size: 100% 100%;
            vertical-align: top;
            margin-top: 2px;
        }
    </style>
</head>

<body>

<header class="mui-bar mui-bar-transparent">
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

    <h1 class="mui-title"></h1>
</header>

<div class="mui-content">
    <!-- 轮播图 -->
    <div id="headSlider" class="mui-slider">
        <c:choose>
            <c:when test="${empty imgs}">
                <div class="mui-slider-group">
                    <div class="mui-slider-item">
                        <a href="#">
                            <div class="ui-img-div">
                                <img data-preview-src="" data-preview-group="slider_head"
                                     src="${home}/weixin/image/entertain/place/banner.jpg">
                            </div>
                        </a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="mui-slider-group">
                    <c:forEach items="${imgs}" var="img">
                        <div class="mui-slider-item">
                            <a href="#">
                                <div class="ui-img-div">
                                    <img data-preview-src="" data-preview-group="slider_head" src="${img}">
                                </div>
                            </a>
                        </div>
                    </c:forEach>
                </div>
                <div class="mui-slider-indicator">
                    <c:forEach items="${imgs}" var="img" varStatus="i">
                        <div class="mui-indicator ${i.count eq 1?'mui-active':''}"></div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- 主体 -->
    <div class="ui-place-main">
        <!-- 场馆名称 -->
        <div class="ui-place-head">
            <h5>${place.title}</h5>
            <small>${empty countYuyue?0:countYuyue}人预订成功</small>
        </div>

        <!-- 预订项目 -->
        <div class="ui-margin-top-10" id="orderProgame">
            <div class="ui-order-title">项目预约</div>
            <div class="mui-row">
                <c:choose>
                    <c:when test="${empty cates}">
                        <div class="mui-content-padded mui-text-center ui-text-info">暂无预约项目哦</div>
                    </c:when>
                    <c:otherwise>
                        <ul class="mui-table-view mui-grid-view">
                            <c:forEach items="${cates}" var="c" varStatus="i">
                                <li data-id="${c.id}"
                                    class="mui-table-view-cell mui-col-xs-3 ui-yuyue ${i.count gt 4?'mui-hidden':''}">
                                    <a>
                                        <span class="ui-icon" style="background-image: url(${home}${c.imgPath});"></span>

                                        <div class="mui-media-body">
                                            <small>${c.name}</small>
                                            <button type="button"
                                                    class="mui-btn mui-btn-warning">预约
                                            </button>
                                        </div>
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                        <c:if test="${cateCount gt 4}">
                            <div id="moreCate" class="mui-text-center ui-angle-down-div"><span
                                    class="fa fa-angle-down"></span>
                            </div>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <!-- 场馆基本信息 -->
    <div class="ui-margin-top-10" id="placeInfoDiv">
        <div class="ui-order-title ui-bottom-border">场馆信息</div>
        <div class="ui-place-info ui-bottom-border">
            <div class="ui-place-addr">
                <h5>${place.address}</h5>
                <small>营业时间：${place.startTime}-${place.endTime}</small>
            </div>
            <div class="ui-place-phone">
                <a class="fa fa-phone" href="tel:${place.phone}"></a>
            </div>

        </div>
        <p class="ui-info">
            ${empty place.info?'暂无简介哦':place.info}
        </p>
    </div>
</div>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script type="text/javascript">
    mui.init()
    wxgh.previewImageInit();

    var placeId = '${param.id}';
    var homePath = '${home}';

    $(".ui-place-phone").click(function () {

    });

    var page = {
        init: function () {
            var moreBtn=document.getElementById('moreCate');
//            alert(moreBtn);
            var cateList = wxgh.getElement('orderProgame').querySelector('.mui-grid-view')
            if (moreBtn) {
                moreBtn.addEventListener('tap', function () {
                    var span = this.querySelector('fa')
                    if (span.classList.contains('fa-angle-down')) {
                        var clist = cateList.querySelectorAll('.mui-table-view-cell')
                        for (var i = 3; i < clist.length; i++) {
                            wxgh.show(clist[i])
                        }
                        span.classList.remove('fa-angle-down')
                        span.classList.add('fa-angle-up');
                    } else {
                        var clist = cateList.querySelectorAll('.mui-table-view-cell')
                        for (var i = 3; i < clist.length; i++) {
                            wxgh.hide(clist[i])
                        }
                        span.classList.remove('fa-angle-up')
                        span.classList.add('fa-angle-down');
                    }
                })
            }

            mui('.mui-table-view').on('tap', '.ui-yuyue', function () {
                var dataId = this.getAttribute('data-id')
                mui.openWindow(homePath + '/wx/entertain/nanhai/place/subscribe.html?id=' + placeId + '&type=' + dataId);
//                mui.toast('预约')
            })
        }
    }

    window.onload = page.init();
</script>
</body>

</html>
