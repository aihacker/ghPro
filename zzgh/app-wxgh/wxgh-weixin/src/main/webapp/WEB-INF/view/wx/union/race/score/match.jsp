<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/8
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>计分板</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/weixin/style/union/race/match.css">
    <style type="text/css">
        .mui-table-view-cell select{
            opacity: 1;
        }
    </style>
</head>

<body>
<div class="mui-content">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>选择计分制</span>
                <select id="jifenSelect">
                    <option value="15">15分制</option>
                    <option value="21" selected>21分制</option>
                    <option value="25">25分制</option>
                </select>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right" style="padding-bottom: 10px;">
                <span style="top: 0;">选择比赛选手</span>
                <small class="ui-user-select">请选择</small>
            </a>
        </li>
    </ul>

    <div id="lunNumbDiv" class="mui-content-padded mui-text-center">
        第 1 轮
    </div>

    <div class="ui-race">
        <div class="ui-race-left">
            <c:if test="${!dan}">
                <span>请选择</span>
            </c:if>
            <span>请选择</span>
        </div>
        <div class="ui-race-img">
            <img id="racePanelImg" src="${home}/weixin/image/union/race/race_yumao_panel.png"/>
        </div>
        <div class="ui-race-right">
            <c:if test="${!dan}">
                <span>请选择</span>
            </c:if>
            <span>请选择</span>
        </div>
    </div>

    <div class="ui-score">
        <div class="ui-jiayi-score">
            <span>甲方</span>
            <span class="ui-score-val">
                <span>0</span>:<span>0</span>
            </span>
            <span>乙方</span>
        </div>

        <div class="ui-jia-div">
            <c:if test="${!dan}">
                <div class="ui-team-group">
                    <label>甲方</label>
                </div>
            </c:if>
            <div class="ui-item">
                <div class="ui-team">
                    <span class="ui-lable"></span>
                    <span class="ui-name">请选择</span>
                </div>
                <div class="ui-score-ban">
                    <span class="score-jian">减分数</span>
                    <span class="score-jia">添加分数</span>
                </div>
                <div class="ui-score-total">
                    <span>0</span>
                    <span>总分</span>
                </div>
            </div>
            <div class="ui-item">
                <div class="ui-team">
                    <span class="ui-lable"></span>
                    <span class="ui-name">请选择</span>
                </div>
                <div class="ui-score-ban">
                    <span class="score-jian">减分数</span>
                    <span class="score-jia">添加分数</span>
                </div>
                <div class="ui-score-total">
                    <span>0</span>
                    <span>总分</span>
                </div>
            </div>
        </div>

        <c:if test="${!dan}">
            <div class="ui-yi-div">
                <div class="ui-team-group">
                    <label>乙方</label>
                </div>
                <div class="ui-item">
                    <div class="ui-team">
                        <span class="ui-lable"></span>
                        <span class="ui-name">请选择</span>
                    </div>
                    <div class="ui-score-ban">
                        <span class="score-jian">减分数</span>
                        <span class="score-jia">添加分数</span>
                    </div>
                    <div class="ui-score-total">
                        <span>0</span>
                        <span>总分</span>
                    </div>
                </div>
                <div class="ui-item">
                    <div class="ui-team">
                        <span class="ui-lable"></span>
                        <span class="ui-name">请选择</span>
                    </div>
                    <div class="ui-score-ban">
                        <span class="score-jian">减分数</span>
                        <span class="score-jia">添加分数</span>
                    </div>
                    <div class="ui-score-total">
                        <span>0</span>
                        <span>总分</span>
                    </div>
                </div>
            </div>
        </c:if>
    </div>

    <div class="ui-rule">
        <p>
            发球规则：<br/> 1.发球员的分数为0或双数时，双方运动员均在各自的右发球区发球或接球；
            <br/> 2.发球员的分数为单数时，双方运动员均应在各自的左发球区发球或接球.
        </p>
    </div>

    <div class="ui-btn-bootom">
        <button id="jifenBtn" type="button" class="mui-btn mui-btn-success mui-btn-block ui-btn">开始计分</button>
    </div>
</div>

<div id="chooseUser" class="mui-popover">
    <div class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view mui-table-view-radio">
            </ul>
        </div>
    </div>
</div>

<div id="addScorePopup" class="mui-popup mui-popup-in mui-hidden">
    <div class="mui-popup-inner">
        <div class="mui-popup-title" id="score-op-title">添加分数</div>
        <div class="mui-numbox" id="score-op-data" data-numbox-min='-10' data-numbox-max='10'>
            <button class="mui-btn mui-btn-numbox-minus" type="button">-</button>
            <input id="numBoxInput" class="mui-input-numbox" value="1" type="number">
            <button class="mui-btn mui-btn-numbox-plus" type="button">+</button>
        </div>
    </div>
    <div class="mui-popup-buttons">
        <span class="mui-popup-button">取消</span>
        <span class="mui-popup-button mui-popup-button-bold">确定</span>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    var type = '${race.raceType}'
    var raceId = '${race.id}'
    var self = homePath + '/wx/union/race/score/api/'

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005
    });
</script>
<script src="${home}/weixin/script/union/race/match.js"></script>
</body>

</html>
