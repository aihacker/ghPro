<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>采购报表分析</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <style>
        table{
            font-size: 13px!important;/*固定字体大小*/
        }
        table tr th{   /*文本居中*/
            text-align: center;
            vertical-align: middle!important;
        }
     /*   td {white-space: nowrap;}  不换行*/
        .ret {
            border:none;
            background-color: #f5f5f5;
            color: #428bca;
        }
        .ret:hover, .ret:focus {
            color: #2a6496;
            text-decoration: underline;
        }
    </style>
</head>

<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row">
                <ol class="breadcrumb">
                    <%--<li><a href="${home}/reportform/form/contractmost/query.html">签订合同—数量最多—的供应商</a></li>--%>
                    <li><a href="">供应商签订合同数量明细</a></li>
                    <li><a href=""></a></li>
                        <button id="ret" class="ret" onclick="history.back()">返回</button>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="add-table col-xs-12">

        <table id="myTable" class="table table-bordered tablesorter">
            <thead align="center">
            <tr align="center">
                <th>序号</th>
                <th>供应商</th>
                <th>合同编号</th>
                <th>合同名称</th>
                <th>合同类型</th>
                <th>合同金额</th>
                <th>经办部门</th>
                <th>采购方式</th>
                <%--<th>定稿时间</th>--%>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}" align="center">
                    <td>${status.index+1}</td>
                    <td>${row.supplier_name}</td>
                    <td>${row.contract_code}</td>
                    <td >${row.contract_name}</td>
                    <td>${row.contract_type_name}</td>
                    <td>${row.contract_amount}</td>
                    <td>${row.undertake_dept}</td>
                    <td>${row.purchase_way}</td>
                    <%--<td>${row.purchase_time}</td>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>
    </div>
</div>
</body>
</html>
