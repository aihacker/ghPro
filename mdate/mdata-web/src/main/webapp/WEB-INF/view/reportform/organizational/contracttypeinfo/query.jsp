<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>合同类型小类</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <link rel="stylesheet" href="${home}/style/tree/metroStyle/metroStyle.css" type="text/css">
    <link rel="stylesheet" href="${home}/style/tree//zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${home}/script/tree/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${home}/script/tree/jquery.ztree.excheck.js"></script>
</head>
<style>
    table {
        font-size: 13px !important;
    }
</style>
<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="">合同类型小类</a></li>
                    <li><a href="javascript:location.href=document.referrer;">返回</a></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>


    <div class="add-table col-xs-12">
        <p><a href="operate.do?action=new_&parent=${queryResult.rows[0].parent_num}">
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
            新增合同类型小类</a>
        </p>
        <table class="table table-bordered">
            <thead>
            <tr>
                <td>序号</td>
                <td>小类编号</td>
                <td>合同小类名称</td>
                <td>所属父类编号</td>
                <td>父类名称</td>
                <td>操作</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}">
                    <td>${status.index+1}</td>
                    <td>${row.type_code}</td>
                    <td>${row.type_name}</td>
                    <td>${row.parent_num}</td>
                    <td>${typename}</td>
                    <td class="op" style="width: 25%">
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
