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
    <title></title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view-cell span:last-child {
            display: inline-block;
        }
    </style>
</head>

<body>


<div class="mui-scroll-wrapper ui-scroll-wrapper" style="padding-top: 0px;">
    <div class="mui-scroll">
        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell mui-text-center">
                基本信息
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">类别</label>
                    <span>${userSuggest.cateName}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">用户名</label>
                    <span>${userSuggest.username}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">申请时间</label>
                    <span><fmt:formatDate value="${userSuggest.createTime}" pattern="yyyy-MM-dd HH:mm"/> </span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">部门</label>
                    <span>${userSuggest.deptname}</span>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a>
                    <label class="ui-li-label ui-text-info">标题</label>
                    <span>${userSuggest.title}</span>
                </a>
            </li>
            <li class="mui-table-view-cell" style="height: auto;">
                    <label class="ui-li-label ui-text-info">内容</label>
                    <span>${userSuggest.content}</span>
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


<script type="text/javascript ">
    mui.init()


</script>
</body>

</html>
