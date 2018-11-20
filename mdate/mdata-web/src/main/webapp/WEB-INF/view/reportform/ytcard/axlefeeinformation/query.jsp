<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>车辆出入停车场信息</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp?query"/>
    <style>
        table  {font-size: 13px !important;
            white-space:nowrap;}
        table th {
            text-align: center;
        }
        .ret {
            border:none;
            background-color: #f5f5f5;
            color: #428bca;
            float: left;
            line-height: 30px;
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
            <div class="row" align="center">
                <ol class="breadcrumb">
                    <li><b><span style="font-size: 22px;color: #666600;">月份油卡信息查看</span></b></li>
                    <button id="ret" class="ret" onclick="history.back()">返回</button>
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
                <td><b>发票代码</b></td>
                <td><b>发票号码</b></td>
                <td><b>发票金额</b></td>
                <td><b>通行城市</b></td>
                <td><b>交易时间</b></td>
                <td><b>节假日</b></td>
                <%--<td><b>序号</b></td>--%>
                <td><b>入口</b></td>
                <td><b>出口</b></td>
                <td><b>拆分金额</b></td>
                <td><b>交易金额</b></td>
                <td><b>etc卡号</b></td>
                <td><b>车牌号</b></td>
            </tr>

            </thead>

           <tbody>

           <c:forEach items="${queryResult.rows}" var="row" varStatus="status" >
               <tr class="${row.locked == 1? 'locked': ''}" align="center">
                   <td>${row.detail_id}</td>
                   <td>${row.invoice_code}</td>
                   <td>${row.invoice_number}</td>
                   <td>${row.invoice_sum}</td>
                   <td>${row.through_city}</td>

                   <%--<td>${row.serial_number}</td>--%>
                   <%--<td>${row.deal_time}</td>--%>
                   <td <c:choose>
                       <c:when test="${row.weekcq.equals('Saturday')||row.weekcq.equals('Sunday')}"><%--周末两天--%>
                           bgcolor="#20ffff"
                       </c:when>
                       <c:when test="${row.weekcq.equals('元旦')}"><%--元旦--%>
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:when test="${row.weekcq.equals('春节')}">
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:when test="${row.weekcq.equals('清明节')}">
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:when test="${row.weekcq.equals('劳动节')}">
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:when test="${row.weekcq.equals('端午节')}">
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:when test="${row.weekcq.equals('中秋节')}">
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:when test="${row.weekcq.equals('国庆节')}">
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:otherwise>

                       </c:otherwise>
                   </c:choose>>${row.deal_time}</td>
                   <%--<td>${row.weekcq}</td>--%>
                   <td <c:choose>
                       <c:when test="${row.weekcq.equals('Saturday')}"><%--周六--%>
                           ${row.weekcq="星期六"}
                       </c:when>
                       <c:when test="${row.weekcq.equals('Sunday')}"><%--周日--%>
                           ${row.weekcq="星期日"}
                       </c:when>
                       <c:when test="${row.weekcq.equals('Monday')}">
                           ${row.weekcq=""}
                       </c:when>
                       <c:when test="${row.weekcq.equals('Tuesday')}">
                           ${row.weekcq=""}
                       </c:when>
                       <c:when test="${row.weekcq.equals('Wednesday')}">
                           ${row.weekcq=""}
                       </c:when>
                       <c:when test="${row.weekcq.equals('Thursday')}">
                           ${row.weekcq=""}
                       </c:when>
                       <c:when test="${row.weekcq.equals('Friday')}">
                           ${row.weekcq=""}
                       </c:when>

                       <c:otherwise>

                       </c:otherwise>
                   </c:choose>>${row.weekcq}</td>
                   <td>${row.entrance}</td>
                   <td>${row.exit_s}</td>
                   <td>${row.split_sum}</td>
                   <td>${row.deal_sum}</td>
                   <td>${row.etc_card_number}</td>
                   <td>${row.licence_plate_number}</td>
               </tr>
           </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>

    </div>

</div>
</body>
</html>
