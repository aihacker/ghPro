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
               <th>序号</th>
               <th>卡号</th>
               <th>车牌号</th>
               <th>时间</th>
                <th>节假日</th>
               <th>业务类型</th>
               <th>品种</th>
               <th>数量</th>
               <th>单价</th>
               <th>金额</th>
               <th>奖励分值</th>
               <th>优惠价</th>
               <th>余额</th>
               <th>地点</th>
               <th>操作员</th>
               <th>备注</th>
            </tr>

            </thead>

           <tbody>

           <c:forEach items="${queryResult.rows}" var="row" varStatus="status" >
               <tr class="${row.locked == 1? 'locked': ''}" align="center">
                   <td>${row.card_id}</td>
                   <td>${row.card_number}</td>
                   <td>${row.license_plate_number}</td>
                   <%--<td>${row.date}</td>--%>
                   <td <c:choose>
                       <c:when test="${row.weekyk.equals('Saturday')||row.weekyk.equals('Sunday')}"><%--周末两天--%>
                           bgcolor="#20ffff"
                       </c:when>
                       <c:when test="${row.weekyk.equals('元旦')}"><%--元旦--%>
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:when test="${row.weekyk.equals('春节')}">
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:when test="${row.weekyk.equals('清明节')}">
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:when test="${row.weekyk.equals('劳动节')}">
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:when test="${row.weekyk.equals('端午节')}">
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:when test="${row.weekyk.equals('中秋节')}">
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:when test="${row.weekyk.equals('国庆节')}">
                           bgcolor="#ffe4c4"
                       </c:when>
                       <c:otherwise>

                       </c:otherwise>
                   </c:choose>>${row.date}</td>
                   <%--<td>${row.节假日}</td>--%>
                   <td <c:choose>
                       <c:when test="${row.weekyk.equals('Saturday')}"><%--周六--%>
                           ${row.weekyk="星期六"}
                       </c:when>
                       <c:when test="${row.weekyk.equals('Sunday')}"><%--周日--%>
                           ${row.weekyk="星期日"}
                       </c:when>
                       <c:when test="${row.weekyk.equals('Monday')}">
                           ${row.weekyk=""}
                       </c:when>
                       <c:when test="${row.weekyk.equals('Tuesday')}">
                           ${row.weekyk=""}
                       </c:when>
                       <c:when test="${row.weekyk.equals('Wednesday')}">
                           ${row.weekyk=""}
                       </c:when>
                       <c:when test="${row.weekyk.equals('Thursday')}">
                           ${row.weekyk=""}
                       </c:when>
                       <c:when test="${row.weekyk.equals('Friday')}">
                           ${row.weekyk=""}
                       </c:when>

                       <c:otherwise>

                       </c:otherwise>
                   </c:choose>>${row.weekyk}</td>
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
               </tr>
           </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>

    </div>

</div>
</body>
</html>
