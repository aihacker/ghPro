<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/8
  Time: 18:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>四小审核状态</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
    </style>
</head>

<body>


<div class="mui-content">
    <ul class="mui-table-view">
        <c:choose>
            <c:when test="${empty fs}">
                <li class="mui-table-view-cell">
                    <div class="mui-text-center ui-text-info">暂无审核进度哦</div>
                </li>
            </c:when>
            <c:otherwise>
                <c:forEach items="${fs}" var="f">
                    <li data-id="${f.id}" data-status="${f.deviceStatus}" class="mui-table-view-cell">
                        <div>
                                ${f.marketing}
                            <small class="mui-pull-right${f.status eq 0?'':(f.status eq 1?' ui-text-success':' ui-text-danger')}">${f.status eq 0?'未审核':(f.status eq 1?'已通过':'未通过')}</small>
                        </div>
                        <p>
                            <span>${f.fpName} - ${f.fpcName} x${f.numb}</span>
                        </p>
                        <p>
                            申请类型：${f.deviceStatus eq 1?'新增':'更换'}
                            <span class="mui-pull-right">${f.suggest}</span>
                        </p>
                    </li>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </ul>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    $(function () {
        $('.mui-table-view').on('tap', 'li', function () {
            var id = $(this).attr('data-id')
            if(id){
                var status = $(this).attr('data-status')
                mui.openWindow(homePath + '/wx/four/status/show.html?id=' + id + '&deviceStatus=' + status)
            }
        })
    })

</script>
</body>

</html>
