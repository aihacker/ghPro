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
    <title>${apply.fpName}-${apply.fpcName}</title>
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

    <%--<h1 class="mui-title">${apply.fpName}-${apply.fpcName}</h1>--%>
<%--</header>--%>

<div class="mui-scroll-wrapper ui-scroll-wrapper">
    <div class="mui-scroll">

        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">申请时间</label>
                    <span><fmt:formatDate value="${apply.applyTime}" type="both"/></span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">申请部门</label>
                    <span>${apply.deptName}</span>
                </a>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-15">
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">四小项目</label>
                    <span>${apply.fpName}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">项目内容</label>
                    <span>${apply.fpcName}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">品牌</label>
                    <span>${apply.brand}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">规格型号</label>
                    <span>${apply.modelName}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">数量</label>
                    <span>${apply.numb}个</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">设备状态</label>
                    <span>${apply.deviceStatus eq 1?'新购':'维修'}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">预算金额</label>
                    <span>${apply.budget}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-ellipsis-2">
                    <label class="ui-li-label ui-text-info">备注说明</label>
                    <span>${apply.remark}</span>
                </a>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-15">
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
                        <span class="ui-text-primary">未审核</span>
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
