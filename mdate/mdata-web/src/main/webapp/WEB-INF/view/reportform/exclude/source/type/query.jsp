<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>单一来源采购方式</title>
    <link type="text/css" rel="stylesheet" href="${home}/css/reportform/style.css">
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <script src="${home}/script/lib/My97DatePicker/WdatePicker.js"></script>
    <style>
        table{
            font-size: 13px!important;
            white-space:nowrap;
        }
    </style>
    <script type="text/javascript">
        function conType(obj) {
            var conValue = obj.value;
            var url = "query.html?name=" + conValue;
            window.location.href = url;
        }

    </script>
    <script type="text/javascript">
        function check() {

            var startTime =  $('#startTime').val();
            var endTime =  $('#endTime').val();
            var url = "query.html?startTime="+startTime+"&endTime="+endTime;
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
                    <li><b><span style="font-size: 22px;color: #666600;">签订合同数量及金额最多的合同类型</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="form-inline clearfix">
        <div class="form-group col-xs-12">
            <label  class="font-color" for="startTime">开始日期：</label>
            <input  type="text"  value="${param.startTime}" id="startTime" name="startTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd'})" placeholder="开始日期"/>&nbsp;&nbsp;
            <label  class="font-color"  for="endTime">结束日期：</label>
            <input  type="text"   value="${param.endTime}" id="endTime" name="endTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd'})" placeholder="结束日期"/>&nbsp;&nbsp;
            <button type="submit" class="btn btn-success" onclick="check();">查询</button>
        </div>
    </div>
   <%-- <div>
        <p><span>&nbsp;&nbsp;&nbsp;&nbsp;请选择采购方式:&nbsp;&nbsp;&nbsp;
            <select name="constractType" id="constractType" onchange="conType(this)" style="width: 200px;height: 25px">
                <option value="01" <c:if test="${param.name.equals('01')}" > selected="selected"</c:if>>请选择</option>
            <c:forEach items="${typeList}" var="typeList" varStatus="status">
                <option value=${typeList} <c:if test="${param.name == typeList}" > selected="selected"</c:if> >${typeList}</option>
            </c:forEach>
                &lt;%&ndash;<option value="02" <c:if test="${param.name.equals('02')}" > selected="selected"</c:if>>所有</option>&ndash;%&gt;

    </select>
            </span>
        </p>
    </div>--%>

    <div class="add-table col-xs-12">

        <table class="table table-bordered table-hover">
            <thead align="center">
            <tr align="center">
                <td><b>序号</b></td>
                <td><b>合同类型</b></td>
                <td><b>签订合同数量</b></td>
                <td><b>合同总金额&nbsp&nbsp&nbsp&nbsp单位:元</b></td>
                <td><b>采购方式</b></td>
            </tr>

            </thead>
            <tbody>

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}" align="center">
                    <td>${status.index+1}</td>
                    <td>${PercentUtil.getChinese(row.contract_type)}</td>
                    <%--<td><a href="javascript:amount('${row.contract_type}','${row.purchase_way}');">${row.cc}</a></td>--%>
                    <td><a href="${home}/reportform/form/departmentandsupplier/query.html?type=${row.contract_type}&&purchaseWay=${row.purchase_way}">${row.cc}</a></td>
                    <td>${row.am}</td>
                    <td>${row.purchase_way}</td>


                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/modal.jsp"/>
        <script type="text/javascript" src="${home}/script/reportform/simplebytype.js"></script>
        <jsp:include page="/common/pager.jsp"/>
    </div>

        <%--    ${param.code}
            <%
                String ss = request.getParameter("code");
                out.println(ss);
            %>--%>

</div>
<script type="text/javascript" src="${home}/script/jquery.tablesorter.js"></script>
</body>
</html>
