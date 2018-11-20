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
                    <li><b><span style="font-size: 22px;color: #666600;">车辆出入停车场信息</span></b></li>
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
               <th>停车场名称</th>
               <th>车牌号码</th>
               <th>车型</th>
               <th>车主编号</th>
               <th>车主姓名</th>
               <th>车主部门</th>
               <th>入场时间</th>
                <th>出场时间</th>
                <th>节假日</th>
                <th>对应入场时间</th>
                <th>出入相差时间</th>
               <th>入场操作员编号</th>
               <th>入场操作员姓名</th>
               <th>入场设备</th>
               <th>入场图像</th>
               <th>收费时间</th>
               <th>操作员编号</th>
               <th>操作员</th>
               <th>出场设备</th>
               <th>出场图像</th>
               <th>车况</th>
               <th>车辆颜色</th>
               <th>事件类型</th>
               <th>收费类型</th>
               <th>付款方式</th>
               <th>应收金额</th>
               <th>滚动收费减免金额</th>
               <th>优惠券金额</th>
               <th>实收金额</th>
               <th>班次</th>
               <th>停车时间</th>
               <th>备注</th>
               <th>入场工作站</th>
               <th>出场工作站</th>
            </tr>

            </thead>

           <tbody>

           <c:forEach items="${queryResult.rows}" var="row" varStatus="status" >
               <tr class="${row.locked == 1? 'locked': ''}" align="center">
                   <td>${status.index+1}</td>
                   <%--<td>${row.id}</td>--%>
                   <td>${row.park_name}</td>
                   <td>${row.license_number}</td>
                   <td>${row.motorcycle_type}</td>
                   <td>${row.owner_number}</td>
                   <td>${row.owner_name}</td>
                   <td>${row.owner_department}</td>
                   <td>${row.approach_time}</td>
                   <%--<td>${row.playing_time}</td>--%>
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
                   </c:choose>>${row.playing_time}</td>
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
                   <td>${row.come_back}</td>
                   <td>${row.hour}</td>
                   <td>${row.enter_operator_number}</td>
                   <td>${row.enter_operator_name}</td>
                   <td>${row.entrance_equipment}</td>
                   <td>${row.enter_the_image}</td>
                   <td>${row.chargeable_time}</td>
                   <td>${row.operator_number}</td>
                   <td>${row.operator}</td>
                   <td>${row.play_equipment}</td>
                   <td>${row.his_image}</td>
                   <td>${row.vehicle_condition}</td>
                   <td>${row.vehicle_color}</td>
                   <td>${row.event_type}</td>
                   <td>${row.charge_type}</td>
                   <td>${row.payment_method}</td>
                   <td>${row.amount_receivable}</td>
                   <td>${row.amount_fee_deduction}</td>
                   <td>${row.coupon_amount}</td>
                   <td>${row.sum_received}</td>
                   <td>${row.classes}</td>
                   <td>${row.stopping_time}</td>
                   <td>${row.remark}</td>
                   <td>${row.admission_station}</td>
                   <td>${row.exit_station}</td>

               </tr>
           </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>

    </div>

</div>
</body>
</html>
