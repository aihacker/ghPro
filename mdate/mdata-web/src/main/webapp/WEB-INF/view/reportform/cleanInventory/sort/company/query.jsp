<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ page import="com.gpdi.mdata.web.utils.CountStringUtil" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>投标公司排查</title>
    <link type="text/css" rel="stylesheet" href="${home}/css/reportform/style.css">
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <link rel="stylesheet" href="${home}/script/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="${home}/script/jquery.tablesorter.js"></script>
    <script type="text/javascript" src="${home}/script/echarts.common.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#myTable").tablesorter({ //禁止某列排序
                headers:{
                    0:{
                        sorter:false
                    },
                    2:{
                        sorter:false
                    }
                },
                sortList: [[1,1]],
                debug:true
            });
        });
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#myTable2").tablesorter({ //禁止某列排序
                headers:{
                    0:{
                        sorter:false
                    },
                    2:{
                        sorter:false
                    }
                },
                sortList: [[1,1]],
                debug:true
            });
        });
    </script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#myTable3").tablesorter({ //禁止某列排序
                headers:{
                    0:{
                        sorter:false
                    },
                    2:{
                        sorter:false
                    }
                },
                sortList: [[1,1]],
                debug:true
            });
        });
    </script>
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
    $(function () {
        $('.male').on('click',function () {
            var obj = $('#male').val();
            $('#dept').val('');
            $('#code').val('');
            if (obj == null || obj == "") {
                alert("公司名称不能为空");
                return false;
            }
        });
        $('.dept').on('click',function () {
            var obj = $('#dept').val();
            $('#male').val('');
            $('#code').val('');
            if (obj == null || obj == "") {
                alert("部门名称不能为空");
                return false;
            }
        });
        $('.code').on('click',function () {
            var obj = $('#code').val();
            $('#dept').val('');
            $('#male').val('');
            if (obj == null || obj == "") {
                alert("合同编号不能为空");
                return false;
            }
        })
    })
