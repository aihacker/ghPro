<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>合同围标可能性排查信息</title>
    <link type="text/css" rel="stylesheet" href="${home}/css/reportform/style.css">
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <style>
        table {
            font-size: 13px !important;
            /* important的作用:可以忽略后续设置，始终执行当前的设置*/
            white-space: nowrap;
        }

      thead th {
            text-align: center;
        }

        .table-bordered  tbody tr td {
            border: 1px solid #0C0C0C;
        }
        .table-bordered thead tr th {
            border: 1px solid #0C0C0C;
        }
    </style>


    <%--<script type="text/javascript">
        function merge(tableId,col){
            var tr = document.getElementById(tableId);
            for(var i=1; i<tr.rows.length; i++){                //表示数据内容的第二行
                if(tr.rows[i].cells[col].innerHTML == tr.rows[i - 1].cells[col].innerHTML){//col代表列
                    var t = i-1;
                    while(tr.rows[i].cells[col].innerHTML == tr.rows[t].cells[col].innerHTML){
                        tr.rows[i].cells[col].style.display="none";
                        if(tr.rows[t].cells[col].rowSpan <= (i-t)){
                            tr.rows[t].cells[col].rowSpan +=1;      //设置前一行的rowspan+1
                        }
                        i++;
                    }
                }
            }
        }
    </script>--%>

</head>

<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row" align="center">
                <ol class="breadcrumb">
                    <%--<li><b><span style="font-size: 22px;color: #666600;">公开采购投标阶段围标可能性排查信息</span></b></li>--%>
                    <li><b><span style="font-size: 22px;color: #666600;">合同围标可能性排查信息</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>


    <div class="add-table col-xs-12">

        <table class="table table-bordered table-hover" id="table1" style="border: 1px solid #0C0C0C;">
            <thead align="center">
            <tr align="center" width="100%" style="height:20px;background-color: #ffffff;">
                <%-- <th>序号</th>--%>
                <th>合同名称</th>
                <th>合同编码</th>
                <th>签订时间</th>
                <th>采购方式</th>
                <th>参与投标公司名称</th>
                <th>是否存在围标可能性</th>
                <th>法人</th>
                <th>股东1</th>
                <th>股东2</th>
                <th>高管1</th>
                <th>高管2</th>
            </tr>

            </thead>
            <tbody id="tbody-result" name="tbody-result" >

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <c:if test="${!row.project_code.equals('18fsZN0056')}">
                <tr class="${row.locked == 1? 'locked': ''}"  align="center">

                    <td>${row.contract_name}</td>
                    <td>${row.contract_code}</td>
                    <td>${row.purchase_time}</td>
                    <td>${row.purchase_way}</td>
                    <td>${row.company_name}</td>

                    <c:choose>
                        <c:when test="${row.legal_representative.equals(row.relName) || row.shareholder_one.equals(row.relName) || row.shareholder_two.equals(row.relName) || row.senior_admin_one.equals(row.relName) || row.senior_admin_two.equals(row.relName)}">
                            <td>存在围标的可能性</td>
                        </c:when>
                        <c:otherwise>
                            <td>&nbsp;</td>
                        </c:otherwise>
                    </c:choose>
                    <td <c:if test="${row.legal_representative.equals(row.relName)}">bgcolor="#ffe4c4"</c:if>>${row.legal_representative}</td>
                    <td <c:if test="${row.shareholder_one.equals(row.relName)}">bgcolor="#ffe4c4"</c:if>>${row.shareholder_one}</td>
                    <td <c:if test="${row.shareholder_two.equals(row.relName)}">bgcolor="#ffe4c4"</c:if>>${row.shareholder_two}</td>
                    <td <c:if test="${row.senior_admin_one.equals(row.relName)}">bgcolor="#ffe4c4"</c:if>>${row.senior_admin_one}</td>
                    <td <c:if test="${row.senior_admin_two.equals(row.relName)}">bgcolor="#ffe4c4"</c:if>>${row.senior_admin_two}</td>

                </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>
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
            $("#table1").rowspan(0);//传入的参数是对应的列数从0开始
        $("#table1").rowspan(1);
        $("#table1").rowspan(2);
        // $("#table1").rowspan(3);
        /* $("#table1").rowspan(3);
         $("#table1").rowspan(4);*/
    });
</script>
