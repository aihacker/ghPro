<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>领导及其亲属围绕企业经商排查</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <style>
        table{font-size: 13px!important;}
    </style>
    <script type="text/javascript">
        function conType(obj) {
            var conValue = obj.value;
            var url = "query.html?name=" + conValue;
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
                    <li><b><span style="font-size: 22px;color: #666600;">领导及其亲属围绕企业经商排查</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>

    <div class="add-table col-xs-12">

        <table class="table table-bordered">
            <thead align="center">
            <tr align="center">
                <td><b>序号</b></td>
               <%-- <td><b>项目名称</b></td>
                <td><b>供应商</b></td>
                <td><b>供应商法人</b></td>
                <td><b>股东1</b></td>
                <td><b>股东2</b></td>
                <td><b>高管1</b></td>
                <td><b>高管2</b></td>--%>
                <td><b>亲属姓名</b></td>
                <td><b>领导</b></td>
                <td><b>与领导关系</b></td>
                <td><b>任职供应商名称</b></td>
                <td><b>所任职务</b></td>
                <td><b>供应商与公司签订合同数</b></td>
                <td><b>任职公司名称是否报备</b></td>

            </tr>

            </thead>
            <tbody>

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}" align="center">
                    <td>${status.index+1}</td>
                  <%--<td>${row.contract_name}</td>--%>
                    <%--<td>${row.supplier_name}</td>--%>

                        <%-- <td <c:if test="${row.legal_representative.equals(row.kinsfolk_name)}">bgcolor="#ffe4c4"</c:if>>${row.legal_representative}</td>
                       <td <c:if test="${row.shareholder_one.equals(row.kinsfolk_name)}">bgcolor="#ffe4c4"</c:if>>${row.shareholder_one}</td>
                       <td <c:if test="${row.shareholder_two.equals(row.kinsfolk_name)}">bgcolor="#ffe4c4"</c:if>>${row.shareholder_two}</td>
                       <td <c:if test="${row.senior_admin_one.equals(row.kinsfolk_name)}">bgcolor="#ffe4c4"</c:if>>${row.senior_admin_one}</td>
                       <td <c:if test="${row.senior_admin_two.equals(row.kinsfolk_name)}">bgcolor="#ffe4c4"</c:if>>${row.senior_admin_two}</td>--%>

                <%--<c:forEach items="${purList}" var="purList">
                    <c:choose>
                        <c:when test="${purList.equals(row.legal_representative) || purList.equals(row.shareholder_one) || purList.equals(row.shareholder_two) || purList.equals(row.senior_admin_one) || purList.equals(row.senior_admin_two)}">
                            <td>${purList}</td>
                        </c:when>
                    </c:choose>
                </c:forEach>--%>
                    <td bgcolor="#ffe4c4">${row.kinsfolk_name}</td>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td>
                    <td>${row.supplier_name}</td>
                    <%--<td>${row.duty}</td>--%>
                    <td>${row.duty}</td>
                    <td><a href="${home}/reportform/exclude/relativesrel/query.html?supperName=${row.supplier_name}" >${row.sname}</a></td>
                    <td><c:choose><c:when test="${row.etname !=0}">是</c:when><c:otherwise>否</c:otherwise></c:choose></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <jsp:include page="/common/pager.jsp"/>
    </div>

</div>
</body>
</html>
