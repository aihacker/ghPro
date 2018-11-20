<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>采购报表分析</title>
    <link type="text/css" rel="stylesheet" href="${home}/css/reportform/style.css">
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <script type="text/javascript" src="${home}/script/jquery.tablesorter.js"></script>
    <script type="text/javascript" src="${home}/script/echarts.common.min.js"></script>

    <style>
        table{
            font-size: 13px!important;/*固定字体大小*/
        }
        table tr th{   /*文本居中*/
            text-align: center;
            vertical-align: middle!important;
        }
        .ret {
            border:none;
            background-color: #f5f5f5;
            color: #428bca;
        }
        .ret:hover, .ret:focus {
            color: #2a6496;
            text-decoration: underline;
        }
       /* .table2{
            width: 50%;
            float: left;
        }
        .table3{
            width: 50%;
            float: right;
        }*/

     /*   td {white-space: nowrap;}  不换行*/

    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#myTable").tablesorter({ //禁止某列排序
                headers:{
                        0:{
                            sorter:false
                        },
                        1:{
                            sorter:false
                        },
                        2:{
                            sorter:false
                        },
                        3:{
                            sorter:false
                        }
                    },
                    debug:true
            });
        });

    </script>
</head>

<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row">
                <ol class="breadcrumb">
                    <%--<li><a href="${home}/reportform/form/contractmost/query.html">签订合同—数量最多—的供应商</a></li>--%>
                    <li><a href="">签订合同数量明细</a></li>
                    <li><a href=""></a></li>
                    <button id="ret" class="ret" onclick="history.back()">返回</button>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <label>供应商名称:</label><input type="text" value="${queryResult.rows[0].supplier_name}">
    <label>签订合同数量:</label><input type="text">
    <label>采购方式:</label><input type="text" value="${queryResult.rows[0].undertake_dept}>
    <div class="add-table col-xs-12>

        <table id="myTable" class="table table-bordered tablesorter" style="width: 50%;float: left">
            <thead align="center">
            <tr align="center">
                    <th>序号</th>
                    <th>经办部门</th>
                    <th>经办部门数量</th>
                    <th>百分比</th>


            </tr>
            </thead>
            <tbody>

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}" align="center">
                <td>${status.index+1}</td>
                <td>${row.undertake_dept}</td>
                <td><a href="${home}/reportform/form/partcontractnumber/query.html?dept=${row.undertake_dept}&&supperName=${row.supplier_name}&&purchaseWay=${row.purchase_way}">${row.sss}</a></td>
                <td>${PercentUtil.percent(row.uuu)}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <table id="myTable1" class="table table-bordered tablesorter" style="width: 50%;float: left">
            <thead align="center">
            <tr align="center">

                <th>合同类型</th>
                <th>合同数量</th>
                <th>百分比</th>


            </tr>
            </thead>
            <tbody>
            <c:forEach items="${vale}" var="tp" varStatus="status">
                <tr>

                    <td>${tp.getContractTypeName()}</td>
                    <%--<td></td>--%>
                    <td><a href="${home}/reportform/form/partcontractnumber/query.html?type=${tp.getContractTypeName()}&&supperName=${tp.getSupplierName()}&&purchaseWay=${tp.getPurchaseWay()}">${tp.getContractCode()}</a></td>
                    <%--<td>${PercentUtil.percent(tp.getPercent())}</td>--%>
                    <td>${PercentUtil.percent(tp.getPercent())}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <jsp:include page="/common/pager.jsp"/>

    </div>
</div>
</body>

</html>
