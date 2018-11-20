<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户配置管理</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>

</head>
<style>
    table{
        font-size: 13px!important;
    }
</style>
<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="">用户配置管理</a></li>
                    <li><a href=""></a></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <form:form modelAttribute="queryData" method="get">
            <div>
                <div class="form-inline clearfix">
                    <div class="form-group col-xs-12">
                        <label  class="font-color">用户名：</label>
                        <form:input path="name" cssClass="form-control width40"/>
                            <button type="submit" class="btn btn-danger">查询</button>
                    </div>
                </div>
        </div>
    </form:form>

    <div class="add-table col-xs-12">
        <%--<p><a href="operate.do?action=new_" >--%>
            <%--<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>--%>
            <%--新增FTP配置</a>--%>
        <%--</p>--%>
        <table class="table table-bordered">
            <thead>
            <tr>
                <td><input type="checkbox"/></td>
                <td>名称</td>
                <td>工号</td>
                <td>岗位</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}">
                    <td><input type="checkbox" value="${row.user_id}"></td>
                        <%--<td>${status.index+1}</td>--%>
                    <td><a href="show.html?id=${row.user_id}">${row.user_name}</a></td>
                    <td>${row.user_code}</td>
                    <td>${row.position}</td>
                    <td class="op" style="width: 15%">
                        <a href="javascript:edit(${row.user_id});">编辑</a>
                        <a href="javascript:del(${row.user_id});">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>
    </div>
</div>
</body>
</html>
