<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>采购报表分析</title>
    <link type="text/css" rel="stylesheet" href="${home}/css/reportform/style.css">
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp?query"/>
    <script src="${home}/script/lib/My97DatePicker/WdatePicker.js"></script>
    <style>
        table {font-size: 13px !important;
            white-space:nowrap;
        }
    </style>

    <script type="text/javascript" >
        $(function(){

            $("input[name=search_type]").on("change", function () {
                load_more();
            });

            $("select[name=constractType]").on("change", function (){
                load_more();
            });

            $("select[name=constractDept]").on("change", function (){
                load_more();
            })

        });

        function load_more(id){
            var screenvalue = $("#screen").val();
            var startTime =  $('#startTime').val();
            var endTime =  $('#endTime').val();
            var searchType = $("input[name=search_type]:checked").val();
            var name = $("select[name=constractType]").val();
            var dept = $("select[name=constractDept]").val();
            if(name=='请选择'){
                name=''
            }
            if(dept=='请选择'){
                dept=''
            }
            var isExport=0;
            if (id == "check") {
                isExport=0;
            }else if(id=="export"){
                isExport=1;
            }
            var url = "query.html?code=" + screenvalue+"&startTime="+startTime+"&endTime="+endTime+"&searchType="+searchType+"&name="+name+"&dept="+dept+"&isExport="+isExport;
            window.location.href = url;

        }
    </script>

</head>
<%

