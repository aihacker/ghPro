<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>采购报表分析</title>
    <link type="text/css" rel="stylesheet" href="${home}/css/reportform/style.css">
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <script src="${home}/script/lib/My97DatePicker/WdatePicker.js"></script>
    <style>
        table {
            font-size: 13px !important;
            white-space:nowrap;
        }
    </style>

    <script type="text/javascript" >
        function check() {
            var conValue = $("#constractType").val();
            var screenvalue = $("#screen").val();
            var startTime =  $('#startTime').val();
            var endTime =  $('#endTime').val();
            var parameter ="?code="+screenvalue+"&name="+conValue+"&startTime="+startTime+"&endTime="+endTime;
            var url = "query.html"+parameter;
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
                    <li><a href=""><b><span style="font-size: 22px;color: #666600;">按项目类型分类---签订数量不超过3个候选供应商的合同信息</span></b></a>
                    </li>
                    <li><a href=""> </a></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div>
        <p><span>&nbsp;&nbsp;&nbsp;&nbsp;请选择合同类型:&nbsp;&nbsp;&nbsp;
            <select name="constractType" id="constractType" onchange="check()" style="width: 200px;height: 25px">
                <option value="01" <c:if test="${param.name.equals('01')}"> selected="selected"</c:if>>请选择</option>
            <c:forEach items="${list}" var="typeList" varStatus="status">
                <option value=${typeList}
                        <c:if test="${param.name == typeList}"> selected="selected"</c:if> >${PercentUtil.getChinese(typeList)}</option>
            </c:forEach>

            </select>
            </span>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;排名前:&nbsp;&nbsp;
            <select name="screen" id="screen" onchange="check()" style="width: 50px;height: 25px">
                <option value="5" <c:if test="${param.code==5}"> selected="selected" </c:if>>5</option>
                <option value="10" <c:if test="${param.code==10 || param.code==null}"> selected="selected" </c:if>>10</option>
                <option value="15" <c:if test="${param.code==15}"> selected="selected" </c:if>>15</option>
                <option value="20" <c:if test="${param.code==20}"> selected="selected" </c:if>>20</option>
                <option value="30" <c:if test="${param.code==30}"> selected="selected" </c:if>>30</option>
                <option value="50" <c:if test="${param.code==50}"> selected="selected" </c:if>>50</option>

            </select>个
            </span>
        </p>
    </div>
    <div class="form-inline clearfix">
        <div class="form-group col-xs-12">
            <label  class="font-color" for="startTime">开始日期：</label>
            <input  type="text"  value="${param.startTime}" id="startTime" name="startTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd'})" placeholder="开始日期"/>&nbsp;&nbsp;
            <label  class="font-color"  for="endTime">结束日期：</label>
            <input  type="text"   value="${param.endTime}" id="endTime" name="endTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd'})" placeholder="结束日期"/>&nbsp;&nbsp;
            <button type="submit" class="btn btn-success" onclick="check();">查询</button>
        </div>
    </div>
    <div class="add-table col-xs-12">

        <table class="table table-bordered table-hover">
            <thead align="center">
            <tr align="center">
                <td><b>序号</b></td>
                <td><b>合同编号</b></td>
                <td><b>合同名称</b></td>
                <td><b>合同类型</b></td>
                <td><b>签订合同数量</b></td>
                <td><b>候选供应商名称</b></td>

            </tr>
            </thead>

            <tbody id="tbody-result" name="tbody-result">
            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}" align="center">
                    <td>${status.index+1}</td>
                    <td>${row.contract_code}</td>
                    <td>${row.contract_name}</td>
                    <td>${row.contract_type_name}</td>
                    <td>${row.cc}</td>
                    <c:if test="${row.cc>1}"><td>${row.gc.split("\\,")[0]}<br/>${row.gc.split("\\,")[1]}</td></c:if>
                    <c:if test="${row.cc<2}"><td>${row.gc}</td></c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/modal.jsp"/>
        <script type="text/javascript" src="${home}/script/reportform/contract.js"></script>
        <jsp:include page="/common/pager.jsp"/>
    </div>
</div>
<script type="text/javascript" src="${home}/script/jquery.tablesorter.js"></script>
</body>

</html>
