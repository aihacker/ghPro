<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/19
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>
        <c:choose>
            <c:when test="${param.type == 1}">
                公告详情
            </c:when>
            <c:otherwise>
                招募详情
            </c:otherwise>
        </c:choose>
    </title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        html, body, .mui-content {
            height: 100%;
        }

        .mui-scroll-wrapper {
            background-color: #fff;
            padding-top: 44px;
            padding-bottom: 40px;
        }

        .mui-content {
            padding-bottom: 45px;
        }

        .img-item {
            display: inline-block;
            margin: 5px;
            position: relative;
        }

        .img-item input[type=file] {
            opacity: 0;
            background-color: transparent;
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
        }

        .plus-img-div img,
        .img-item img {
            width: 100px;
            height: 100px;
        }

        .img-item .fa-minus-circle {
            content: '';
            position: absolute;
            top: -1px;
            right: -4px;
            color: #a94442;
            background-color: #fff;
            -moz-border-radius: 20px;
            -webkit-border-radius: 20px;
            border-radius: 20px;
        }

        .img-item .fa-minus-circle:hover,
        .img-item .fa-minus-circle:focus {
            color: #843534;
        }

        .mui-table-view.mui-grid-view .mui-table-view-cell .mui-media-object {
            width: 45px;
            height: 45px;
            border-radius: 50%;
        }
    </style>
</head>

<body>

<div class="mui-scroll-wrapper">
<div class="mui-scroll " style="background-color: #efeff4;">

    <div style="background-color: #FFFFFF;">
        <p style="font-size: 20px;font-family: '微软雅黑';color: black;    padding: 10px 10px 3px 10px;margin: 0px;">${notice.title}</p>
        <p style="padding-left: 10px;font-size: 16px;">
            <fmt:formatDate value="${notice.addTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
        </p>
        <c:if test="${!empty notice.img}">
            <div style="height: 200px;text-align: center;padding: 10px;background-color: #efeff4;">
                <img style="height: 100%;width: 100%;" src="${home}${notice.img}">
                    <%--<img style="height: 100%;width: 100%;" src="https://ss0.baidu.com/73F1bjeh1BF3odCf/it/u=3338393601,1519371023&fm=85&s=6BE78856004C86EE1C1FF7B90300700E">--%>
            </div>
        </c:if>
        <p style="font-size: 18px;font-family: '微软雅黑';padding: 10px 10px 3px 10px;margin: 0px;letter-spacing: 2px;">
            &nbsp;&nbsp;&nbsp;&nbsp;${notice.content}
        </p>
    </div>

    <c:choose>

        <c:when test="${param.type == 1}">
            <div style="margin-top: 10px;padding: 10px;background-color: #FFFFFF;">
                <p style="color: #0398EB;">【活动须知】</p>
                <p class="mui-ellipsis-2">联系电话：${act.phone}</p>
                <p class="mui-ellipsis-2">地点：${act.address}
                    <c:choose>
                        <c:when test="${act.addrTitle != null && act.addrTitle != ''}">
                            (${act.addrTitle})
                        </c:when>
                    </c:choose>
                </p>
                <c:if test="${act.regular eq 1}">
                    <c:forEach items="${regular}" var="item">
                        周 ${item.week} 的 ${item.startTime} 至 ${item.endTime}
                        <br>
                    </c:forEach>
                </p>
                </c:if>
                <c:if test="${act.regular eq 0}">
                <p>时间：<fmt:formatDate value="${act.startTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                    至<br>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${act.endTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                </p>
                </c:if>
                <p>内容：${act.info}</p>
            </div>
        </c:when>
        <c:otherwise>
            <div style="margin-top: 10px;padding: 10px;background-color: #FFFFFF;">
                <ul class="mui-table-view">
                    <li class="mui-table-view-cell" style="font-size: 12px;">团队成员（${fn:length(member)}）<span
                            class="mui-right mui-pull-right"><a
                            href="${home}/wx/union/innovation/team/user/index.html?groupId=${notice.pid}">更多 ></a></span>
                    </li>
                        <%--<li class="mui-table-view-cell">--%>
                        <%--<c:forEach items="${member}" var="item" varStatus="status">--%>
                        <%--<c:if test1="${status.count <= 5}">--%>
                        <%--<div style="width: 20%;float: left;text-align: center;">--%>
                        <%--<img src="${item.avatar}" style="width: 80%;border-radius: 50%;">--%>
                        <%--<p>${item.name}</p>--%>
                        <%--</div>--%>
                        <%--</c:if>--%>
                        <%--</c:forEach>--%>
                        <%--</li>--%>
                </ul>
                <div>
                    <ul class="mui-table-view mui-grid-view">
                        <c:forEach items="${member}" var="item" varStatus="status">
                            <c:if test="${status.count <= 4}">
                                <li class="mui-table-view-cell mui-media mui-col-sm-2 mui-col-xs-3">
                                    <a href="javascript:;">
                                        <img class="mui-media-object" src="${item.avatar}">
                                        <div class="mui-media-body">${item.username}</div>
                                    </a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

    <div class="ui-fixed-bottom">
        <c:choose>
            <c:when test="${param.type == 1}">
                    <a href="${home}/wx/entertain/act/team/show.html?id=${act.id}&type=4" class="mui-btn mui-btn-block mui-btn-primary ui-btn">查看活动详情</a>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${check == true}">
                        <button id="in-team" class="mui-btn mui-btn-block mui-btn-primary ui-btn">进入团队
                        </button>
                    </c:when>
                    <c:otherwise>
                        <button id="btn-join" class="mui-btn mui-btn-block mui-btn-primary ui-btn">申请加入
                        </button>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript" ></script>
<script>

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    $("#in-team").click(function () {
        mui.openWindow(homePath + "/wx/union/innovation/team/show.html?id=" + ${notice.pid});
    })

    $("#btn-join").click(function () {
        $.ajax({
            url: "${home}/wx/union/innovation/team/api/apply.json",
            data: {
//                action: "applyJoinTeam",
                id: "${notice.pid}",
                type: 4
            },
            success: function (rs) {
                if (rs.ok) {
                    mui.alert("申请已发出");
                    return;
                } else {
                    mui.alert(rs.msg);
                    return;
                }
            }
        })
    });
</script>

</body>

</html>

