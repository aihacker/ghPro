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
    <title>女工园地</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/bootstrap-3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="${home}/libs/font-awesome-4.7.0/css/font-awesome.min.css">

    <style>
        html, body {
            height: 100%;
        }

        .mui-content {
            height: 100%;
        }
    </style>
</head>

<body>
<div class="mui-content">

    <div class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <div class="ui-img-div">
                <img src="${home}/weixin/image/common/female/banner.png">
            </div>

            <div class="mui-row"
                 style="height: 100px;background-color: white;line-height: 100px;text-align: center;font-size: 17px;">
                <div class="mui-col-xs-6 mui-col-sm-6 mui-row" style="height: 100%;" onclick="window.location.href='${home}/wx/common/female/lesson/index.html'">
                    <div class="mui-col-xs-6 mui-col-sm-6" style="height: 100%;">
                        <img src="${home}/weixin/image/common/female/nvgongketang.png" style="width: 80%;">
                    </div>
                    <div class="mui-col-xs-6 mui-col-sm-6" style="height: 100%;">
                        女子课堂
                    </div>
                </div>

                <div class="mui-col-xs-6 mui-col-sm-6 mui-row" style="height: 100%;" onclick="window.location.href='${home}/wx/common/female/mama/index.html'">
                    <div class="mui-col-xs-6 mui-col-sm-6" style="height: 100%;">
                        <img src="${home}/weixin/image/common/female/mamaxiaowu.png" style="width: 80%;">
                    </div>
                    <div class="mui-col-xs-6 mui-col-sm-6" style="height: 100%;">
                        妈妈小屋
                    </div>
                </div>
            </div>

            <div style="background-color: white;height: 40px;margin-top: 10px;padding-top: 20px;">
                <hr style="border-top: 1px solid red;margin:0px;">
                <p style="margin-bottom: 0px;width: 100px;margin: 0 auto;text-align: center;font-size: 17px;color: black;background-color: white;margin-top: -14px;z-index: 10;">
                    最新课堂</p>
            </div>

            <ul class="mui-table-view">
                <c:choose>
                    <c:when test="${lesson != null && fn:length(lesson) >0}">
                        <c:forEach items="${lesson}" var="item">
                            <li class="mui-table-view-cell li-item" data-id="${item.id}">
                                <div class="mui-row" style="height: 120px;">
                                    <div class="mui-col-xs-4 mui-col-sm-4 ui-img-div" style="height: 100%;">
                                        <img src="${home}${item.thumb}">
                                    </div>
                                    <div class="mui-col-xs-8 mui-col-sm-8" style="height: 100%;padding: 15px;">
                                        <p class="mui-ellipsis"
                                           style="color: black;font-size: 18px;margin-bottom: 5px;">
                                                ${item.name}
                                        </p>
                                        <p class="mui-ellipsis-2">${item.content}</p>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <li class="mui-table-view-cell" style="text-align: center;">
                            暂无
                        </li>
                    </c:otherwise>
                </c:choose>

            </ul>

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

    window.onload = function () {
        $("body").on("tap", ".li-item", function () {
            var id = $(this).attr("data-id");
            window.location.href = "${home}/wx/common/female/lesson/show.html?id=" + id;
        })
    }

</script>
</body>

</html>
