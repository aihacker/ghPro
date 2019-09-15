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
    <meta charset="UTF-8">
    <title>${apply.actName}</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view-cell span:last-child {
            display: inline-block;
        }
    </style>
</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

    <%--<h1 class="mui-title">${apply.actName}</h1>--%>
<%--</header>--%>

<div class="mui-scroll-wrapper ui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">活动名称</label>
                </a>

                <div style="word-break: break-all;">${apply.actName}</div>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">活动时间</label>
                    <span><fmt:formatDate value="${apply.actTime}" pattern="yyyy-MM-dd HH:mm"/> </span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">预计人数</label>
                    <span>${apply.joinNumb}人</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">预计金额</label>
                    <span>${apply.actCost}元</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">联系电话</label>
                    <span>${apply.mobile}</span>
                </a>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-15">
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">申请时间</label>
                    <span><fmt:formatDate value="${apply.applyTime}" type="both"></fmt:formatDate></span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-ellipsis-2">
                    <label class="ui-li-label ui-text-info">活动地点</label>
                </a>

                <div style="word-break: break-all;">${apply.address}</div>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">活动内容</label>
                </a>

                <div style="word-break: break-all;">${apply.actInfo}</div>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">申请缘由</label>
                </a>

                <div style="word-break: break-all;">${apply.reason}</div>
            </li>
        </ul>
        <ul class="mui-table-view ui-margin-top-15">
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">审核时间</label>
                    <span><fmt:formatDate value="${apply.auditTime}" type="both"></fmt:formatDate></span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">审核费用</label>
                    <span>${apply.auditCost}元</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a style="overflow: scroll">
                    <label class="ui-li-label ui-text-info">审核意见</label>
                    <span>${apply.auditIdea}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">审核结果</label>
                    <c:if test="${apply.status eq 1}">
                        <span class="ui-text-success">审核成功</span>
                    </c:if>
                    <c:if test="${apply.status eq 2}">
                        <span class="ui-text-danger">审核未通过</span>
                    </c:if>
                    <c:if test="${apply.status eq 0}">
                        <span class="ui-text-info" style="color: #0062cc">暂时未审核</span>
                    </c:if>
                </a>
            </li>
        </ul>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()
    mui('.ui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });
</script>
</body>

</html>
