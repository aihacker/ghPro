<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<head>
    <title>应招未招，应公开未公开</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <script type="text/javascript" src="${home}/script/layui/layui.all.js"></script>
    <style>
        table{font-size: 13px!important;}
        .table tr th{
            text-align: center;
            vertical-align: middle!important;
            font-size:18px;
        }
        .ret {
            color: #fff5fa;
            float: left;
        }

    </style>
    <script type="text/javascript">
        function say(a,b,c,d,e) {
            var moneys = a;//规定金额
            var title1 = b;//项目类型1
            var title2 = d;//项目类型2
            var rolesNumber= c;//规则文件1000是18年以前的
            var amount= e;//实际招标金额

               if(rolesNumber ==1000) {
                     if (moneys == 2000000) {

                     if (title1 == "工程建设项目") {
                         layer.alert(title1);
                             layer.alert("工程建设项目-->工程服务-->招标金额" + amount + ">=200W,必用-->公开招标 , 根据'（中电信粤电函〔2017〕1159号）文件规则'", {icon: 7});
                         } else if (title1 == "非工程建设项目") {
                             layer.alert("非工程建设项目-->综合服务-->招标金额" + amount + ">=200W,必用-->公开招标 , 根据'（中电信粤电函〔2017〕1159号）文件规则'", {icon: 7});
                         }
                     } else if (moneys == 1000000 && title2 == "工程货物") {
                         layer.alert("工程建设项目-->工程货物-->物资采购-->招标金额" + amount + ">=100W,必用-->公开招标 , 根据'（中电信粤电函〔2017〕1159号）文件规则'", {icon: 7});
                     } else if (moneys == 500000 && title2 == "工程服务") {
                         layer.alert("工程建设项目-->工程服务-->勘察/设计/监理-->招标金额" + amount + ">=50W,必用-->公开招标 , 根据'（中电信粤电函〔2017〕1159号）文件规则'", {icon: 7});
                     }
                 }



        }
        function filtration() {
            var hideIpt = $("#hid").val();
            var url = "query.html?hid=" + hideIpt;
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
                    <button id="ret" class="btn btn-success ret" onclick="filtration()">过滤业务代理(含农村统包)类型</button>
                    <input type="hidden" name="hid" id="hid" value="业务代理(含农村统包)">
                    <li><b><span style="font-size: 22px;color: #666600;">应招未招，应公开未公开</span></b></li>
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
               <th>项目名称</th>
               <th>供应商</th>
               <th>项目类型</th>
               <th>所属工程类别</th>
               <th>合同估算金额</th>
               <th>采购方式</th>
               <th>是否违规</th>
            </tr>

            </thead>
            <tbody>
            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
            <tr class="${row.locked == 1? 'locked': ''}" align="center">
            <td>${status.index+1}</td>
            <td>${row.contract_name}</td>
            <td>${row.supplier_name}</td>
            <td>${row.contract_type_name}</td>
            <td>${row.title_three}</td>
            <td>${row.contract_amount}</td>
            <td>${row.purchase_way}</td>

                <td><button type="button" id="rules" name="rules" class="btn btn-danger" onclick="say('${row.money}','${row.title_one}','${row.roles_number}','${row.title_two}','${row.contract_amount}')">疑似违规</button></td>
            </tr>
            </c:forEach>
            </tbody>

        </table>

        <jsp:include page="/common/pager.jsp"/>
    </div>

</div>

</body>
</html>
