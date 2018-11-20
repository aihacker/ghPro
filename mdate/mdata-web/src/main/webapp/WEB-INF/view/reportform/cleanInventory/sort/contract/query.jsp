<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>即时清结</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>

</head>
<style>
    table{
        font-size: 13px!important;
    }
    td {white-space: nowrap;}
</style>
<script type="text/javascript" >
    //清空文本框数据
    /*  $(function() {
          $("input").attr("value","");
      });*/
    /*  $("#form input").val("");*/
</script>
<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="javascript:history.back(-1)">返回</a></li>
                    <li><a href=""></a></li>
                </ol>
            </div>
        </div>
    </div>

    <div class="add-table col-xs-12">

        <table class="table table-bordered" >
            <thead align="center">
            <tr align="center" class="cdc">
                <td><b>序号</b></td>
                <td><b>合同编号</b></td>
                <td><b>合同名称</b></td>
                <td><b>供应商</b></td>
                <td><b>合同金额</b></td>
                <td><b>定稿时间</b></td>
                <td><b>承办部门</b></td>
                <td><b>承办人</b></td>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="cdc2" >
                    <td>${status.index+1}</td>
                    <td>${row.contract_code}</td>
                    <td >${row.contract_name}</td>
                    <td>${row.supplier_name}</td>
                    <td>${row.contract_amount}</td>
                    <td>${row.purchase_time}</td>
                    <td>${row.undertake_dept}</td>
                    <td>${row.undertake_man}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>
    </div>
</div>
</body>
</html>
