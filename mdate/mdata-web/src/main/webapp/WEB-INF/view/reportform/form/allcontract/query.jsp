<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>采购报表分析</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>

</head>
<style>
    table{
        font-size: 13px!important;
    }
    td {white-space: nowrap;}
</style>
<script type="text/javascript" >
    function check(id) {
        var contractNum=$("#male").val();
        var contractName=$("#contractName").val();
        var supperName=$("#supperName").val();
        var isExport=0;
        if(id=="export"){
            isExport=1;
        }
        var url = "query.html?contractNum="+contractNum+"&contractName="+contractName+"&supperName="+supperName+"&isExport="+isExport;
        window.location.href = url;
    }
    //清空文本框数据
      /*  $(function() {
            $("input").attr("value","");
        });*/
      /*  $("#form input").val("");*/
</script>
<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="">采购报表分析</a></li>
                    <li><a href=""></a></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <!--modelAttribute="queryData":通过绑定实体queryData类进行初始化-->
       <form:form modelAttribute="queryData" method="get">
        <div class="form-inline clearfix">
            <div class="form-group col-xs-12">
                <label  class="font-color" for="male">合同查询：</label>
                <form:input path="contractNum"  cssClass="form-control width15" placeholder="合同编号" id="male"/> +
                <form:input path="contractName" id="contractName"  cssClass="form-control width15" placeholder="合同名称"/> +
                <form:input path="supperName"  id="supperName" cssClass="form-control width15" placeholder="供应商名称"/>
                <button type="submit" class="btn btn-success">查询</button>

                <button type="reset"  class="btn btn-warning">清空</button>
            </div>
        </div>
    </form:form>

    <button id="export" class="btn btn-success" onclick="check(this.id)">导出</button>
    <div class="add-table col-xs-12">

        <table class="table table-bordered" >
            <thead align="center">
            <tr align="center" class="cdc">
                <td><b>序号</b></td>
                <td><b>合同编号</b></td>
                <td><b>定稿时间</b></td>
                <td><b>合同名称</b></td>
                <td><b>合同金额</b></td>
                <td><b>承办单位</b></td>
                <td><b>承办部门</b></td>
                <td><b>承办人</b></td>
                <td><b>对方名称</b></td>
                <td><b>采购方</b></td>
                <td><b>采购类型</b></td>
                <td><b>公司代码</b></td>
                <td><b>采购种类</b></td>
                <td><b>设备/服务器类型</b></td>
                <td><b>设备类型第一层</b></td>
                <td><b>设备类型第二层</b></td>
                <td><b>签约类型</b></td>
                <td><b>是否关联交易</b></td>
                <td><b>关联交易类型</b></td>
                <td><b>是否ITC项目</b></td>
                <td><b>是否属于关键合同</b></td>
                <td><b>收付类型</b></td>
                <td><b>合同类型</b></td>
                <td><b>采购结果编号</b></td>
                <td><b>合同属性</b></td>
                <td><b>归档日期</b></td>
                <td><b>合同状态</b></td>
            </tr>
            </thead>
            <tbody>

           <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="cdc2" >

                    <td>${row.id}</td>
                    <td>${row.contract_code}</td>
                    <td>${row.purchase_time}</td>
                    <td >${row.contract_name}</td>
                    <td>${row.contract_amount}</td>
                    <td>${row.undertake_org}</td>
                    <td>${row.undertake_dept}</td>
                    <td>${row.undertake_man}</td>
                    <td>${row.supplier_name}</td>
                    <td>${row.purchase_way}</td>
                    <td>${row.purchase_type}</td>
                    <td>${row.company_code}</td>
                    <td>${row.purchase_kind}</td>
                    <td>${row.set_service_type}</td>
                    <td>${row.set_type_lv1}</td>
                    <td>${row.set_type_lv2}</td>
                    <td>${row.sign_type}</td>
                    <td>${row.is_correlation_trade}</td>
                    <td>${row.correlation_trade_type}</td>
                    <td>${row.is_itc_project}</td>
                    <td>${row.is_pivotal_contract}</td>
                    <td>${row.receipt_pay_type}</td>
                    <td>${PercentUtil.getChinese(row.contract_type)}</td>
                    <td>${row.purchase_result_code}</td>
                    <td>${row.contract_prop}</td>
                    <td>${row.archive_date}</td>
                    <td>${row.contract_status}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>
    </div>
</div>
</body>
</html>
