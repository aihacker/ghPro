<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>投标公司排查</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>

</head>
<style>
    table {
        font-size: 13px !important;
    }

    td {
        white-space: nowrap;
    }
</style>
<script type="text/javascript">
    function check() {
        var codes = $('#male').val();
        if (codes == null || codes == "") {
            alert("合同编号不能为空");
            return false;
        }
    }

</script>
<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row">
                <ol class="breadcrumb">
                    <li><a href="">投标公司排查</a></li>
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
                <label class="font-color" for="male">部门名称：</label>
                <form:input path="name" cssClass="form-control width15" placeholder="公司名称" id="male"
                            value="${param.name}"/>
                <button type="submit" class="btn btn-success" onclick="return check();">查询</button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <label class="font-color" for="male" style="font-size: 24px">签订合同数量:${count}个</label>
            </div>
        </div>
    </form:form>

    <div class="add-table col-xs-12">



        <div style="border: 1px solid #010bff;width: 100%;">
            <table border="1px" width="100%">
                <thead>
                <th>
                    公司名称
                </th>
                <th>
                    合同数量
                </th>

                <th>
                    百分比
                </th>

                </thead>
                <tbody>
                <c:forEach var="entry" items="${hashMap}">
                  <tr>
                      <td>
                          ${entry.key}
                      </td>
                      <td>
                            ${entry.value}
                      </td>
                      <td>
                          &nbsp;

                     ${PercentUtil.percent(entry.value/count)}
                      </td>

                    </tr>


                </c:forEach>
                </tbody>
            </table>

            <%--<textarea rows="8">${output}</textarea>--%>

        </div>


    </div>
</div>
</body>
</html>
