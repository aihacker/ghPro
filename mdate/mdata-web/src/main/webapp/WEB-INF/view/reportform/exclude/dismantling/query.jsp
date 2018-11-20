<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ page import="com.gpdi.mdata.web.utils.JiebaUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>拆单可能性排查</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <script src="${home}/script/lib/My97DatePicker/WdatePicker.js"></script>
    <%--<link rel="stylesheet" href="${home}/style/tree/metroStyle/metroStyle.css" type="text/css">
    <link rel="stylesheet" href="${home}/style/tree//zTreeStyle/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="${home}/script/tree/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${home}/script/tree/jquery.ztree.excheck.js"></script>--%>
    <style>
        table {
            font-size: 13px !important;
        }

        .table tr th {
            text-align: center;
            vertical-align: middle !important;
            white-space: nowrap;
        }

        .table tr td {
            /*text-align: center;*/
            vertical-align: middle !important;
            white-space: nowrap;
        }

        .table-bordered tbody tr td {
            border: 1px solid #0C0C0C;
        }

        .table-bordered thead tr th {
            border: 1px solid #0C0C0C;
        }
    </style>

    <script type="text/javascript">
        function initCheckbox() {
            debugger;
            var aa = ${param.str1};
            var bb =${param.str2};
            var cc =${param.str3};
            if (aa == 1) {
                $("#box1").attr("checked", true);
            }
            if (bb == 2) {
                $("#box2").attr("checked", true);
            }
            if (cc == 3) {
                $("#box3").attr("checked", true);
            }
        }
        $(function () {
            initCheckbox();
        });

    </script>
    <script type="text/javascript">

        function check(id) {
            var screenvalue = $("#score").val();
            var startTime = $('#startTime').val();
            var endTime = $('#endTime').val();
            var contractType = $('#constractType').val();
            var supperName = $('#supperName').val();
            var department = $('#constractDept').val();
            var purchaseWay = $('#purchaseWay').val();
            if (startTime == null || startTime == "") {
                startTime = "2017-01-01";
            }
            if (endTime == null || endTime == "") {
                endTime = "2017-12-30";
            }
            var str1 = 0;
            var str2 = 0;
            var str3 = 0;
            var str4 = 0;
            var groupCheckbox = $("input[name='box']");
            for (var i = 0; i < groupCheckbox.length; i++) {
                if (groupCheckbox[i].checked) {
                    var val = groupCheckbox[i].value;
                    if (val == 1) {
                        str1 = 1;
                    } else if (val == 2) {
                        str2 = 2;
                    } else if (val == 3) {
                        str3 = 3;
                    } else if (val == 4) {
                        str4 = 4;
                    }
                }
            }
            var isExport=0;
            if (id == "check") {
               isExport=0;
            }else if(id=="export"){
               isExport=1;
            }
            var url = "query.html?code=" + screenvalue + "&startTime=" + startTime + "&endTime=" + endTime + "&str1=" + str1 + "&str2=" + str2 + "&str3=" + str3 + "&str4=" + str4 + "&type=" + contractType + "&supperName=" + supperName + "&dept=" + department + "&purchaseWay=" + purchaseWay+"&isExport="+isExport;
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
                    <li><b><span style="font-size: 22px;color: #666600;">合同拆单可能性排查</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div class="form-inline clearfix">
        <div class="form-group col-xs-12">
            <p>
                <label class="font-color" for="startTime">开始日期：</label>
                <input type="text" size="15" value="${param.startTime}" id="startTime" name="startTime" class="Wdate"
                       onFocus="WdatePicker({lang:'zh-cn',maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy/M/d'})"
                       placeholder="开始日期"/>&nbsp;&nbsp;
                <label class="font-color" for="endTime">结束日期：</label>
                <input type="text" size="15" value="${param.endTime}" id="endTime" name="endTime" class="Wdate"
                       onFocus="WdatePicker({lang:'zh-cn',minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy/M/d'})"
                       placeholder="结束日期"/>&nbsp;&nbsp;
                合同类型:
                <%--<input  type="text"  size="15" id="type" name="type" value="${param.type}" placeholder="合同类型">--%>
                <select name="constractType" id="constractType" style="width: 200px;height: 25px">
                    <option value="01" <c:if test="${param.type.equals('01')}"> selected="selected"</c:if>>请选择</option>
                    <c:forEach items="${list}" var="typeList" varStatus="status">
                        <option value=${typeList}
                                <c:if test="${param.type == typeList}"> selected="selected"</c:if> >${typeList}</option>
                    </c:forEach>
                </select>

                部门:
                <%--<input  type="text"  size="15" id="dept" name="dept" value="${param.dept}" placeholder="部门">--%>
                <select name="constractDept" id="constractDept" style="width: 150px;height: 25px">
                    <option value="01" <c:if test="${param.dept.equals('01')}"> selected="selected"</c:if>>请选择</option>
                    <c:forEach items="${deptList}" var="deptList" varStatus="status">
                        <option value=${deptList}
                                <c:if test="${param.dept == deptList}"> selected="selected"</c:if> >${deptList}</option>
                    </c:forEach>
                </select>
                <br/>
                采购方式:
                <%--<input  type="text"  size="15" id="dept" name="dept" value="${param.dept}" placeholder="部门">--%>
                <select name="purchaseWay" id="purchaseWay" style="width: 150px;height: 25px">
                    <option value="01" <c:if test="${param.purchaseWay.equals('01')}"> selected="selected"</c:if>>请选择
                    </option>
                    <c:forEach items="${purchaseWay}" var="purchaseWay" varStatus="status">
                        <option value=${purchaseWay}
                                <c:if test="${param.purchaseWay == purchaseWay}"> selected="selected"</c:if> >${purchaseWay}</option>
                    </c:forEach>
                </select>
                <br>供应商:<input type="text" size="15" id="supperName" name="supperName" value="${param.supperName}"
                               placeholder="供应商名称">
                &nbsp;&nbsp;&nbsp;&nbsp;<button type="submit" id="check" class="btn btn-success"
                                                onclick="check(this.id);">查询
            </button>
                <button type="submit" id="export" class="btn btn-success" onclick="check(this.id);">导出</button>
            </p>
            <p>
                <label class="font-color" for="startTime">去掉: </label>
                <span> &nbsp;xxxx年xx月&nbsp; </span><input type="checkbox" name="box" id="box1" value="1"/>&nbsp;
                <span> &nbsp;xx期&nbsp;</span><input type="checkbox" name="box" id="box2" value="2"/>&nbsp;
                <span> &nbsp;第xx批&nbsp;</span><input type="checkbox" name="box" id="box3" value="3"/>&nbsp;
                <span> &nbsp;业务代理&nbsp;</span><input type="checkbox" name="box" id="box4" value="4"/>&nbsp;
                &nbsp;<span> 等字样</span>
            </p>

        </div>
    </div>
    <div class="add-table col-xs-12">

        <table id="myTable" class="table table-bordered tablesorter" style="border:1px solid #0C0C0C;">
            <thead align="center">
            <tr align="center" style="width:100%;">
                <th>序号</th>
                <th>合同名称对比</th>
                <th>签订时间</th>
                <th>权重<br/>
                    <select name="score" id="score" onchange="check()">
                        <option value="0.95" <c:if test="${param.code==0.95}"> selected="selected" </c:if>>0.95</option>
                        <option value="0.9" <c:if test="${param.code==0.9}"> selected="selected" </c:if>>0.9</option>
                        <option value="0.85" <c:if test="${param.code==0.85}"> selected="selected" </c:if>>0.85</option>
                        <option value="0.8" <c:if
                                test="${param.code==0.8 || param.code==null}"> selected="selected" </c:if>>0.8
                        </option>
                        <option value="0.75" <c:if test="${param.code==0.75}"> selected="selected" </c:if>>0.75</option>
                        <option value="0.7" <c:if test="${param.code==0.7}"> selected="selected" </c:if>>0.7</option>
                    </select>
                    以上
                </th>
                <th>合同金额</th>
                <th>采购方式</th>
                <th>合同编号</th>
                <th>标的物</th>
                <th>采购方案号</th>
                <th>经办部门</th>
                <th>供应商</th>
                <th>合同类型</th>

            </tr>

            </thead>
            <tbody>
            <c:forEach items="${queryResult}" var="row" varStatus="status">
                <tr style="width:100%;">
                    <td>${status.index+1}</td>

                    <td>
                        <div style="overflow:hidden;text-overflow: ellipsis;width: 798px;">${JiebaUtil.getSomeDiff(row.colect.toString().replace("[","").replace("]","").replace(" ",""))}</div>
                    </td>
                    <td>${row.purTime.toString().replace("[","").replace("]","&nbsp").replace(",","</br>")}</td>
                    <td>${row.score}</td>
                    <td>${row.purMoney.toString().replace("[","").replace("]","&nbsp").replace(",","</br>")}</td>
                    <td>${row.purWay.toString().replace("[","").replace("]","&nbsp").replace(",","</br>")}</td>
                    <td>${row.purCode.toString().replace("[","").replace("]","&nbsp").replace(",","</br>")}</td>
                    <td>&nbsp;</td>
                    <td>${row.purPlanCode.toString().replace("[","").replace("]","&nbsp").replace(",","</br>")}</td>
                    <td>${row.undertakeDept}</td>
                    <td>${row.supplierName}</td>
                    <td>${row.contractTypeName}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%--<script src="${home}/script/reportform/typeModel.js"></script>--%>
        <%-- <jsp:include page="/common/pager.jsp"/>--%>
    </div>

</div>

</body>
</html>

