<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>查看识别的pdf文件</title>
    <link type="text/css" rel="stylesheet" href="${home}/css/reportform/style.css">
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp?query"/>
    <style>
        table {font-size: 13px !important;
            white-space:nowrap;
        }
        .jr ul{
            padding: 0;
            margin: 0;
            list-style:none;
            margin-bottom: 10px;
            margin-left: 2rem;
        }
        .jr ul li{
            display: inline-block;
            margin-right: 5px;
            text-align: center;

        }
        .jr ul li span{
            text-align: center;
            display: inline-block;
            padding: 6px 12px;
        }
        /*.jr ul .orange{
            background: orange;
        }*/
        .jr ul .zq .text{

        }
        .ret {
            color: #428bca;
            float: left;
            margin-top: 5px;
            font-size: 16px;
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
                    <a href="${home}/reportform/ytcard/insertholidaytime/query.html" class="ret">增加节假日时间</a>
                    <li><b><span style="font-size: 22px;color: #666600;">粤通卡数据查看</span></b></li>
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
                <td><b>入口</b></td>
                <td><b>出口</b></td>
                <td><b>拆分金额</b></td>
                <td><b>交易金额</b></td>
                <td><b>etc卡号</b></td>
                <td><b>车牌号</b></td>
                <td><b>节假日</b></td>

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
                    <td <c:choose>
                        <c:when test="${row.week.equals('Saturday')||row.week.equals('Sunday')}"><%--周末两天--%>
                            bgcolor="#20ffff"
                        </c:when>
                        <c:when test="${row.week.equals('元旦')}"><%--元旦--%>
                            bgcolor="#ffe4c4"
                        </c:when>
                        <c:when test="${row.week.equals('春节')}">
                            bgcolor="#ffe4c4"
                        </c:when>
                        <c:when test="${row.week.equals('清明节')}">
                            bgcolor="#ffe4c4"
                        </c:when>
                        <c:when test="${row.week.equals('劳动节')}">
                            bgcolor="#ffe4c4"
                        </c:when>
                        <c:when test="${row.week.equals('端午节')}">
                            bgcolor="#ffe4c4"
                        </c:when>
                        <c:when test="${row.week.equals('中秋节')}">
                            bgcolor="#ffe4c4"
                        </c:when>
                        <c:when test="${row.week.equals('国庆节')}">
                            bgcolor="#ffe4c4"
                        </c:when>
                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>>${row.deal_time}</td>
                    <td>${row.entrance}</td>
                    <td>${row.exit_s}</td>
                    <td>${row.split_sum}</td>
                    <td>${row.deal_sum}</td>
                    <td>${row.etc_card_number}</td>
                    <td>${row.licence_plate_number}</td>
                    <td <c:choose>
                        <c:when test="${row.week.equals('Saturday')}"><%--周六--%>
                            ${row.week="星期六"}
                        </c:when>
                        <c:when test="${row.week.equals('Sunday')}"><%--周日--%>
                            ${row.week="星期日"}
                        </c:when>
                        <c:when test="${row.week.equals('Monday')}">
                            ${row.week=""}
                        </c:when>
                        <c:when test="${row.week.equals('Tuesday')}">
                            ${row.week=""}
                        </c:when>
                        <c:when test="${row.week.equals('Wednesday')}">
                            ${row.week=""}
                        </c:when>
                        <c:when test="${row.week.equals('Thursday')}">
                            ${row.week=""}
                        </c:when>
                        <c:when test="${row.week.equals('Friday')}">
                            ${row.week=""}
                        </c:when>

                        <c:otherwise>

                        </c:otherwise>
                    </c:choose>>${row.week}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>

    </div>

</div>
</body>
</html>
