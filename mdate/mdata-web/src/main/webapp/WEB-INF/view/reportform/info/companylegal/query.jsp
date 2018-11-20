<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>公司信息查询</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp?query"/>
    <style>
        table  {font-size: 13px !important;
            white-space:nowrap;}
        table th {
            text-align: center;
        }
    </style>
    <script type="text/javascript">

        function check(id) {
            var name = $("#male").val();
            var isExport=0;
            if(id=="export"){
                isExport=1;
            }
            var url = "query.html?name=" + name +"&isExport="+isExport;
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
                    <li><b><span style="font-size: 22px;color: #666600;">公司信息查看</span></b></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <form:form modelAttribute="ytcardData" method="get" >
        <div class="form-inline clearfix">
            <div class="form-group col-xs-12">
                <label  class="font-color" for="male">公司查询：</label>
                <form:input path="name"  cssClass="form-control width15" placeholder="公司名称" id="male"/>
                <button type="submit" class="btn btn-success" onclick="return check();">查询</button>
            </div>
        </div>
    </form:form>
    <span><button id="export" class="btn btn-success" onclick="check(this.id);">导出</button></span>
    <div class="add-table col-xs-12">

            <table class="table table-bordered table-hover">
            <thead align="center">
            <tr align="center" width="100%" style="height:20px;background-color: #ffffff;">
               <th>序号</th>
               <th>公司名称</th>
               <th>公司历史曾用名</th>
               <th>法人代表</th>
               <th>纳税号</th>
               <th>社会信用编码</th>
               <th>股东1</th>
               <th>股东2</th>
               <th>股东3</th>
               <th>股东4</th>
               <th>高管1</th>
               <th>高管2</th>
               <th>高管3</th>
               <th>实际控制人</th>
               <th>是否国企</th>
               <th>更新时间</th>
            </tr>

            </thead>

           <tbody>

           <c:forEach items="${queryResult.rows}" var="row" varStatus="status" >
               <tr class="${row.locked == 1? 'locked': ''}" align="center">
                   <%--<td>${status.index+1}</td>--%>
                   <td>${row.id}</td>
                   <td>${row.company_name}</td>
                   <td>${row.company_historical_name}</td>
                   <td>${row.legal_representative}</td>
                   <td>${row.tax_code}</td>
                   <td>${row.credit_code}</td>
                   <td>${row.shareholder_one}</td>
                   <td>${row.shareholder_two}</td>
                   <td>${row.shareholder_three}</td>
                   <td>${row.shareholder_four}</td>
                   <td>${row.senior_admin_one}</td>
                   <td>${row.senior_admin_two}</td>
                   <td>${row.senior_admin_three}</td>
                   <td>${row.controller}</td>
                   <td>${row.is_country}</td>
                   <td>${row.input_time}</td>
               </tr>
           </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>

    </div>

</div>
</body>
</html>
