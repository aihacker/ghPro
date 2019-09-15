<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/9
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <title>【创新建议】${data.title}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view-cell span:last-child {
            display: inline-block;
        }
    </style>
</head>

<body>
<%--<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">创新化建议</h1>
</header>--%>

<div class="mui-scroll-wrapper  ui-scroll-wrapper-bootom">
    <div class="mui-scroll">
        <ul class="mui-table-view">
            <li class="mui-table-view-cell mui-text-center">
                基本信息
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">标题</label>
                <span>${data.title}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">类别</label>
                <span>
                    <c:choose>
                        <c:when test="${data.type == 1}">技能</c:when>
                        <c:when test="${data.type == 2}">营销</c:when>
                        <c:when test="${data.type == 3}">服务</c:when>
                        <c:when test="${data.type == 4}">管理</c:when>
                        <c:otherwise>其它</c:otherwise>
                    </c:choose>
                </span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">单位</label>
                <span>${data.deptname}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">创新建议</label>
                <p class="ui-info-p">
                    ${data.advice}
                </p>
            </li>
        </ul>
        <c:choose>
            <c:when test="${showType == 1}">

            </c:when>
            <c:when test="${showType == 2}">
                <ul class="mui-table-view ui-margin-top-15">
                    <li class="mui-table-view-cell mui-text-center">
                        <label class="ui-li-label ui-text-info">审核状态</label>
                    </li>
                    <li class="mui-table-view-cell mui-text-center">

                <span>
                    <c:choose>
                        <c:when test="${data.applyStatus == 0}">审核中</c:when>
                        <c:when test="${data.applyStatus == 1}">通过</c:when>
                        <c:when test="${data.applyStatus == 2}">不通过</c:when>
                        <c:otherwise>未知状态</c:otherwise>
                    </c:choose>
                </span>
                    </li>
                    <c:if test="${data.auditIdea != null}">
                        <li class="mui-table-view-cell mui-text-center">
                            <label class="ui-li-label ui-text-info">审核意见</label><br>
                            <textarea readonly="readonly">${data.auditIdea}</textarea>
                        </li>
                    </c:if>
                    <c:if test="${data.auditIdea != null}">
                        <li class="mui-table-view-cell">
                            <label class="ui-li-label ui-text-info">审核时间</label>
                            <span>
                        <fmt:formatDate value="${data.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                    </span>
                        </li>
                    </c:if>
                </ul>
            </c:when>
        </c:choose>
    </div>
</div>
<c:if test="${data.applyStatus eq 1}">
    <div class="ui-fixed-bottom">
        <button id="priceBtn" type="button" class="mui-btn ui-btn mui-btn-block mui-btn-success">资金申请</button>
    </div>
</c:if>
<c:if test="${data.applyStatus eq 3}">
    <div class="ui-fixed-bottom">
        <button id="showBtn" type="button" class="mui-btn ui-btn mui-btn-block mui-btn-primary">查看资金申请</button>
    </div>
</c:if>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    $(function () {
        var id = '${param.id}'
        mui('.mui-scroll-wrapper').scroll({
            deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
        });

        $('#priceBtn').on('tap', function () {
            mui.openWindow(homePath + '/wx/union/innovation/member/micro/index.html?id=' + id)
        })
        $('#showBtn').on('tap', function () {
            mui.openWindow(homePath + '/wx/union/innovation/member/micro/detail.html?adviceId=' + id + '&workType=3')
        })
    })
</script>
<script>

</script>
</body>

</html>
