<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ page language="java" contentType="text/html;charset=UTF-8"  %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<head>
    <meta charset="utf-8">
    <title>单一来源采购方式</title>
    <link type="text/css" rel="stylesheet" href="${home}/css/reportform/style.css">
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <script type="text/javascript" src="${home}/script/jquery.tablesorter.js"></script>
    <script type="text/javascript" src="${home}/script/echarts.common.min.js"></script>
    <style>
        table{font-size: 13px!important;}
        .table tr th{
            text-align: center;
            vertical-align: middle!important;
            font-size:18px;
        }
    </style>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#myTable").tablesorter({ //禁止某列排序
                headers:{
                    0:{
                        sorter:false
                    },
                    1:{
                        sorter:false
                    },
                    3:{
                        sorter:false
                    },
                    6:{
                        sorter:false
                    }
                },
                debug:true
            });
        });
    </script>
    <script type="text/javascript">

        function check(id) {
            var isExport=0;
            if (id == "check") {
                isExport=0;
            }else if(id=="export"){
                isExport=1;
            }
            var url = "query.html?isExport="+isExport;
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
                    <li><b><span style="font-size: 22px;color: #666600;">各采购类型所占比列</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>


    <div class="add-table col-xs-12">
        <button type="submit" id="export" class="btn btn-success" onclick="check(this.id);">导出</button>
        <table id="myTable" class="table table-bordered tablesorter">
            <thead align="center">
            <tr width="100%" bgcolor="#E6E6FA" style="height:35px;"  >
                <th>序号</th>
                <th>采购方式</th>
                <th>签订合同数量</th>
                <th>合同总数</th>
                <th>数量所占总比例</th>
                <th>签订合同金额</th>
                <th>合同总金额</th>
                <th>金额所占总比例</th>
            </tr>

            </thead>
            <tbody>

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}" align="center" id="trs">
                    <td >${status.index+1}</td>
                    <td class="aa">${row.aa}</td>
                    <td class="bb">${row.bb}</td>
                    <td>${row.cc}</td>
                    <td>${PercentUtil.percent(row.dd)}</td>
                    <td>${row.ee}</td>
                    <td>${row.ff}</td>
                    <td>${PercentUtil.percent(row.gg)}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
       <%-- <div style="border: 1px solid #000000;width: 100%;display: flex">--%>
        <div style="width: 100%;display: flex">
            <div id="line" style="width: 500px;height:400px;flex: 0.5"></div>
            <div id="roll" style="width: 400px;height:400px;flex: 0.5"></div>
        </div>
        <script type="text/javascript" src="${home}/script/reportform/roll.js"></script>
        <jsp:include page="/common/pager.jsp"/>
    </div>


</div>
</body>
</html>
