<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>拖拉机合同</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <style>
        table {
            font-size: 13px !important;
            /* important的作用:可以忽略后续设置，始终执行当前的设置*/
            white-space: nowrap;
        }

        th {
            text-align: center
        }

        td {
            text-align: center
        }
    </style>




</head>

<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row" align="center">
                <ol class="breadcrumb">
                    <%--<li><b><span style="font-size: 22px;color: #666600;">公开采购投标阶段围标可能性排查信息</span></b></li>--%>
                    <li><b><span style="font-size: 22px;color: #666600;">拖拉机合同排查</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>


    <div class="add-table col-xs-12">

        <table class="table table-bordered table-hover" id="table1">
            <thead align="center">
            <tr align="center" width="100%" style="height:20px;background-color: #ffffff;">
                <%--<th>序号</th>--%>
                <th>领导姓名</th>
                <th>工作部门</th>
                <th>起始时间</th>
                <th>结束时间</th>
                <th>重合供应商</th>
            </tr>

            </thead>
            <tbody id="tbody-result" name="tbody-result">

            <c:forEach items="${list}" var="row" varStatus="status">
                <tr  align="center">
                    <%--<td>${status.index+1}</td>--%>
                    <td>${row.leaderName}</td>
                    <td>${row.workDepartment}</td>
                    <td>${row.startTime}</td>
                    <td>${row.endTime}</td>
                    <c:forEach items="${row.newSupplerNum}" var="wow" varStatus="status">
                        <td><a href="../tractorcontractinfo/query.html?&name=${row.leaderName}&dept=${row.workDepartment}&supperName=${wow}"> ${wow}</a></td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
<script type="text/javascript" src="${home}/script/lib/jquery-ui.js"></script>
<script>
    jQuery.fn.rowspan = function (colIdx) { //封装的一个JQuery小插件
        return this.each(function () {
            var that;
            $('tr', this).each(function (row) {
                $('td:eq(' + colIdx + ')', this).filter(':visible').each(function (col) {
                    if (that != null && $(this).html() == $(that).html()) {
                        var rowspan = $(that).attr("rowSpan");
                        if (rowspan == undefined) {
                            $(that).attr("rowSpan", 1);
                            rowspan = $(that).attr("rowSpan");
                        }
                        rowspan = Number(rowspan) + 1;
                        $(that).attr("rowSpan", rowspan);
                        $(that).css("vertical-align","middle");
                        $(this).hide();
                    } else {
                        that = this;
                    }
                });
            });
        });
    };
    $(function () {
        $("#table1").rowspan(0);
        // $("#table1").rowspan(3);
        /* $("#table1").rowspan(3);
         $("#table1").rowspan(4);*/
    });
</script>