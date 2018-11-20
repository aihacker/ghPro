<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>查看即时清洁清单数据</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp?query"/>
    <style>
        table {
            font-size: 13px !important;
            white-space: nowrap;
        }
    </style>
    <script type="text/javascript">
        function check(id) {
            var isExport = 0;
            if (id == "export") {
                isExport = 1;
            }
            var url = "query.html?isExport=" + isExport;
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
                    <li><b><span style="font-size: 22px;color: #666600;">查看即时清洁清单数据</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>


    <div class="add-table col-xs-12">
        <button type="submit" id="export" class="btn btn-success" onclick="check(this.id)">导出</button>
        <table class="table table-bordered table-hover">
            <thead align="center">
            <tr align="center" width="100%" style="height:20px;background-color: #ffffff;">
                <%-- <td><b>明细序号</b></td>--%>
                <td><b>序号</b></td>
                <td><b>流程</b></td>
                <td><b>标题</b></td>
                <td><b>ISCM编号</b></td>
                <td><b>流程编号</b></td>
                <td><b>是否即时清洁电商企业预付款</b></td>
                <td><b>供应商编号</b></td>
                <td><b>供应商名称</b></td>
                <td><b>物料编码</b></td>
                <td><b>物料名称</b></td>
                <td><b>计量单位</b></td>
                <td><b>成交单价(元)</b></td>
                <td><b>成交数量</b></td>
                <td><b>成交金额(元)</b></td>
                <td><b>拟稿单位</b></td>
                <td><b>拟稿人</b></td>
                <td><b>拟稿部门</b></td>
                <td><b>采购金额(万)</b></td>
                <td><b>拟稿时间</b></td>
                <td><b>采购方式</b></td>
                <td><b>是否在经过公开评选年度供应商范围内组织的采购</b></td>
                <td><b>采购分类</b></td>
                <td><b>资金来源</b></td>
                <td><b>最新到达采购实施部门时间</b></td>
                <td><b>采购实施部门提交时间</b></td>
                <td><b>当前状态</b></td>
                <td><b>当前处理人</b></td>
                <td><b>产品或服务类型</b></td>

            </tr>

            </thead>

            <tbody>

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}" align="center">
                        <%--<td>${status.index+1}</td>--%>
                        <%--<td>${(page.pageNum - 1) * page.pageSize + num.count}</td>--%>
                    <td>${row.id}</td>
                    <td>${row.flow_path}</td>
                    <td>${row.title}</td>
                    <td>${row.iscm_number}</td>
                    <td>${row.flow_path_number}</td>
                    <td>${row.clean_advance}</td>
                    <td>${row.supplier_code}</td>
                    <td>${row.supplier_name}</td>
                    <td>${row.material_code}</td>
                    <td>${row.material_name}</td>
                    <td>${row.unit_measurement}</td>
                    <td>${row.transaction_price}</td>
                    <td>${row.turnover}</td>
                    <td>${row.trading_volume}</td>
                    <td>${row.draft_unit}</td>
                    <td>${row.undertake_man}</td>
                    <td>${row.draft_department}</td>
                    <td>${row.purchase_amount}</td>
                    <td>${row.draft_time}</td>
                    <td>${row.procurement_method}</td>
                    <td>${row.whether_open_purchasing}</td>
                    <td>${row.procurement_classification}</td>
                    <td>${row.capital_source}</td>
                    <td>${row.newest_time}</td>
                    <td>${row.procurement_submit_time}</td>
                    <td>${row.current_state}</td>
                    <td>${row.current_handler}</td>
                    <td>${row.product_service_type}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>

    </div>

</div>
</body>
</html>
