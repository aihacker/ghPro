<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/2
  Time: 16:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学习计划</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .main-timeline {
            width: 100%;
            margin: 20px auto;
            position: relative;
        }

        .main-timeline:before {
            content: "";
            display: block;
            width: 2px;
            height: 100%;
            background: rgba(37, 48, 59, .4);
            position: absolute;
            top: 0;
            left: 10%;
        }

        .main-timeline .timeline {
            width: 100%;
            margin-bottom: 20px;
            position: relative;
        }

        .main-timeline .timeline:after {
            content: "";
            display: block;
            clear: both;
        }

        .main-timeline .timeline-content {
            width: 75%;
            float: right;
            margin: 5px 5% 0 0;
            border-radius: 6px;
        }

        .main-timeline .point {
            display: block;
            width: 20px;
            height: 20px;
            border-radius: 50%;
            background: #25303b;
            padding: 5px 0;
            position: absolute;
            top: 20px;
            left: 10%;
            font-size: 12px;
            font-weight: 900;
            text-transform: uppercase;
            color: rgba(255, 255, 255, 0.5);
            border: 2px solid rgba(255, 255, 255, 0.2);
            box-shadow: 0 0 0 7px #25303b;
            margin-left: -10px;
        }

        .main-timeline .title {
            padding: 15px;
            margin: 0;
            font-size: 20px;
            color: #fff;
            text-transform: uppercase;
            letter-spacing: -1px;
            border-radius: 6px 6px 0 0;
            position: relative;
            background: #58b25e;
        }

        .main-timeline .title:after {
            content: "";
            width: 10px;
            height: 10px;
            position: absolute;
            top: 20px;
            left: -5px;
            transform: rotate(-45deg);
            background: #58b25e;
        }

        .main-timeline .description {
            padding: 15px;
            margin: 0;
            font-size: 14px;
            color: #656565;
            background: #fff;
            border-radius: 0 0 6px 6px;
        }

        .ui-empty {
            padding: 15px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <c:choose>
        <c:when test="${empty plans}">
            <div class="ui-empty">暂无学习计划哦</div>
        </c:when>
        <c:otherwise>
            <div class="main-timeline">
                <c:forEach items="${plans}" var="p">
                    <div class="timeline">
                        <div class="timeline-content">
                            <span class="point"></span>
                            <h2 class="title"><fmt:formatDate value="${p.startTime}" pattern="yyyy-MM-dd"/></h2>
                            <p class="description">${p.content}
                                <c:if test="${!empty p.examId}">
                                    <br><a href="show.html?id=${p.examId}">点击查看</a>
                                </c:if>
                            </p>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    $(function () {

    })
</script>
</body>
</html>