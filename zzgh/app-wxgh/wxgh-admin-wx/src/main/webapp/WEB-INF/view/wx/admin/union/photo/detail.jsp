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
            margin: 0px !important;
        }

        .mui-card:after {

        }
    </style>
</head>
<body>
<div class="mui-scroll-wrapper" style="bottom: 50px;">
    <div class="mui-scroll" style="background-color: white;">

        <div class="mui-card" style="background-color: white;">
            <div class="mui-card-content ui-img-div" style="height: 200px;">
                <img src="${home}${data.cover}" alt="" style="width: 100%;" data-preview-src=""
                     data-preview-group="0"/>
            </div>
            <div class="mui-text-center" style="background-color: white;height: 30px;font-size: 17px;margin-top: 10px;">
                ${data.name}
            </div>
        </div>

        <div style="padding: 10px;">
            <p>联系电话：${data.tel}</p>

            <p>
                比赛规则：<br>
                ${data.rule}
            </p>

            <p>参赛人员：公司各工作者</p>

            <p>
                比赛内容：<br>
                ${data.content}
            </p>

            <p>
                投票方式：
                <c:choose>
                    <c:when test="${data.type == 1}">
                        全民投票
                    </c:when>
                    <c:otherwise>
                        评委筛选
                    </c:otherwise>
                </c:choose>
            </p>

            <p>比赛时间：
                <fmt:formatDate value="${data.startTime}" pattern="yyyy/MM/dd"></fmt:formatDate>
                -
                <fmt:formatDate value="${data.endTime}" pattern="yyyy/MM/dd"></fmt:formatDate>
            </p>

        </div>
    </div>
</div>
<div class="ui-bottom-btn-group ui-fixed-bottom">

    <button id="joinBtn" data-url="/wx/admin/union/photo/works/joins.html?id=${data.id}" type="button" class="mui-btn mui-btn-outlined mui-btn-primary ui-btn">报名列表</button>

    <button data-url="/wx/admin/union/photo/works/index.html?id=${data.id}&type=${data.worksType}" type="button"
               class="mui-btn mui-btn-outlined mui-btn-yellow ui-btn">查看所有作品
    </button>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script type="text/javascript">
    var matchId = '${data.id}';
    var joinType = '${data.worksType}';
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });
    wxgh.previewImageInit();
    $(function () {

        $('.ui-bottom-btn-group').on('tap', 'button[data-url]', function () {
            var url = $(this).data('url')
            mui.openWindow(homePath + url)
        })

    });

</script>
</body>
</html>