%>
<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row" align="center">
                <ol class="breadcrumb">
                    <li><b><span style="font-size: 22px;color: #666600;">搜索前
                       <select name="screen" id="screen" onchange="check()">
                                   <option value="5" ${queryData.code==5?'selected="selected"':''}>5</option>
                                   <option value="10" ${(queryData.code==10||queryData.code==null)?'selected="selected"':''}>10</option>
                                   <option value="15" ${queryData.code==15?'selected="selected"':''}>15</option>
                                   <option value="20" ${queryData.code==20?'selected="selected"':''}>20</option>
                                   <option value="30" ${queryData.code==30?'selected="selected"':''}>30</option>
                                   <option value="50" ${queryData.code==50?'selected="selected"':''}>50</option>

                        </select>个供应商</span></b>

                    </li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <%--<form:form modelAttribute="queryData"  method="post" id="baseInfo" enctype="multipart/form-data" >--%>

    <div class="form-inline clearfix">
        <div class="form-group col-xs-12">
            <label class="radio-inline">
                <input type="radio" name="search_type" value="1" ${queryData.searchType==1?'checked':''}> 签订数量最多的供应商
            </label>
            <label class="radio-inline">
                <input type="radio" name="search_type" value="2" ${queryData.searchType==2?'checked':''}> 签订金额最多的供应商
            </label>
            <label class="radio-inline">
                <input type="radio" name="search_type" value="3" ${queryData.searchType==3?'checked':''}> 签订数量及金额均最多的供应商
            </label>
        </div>
        <div class="form-group col-xs-12">
            <p>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;请选择合同类型:&nbsp;&nbsp;&nbsp;
                    <select name="constractType" id="constractType" style="width: 200px;height: 25px">
                        <option ${queryData.name==null?'selected="selected"':''}>请选择</option>
                        <c:forEach items="${types}" var="ctype" varStatus="status">
                            <option value=${ctype} ${queryData.name==ctype?'selected="selected"':''}>${ctype}</option>
                        </c:forEach>
                    </select>
                </span>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;请选择经办部门:&nbsp;&nbsp;&nbsp;
                    <select name="constractDept" id="constractDept"  style="width: 200px;height: 25px">
                        <option ${queryData.dept==null?'selected="selected"':''}>请选择</option>
                        <c:forEach items="${depts}" var="d" varStatus="status">
                            <option value=${d} ${queryData.dept==d?'selected="selected"':''}>${d}</option>
                        </c:forEach>
                    </select>
                </span>
            </p>
        </div>
        <div class="form-group col-xs-12">
            <label  class="font-color" for="startTime">开始日期：</label>
            <input  type="text"  value="${param.startTime}" id="startTime" name="startTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd'})" placeholder="开始日期"/>&nbsp;&nbsp;
            <label  class="font-color"  for="endTime">结束日期：</label>
            <input  type="text"   value="${param.endTime}" id="endTime" name="endTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd'})" placeholder="结束日期"/>&nbsp;&nbsp;
            <button type="submit" id="check" class="btn btn-success" onclick="load_more(this.id);">查询</button>
            <button type="submit" id="export" class="btn btn-success" onclick="load_more(this.id);">导出</button>
        </div>
    </div>

    <div class="add-table col-xs-12">

            <table class="table table-bordered table-hover">
            <thead align="center">
            <tr align="center" width="100%" style="height:20px;background-color: #ffffff;">
                <td><b>序号</b></td>
                <td><b>供应商名称</b></td>
                <td><b>签订合同数量</b></td>
                <td><b>合同总金额&nbsp&nbsp&nbsp&nbsp单位:元</b></td>
            </tr>

            </thead>

           <tbody>

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status">
                <tr class="${row.locked == 1? 'locked': ''}" align="center">
                    <td>${status.index+1}</td>
                    <td width="50%">${row.supplier_name}</td>
                    <td class="op">
                       <a href="${home}/reportform/form/allcontractnumber/query.html?supperName=${row.supplier_name}">${row.cc}</a>
                    </td>
                    <td>${row.dd}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


        <!-- 模态框（Modal） -->
        <!--tabindex属性:将tabIndex赋值为-1，则在使用[Tab]键时，此元素被忽略。注意：如果使用-1值时，onfocus与onblur事件仍被启动。   tabIndex的值可为0至32767之间的任意数字-->
        <!--role属性:role的发挥的作用是供有障碍的人士使用，这个元素所扮演的角色，主要是供残疾人使用。使用role可以增强文本的可读性和语义化。，-->
        <!--aria-label属性:正常情况下，form表单的input组件都有对应的label.当input组件获取到焦点时，屏幕阅读器会读出相应的label里的文本。-->
        <!--aria-labelledby属性:当想要的标签文本已在其他元素中存在时，可以使用aria-labelledby，并将其值为所有读取的元素的id。。-->
        <!--aria-hidden="true":隐藏要弹出的内容,这个是用于屏幕阅读器的，帮助残障人士更好的访问网站。-->
      <%--  <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
             aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content " style="width:800px;margin: 100px -100px ">
                    <div class="modal-header" style="height: 25px">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    </div>
                    <div  style="verstical-align: middle; text-align: center;">
                        <div class="table-responsive">
                                &lt;%&ndash;<table id="myTable" class="tablesorter">&ndash;%&gt;
                                <table id="myTable" class="table table-bordered tablesorter">
                                    <thead>
                            <tr width="100%" bgcolor="#E6E6FA" style="height:30px;">

                                    <th>序号</th>
                                    <th>供应商</th>
                                    <th>合同编号</th>
                                    <th>合同名称</th>
                                    <th>合同类型</th>
                                    <th>合同金额</th>
                                    <th>经办部门</th>
                                    <th>采购方式</th>
                                    <th>定稿时间</th>
                                </tr>
                            </thead>
                            <tbody id="message" ></tbody>
                        </table>
                        </div>
                    </div>
                    <jsp:include page="/common/pager.jsp"/>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal" >返回</button>
                    </div>
                </div>
                <!-- /.modal-content -->
            </div>
            <!-- /.modal -->
        </div>
        <script type="text/javascript" src="${home}/script/reportform/report.js"></script>--%>
        <jsp:include page="/common/pager.jsp"/>

    </div>

</div>
<%--
<script type="text/javascript" src="${home}/script/jquery.tablesorter.js"></script>
--%>

</body>
</html>
