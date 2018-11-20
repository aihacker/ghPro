<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>表名列表</title>
    <%--<jsp:include page="/common/styles.jsp"/>--%>
    <%--<jsp:include page="/common/scripts.jsp"/>--%>
    <link rel="stylesheet" href="${home}/style/lib/bootstrap.min.css"/>
    <link rel="stylesheet" href="${home}/style/my.css">
    <link rel="stylesheet" href="${home}/style/query.css">
    <script src="${home}/script/lib/jquery-3.1.1.min.js"></script>
    <script src="${home}/script/lib/bootstrap.min.js"></script>
    <script type="text/javascript" src="${home}/script/lib/jquery.form.js"></script>
    <script type="text/javascript" src="${home}/script/pub.js"></script>
    <script type="text/javascript" src="${home}/script/site.js"></script>
    <script type="text/javascript" src="${home}/script/query.js"></script>
    <script type="text/javascript" src="${home}/script/querystyle.js"></script>
</head>
<style>
    table {
        font-size: 13px !important;
    }
</style>
<body>
<div class="main">

    <div class="clearfix" style="margin-top: 5px">&nbsp;</div>
    <form:form modelAttribute="queryData" method="get">
        <div>
            <div class="form-inline clearfix">
                <div class="form-group col-xs-12">
                    <label class="font-color">模式名：</label>
                    <select name="schema" class="form-control">
                        <option value="">请选择</option>
                        <c:forEach items="${schemas}" var="schema">
                            <option value="${schema}">${schema}</option>
                        </c:forEach>
                    </select>
                    <label class="font-color">表名：</label>
                    <form:input path="name" cssClass="form-control width30"/>
                    <input hidden value="${roleId}" name="roleId" />
                    <button type="submit" class="btn btn-danger">查询</button>
                </div>
            </div>
        </div>
    </form:form>
    <div class="add-table col-xs-12">
        <%--<p><a href="operate.do?action=new_" >--%>
        <%--<span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>--%>
        <%--新增角色配置</a>--%>
        <%--</p>--%>
        <table class="table table-bordered" id="tableIndex">
            <thead>
            <tr>
                <td><input type="checkbox"/></td>
                <td>模式名</td>
                <td>表名</td>
                <td>表数据权限</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}">
                    <td><input type="checkbox" value="${row.tablename}" name="table"<c:if test="${row.check==0}">checked</c:if> ></td>
                        <%--<td>${status.index+1}</td>--%>
                    <td class="schemaname">${row.schemaname}</td>
                    <td>${row.tablename}</td>
                    <td class="op" style="width: 15%">
                        <input type="radio" name="${status.index}" value="0" <c:if test="${row.authority==0}">checked</c:if>> 查看
                        <input type="radio" name="${status.index}" value="1"  <c:if test="${row.authority==1}">checked</c:if>> DML
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="col-xs-3 clear-float center-block mt20 clearfix">
            <input type="submit" value="保存" class="btn btn-danger pull-left" onclick="save(${roleId})"/>
            <input type="button" value="返回" class="btn btn-primary pull-right" onclick="location.href=document.referrer;"/>
        </div>
        <script src="${home}/script/system/role.js"></script>
        <jsp:include page="/common/pager.jsp"/>
    </div>
</div>
</body>
</html>
