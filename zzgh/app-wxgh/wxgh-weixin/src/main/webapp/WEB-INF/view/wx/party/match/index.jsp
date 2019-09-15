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
    <title>比赛列表</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view-cell:after{
            background-color: initial;
        }
        .mui-card{
            margin: 0px;
        }
        p.p-status{
            /* background: black; */
            /* opacity: 0.5; */
            height: 35px;
            /* margin-top: -22px; */
            text-indent: 10px;
            font-size: 15px;
            color: black;
            line-height: 35px;
        }
    </style>
</head>
<body>

<div class="mui-content mui-scroll-wrapper">

    <ul class="mui-table-view mui-scroll" style="background-color: #efeff4;margin-top: 0px;">

        <c:forEach items="${list}" var="item">
            <li class="mui-table-view-cell" onclick="window.location.href='${home}/wx/party/match/detail.html?id=${item.id}'">
                <div class="mui-card">
                    <div class="mui-card-content ui-img-div" style="height: 150px;">
                        <img src="${home}${item.cover}"/>
                    </div>
                    <p class="p-status mui-ellipsis">${item.name}</p>
                    <p style="text-indent: 10px;">
                        <c:choose>
                            <c:when test="${item.status == 1}">
                                活动尚未开始，倒计时
                            </c:when>
                            <c:when test="${item.status == 2}">
                                进行中，剩余
                            </c:when>
                            <c:otherwise>
                                活动已结束，逾期
                            </c:otherwise>
                        </c:choose>
                        <span style="color: red;">${item.statusVal}</span>天
                    </p>
                </div>
            </li>
        </c:forEach>

    </ul>

</div>


<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init({
        swipeBack: true //启用右滑关闭功能
    });
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    $(function () {

    });

</script>
</body>
</html>
