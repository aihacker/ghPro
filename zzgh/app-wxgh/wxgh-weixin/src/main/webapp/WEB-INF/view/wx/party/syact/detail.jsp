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
    <title>活动详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <style>
        .mui-card {
            box-shadow: none;
            background-color: #efeff4;
        }

        mui-card:after {

        }
    </style>
</head>
<body class="mui-scroll-wrapper">

<div class="mui-content mui-scroll">

    <div class="mui-card">
        <div class="mui-card-content ui-img-div" style="height: 150px;">
            <img src="${data.cover}" alt="" style="width: 100%;" data-preview-src=""
                 data-preview-group="0"/>
        </div>
        <div class="mui-text-center" style="background-color: #efeff4;height: 30px;font-size: 18px;margin-top: 10px;">
            ${data.name}
        </div>
    </div>

    <hr>
    <div style="padding: 10px;">
        <p>联系人：${data.linkman}</p>

        <p>联系电话：${data.tel}</p>

        <p>活动时间：
            <fmt:formatDate value="${data.startTime}" pattern="yyyy/MM/d"></fmt:formatDate>
            -
            <fmt:formatDate value="${data.endTime}" pattern="yyyy/MM/d"></fmt:formatDate>
        </p>

        <p>活动地址：${data.address}</p>

        <p>活动内容：
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            ${data.content}
        </p>

        <p>备注：
            <br>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            ${data.remark}
        </p>
        <hr>
        <div class="mui-row">
            <p class="mui-col-xs-5" style="margin-bottom: 0px;    margin-top: 14px;">
                已报名（<c:out value="${fn:length(joinList)}"></c:out>）
            </p>

            <p onclick="window.location.href='${home}/wx/party/syact/join/index.html?aid=${param.id}'"
               class="mui-col-xs-7 mui-text-right" style="margin-bottom: 0px;height: 50px;">
                <c:choose>
                    <c:when test="${fn:length(joinList) > 3}">
                        <c:forEach items="${joinList}" var="item" varStatus="status">
                            <c:if test="${status.index < 3}">
                                <img src="${item.avatar}" style="height: 30px;margin-top: 10px;">
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${joinList}" var="item">
                            <img src="${item.avatar}" style="height: 30px;margin-top: 10px;">
                        </c:forEach>

                    </c:otherwise>
                </c:choose>

            </p>
        </div>
        <hr>
    </div>
    <div class=" ui-flex">
        <c:choose>
            <c:when test="${actStatus == 1}">
                <button type="button" class="mui-btn-grey mui-btn mui-btn-blue mui-btn-block" disabled="disabled"
                        style="padding: 8px 0;z-index: 2;border-radius: 0px;margin-bottom: 0px;">活动尚未开始
                </button>
            </c:when>
            <c:when test="${actStatus == 2}">
                <c:if test="${isJoin == 0}">
                    <button type="button" id="btn-join" class="mui-btn-blue mui-btn mui-btn-blue mui-btn-block"
                            style="padding: 8px 0;z-index: 2;border-radius: 0px;margin-bottom: 0px;">我要报名
                    </button>
                </c:if>
                <c:if test="${isJoin == 1}">
                    <button type="button" class="mui-btn-green mui-btn mui-btn-blue mui-btn-block mui-disabled" disabled="disabled"
                            style="padding: 8px 0;z-index: 2;border-radius: 0px;margin-bottom: 0px;">已报名
                    </button>
                </c:if>
            </c:when>
            <c:when test="${actStatus == 3}">
                <button type="button" class="mui-btn-danger mui-btn mui-btn-blue mui-btn-block" disabled="disabled"
                        style="padding: 8px 0;z-index: 2;border-radius: 0px;margin-bottom: 0px;">活动已结束
                </button>
            </c:when>
        </c:choose>
    </div>
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

    $(function () {
        $("#btn-join").click(function () {
            $.ajax({
                url: "${home}/wx/party/syact/join/join.json",
                data: {
//                    action: "join",
                    aid: ${param.id}
                },
                success: function (rs) {
                    if (rs.ok) {
                        mui.alert("报名成功", function () {
                            window.location.reload();
                        });
                    } else {
                        mui.alert("报名失败，请稍后再试");
                    }
                }
            })
        });
    });

</script>
</body>
</html>
