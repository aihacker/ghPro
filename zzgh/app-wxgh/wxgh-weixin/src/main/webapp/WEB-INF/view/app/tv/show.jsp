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
    <c:if test="${species == 4}">
        <c:if test="${hasOne}">
            <h5 class="ui-h5-title ui-margin-top-10">小组赛</h5>
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/stage/event_tv/schedule.html?eventId=${param.id}&stageId=${stateId1}"
                       class="mui-navigate-right">
                        赛事日程表
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/api/tv/query_score.html?eventId=${param.id}&real=false&stageCount=1&scroll=false"
                       class="mui-navigate-right">
                        小组赛赛程结果
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/stage/one/standings_show.html?eventId=${param.id}"
                       class="mui-navigate-right">
                        积分榜
                    </a>
                </li>
            </ul>
            <c:if test="${!hasTwo}">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell">
                        <div class="mui-text-center ui-text-info">淘汰赛阶段还没有开始哦~</div>
                    </li>
                </ul>
            </c:if>
        </c:if>
        <c:if test="${hasTwo}">
            <h5 class="ui-h5-title ui-margin-top-10">淘汰赛</h5>
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/stage/event_tv/schedule.html?eventId=${param.id}&stageId=${stateId2}"
                       class="mui-navigate-right">
                        赛事日程表
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/api/tv/query_score.html?eventId=${param.id}&real=true&stageCount=2&scroll=false"
                       class="mui-navigate-right">
                        淘汰赛赛程结果
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/stage/two/standings_show.html?stageId=${stateId2}"
                       class="mui-navigate-right">
                        赛事名次
                    </a>
                </li>
            </ul>
        </c:if>
        <c:if test="${!hasOne and !hasTwo}">
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    <div class="mui-text-center ui-text-info">比赛还没有开始哦~</div>
                </li>
            </ul>
        </c:if>
    </c:if>
    <c:if test="${species !=4}">
    <c:choose>
        <c:when test="${hasOne && hasTwo}">
            <h5 class="ui-h5-title ui-margin-top-10">第一阶段比赛</h5>
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/stage/event_tv/schedule.html?eventId=${param.id}&stageId=${stateId1}"
                       class="mui-navigate-right">
                        赛事日程表
                    </a>
                </li>
				<li class="mui-table-view-cell">
                    <a href="${HOST}/imatch/scoring/captain/wx_show.html?stageId=${stateId1}"
                       class="mui-navigate-right">
                        赛事排阵
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
            </ul>
            <h5 class="ui-h5-title ui-margin-top-10">第二阶段比赛</h5>
                <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/stage/event_tv/schedule.html?eventId=${param.id}&stageId=${stateId2}"
                        class="mui-navigate-right">
                            赛事日程表
                    </a>
                </li>
				<li class="mui-table-view-cell">
                    <a href="${HOST}/imatch/scoring/captain/wx_show.html?stageId=${stateId2}"
                       class="mui-navigate-right">
                        赛事排阵
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
                    <li class="mui-table-view-cell">
                        <a href="${HOST}/ivenue/stage/two/standings_show.html?stageId=${stateId2}"
                           class="mui-navigate-right">
                            赛事名次
                        </a>
                    </li>
            </ul>
    </c:when>
    <c:otherwise>
        <c:if test="${hasOne}">
            <h5 class="ui-h5-title ui-margin-top-10">第一阶段比赛</h5>
            <ul class="mui-table-view">
				<li class="mui-table-view-cell">
					<a href="${HOST}/ivenue/stage/event_tv/schedule.html?eventId=${param.id}&stageId=${stateId1}"
						class="mui-navigate-right">
						赛事日程表
					</a>
				</li>
				<li class="mui-table-view-cell">
                    <a href="${HOST}/imatch/scoring/captain/wx_show.html?stageId=${stateId1}"
                       class="mui-navigate-right">
                        赛事排阵
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
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/stage/one/standings_show.html?eventId=${param.id}"
                       class="mui-navigate-right">
                        赛事名次
                    </a>
                </li>
            </ul>
        </c:if>
        <c:if test="${hasTwo}">
            <h5 class="ui-h5-title ui-margin-top-10">第一阶段比赛</h5>
            <ul class="mui-table-view">
				<li class="mui-table-view-cell">
					<a href="${HOST}/ivenue/stage/event_tv/schedule.html?eventId=${param.id}&stageId=${stateId2}"
						class="mui-navigate-right">
						赛事日程表
					</a>
				</li>
					<li class="mui-table-view-cell">
						<a href="${HOST}/imatch/scoring/captain/wx_show.html?stageId=${stateId2}"
						   class="mui-navigate-right">
							赛事排阵
						</a>
					</li>
				<li class="mui-table-view-cell">
					<a href="${HOST}/ivenue/api/wx/query_score.html?eventId=${param.id}&real=true&stageCount=2&scroll=false"
						class="mui-navigate-right">
						第一阶段实时比分
					</a>
				</li>
				<li class="mui-table-view-cell">
					<a href="${HOST}/ivenue/api/wx/query_score.html?eventId=${param.id}&real=false&stageCount=2&scroll=false"
						class="mui-navigate-right">
						第一阶段赛事结果
					</a>
				</li>
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/stage/two/standings_show.html?stageId=${stateId2}"
                       class="mui-navigate-right">
                        赛事名次
                    </a>
                </li>
            </ul>
        </c:if>
        <c:if test="${!hasOne and !hasTwo}">
            <ul class="mui-table-view">
                <li class="mui-table-view-cell">
                    <div class="mui-text-center ui-text-info">挑战阶段-第一阶段还没有开始哦~</div>
                </li>
            </ul>
        </c:if>
    </c:otherwise>
    </c:choose>

    <c:if test="${hasThree}">
    <h5 class="ui-h5-title ui-margin-top-10">挑战阶段一</h5>
    <ul class="mui-table-view">
        <c:choose>
            <c:when test="${hasThree}">
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/stage/event_tv/schedule.html?eventId=${cEventId1}&stageId=${stateId3}"
                       class="mui-navigate-right">
                        挑战阶段-赛事日程表
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/api/wx/query_score.html?eventId=${cEventId1}&real=true&stageCount=1&scroll=false"
                       class="mui-navigate-right">
                        挑战阶段一 实时比分
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/api/wx/query_score.html?eventId=${cEventId1}&real=false&stageCount=1&scroll=false"
                       class="mui-navigate-right">
                        挑战阶段一 比赛结果
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="mui-table-view-cell">
                    <div class="mui-text-center ui-text-info">挑战阶段-第一阶段还没有开始哦~</div>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>


    <h5 class="ui-h5-title ui-margin-top-10">挑战阶段二</h5>
    <ul class="mui-table-view">
        <c:choose>
            <c:when test="${hasFour}">
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/stage/event_tv/schedule.html?eventId=${cEventId2}&stageId=${stateId4}"
                       class="mui-navigate-right">
                        挑战阶段-赛事日程表
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/api/wx/query_score.html?eventId=${cEventId2}&real=true&stageCount=2&scroll=false"
                       class="mui-navigate-right">
                        挑战阶段二 实时比分
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="${HOST}/ivenue/api/wx/query_score.html?eventId=${cEventId2}&real=false&stageCount=2&scroll=false"
                       class="mui-navigate-right">
                        挑战阶段二 比赛结果
                    </a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="mui-table-view-cell">
                    <div class="mui-text-center ui-text-info">挑战阶段-第二阶段还没有开始哦~</div>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
    </c:if>
    </c:if>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
</body>
</html>
