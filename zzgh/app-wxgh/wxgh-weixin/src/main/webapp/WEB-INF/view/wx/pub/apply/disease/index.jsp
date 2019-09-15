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
    <title>"${apply.category}"申请</title>
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

    <%--<h1 class="mui-title">"${apply.category}"申请</h1>--%>
<%--</header>--%>

<div class="mui-scroll-wrapper ui-scroll-wrapper">
    <div class="mui-scroll">

        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">申请人</label>
                    <span>${apply.name}</span>
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
                    <label class="ui-li-label ui-text-info">申请类别</label>
                    <span>${apply.category}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">申请金额</label>
                    <span>${apply.applyMoney}元</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">申请时间</label>
                    <span><fmt:formatDate value="${apply.applyTime}" type="both"/></span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-ellipsis-2">
                    <label class="ui-li-label ui-text-info">备注信息</label>
                    <span>${apply.remark}</span>
                </a>
            </li>
        </ul>

        <c:if test="${apply.step lt 2}">
            <ul class="mui-table-view ui-margin-top-15">
                <li class="mui-table-view-cell">
                    <a class="mui-ellipsis-2 mui-navigate-right"
                       href="${home}/wx/common/disease/apply${apply.cateId}/index.html?id=${apply.id}">
                        <label class="ui-li-label">系统提示</label>
                        <span class="ui-text-danger">资料待补全哦</span>
                    </a>
                </li>
            </ul>
        </c:if>

        <c:choose>
            <c:when test="${apply.status eq 0}">
                <ul class="mui-table-view ui-margin-top-15">
                    <li class="mui-table-view-cell">
                        <a>
                            <label class="ui-li-label ui-text-info">审核结果</label>
                            <span class="ui-text-primary">未审核</span>
                        </a>
                    </li>
                </ul>
            </c:when>
            <c:otherwise>
                <ul class="mui-table-view ui-margin-top-15">
                    <li class="mui-table-view-cell">
                        <a>
                            <label class="ui-li-label ui-text-info">审核时间</label>
                            <span><fmt:formatDate value="${apply.auditTime}" type="both"/></span>
                        </a>
                    </li>
                    <li class="mui-table-view-cell">
                        <a>
                            <label class="ui-li-label ui-text-info">审核资金</label>
                            <span>${apply.auditMoney}</span>
                        </a>
                    </li>
                    <li class="mui-table-view-cell">
                        <a class="mui-ellipsis-2">
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
                        </a>
                    </li>
                </ul>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script type="text/javascript">
    mui.init()
    mui('.ui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    //    var noinfoBtn = document.getElementById('noInfoBtn')
    //    if (noinfoBtn) {
    //        noinfoBtn.addEventListener('tap', function () {
    //
    //        })
    //    }

</script>
</body>

</html>
