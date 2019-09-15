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
    <title>岗位创新建议</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>

    <style>
        .mui-table-view-cell span:last-child {
            display: inline-block;
        }
    </style>
</head>

<body>
<!--
<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">岗位创新建议</h1>
</header>-->

<div class="mui-scroll-wrapper ui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">团队名称</label>
                    <span>${apply.name}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">项目名称</label>
                    <span>${apply.projectName}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">实施情况</label>
                    <span>
                        <c:choose>
                            <c:when test="${apply.isIng == 1}">
                                已经实施
                            </c:when>
                            <c:otherwise>
                                未实施
                            </c:otherwise>
                        </c:choose>
                    </span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">项目带头人</label>
                    <span>${apply.userName}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">项目场地</label>
                    <span>${apply.address}</span>
                </a>
            </li>

            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">创新点</label>
                    <span>${apply.innovate}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">可行性分析</label>
                    <span>${apply.analyse}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">简介</label>
                    <span>${apply.resultInfo}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">备注</label>
                    <span>${apply.remark}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">申请时间</label>
                    <span><fmt:formatDate value="${apply.applyTime}" pattern="yyyy-MM-dd HH:mm"></fmt:formatDate> </span>
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
