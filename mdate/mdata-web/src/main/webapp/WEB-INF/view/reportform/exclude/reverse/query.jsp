<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>排查是否存在倒签合同的情况</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <style>
        table{font-size: 13px!important;}
        td {white-space: nowrap;}

    </style>
    <script type="text/javascript">
        function func(obj) {
            var conValue = obj.value;
            var url = "query.html?code=" + conValue;
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
                    <li><b><span style="font-size: 22px;color: #666600;">排查是否存在倒签合同的情况</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <div>
        <p><span>&nbsp;&nbsp;&nbsp;&nbsp;时间相差:&nbsp;&nbsp;&nbsp;
            <select name="dateTime" id="dateTime" onchange="func(this)" style="width: 100px;height: 25px" >
                <option value="3" <c:if test="${param.code.equals('3')}" > selected="selected"</c:if>>3</option>
                <option value="5" <c:if test="${param.code.equals('5')}" > selected="selected"</c:if>>5</option>
                <option value="7" <c:if test="${param.code.equals('7') || param.code ==null }" > selected="selected"</c:if>>7</option>
                <option value="10" <c:if test="${param.code.equals('10')}" > selected="selected"</c:if>>10</option>
                <option value="30" <c:if test="${param.code.equals('30')}" > selected="selected"</c:if>>30</option>
                <option value="0" <c:if test="${param.code.equals('0')}" > selected="selected"</c:if>>30天以上</option>
    </select>天以内
            </span>
        </p>
    </div>

    <div class="add-table col-xs-12">

        <table class="table table-bordered">
            <thead align="center">
            <tr align="center">
                <td><b>序号</b></td>
                <td><b>项目名称</b></td>
                <td><b>供应商</b></td>
                <td><b>签订时间</b></td>
                <td><b>合同履约起始时间</b></td>
                <td><b>时间相差天数</b></td>
                <td><b>合同收付类型</b></td>
            </tr>

            </thead>
            <tbody>

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}" align="center">
                    <td>${status.index+1}</td>
                    <td>${row.contract_name}</td>
                    <td>${row.supplier_name}</td>
                    <td>${row.finalize_date}</td>
                    <td>${row.performance_begin}</td>
                    <td>${row.tim}</td>
                    <td>${row.receipt_pay_type}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <jsp:include page="/common/pager.jsp"/>
    </div>

</div>
</body>
</html>
