<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>系统参数管理</title>
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
                    <li><a href="">系统参数管理</a></li>
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
                        <label  class="font-color">系统变量名称：</label>
                        <form:input path="name" cssClass="form-control width40"/>
                            <button type="submit" class="btn btn-danger">搜索</button>
                    </div>
                </div>
        </div>
    </form:form>

    <div class="add-table col-xs-12">
        <p><a href="operate.do?action=new_" >
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
            新增系统参数</a>
        </p>
        <table class="table table-bordered">
            <thead>
            <tr>
                <td><input type="checkbox"/></td>
                <th>系统变量名称</th>
                <th>系统变量值</th>
                <th>描述</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}">
                    <td><input type="checkbox" value="${row.id}"></td>
                        <%--<td>${status.index+1}</td>--%>
                    <td>${row.var_name}</td>
                    <%--<td><a href="show.html?action=edit&id=${row.id}">${row.table_name}</a></td>--%>
                    <td>${row.var_value}</td>
                    <td>${row.description}</td>
                    <td class="op" style="width: 15%">
                        <a href="javascript:edit(${row.id});">编辑</a>
                        <a href="javascript:del(${row.id});">删除</a>
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
