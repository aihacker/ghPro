<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>系统参数查询</title>

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
    <div class="col-xs-12">
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

    <%--<form:form modelAttribute="queryData" method="get">--%>
        <%--<div class="clearfix mt20">--%>
            <%--<div class="col-xs-6">--%>
                <%--<div class="form-inline clearfix">--%>
                    <%--<div class="form-group col-xs-12">--%>
                        <%--<label  class="font-color">系统变量名称：</label>--%>
                        <%--<form:input path="name" cssClass="form-control width70" placeholder="系统变量名称"/>--%>
                    <%--</div>--%>
                <%--</div>--%>
            <%--</div>--%>
        <%--</div>--%>

        <%--<div class="col-xs-3 clear-float center-block mt20 clearfix">--%>
            <%--<button type="submit" class="btn btn-danger pull-left">搜索</button>--%>
            <%--<button type="reset" class="btn btn-primary pull-right">重置</button>--%>
        <%--</div>--%>
    <%--</form:form>--%>


    <div class="add-table">
        <p><a href="operate.do?action=new_" >
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
            增加系统参数</a>
        </p>
        <table class="table" border="1" cellspacing="0" cellpadding="0">
            <thead>
            <tr>
                <td>序号</td>
                <td>系统变量名称</td>
                <td>系统变量值</td>
                <td>描叙</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}">
                    <%--<td><input type="checkbox" value="${row.id}"></td>--%>
                        <td>${status.index+1}</td>
                    <td><a href="show.html?id=${row.id}">${row.var_name}</a></td>
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
