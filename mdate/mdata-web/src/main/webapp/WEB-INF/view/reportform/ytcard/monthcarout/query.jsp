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
        /*.right{
            float: right;
            margin-top: -30px;
            margin-right: 633px;
        }*/
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
        .cph{
            margin-left: 15px;
        }
    </style>
    <script>
        function check() {
            var carNumber = $("#carnumber").val();
            if (carNumber == null || carNumber == ""){
                alert("请输入车牌号");
                return false;
            }
            var selectYear = $('#testSelect option:selected') .val();
            var parater = "?params="+carNumber+"&yearss="+selectYear;
            var url =  "query.html"+parater;
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
                    <li><b><span style="font-size: 22px;color: #666600;">车辆出入停车场信息</span></b></li>
                    <button id="ret" class="ret" onclick="history.back()">返回</button>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="left">
    <label  class="cph">车牌号:</label><input type="text" value="${param.params}" id="carnumber"><%--<button onclick="check()">查询</button>--%>

    <label style="font-size: 15px;margin-left: 15px;">年份:</label>
        <select id="testSelect" style="height: 26px;width: 174px;">
        <option value ="2018" <c:if test="${param.yearss == 2018}">selected="selected"</c:if>>2018</option>
        <option value="2019" <c:if test="${param.yearss == 2019}">selected="selected"</c:if>>2019</option>
        <option value="2020" <c:if test="${param.yearss == 2020}">selected="selected"</c:if>>2020</option>
        </select>
        <button onclick="check()" style="margin-left: 15px">查询</button>
    </div>
    <div class="add-table col-xs-12">

            <table class="table table-bordered table-hover">
            <thead align="center">
            <tr align="center" width="100%" style="height:20px;background-color: #ffffff;">
               <th>月份</th>
               <th>异常情况（出入车场）</th>
               <th>里程（公里）</th>
               <th>异常情况(耗油/里程)</th>
               <th>加油数量(升)</th>
                <th>加油金额(元)</th>
               <th>修车费</th>
               <th>异常情况（修车次数）</th>
               <th>车桥费（元）</th>
            </tr>
            </thead>
           <tbody>
           <c:forEach items="${deregulationTemp}" var="row" varStatus="status" >
               <tr align="center">
                   <td>${row.month}</td>
                   <c:if test="${row.time == 0}"><td></td></c:if><%--出入车场（可能违规的次数）--%>
                   <c:if test="${row.time != 0}"><td><a href="${home}/reportform/ytcard/outinformation/query.html?pageMonth=${row.month}&params=${param.params}&yearss=${param.yearss}">${row.time}</a></td></c:if>
                   <td><a href="${home}/reportform/ytcard/mileageinformation/query.html?pageMonth=${row.month}&params=${param.params}&yearss=${param.yearss}" <c:choose>
                       <c:when test="${carProperties.equals('综合营销服务用车') && row.mileage>carRepairFee}">
                           style="color: red"
                       </c:when>
                       <c:when test="${carProperties.equals('生产用车') && row.mileage>carRepairFee}">
                           style="color: red"
                       </c:when>
                       <c:otherwise>
                       </c:otherwise>
                   </c:choose>>${row.mileage}</a></td><%--里程（公里）--%>

                   <c:if test="${row.violations.equals('存在0条疑似违规')}"><td></td></c:if><%--疑似违规--%>
                   <c:if test="${!row.violations.equals('存在0条疑似违规')}"><td>${row.violations}</td></c:if><%--疑似违规--%>

                   <%--<td><a href="${home}/reportform/ytcard/oilcardinformation/query.html?pageMonth=${row.month}&params=${param.params}&yearss=${param.yearss}">${row.oil}</a></td>&lt;%&ndash;油卡&ndash;%&gt;--%>
                   <td><a href="${home}/reportform/ytcard/oilcardinformation/query.html?pageMonth=${row.month}&params=${param.params}&yearss=${param.yearss}">${row.litre}</a></td>
                   <td><a href="${home}/reportform/ytcard/oilcardinformation/query.html?pageMonth=${row.month}&params=${param.params}&yearss=${param.yearss}">${row.oil}</a></td>


                   <td><a href="${home}/reportform/ytcard/repairinginformation/query.html?pageMonth=${row.month}&params=${param.params}&yearss=${param.yearss}" <c:if test="${row.totalRepair>1000}">style="color: red" </c:if>>${row.carRepairing}</a></td><%--修车费--%>
                   <c:if test="${row.repairingId>3}"><td>存在${row.repairingId}次修车记录</td></c:if>
                   <c:if test="${row.repairingId<=3}"><td></td></c:if>
                   <td><a href="${home}/reportform/ytcard/axlefeeinformation/query.html?pageMonth=${row.month}&params=${param.params}&yearss=${param.yearss}">${row.axleFee}</a></td><%--车桥费--%>
               </tr>
           </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>

    </div>

</div>
</body>
</html>