</script>
<script>
    function fun(com) {
        var cp = com;
        var obj2 = $('#dept').val();
        // alert(cp+"====="+obj2);
        window.location.href="${home}/reportform/cleanInventory/sort/company/query.html?name="+cp+"&hid="+obj2;

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
                <label class="font-color" for="male">公司名称：</label>
                <form:input path="name" cssClass="form-control width15" placeholder="公司名称" id="male"
                            value="${param.name}"/>
                <button type="submit" class="btn btn-success male">查询</button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <c:if test="${!empty count}"><label class="font-color" for="male" style="font-size: 24px">签订项目数量:${numbe}个</label>
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label class="font-color" for="male" style="font-size: 24px">参与投标项目数量:${count}个</label></c:if>
            </div>
            <div class="form-group col-xs-12" style="margin-top: 5px">
                <label class="font-color" for="dept">部门名称：</label>
                <form:input path="dept" cssClass="form-control width15" placeholder="部门名称" id="dept"
                            value="${param.dept}${param.hid}"/>
                <button type="submit" class="btn btn-success dept">查询</button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <%--<c:if test="${!empty queryResult.rows[0].sum}"><label class="font-color" for="code" style="font-size: 24px">部门所签项目总数:${temps.sum}</label></c:if>--%>
                <c:if test="${!empty temps[0].sum}"><label class="font-color" for="code" style="font-size: 24px">部门所签项目总数:${temps[0].sum}</label></c:if>
            </div>
            <div class="form-group col-xs-12" style="margin-top: 5px">
                <label class="font-color" for="code">项目编号：</label>
                <form:input path="code" cssClass="form-control width15" placeholder="项目编号" id="code"
                            value="${param.code}"/>
                <button type="submit" class="btn btn-success code">查询</button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <c:if test="${!empty cname}"><label class="font-color" for="code" style="font-size: 24px">公司名:${cname}</label></c:if>
            </div>
        </div>
    </form:form>
    <c:if test="${queryType == 0}">
        <div class="add-table col-xs-12">
            <div class="layui-tab layui-tab-brief">
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <table class="table table-bordered table-hover">
                            <thead align="center">

                            <th width="5%" align="center">序号</th>
                            <th width="60%" align="center">中标公司名称</th>
                            <th width="20%" align="center">签订项目数量</th>
                            <th width="15%" align="center">百分比</th>
                            </thead>
                            <tbody>
                           <%-- <c:forEach items="${hashMap}" var="entry">
                                <tr>
                                    <td>
                                        <a href="${home}/reportform/cleanInventory/sort/company/query.html?code=${entry.key.split("##")[1]}">
                                                ${entry.key.split("##")[1]}
                                        </a></td>
                                    <td>${entry.key.split("##")[0]}</td>
                                </tr>
                            </c:forEach>--%>
                           <c:forEach items="${temps}" var="row" varStatus="status">
                               <c:if test="${row.nam>1}">
                               <tr  align="center">
                                       <td>${status.index+1}</td>
                                    <td>
                                         <a href="javascript:fun('${row.supplierName}');">
                                        ${row.supplierName}
                                        </a>
                                    </td>
                                    <td>
                                        <a href="${home}/reportform/cleanInventory/sort/detail/query.html?name=${row.supplierName}&dept=${param.dept}">${row.nam}</a>
                                    </td>
                                    <td>
                                      ${PercentUtil.percent(row.percent)}
                                    </td>
                                </tr>
                               </c:if>
                           </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>

    </c:if>
    <c:if test="${queryType == 1}">
        <div class="add-table col-xs-12">

            <div class="layui-tab layui-tab-brief">
                <ul class="layui-tab-title">
                    <li class="layui-this">1个同时参与公司的情况</li>
                    <li>2个同时参与公司的情况</li>
                    <li>3个同时参与公司的情况</li>
                </ul>
                <div class="layui-tab-content">
                    <div class="layui-tab-item layui-show">
                        <table id="myTable" class="table table-bordered tablesorter" align="center">
                        <%--<table class="table table-bordered table-hover">--%>
                            <thead align="center">
                            <th align="center">
                                公司名称
                            </th>
                            <th align="center">
                                项目数量
                            </th>

                            <th align="center">
                                百分比
                            </th>
                            </thead>
                            <tbody align="center">
                             <c:forEach var="entry" items="${hashMap}">
                                <%---------------排除同是参与1次的情况,最小都是2次以上的数据---------%>
                                <c:if test='${entry.key != "cid" && entry.key != "count" && entry.key != "numbe" && CountStringUtil.haveChar(entry.key,";") == 0 && entry.value>1}' >

                                    <tr>
                                        <td>
                                                ${entry.key.replace(';','<br/>')}
                                        </td>
                                        <td>
                                            <a href="${home}/reportform/cleanInventory/sort/contract/query.html?name=${empty cname?param.name:cname}&supperName=${entry.key}">${entry.value}</a>
                                        </td>
                                        <td>
                                            &nbsp;

                                                ${PercentUtil.percent(entry.value/count)}
                                        </td>
                                    </tr>
                                </c:if>
                             </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="layui-tab-item">
                        <%--<table class="table table-bordered table-hover">--%>
                            <table id="myTable2" class="table table-bordered tablesorter">
                            <thead align="center">
                            <th>
                                公司名称
                            </th>
                            <th>
                                项目数量
                            </th>

                            <th>
                                百分比
                            </th>
                            </thead>
                            <tbody align="center">
                            <c:forEach var="entry" items="${hashMap}">
                                <c:if test='${entry.key != "cid" && entry.key != "count" && CountStringUtil.haveChar(entry.key,";") == 1 && entry.value>1}' >

                                    <tr>
                                        <td>
                                                ${entry.key.replace(';','<br/>')}
                                        </td>
                                        <td>
                                            <a href="${home}/reportform/cleanInventory/sort/contract/query.html?name=${empty cname?param.name:cname}&supperName=${entry.key}">${entry.value}</a>
                                        </td>
                                        <td>
                                            &nbsp;

                                                ${PercentUtil.percent(entry.value/count)}
                                        </td>

                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <div class="layui-tab-item">
                        <%--<table class="table table-bordered table-hover">--%>
                            <table id="myTable3" class="table table-bordered tablesorter">
                            <thead align="center">
                            <th>
                                公司名称
                            </th>
                            <th>
                                项目数量
                            </th>

                            <th>
                                百分比
                            </th>
                            </thead>
                            <tbody align="center">
                            <c:forEach var="entry" items="${hashMap}">
                                <c:if test='${entry.key != "cid" && entry.key != "count" && CountStringUtil.haveChar(entry.key,";") == 2 && entry.value>1}' >

                                    <tr>
                                        <td>
                                                ${entry.key.replace(';','<br/>')}
                                        </td>
                                        <td>
                                            <a href="${home}/reportform/cleanInventory/sort/contract/query.html?name=${empty cname?param.name:cname}&supperName=${entry.key}">${entry.value}</a>
                                        </td>
                                        <td>
                                            &nbsp;

                                                ${PercentUtil.percent(entry.value/count)}
                                        </td>

                                    </tr>
                                </c:if>
                            </c:forEach>
                            </tbody>
                        </table>

                    </div>
                </div>
            </div>
        </div>
    </c:if>
</div>
<script src="${home}/script/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use('element', function(){
        var $ = layui.jquery
            ,element = layui.element; //Tab的切换功能，切换事件监听等，需要依赖element模块


    });
</script>

</body>
</html>
