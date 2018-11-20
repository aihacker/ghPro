<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ page import="com.gpdi.mdata.web.utils.NumberValidationUtils" %>
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


        function addFileRules() {
            var ruleNumber = $('#ruleNumber').val();
            if (ruleNumber == ""|| isNaN(ruleNumber)|| isInteger(ruleNumber)) {
                alert(ruleNumber + " 规则编码必须为数字,请参照其他规则编码填写!！");
                return false;
            }
            var startTime = $('#startTime').val();
            if (startTime == ""){
                alert("生效日期不能为空");
                return false;
            }
            var startFileName = $('#startFileName').val();
            if (startFileName ==""){
                alert("规则文件名称不能为空");
                return false;
            }
            var startFileNumber = $('#startFileNumber').val();
            if (startFileNumber ==""){
                alert("规则文件编号不能为空");
                return false;
            }
            var oneMoney = $('#oneMoney').val();
            if (oneMoney == ""|| isNaN(oneMoney)|| isInteger(oneMoney)) {
                alert("指定招标中的金额不能为空,且必须为数字");
                return false;
            }

            var twoMoney = $('#twoMoney').val();
            if (twoMoney == ""|| isNaN(twoMoney)|| isInteger(twoMoney)) {
                alert("指定招标中的金额不能为空,且必须为数字");
                return false;
            }

            var threeMoney = $('#threeMoney').val();
            if (threeMoney == ""|| isNaN(threeMoney)|| isInteger(threeMoney)) {
                alert("指定招标中的金额不能为空,且必须为数字");
                return false;
            }


            if (!window.confirm('确定保存吗?')) {
                return false;
            }
            $.ajax({
                dataType: 'text',
                url: 'operate.json?action=save',
                data:$('#file_form').serialize(),
                traditional: true,
                success: function (result) {
                    var result= jQuery.parseJSON(result);
                    if (result.ok) {
                        alert("保存规则成功!请返回查看!");
                        window.location.href = "./query.html?code="+ruleNumber;
                    }
                },
                error: function () {
                    alert('保存失败..')
                }
            });

        }
        function isInteger(obj) {
            return obj%1 != 0
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
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <form name="file_form" id="file_form" method="post">
        <input type="hidden" name="action" value="daoExcel"/>
    <div class="add-table col-xs-12">

        <table class="table table-striped table-hover"  bordercolor="#000000" border="1" cellspacing="0">
            <thead align="center">
            <tr align="center" width="80%" style=" text-align:center;height:20px;" >
                <th>规则编号</th>
                <th>生效日期</th>
                <th>生效文件名称</th>
                <th>生效文件号</th>
                <th>被废止文件名称</th>
                <th>被废止文件号</th>
                <%--<th>被废止日期</th>--%>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${fileRulers}" var="wow" varStatus="status">
                <tr align="center" width="100%" style="height:20px;">
                    <td><input type="text" id="ruleNumber" name="ruleNumber" value="${wow.ruleNumber+1}"></td>
                    <td><input type="text" name="startTime" id="startTime" value=""></td>
                    <td><input type="text" name="startFileName" id="startFileName" value=""></td>
                    <td><input type="text" name="startFileNumber" id="startFileNumber" value=""></td>
                    <td><input type="text" name="endFileName" value="${wow.fileBasisName}"></td>
                    <td><input type="text" name="endFileNumber" value="${wow.fileNumber}"></td>
                    <%--<td><input type="text" name="endTime" value=""></td>--%>
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
                    <td><input type="text" size="12" name="way" value="${row.way}"></td>
                    <td><input type="text" size="12" name="projectGoodsOne" value="${row.project_goods_one}"></td>
                    <td><input type="text" size="12" name="projectGoodsTwo" value="${row.project_goods_two}"></td>
                    <td><input type="text" size="12" name="projectServicesOne" value="${row.project_services_one}"></td>
                    <td><input type="text" size="12" name="projectServicesTwo" value="${row.project_services_two}"></td>
                    <td><input type="text" size="12" name="noProjectGoodsOne" value="${row.no_project_goods_one}"></td>
                    <td><input type="text" size="12" name="noProjectGoodsTwo" value="${row.no_project_goods_two}"></td>
                    <td><input type="text" size="12" name="noProjectGoodsThree" value="${row.no_project_goods_three}"></td>
                    <td><input type="text" size="12" name="integratedServicesOne" value="${row.integrated_services_one}"></td>
                    <td><input type="text" size="12" name="integratedServicesTwo" value="${row.integrated_services_two}"></td>
                    <td><input type="text" size="12" name="integratedServicesThree" value="${row.integrated_services_three}"></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="add-table col-xs-12" style="width: 100%; height:500px;display: flex;" >
        <div id="line" style="width: 500px;height:236px;flex: 0.4;border:1px solid #000;">
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
                    <td> ≥ <input type="text" size="5" name="oneMoney" id="oneMoney" value="${list.get(0).money.substring(0,list.get(0).money.length()-4)}"> 万</td>
                </tr>
                <tr align="center"  style="height:20px;">
                    <td>${list.get(1).titleThree}</td>
                    <td> ≥ <input type="text" size="5" name="twoMoney" id="twoMoney" value="${list.get(1).money.substring(0,list.get(1).money.length()-4)}"> 万</td>
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
                    <td> ≥ <input type="text" name="threeMoney" id="threeMoney" size="5" value="${list.get(2).money.substring(0,list.get(2).money.length()-4)}"> 万</td>
                </tr>
                </tbody>
            </table>
        </div>

        <%--<div id="roll" style="width: 400px;height:236px;flex: 0.6;border:1px solid #000;" align="center">--%>
        <div id="roll" style="width: 350px;height:236px;flex: 0.6;border:0px solid #fff5fa;" align="center">
            <br>
            <br>
            <br>
            <br>
            <br>
            <div align="center"><input name="upload" id="upload" type="button" value="保存" onclick="addFileRules();" class="btn btn-success "/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="reset" value="重置" class="btn btn-primary"></div>
           <%--     依法必须进行招标的项目是指工程建设项目中达到以下标准之一的项目：
            </br>（1）施工（含系统集成）发包单项合同估算价在${list.get(0).money.substring(0,list.get(0).money.length()-4)}万元人民币及以上的；
            </br>（2）重要设备（含软件）、材料等物资的采购，单项合同估算价在${list.get(2).money.substring(0,list.get(2).money.length()-4)}万元人民币及以上的；
            </br>（3）勘察、设计、监理等服务的采购，单项合同估算价在${list.get(1).money.substring(0,list.get(1).money.length()-4)}万元人民币及以上的；
            </br>（4）单项合同估算价低于第（一）、（二）、（三）项规定的标准，但项目总投资额在3000万元人民币及以上的；
            </br>（5）使用国际组织或外国政府贷款、援助资金的（提供贷款或资金方有合法的特殊要求除外）。
            </p>--%>
        </div>
    </div>

    </form>

    </div>

</div>
</body>
</html>

