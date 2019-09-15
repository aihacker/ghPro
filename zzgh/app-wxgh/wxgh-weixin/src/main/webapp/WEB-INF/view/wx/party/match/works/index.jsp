<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/17
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>

<head>
    <title>所有作品</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <style>
        .mui-table-view-cell:after {
            background-color: initial;
        }

        .mui-card {
            margin: 0px;
        }

        p.p-status {
            height: 35px;
            text-indent: 10px;
            font-size: 15px;
            color: black;
            line-height: 35px;
        }

        .mui-table-view-cell {
            padding: 8px 5px;
        }

        span.span-index {
            font-size: 31px;
            background-color: black;
            color: white;
            border-radius: 50%;
            padding: 5px;
            margin-left: 2px;
        }

        p.p-index {
            width: 55px;
            height: 55px;
            background-color: white;
            padding: 3px;
            border-radius: 50%;
            margin-top: -25px;
        }

    </style>
</head>
<body>

<div class="mui-content mui-scroll-wrapper">

    <ul class="mui-table-view mui-scroll" style="background-color: #efeff4;margin-top: 0px;">
        <c:choose>
            <c:when test="${!empty joins}">
                <c:forEach items="${joins}" var="item" varStatus="status">
                    <li class="mui-table-view-cell">
                        <div class="mui-card">
                            <div class="mui-card-content" style="height: 200px;
                            <c:if test='${item.type == 2}'>margin-bottom: 16px;</c:if>
                            <c:if test="${item.type == 2}">overflow: hidden;</c:if>">
                                <c:if test="${item.type == 1}">
                                    <div class="mui-slider">
                                        <div class="mui-slider-group mui-slider-loop">
                                            <c:forEach items="${item.files}" var="img">
                                                <div class="mui-slider-item">
                                                    <div class="ui-img-div" style="height: 100%;">
                                                        <img src="${home}${img.thumb}"
                                                             style="width: 100%;max-height: initial;"
                                                             data-preview-src="${home}${img.path}"
                                                             data-preview-group="${item.id}"/>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${item.type == 2}">
                                    <c:forEach items="${item.files}" var="vid">
                                        <video src="${home}${vid.path}"
                                               style="width: 100%;height:100%;background:transparent url('${item.previewImage}') 0 0 no-repeat;-webkit-background-size:100% 100%;;-moz-background-size:100% 100%;;-o-background-size:100% 100%;;background-size: 100% 100%;"
                                               controls="controls"
                                               poster="${home}${vid.thumb}"></video>
                                    </c:forEach>
                                </c:if>
                            </div>
                            <div class="mui-row" style="z-index: 999;">
                                <div class="mui-col-xs-2 mui-col-sm-2" style="z-index: 999;">
                                    <p class="p-index">
                            <span class="span-index">
                                <c:choose>
                                    <c:when test="${status.index+1 < 10}">
                                        0${status.index+1}
                                    </c:when>
                                    <c:otherwise>${status.index+1}</c:otherwise>
                                </c:choose>
                            </span>
                                    </p>
                                </div>
                                <div class="mui-col-xs-10 mui-col-sm-10">
                                    <p class="p-status mui-ellipsis">作品名称：${item.name}</p>

                                    <div class="p-status"
                                         style="word-wrap: break-word;word-break: break-all;text-indent: 10px;">
                                        作品说明：${item.remark}</div>
                                    <p class="mui-ellipsis mui-text-right"
                                       style="margin-right: 30px;font-size: 12px;text-indent: 10px;margin-top: 5px;">
                                        投稿人：${item.username}</p>


                                    <p class="mui-ellipsis mui-text-right"
                                       style="margin-right: 30px;font-size: 12px;text-indent: 10px;margin-top: 5px;margin-bottom: 5px;">
                                        发布时间：<fmt:formatDate
                                            value="${item.addTime}" pattern="yyyy-MM-dd"></fmt:formatDate></p>

                                    <c:choose>
                                        <c:when test="${match.type == 1}">
                                            <p class="mui-ellipsis mui-text-right p-voteCount"
                                               style="margin-right: 30px;font-size: 12px;text-indent: 10px;margin-top: 5px;">
                                                投票数：<span>${item.voteCount}</span>
                                            </p>

                                            <p class="mui-ellipsis mui-text-right p-voteCount"
                                               style="margin-right: 30px;font-size: 12px;text-indent: 10px;margin-top: 5px;">
                                                全民投票
                                            </p>
                                        </c:when>
                                        <c:when test="${match.type == 2}">
                                            <p class="mui-ellipsis mui-text-right p-voteCount"
                                               style="margin-right: 30px;font-size: 12px;text-indent: 10px;margin-top: 5px;">
                                                评委筛选
                                            </p>
                                        </c:when>
                                        <c:otherwise>
                                            <p class="mui-ellipsis mui-text-right p-voteCount"
                                               style="margin-right: 30px;font-size: 12px;text-indent: 10px;margin-top: 5px;">
                                                投票数：<span>${item.voteCount}</span>
                                            </p>

                                            <p class="mui-ellipsis mui-text-right p-voteCount"
                                               style="margin-right: 30px;font-size: 12px;text-indent: 10px;margin-top: 5px;">
                                                全民投票+评委筛选
                                            </p>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:choose>
                                        <c:when test="${matchStatus == 2}">
                                            <c:if test="${(match.type == 1 ||  match.type == 3)  && matchStatus == 2}">
                                                <c:choose>
                                                    <c:when test="${item.isVote eq 0}">
                                                        <button class="mui-btn mui-btn-blue mui-pull-right"
                                                                style="margin: 10px"
                                                                data-id="${item.id}" onclick="vote(this);"
                                                                data-loading-text="投票中">投TA一票
                                                        </button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="mui-btn mui-btn-blue mui-pull-right mui-disabled"
                                                                disabled="disabled" style="margin: 10px">你已投票
                                                        </button>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
                                            <c:if test="${match.type == 2}">
                                                <button class="mui-btn mui-btn-blue mui-pull-right mui-disabled"
                                                        disabled="disabled" style="margin: 10px">评委筛选
                                                </button>
                                            </c:if>
                                        </c:when>
                                        <c:when test="${matchStatus == 1}">
                                            <button class="mui-btn mui-btn-grey mui-pull-right mui-disabled"
                                                    disabled="disabled"
                                                    style="margin: 10px">尚未开始
                                            </button>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="mui-btn mui-btn-danger mui-pull-right mui-disabled"
                                                    disabled="disabled" style="margin: 10px">已经结束
                                            </button>
                                        </c:otherwise>
                                    </c:choose>

                                </div>
                            </div>
                        </div>
                    </li>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <li class="mui-text-center">暂无作品</li>
            </c:otherwise>
        </c:choose>


    </ul>

</div>


<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script type="text/javascript">
    mui.init({
        swipeBack: true //启用右滑关闭功能
    });
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });
    wxgh.previewImageInit();
    //获得slider插件对象
    var gallery = mui('.mui-slider');
    gallery.slider({
        interval: 100000//自动轮播周期，若为0则不自动播放，默认为0；
    });

    $(function () {

    });

    function vote(btn) {
        var id = btn.getAttribute("data-id");
        btn.className += " mui-disabled";
        btn.setAttribute("disabled", "disabled");

        $.ajax({
            url: "${home}/wx/party/match/works/vote.json",
            data: {
//                action: "vote",
                joinId: id
            },
            success: function (rs) {
                callback(rs);
            }
        })
        function callback(rs) {
            if (rs.ok) {
                ui.alert("投票成功", function () {
                    $(btn).text("你已投票");
                    var voteCount = $(btn).siblings("p.p-voteCount").children("span").text();
                    voteCount = Number(voteCount);
                    voteCount++;
                    $(btn).siblings("p.p-voteCount").children("span").text(voteCount);
                });
            } else {
                ui.alert(rs.msg);
            }
        }
    }

</script>
</body>
</html>
