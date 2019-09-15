<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/17
  Time: 17:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="date" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>台账明细列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>

        body {
            background-color: #fff;
            font-family: "微软雅黑";
        }

        .left-img {
            width: 40px;
            height: 40px;
            display: block;
            border-radius: 50%;
            color: #fff;
            margin-top: 12px;
            margin-right: 10px;
            text-align: center;
        }

        .buytime-span {
            font-size: 14px;
            color: #8f8f94;
        }

        .mui-media .mui-ellipsis {
            color: #000;
            padding-right: 20px;
            padding-top: 4px;
        }

        .left-color-zhise {
            background-color: #d0659b;
        }

        .left-color-huang {
            background-color: #ff972d;
        }

        .left-color-green {
            background-color: #5ed198;
        }

        .left-color-blue {
            background-color: #669aff;
        }

        .mui-content {
            background: #fff;
        }
    </style>
</head>

<body>
<header class="mui-bar mui-bar-nav ui-head">
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

    <h1 class="mui-title">台账明细列表</h1>
</header>

<div class="mui-content">

    <c:choose>
        <c:when test="${empty details}">
            <div class="mui-content-padded mui-text-center">
                暂无明细列表哦
            </div>
        </c:when>
        <c:otherwise>
            <ul class="mui-table-view">
                <c:forEach items="${details}" var="d" varStatus="i">
                    <li class="mui-table-view-cell mui-media">
                        <a href="${home}/wx/four/show.html?id=${d.id}&type=2" class="mui-navigate-right">
                            <div class="mui-media-object mui-pull-left"><span
                                    class="left-img left-color-green">${i.index+1}</span></div>
                            <div class="mui-media-body">
                                    ${d.deptName}
                                <p class="mui-ellipsis">${d.fpcName}</p>
                                <span class="buytime-span">采购时间：<date:formatDate value="${d.time}"
                                                                                 pattern="yyyy年MM月"/></span>
                            </div>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()
</script>
</body>

</html>