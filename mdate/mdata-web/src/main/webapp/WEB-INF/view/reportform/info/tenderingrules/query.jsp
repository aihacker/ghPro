<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>采购方式适用列表</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <style>
        table {
            font-size: 13px !important;
            /* important的作用:可以忽略后续设置，始终执行当前的设置*/
            /*white-space: nowrap;*/
        }

        th {
            text-align: center;
        }

        td {
            text-align: center;
        }
    </style>
    <script>
        function add() {
            window.location.href="./show.html?code=${fileRulers.get(0).ruleNumber}";
        }
    </script>

</head>

<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row" align="center">
                <ol class="breadcrumb">
                    <li style="float: left;"><button id="ret" class="ret" onclick="history.back()">返回</button></li>
                    <li><b><span style="font-size: 22px;color: #666600;">采购方式适用列表</span></b></li>
                    <li style="float: right;"><button type="button" class="btn btn-success" onclick="return add();">新增规则</button></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>

    <div class="add-table col-xs-12">
        <table class="table table-striped table-hover"  bordercolor="#000000" border="1" cellspacing="0">
            <thead align="center">
            <tr align="center" width="80%" style=" text-align:center;height:20px;" >
                <th>规则编号</th>
                <th>生效日期</th>
                <th>废止日期</th>
                <th>生效文件名称</th>
                <th>生效文件号</th>
                <th>被废止文件名称</th>
                <th>废止文件号</th>
            </tr>
            </thead>
            <tbody>
        <c:forEach items="${fileRulers}" var="wow" varStatus="status">
         <tr align="center" width="100%" style="height:20px;">
             <td>${wow.ruleNumber}</td>
             <td>${wow.startDate}</td>
             <c:if test="${wow.endDate !=null && wow.endDate.equals('2099-12-30')}"><td>----</td></c:if>
             <c:if test="${wow.endDate != '2099-12-30'}"><td>${wow.endDate}</td></c:if>
             <td>${wow.fileBasisName}</td>
             <td>${wow.fileNumber}</td>
             <td>${wow.fileAbolishName}</td>
             <td>${wow.fileAbolishNumber}</td>
            </tr>
        </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="add-table col-xs-12">

        <table class="table table-striped table-hover"  bordercolor="#000000" border="1" cellspacing="0">
            <thead align="center">
            <tr align="center" width="100%" style=" text-align:center;height:20px;" >
                <th colspan="11"  style="background: #619089;color: #fff106; font-size: 16px; border-bottom: 1px solid #070e03;">采购方式适用列表</th>
            </tr>
            <tr align="center" width="100%" style=" text-align:center;height:20px;" >
                <th rowspan="2" align="center" style="border-bottom: 1px solid #070e03;">种类</th>
                <th colspan="4" style=" border-bottom: 1px solid #070e03;">工程建设项目</th>
                <th colspan="6" style=" border-bottom: 1px solid #070e03;">非工程建设项目</th>
            </tr>
            <tr align="center" width="100%" style="height:20px;">
                <th colspan="2" style=" border-bottom: 1px solid #070e03;">工程货物</th>
                <th colspan="2" style=" border-bottom: 1px solid #070e03;">工程服务</th>
                <th colspan="3" style=" border-bottom: 1px solid #070e03;">非工程货物</th>
                <th colspan="3" style=" border-bottom: 1px solid #070e03;">综合服务</th>
            </tr>
            </thead>
            <tbody id="tbody-result" name="tbody-result">

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr align="center" width="100%" style="height:20px;">
                    <td>${row.way}</td>
                    <td <c:if test="${row.project_goods_one !=null && row.project_goods_one.equals('必用*') }" >style="color: red"</c:if>>${row.project_goods_one}</td>
                    <td>${row.project_goods_two}</td>
                    <td <c:if test="${row.project_goods_one !=null && row.project_goods_one.equals('必用*') }" >style="color: red"</c:if>>${row.project_services_one}</td>
                    <td>${row.project_services_two}</td>
                    <td <c:if test="${row.project_goods_one !=null && row.project_goods_one.equals('必用*') }" >style="color: red"</c:if>>${row.no_project_goods_one}</td>
                    <td>${row.no_project_goods_two}</td>
                    <td>${row.no_project_goods_three}</td>
                    <td <c:if test="${row.project_goods_one !=null && row.project_goods_one.equals('必用*') }" >style="color: red"</c:if>>${row.integrated_services_one}</td>
                    <td>${row.integrated_services_two}</td>
                    <td>${row.integrated_services_three}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="add-table col-xs-12" style="width: 100%; height:500px;display: flex;" >
        <div id="line" style="width: 500px;height:218px;flex: 0.4;border:1px solid #000;">
            <p></p>
            <p><b>合同类型及金额满足以下条件的必须进行公开招标:</b></p>
            <table class="table  table-hover" width="40%" id="table2" bordercolor="#000000" border="1" cellspacing="0">
                <thead align="center">
                <tr align="center" style=" text-align:center;height:20px;" >
                    <th colspan="2" style=" border-bottom: 1px solid #070e03;">工程服务 (法定)</th>
                </tr>
                </thead>
                <tbody>
                <tr align="center"  style="height:20px;">
                    <td>${list.get(0).titleThree}</td>
                    <td> ≥ ${list.get(0).money.substring(0,list.get(0).money.length()-4)} 万</td>
                </tr>
                <tr align="center"  style="height:20px;">
                    <td>${list.get(1).titleThree}</td>
                    <td> ≥ ${list.get(1).money.substring(0,list.get(1).money.length()-4)} 万</td>
                </tr>
                </tbody>
                <thead align="center">
                <tr align="center"  style=" text-align:center;height:20px;" >
                    <th colspan="2" style=" border-bottom: 1px solid #070e03;">工程货物 (法定)</th>
                </tr>
                </thead>
                <tbody>
                <tr align="center"  style="height:20px;">
                    <td>${list.get(2).titleThree}</td>
                    <td> ≥ ${list.get(2).money.substring(0,list.get(2).money.length()-4)} 万</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div id="roll" style="width: 400px;height:218px;flex: 0.6;border:1px solid #000;">
            <br>
                依法必须进行招标的项目是指工程建设项目中达到以下标准之一的项目：
            </br>（1）施工（含系统集成）发包单项合同估算价在${list.get(0).money.substring(0,list.get(0).money.length()-4)}万元人民币及以上的；
            </br>（2）重要设备（含软件）、材料等物资的采购，单项合同估算价在${list.get(2).money.substring(0,list.get(2).money.length()-4)}万元人民币及以上的；
            </br>（3）勘察、设计、监理等服务的采购，单项合同估算价在${list.get(1).money.substring(0,list.get(1).money.length()-4)}万元人民币及以上的；
            </br>（4）单项合同估算价低于第（一）、（二）、（三）项规定的标准，但项目总投资额在3000万元人民币及以上的；
            </br>（5）使用国际组织或外国政府贷款、援助资金的（提供贷款或资金方有合法的特殊要求除外）。
            </p>
        </div>
    </div>


    </div>

</div>
</body>
</html>

