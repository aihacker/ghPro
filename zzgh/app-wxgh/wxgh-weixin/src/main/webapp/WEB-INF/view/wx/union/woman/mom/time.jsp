<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/14
  Time: 15:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>小屋开放时间</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .table-div {
            padding: 10px 15px;
        }

        .table-div h4 {
            text-align: center;
            padding: 10px 0;
        }

        .table {
            background-color: #fff;
        }

        table td,
        table th {
            border: solid #646464;
            border-width: 1px 1px 1px 1px;
            padding: 5px 0px;
            min-width: 110px;
            position: relative;
        }

        table tr td {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <c:if test="${!empty times}">
        <div class="ui-fixed-bottom">
            <button id="yuyueBtn" type="button" class="mui-btn mui-btn-primary">立即预约</button>
        </div>
    </c:if>
    <div class="table-div">
        <h4>小屋开放时间</h4>
        <table class="table mui-table">
            <thead>
            <tr>
                <th>星期</th>
                <th>开放时间</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${empty times}">
                    <tr>
                        <td colspan="2" class="ui-empty">未开放任何时间</td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${times}" var="t">
                        <tr>
                            <td>${t.weekName}</td>
                            <td>${t.time}</td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    var momId = '${param.id}';
    $(function () {
        <c:if test="${!empty times}">
        $('#yuyueBtn').on('tap', function () {
            mui.openWindow('yuyue.html?id=' + momId);
        });
        </c:if>
    })
</script>
</body>
</html>