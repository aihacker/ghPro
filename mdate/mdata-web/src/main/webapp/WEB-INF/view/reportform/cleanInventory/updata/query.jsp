<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.gpdi.mdata.web.utils.PercentUtil" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>根据日期更新即时清结数据</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <script src="${home}/script/lib/My97DatePicker/WdatePicker.js"></script>

</head>
<style>
    table{
        font-size: 13px!important;
    }
    td {white-space: nowrap;}
</style>
<script type="text/javascript" >
    function check() {
        var startTime =  $('#startTime').val();
        if(startTime ==null || startTime==""){
            alert("开始时间不能为空");
            return false;
        }
        var endTime =  $('#endTime').val();
        if(endTime ==null || endTime==""){
            alert("结束时间不能为空");
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
                    <li><a href="">更新数据</a></li>
                    <li><a href=""></a></li>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    <!--modelAttribute="queryData":通过绑定实体queryData类进行初始化-->
    <form:form modelAttribute="userData" action="operate.json?action=updata" method="post" id="baseInfo" enctype="multipart/form-data" >
        <div class="form-inline clearfix">
            <div class="form-group col-xs-12">
                <label  class="font-color" for="startTime">开始日期：</label>
                    <%--<input  type="text" size="20" id="startTime" name="startTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" placeholder="开始日期"/>&nbsp;&nbsp;--%>
                <input  type="text"   id="startTime" name="startTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd'})" placeholder="开始日期"/>&nbsp;&nbsp;
                <label  class="font-color" for="endTime">结束日期：</label>
                    <%--<input  type="text" size="20" id="endTime" name="endTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM-dd'})" placeholder="结束日期"/>&nbsp;&nbsp;--%>
                <input  type="text"  id="endTime" name="endTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd'})" placeholder="结束日期"/>&nbsp;&nbsp;
                <button type="submit" class="btn btn-success" onclick="check();">更新数据</button>
            </div>
        </div>
    </form:form>


</div>
</body>
</html>
