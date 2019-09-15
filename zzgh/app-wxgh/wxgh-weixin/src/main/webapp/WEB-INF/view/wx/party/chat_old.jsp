<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/28
  Time: 8:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>党建在线</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-content,
        body {
            background-color: #fff;
        }

        .mui-table-view .mui-media-object {
            width: 48px;
            max-width: 48px;
            height: 48px;
        }

        .ui-tip-title {
            background-color: #efeff4;
            color: #777;
            padding: 2px 10px;
            font-size: 15px;
        }

        .ui-tip-title:first-child {
            margin-top: 15px;
        }

        .ui-empty {
            padding: 10px 0 20px 0;
        }
    </style>
</head>
<body>
<div class="mui-content">

    <c:if test="${!empty partys}">
        <div class="ui-tip-title">
            我的党支部
        </div>
        <ul class="mui-table-view no">
            <c:forEach items="${partys}" var="p">
                <li class="mui-table-view-cell mui-media">
                    <a href="${home}/wx/chat/chat.html?id=${p.id}">
                        <img class="mui-media-object ui-circle mui-pull-left"
                             src="${home}${empty p.path?'/image/default/chat.png':p.path}">
                        <div class="mui-media-body">
                                ${p.name}
                            <p class="mui-ellipsis">${empty p.info?'暂无简介':p.info}</p>
                        </div>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <div class="ui-tip-title">
        青年部落
    </div>
    <c:choose>
        <c:when test="${empty tribes}">
            <div class="ui-empty">没有加入青年部落哦 <a id="applyBtn">
                <small>申请加入</small>
            </a></div>
        </c:when>
        <c:otherwise>
            <ul class="mui-table-view no">
                <c:forEach items="${tribes}" var="p">
                    <li class="mui-table-view-cell mui-media">
                        <a href="${home}/wx/chat/chat.html?id=${p.id}">
                            <img class="mui-media-object ui-circe mui-pull-left"
                                 src="${home}${empty p.path?'/image/default/chat.png':p.path}">
                            <div class="mui-media-body">
                                    ${p.name}
                                <p class="mui-ellipsis">${empty p.info?'暂无简介':p.info}</p>
                            </div>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>

    <c:if test="${!empty dis}">
        <div class="ui-tip-title">
            纪检
            <small>（群体）</small>
        </div>
        <ul class="mui-table-view no">
            <c:forEach items="${dis}" var="p">
                <li class="mui-table-view-cell mui-media">
                    <a href="${home}/wx/chat/chat.html?id=${p.id}">
                        <img class="mui-media-object ui-circe mui-pull-left"
                             src="${home}${empty p.path?'/image/default/chat.png':p.path}">
                        <div class="mui-media-body">
                                ${p.name}
                            <p class="mui-ellipsis">${empty p.info?'暂无简介':p.info}</p>
                        </div>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </c:if>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    $(function () {
        $('#applyBtn').on('tap', function () {
            ui.confirm('是否加入青年部落？', function () {
                wxgh.request.getURL('/wx/party/api/tribe_join.json', function () {
                    ui.alert('加入成功！获得30积分', function () {
                        window.location.reload()
                    })
                })
            })
        })
    })
</script>
</body>
</html>