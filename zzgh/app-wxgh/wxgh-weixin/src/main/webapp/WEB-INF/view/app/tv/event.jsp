<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/6
  Time: 9:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>赛事列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>
<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-status="0" class="mui-control-item mui-active">比赛直播
            <small></small>
        </a>
        <a data-status="1" class="mui-control-item">所有比赛
            <small></small>
        </a>
        <a data-status="2" class="mui-control-item">实时比分
            <small></small>
        </a>
    </div>
    <!-- 比赛列表 -->
    <div id="event_div">
        <div class="ui-img-div" style="padding: 20px">
            <h1 style="color: #428bca;">正在直播的比赛</h1>
        </div>
        <ul class="mui-table-view">
            <c:choose>
                <c:when test="${empty events}">
                    <li class="mui-table-view-cell">
                        <div class="mui-content-padded mui-text-center ui-text-info">
                            暂无直播赛事哦
                        </div>
                    </li>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${events}" var="e">
                        <li class="mui-table-view-cell">
                            <a href="query.html?code=${e.code}" class="mui-navigate-right">
                                    ${e.eventName}
                                <small>（${e.stageName}）</small>
                                - ${e.projectName}
                                <p>
                                    队伍双方：${e.teamA} VS ${e.teamB} <small>(第${e.subMatchNo}场)</small>
                                </p>
                                <p>
                                    直播时间：${e.timeStr}
                                </p>
                                <p>
                                    场地：${e.spacename}
                                </p>
                            </a>
                        </li>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
    <!-- 所有比赛 -->
    <div id="all_event_div" style="display: none">
        <ul class="mui-table-view">
            <c:choose>
                <c:when test="${empty events}">
                    <li class="mui-table-view-cell">
                        <div class="mui-content-padded mui-text-center ui-text-info">
                            暂无直播赛事哦
                        </div>
                    </li>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${eventList}" var="event" varStatus="status">
                        <div class="ui-img-div" style="padding-top: 20px">
                            <span style="color: #428bca;font-size: 20px">${event.name}<small>(${event.stage})</small></span>
                        </div>
                        <div class="ui-img-div">
                            <span style="color: #428bca;font-size: 16px;padding-bottom: 10px">${event.projectName}</span>
                        </div>
                        <div class="ui-img-div" style="height: 12%;">
                    <span class="span_team" style="background-color: #ff5053">
                            ${event.teamNameA}
                    </span>
                            <span class="span_team" style="background-color: #007aff">
                                    ${event.teamNameB}
                            </span>
                        </div>
                        <div>
                            <div class="mui-segmented-control">
                                <a class="mui-control-item player_active_${status.count}">
                                        ${event.aPlayer}
                                </a>
                                <a class="mui-control-item player_active_${status.count}">
                                        ${event.bPlayer}
                                </a>
                            </div>
                        </div>
                        <div>
                            <div class="mui-segmented-control">
                                <a class="inning_a_${status.count} mui-control-item" id="score_0_${status.count}">
                                    :
                                </a>
                                <a class="inning_a_${status.count} mui-control-item" id="score_1_${status.count}">
                                    :
                                </a>
                                <a class="inning_a_${status.count} mui-control-item" id="score_2_${status.count}">
                                    :
                                </a>
                                <c:if test="${event.pointSystem == 11}">
                                    <a class="inning_a_${status.count} mui-control-item" id="score_3_${status.count}">
                                        :
                                    </a>
                                    <a class="inning_a_${status.count} mui-control-item" id="score_4_${status.count}">
                                        :
                                    </a>
                                </c:if>
                            </div>
                        </div>
                        <div class="ui-img-div" style="height: 40%;background-color: #1ab394">
                            <span id="nowScoreA_${status.count}" class="span_score"></span>
                            <span style="font-size: 100px;color: #FFFFFF;">:</span>
                            <span id="nowScoreB_${status.count}" class="span_score"></span>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<style type="text/css">
    .span_score{
        width: 100%;
        color: #FFFFFF;
        font-size: 100px;
        text-align: center;
    }
    .span_team{
        width: 100%;
        height: 100%;
        color: #FFFFFF;
        font-size: 24px;
        text-align: center;
    }
</style>
<script>
    $(function () {
        var $segment = $('#segmentedControl')
        var timer;
        $segment.data('status', 0)
        $segment.on('tap', 'a.mui-control-item', function () {
            var status = $(this).attr('data-status')
            if(status == 0){
                $("#event_div").show();
                $("#all_event_div").hide();
                clearInterval(timer);
            }
            if(status == 1){
                $("#event_div").hide();
                $("#all_event_div").show();
                getScore(${matchs});
                timer = self.setInterval("getScore(${matchs})",2000);
            }
            if(status == 2){
                window.location.href = "http://zncg.fsecity.com:8081/ivenue/api/tv/today_score.html";

            }
        })
    })

    function getScore(matchs) {
        var url = "score.json";
        var data = {
            matchId:matchs[matchs.length-1]
        }
        mui.getJSON(url, data, function (res) {
            if (res.ok) {
                var result = res.data;
                if(result.length>0){
                    $(".player_active_"+matchs.length).removeClass("mui-active");
                    $(".inning_a_"+matchs.length).removeClass("mui-active");
                    for(var i=0;i<result.length;i++){
                        if(i == result.length - 1){
                            $("#nowScoreA_"+matchs.length).html(result[i].scoreA);
                            $("#nowScoreB_"+matchs.length).html(result[i].scoreB);
                            $("#score_"+i+"_"+matchs.length).addClass("mui-active")
                        } else {
                            $("#score_"+i+"_"+matchs.length).html(result[i].scoreA + ":" + result[i].scoreB)
                        }
                    }
                }
            } else {
                alert(res.msg)
            }
            matchs.splice(matchs.length-1,1);
            if(matchs.length>0){
                getScore(matchs)
            }
        })
    }
</script>
</body>
</html>
