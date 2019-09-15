<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/6
  Time: 9:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>赛事详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>
<div class="mui-content">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a href="team.html?id=${param.id}" class="mui-navigate-right">
                查看参赛队伍
            </a>
        </li>
    </ul>
    <h5 class="ui-h5-title ui-margin-top-10">第一阶段比赛</h5>
    <ul class="mui-table-view">
        <%--<c:choose>--%>
        <%--<c:when test1="${hasOne}">--%>
        <li class="mui-table-view-cell">
            <a href="${HOST}/ivenue/stage/event_tv/schedule.html?eventId=${param.id}&stageId=${stateId1}"
               class="mui-navigate-right">
                赛事日程表
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a href="${HOST}/ivenue/api/wx/query_score.html?eventId=${param.id}&real=true&stageCount=1&scroll=false"
               class="mui-navigate-right">
                第一阶段实时比分
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a href="${HOST}/ivenue/api/wx/query_score.html?eventId=${param.id}&real=false&stageCount=1&scroll=false"
               class="mui-navigate-right">
                第一阶段赛事结果
            </a>
        </li>
        <%--</c:when>--%>
        <%--<c:otherwise>--%>
        <%--<li class="mui-table-view-cell">--%>
        <%--<div class="mui-text-center ui-text-info">第一阶段还没有开始哦~</div>--%>
        <%--</li>--%>
        <%--</c:otherwise>--%>
        <%--</c:choose>--%>
    </ul>

    <h5 class="ui-h5-title ui-margin-top-10">第二阶段比赛</h5>
    <ul class="mui-table-view">
        <c:choose>
            <c:when test="${hasTwo}">
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/stage/event_tv/schedule.html?eventId=${param.id}&stageId=${stateId2}"
                       class="mui-navigate-right">
                        赛事日程表
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/api/wx/query_score.html?eventId=${param.id}&real=true&stageCount=2&scroll=false"
                       class="mui-navigate-right">
                        第二阶段实时比分
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/api/wx/query_score.html?eventId=${param.id}&real=false&stageCount=2&scroll=false"
                       class="mui-navigate-right">
                        第二阶段比赛结果
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="mui-table-view-cell">
                    <div class="mui-text-center ui-text-info">第二阶段还没有开始哦~</div>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
</body>
</html>
