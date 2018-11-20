<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>应招标规则说明</title>
    <link type="text/css" rel="stylesheet" href="${home}/css/reportform/style.css">
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
                    <li><b><span style="font-size: 22px;color: #666600;">应招标规则说明</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>


    <div class="add-table col-xs-12">

        <table class="table table-bordered table-hover" id="table1">
            <thead align="center">
            <tr align="center" width="100%" style="height:20px;background-color: #ffffff;">
                <th>序号</th>
                <th>编号</th>
                <th>生效日期</th>
                <th>废止日期</th>
                <th>生效文件名称</th>
                <th>生效文件文号</th>
                <th>被废止文件名称</th>
                <th>废止文件文号</th>

            </tr>

            </thead>
            <tbody id="tbody-result" name="tbody-result">

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}" align="center">
                    <td>${status.index+1}</td>
                    <td>${row.rule_number}</td>
                    <td>${row.start_date}</td>
                    <td>${row.end_date}</td>
                    <td><a href="../tenderingrules/query.html?code=${row.rule_number}">${row.file_basis_name}</a></td>
                    <td>${row.file_number}</td>
                    <%--<td><a href="${home}/temp/wen2018136.doc">${row.file_abolish_name}</a></td>--%>
                    <td>${row.file_abolish_name}</td>
                    <td>${row.file_abolish_number}</td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>
    </div>

</div>
</body>
</html>
