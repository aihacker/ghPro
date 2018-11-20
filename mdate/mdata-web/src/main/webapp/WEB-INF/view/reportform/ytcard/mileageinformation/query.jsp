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
                    <li><b><span style="font-size: 22px;color: #666600;">月份里程信息查看</span></b></li>
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
               <%--<th>序号</th>
               <th>车牌号码</th>
               <th>所属时间</th>
               <th>开始里程</th>
               <th>最终里程</th>
               <th>车辆性质</th>
                <th>里程耗油量</th>
                <th>耗油量/总里程(大于0.15疑似违规)</th>--%>
                   <th>序号</th>
                   <th>车牌号码</th>
                   <th>车辆性质</th>
                   <th>行驶时间</th>
                   <th>行驶里程</th>
                   <th>油耗(百公里)</th>
                   <th>车桥现金费用</th>
                   <th>距上个月里程差</th>
                   <th>月加油量</th>
                   <th>月加油量/月里程</th>
                   <th>投产时间</th>
                   <th>车型</th>
                   <th>汽车排量</th>
                   <th>月规定行驶里程</th>

            </tr>

            </thead>

           <tbody>

           <c:forEach items="${queryResult.rows}" var="row" varStatus="status" >
               <tr class="${row.locked == 1? 'locked': ''}" align="center">
                   <%--<td>${row.id}</td>
                   <td>${row.carnumber}</td>
                   <td>${row.month}</td>
                   <td>${row.startmileage}</td>
                   <td>${row.endmileage}</td>
                   <td>${row.carnature}</td>
                   <td>${row.mileage}</td>
                   <td <c:if test="${row.msc>0.15}">bgcolor="red"</c:if>><label <c:if test="${row.msc>0.15}">style="color: yellow"</c:if>>${String.format("%.3f",row.msc)}</label></td>--%>
                       <td>${row.id}</td>
                       <td>${row.license_plate_number}</td>
                       <td>${row.car_nature}</td>
                       <td>${row.mileage_driven_time}</td>
                       <td>${row.month_driving_mileage}</td>
                       <td>${row.oil_consumption}</td>
                       <td>${row.bridge_cash_charge}</td>
                       <td>${row.month_mileage_difference}</td>
                       <td>${row.litre}</td>
                       <td <c:if test="${row.litre/row.month_mileage_difference*100>row.oil_consumption}">bgcolor="red"</c:if>><label <c:if test="${row.litre/row.month_mileage_difference*100>row.oil_consumption}">style="color: yellow"</c:if>>${String.format("%.3f",row.litre/row.month_mileage_difference*100)}</label></td><%--${row.msca}--%>
                       <td>${row.production_time}</td>
                       <td>${row.car_type}</td>
                       <td>${row.out_oil}</td>
                       <td>${row.monthly_mileage}</td>
               </tr>
           </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>

    </div>

</div>
</body>
</html>
