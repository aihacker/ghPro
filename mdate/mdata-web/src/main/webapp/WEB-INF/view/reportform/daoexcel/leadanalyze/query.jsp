<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>加油站数据台账</title>
    <link type="text/css" rel="stylesheet" href="${home}/css/reportform/style.css">
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp?query"/>
    <style>
        table {font-size: 13px !important;
            white-space:nowrap;
        }
    </style>
    <script type="text/javascript">
        function screen() {
            var screenvalue = $("#screen").val();
            var url = "query.html?code=" + screenvalue;
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
                    <li><b><span style="font-size: 22px;color: #666600;">查看加油站数据</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>


    <div class="add-table col-xs-12">

            <table class="table table-bordered table-hover">
            <thead align="center">
            <tr align="center" width="100%" style="height:20px;background-color: #ffffff;">
                <td><b>序号</b></td>
                <td><b>卡号</b></td>
                <td><b>时间</b></td>
                <td><b>业务类型</b></td>
                <td><b>品种</b></td>
                <td><b>数量</b></td>
                <td><b>单价</b></td>
                <td><b>金额</b></td>
                <td><b>奖励分值</b></td>
                <td><b>优惠价</b></td>
                <td><b>余额</b></td>
                <td><b>地点</b></td>
                <td><b>操作员</b></td>
                <td><b>备注</b></td>
                <td><b>客户名称</b></td>
                <td><b>客户编码</b></td>
                <td><b>网点</b></td>
                <td><b>账单类型</b></td>
                <td><b>起止时间</b></td>
                <td><b>应用类型</b></td>
                <td><b>账单操作员</b></td>
                <td><b>打印日期</b></td>
              <%--  <td><b>合同总金额&nbsp&nbsp&nbsp&nbsp单位:元</b></td>--%>
            </tr>

            </thead>

           <tbody>

           <c:forEach items="${queryResult.rows}" var="row" varStatus="status" >
               <tr class="${row.locked == 1? 'locked': ''}" align="center">
                   <%--<td>${status.index+1}</td>--%>
                       <%--<td>${(page.pageNum - 1) * page.pageSize + num.count}</td>--%>
                       <td>${row.card_id}</td>
                       <td>${row.card_number}</td>
                   <td>${row.date}</td>
                   <td>${row.business_type}</td>
                   <td>${row.variety}</td>
                   <td>${row.number}</td>
                   <td>${row.unit_price}</td>
                   <td>${row.sum}</td>
                   <td>${row.reward_points}</td>
                   <td>${row.preferential_price}</td>
                   <td>${row.balance}</td>
                   <td>${row.site}</td>
                   <td>${row.operator}</td>
                   <td>${row.remark}</td>
                   <td>${row.customer_name}</td>
                   <td>${row.customer_code}</td>
                   <td>${row.website_name}</td>
                   <td>${row.bill_type}</td>
                   <td>${row.startstop_time}</td>
                   <td>${row.apply_type}</td>
                   <td>${row.bill_operator}</td>
                   <td>${row.print_date}</td>
               </tr>
           </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>

    </div>

</div>
</body>
</html>
