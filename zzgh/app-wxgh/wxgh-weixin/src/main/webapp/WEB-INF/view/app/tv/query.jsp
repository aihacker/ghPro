<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: hasee
  Date: 2018/4/3
  Time: 15:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${event.name}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>
<body>
<div class="mui-content">
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
            <a class="mui-control-item player_active">
                ${event.aPlayer}
            </a>
            <a class="mui-control-item player_active">
                ${event.bPlayer}
            </a>
        </div>
    </div>
    <div>
        <div class="mui-segmented-control">
            <a class="inning_a mui-control-item" id="score_0">
                :
            </a>
            <a class="inning_a mui-control-item" id="score_1">
                :
            </a>
            <a class="inning_a mui-control-item" id="score_2">
                :
            </a>
            <c:if test="${event.pointSystem == 11}">
                <a class="inning_a mui-control-item" id="score_3">
                    :
                </a>
                <a class="inning_a mui-control-item" id="score_4">
                    :
                </a>
            </c:if>
        </div>
    </div>
    <div class="ui-img-div" style="height: 40%;background-color: #1ab394">
        <span id="nowScoreA" class="span_score"></span>
        <span style="font-size: 100px;color: #FFFFFF;">:</span>
        <span id="nowScoreB" class="span_score"></span>
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
        getScore();
        var int=self.setInterval("getScore()",1000);

    })
    function getScore() {
        var url = "score.json";
        var mid = ${event.matchId};
        var data = {
            matchId : mid
        }
        mui.getJSON(url, data, function (res) {
            if (res.ok) {
                var result = res.data;
                if(result.length>0){
                    $(".player_active").removeClass("mui-active");
                    $(".inning_a").removeClass("mui-active");
                    for(var i=0;i<result.length;i++){
                        if(i == result.length - 1){
                            $("#nowScoreA").html(result[i].scoreA);
                            $("#nowScoreB").html(result[i].scoreB);
                            $("#score_"+i).addClass("mui-active")
                        } else {
                            $("#score_" + i).html(result[i].scoreA + ":" + result[i].scoreB)
                        }
                    }
                }
            } else {
                alert(res.msg)
            }
        })
    }
</script>
</body>
</html>
