<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>单一来源采购方式</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <style>
        table{font-size: 13px!important;}
    </style>
    <script type="text/javascript">
        function conType(obj) {
            var conValue = obj.value;
            var url = "query.html?name=" + conValue;
            window.location.href = url;
        }

    </script>
</head>

<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row" align="center">
                <ol class="breadcrumb">
                    <li><b><span style="font-size: 22px;color: #666600;">合同金额最高的前10个项目</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div>
        <p><span>&nbsp;&nbsp;&nbsp;&nbsp;请选择采购方式:&nbsp;&nbsp;&nbsp;
            <select name="constractType" id="constractType" onchange="conType(this)" style="width: 200px;height: 25px">
                <option value="01" <c:if test="${param.name.equals('01')}" > selected="selected"</c:if>>请选择</option>
            <c:forEach items="${typeList}" var="typeList" varStatus="status">
                <option value=${typeList} <c:if test="${param.name == typeList}" > selected="selected"</c:if> >${typeList}</option>
            </c:forEach>
               <%-- <option value="02" <c:if test="${param.name.equals('02')}" > selected="selected"</c:if>>所有</option>--%>

    </select>
            </span>
        </p>
    </div>

    <div class="add-table col-xs-12">

        <table class="table table-bordered">
            <thead align="center">
            <tr align="center">
                <td><b>序号</b></td>
                <td><b>项目名称</b></td>
                <td><b>合同金额&nbsp&nbsp&nbsp&nbsp单位:元</b></td>
                <td><b>经办部门</b></td>
                <td><b>供应商</b></td>
                <td><b>采购方式</b></td>
            </tr>

            </thead>
            <tbody>

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}" align="center">
                    <td>${status.index+1}</td>
                    <td>${row.contract_name}</td>
                    <td>${row.contract_amount}</td>
                    <td>${row.undertake_dept}</td>
                    <td>${row.supplier_name}</td>
                    <td>${row.purchase_way}</td>


                </tr>
            </c:forEach>
            </tbody>
        </table>

        <jsp:include page="/common/pager.jsp"/>
    </div>
</div>
</body>
</html>
