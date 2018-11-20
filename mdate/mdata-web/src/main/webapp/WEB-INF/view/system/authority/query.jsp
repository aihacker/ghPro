<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>权限管理</title>
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
                    <li><a href="">权限管理</a></li>
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
                        <label  class="font-color">模块名：</label>
                        <form:input path="name" cssClass="form-control width40"/>
                        <label  class="font-color">模块：</label>
                        <select name="code" style="width: 130px;height: 30px">
                            <option value="">请选择</option>
                            <c:forEach items="${moduleList}" var="module">
                                <option value="${module.moduleCode}" <c:if
                                        test="${module.moduleCode == queryData.code}"> selected="selected"</c:if>>${module.moduleCode}</option>
                            </c:forEach>
                        </select>
                        <button type="submit" class="btn btn-danger">查询</button>
                    </div>
                </div>
        </div>
    </form:form>

    <div class="add-table col-xs-12">
        <p><a href="operate.do?action=new_" >
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
            新增功能权限</a>
            <%--<a href="operate.do?action=new_" >--%>
                <%--<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>--%>
                <%--新增数据权限</a>--%>
        </p>
        <%--<p><a href="#" onclick="batch()" >--%>
            <%--<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>--%>
            <%--批量生成数据权限</a>--%>
        <%--</p>--%>
        <table class="table table-bordered">
            <thead>
            <tr>
                <td><input type="checkbox"/></td>
                <td>模块名称</td>
                <td>模块代码</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}">
                    <td><input type="checkbox" value="${row.module_id}"></td>
                    <td><a href="show.html?id=${row.module_id}">${row.module_name}</a></td>
                    <td>${row.module_code}</td>
                    <td class="op" style="width: 15%">
                        <a href="javascript:edit(${row.module_id});">编辑</a>
                        <c:if test="${row.ifSystem != 0}">
                            <a href="javascript:del(${row.module_id});">删除</a>
                        </c:if>

                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>
    </div>
</div>
<script>
    function batch(){
        $.ajax({
            dataType: 'json',
            url: 'query.json',
            data: {action: 'batch'},
            traditional: true,
            success: function (result) {
                if (result.ok) {
                    alert("批量插入成功")
                }
            },
            error: function () {
                alert('form submit error..')
            }
        });
    }
</script>
</body>
</html>
