<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>导入采购合同台账</title>

    <link rel="stylesheet" href="${home}/script/layui/css/layui.css" />
    <script type="text/javascript" src="${home}/script/layui/layui.js"></script>
    <script src="${home}/script/jquery-1.11.1.js"></script>
    <script src="${home}/script/lib/My97DatePicker/WdatePicker.js"></script>


    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp"/>
    <script type="text/javascript">
        function check(){
debugger;
            var str = $("#startTime").val();
            if (str==null){
                alert("请输入开始时间");
            }
            var end = $("#endTime").val();
            if (end==null){
                alert("请输入结束时间");
            }
            var type = $("#holidaytype").val();
            if (type==null){
                alert("请输入节假日类型");
            }
            var arr = [];
            arr.push(str,end,type);
            var res = arr.toString();
            var uri =  "query.html?params="+res;
            window.location.href = uri;

        }

    </script>
</head>

<body>
<h2 style="margin-left: 388px;margin-top: 40px;">节假日时间录入界面</h2>
<table style="width:800px;margin:50px auto;">
    <%--<tr>
        <th>录入界面</th>
    </tr>--%>
    <tr>
<%--<div style="width:800px;margin:50px auto;">--%>

        <div class="form-inline clearfix">
            <div class="form-group col-xs-12">
                <td>
                <label  class="font-color" for="startTime">开始日期：</label>
                <input  type="text"  value="${param.startTime}" id="startTime" name="startTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd'})" placeholder="开始日期"/>&nbsp;&nbsp;
                </td>
                <td>
                    <label  class="font-color"  for="endTime">结束日期：</label>
                <input  type="text"   value="${param.endTime}" id="endTime" name="endTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd'})" placeholder="结束日期"/>&nbsp;&nbsp;
                </td>
            </div>
        </div>
    </tr>
    <tr>

        <%--<div class="layui-form-item">--%>
            <td>
            <label>节假日类型：</label>
            <%--</td>
            <td>--%>
            <%--<div class="layui-input-inline">--%>
                <select name="provid" id="holidaytype" lay-filter="provid" style="margin-top: 20px; width: 200px">
                    <option value="">请选择</option>
                    <option value="元旦">元旦</option>
                    <option value="春节">春节</option>
                    <option value="清明节">清明节</option>
                    <option value="劳动节">劳动节</option>
                    <option value="端午节">端午节</option>
                    <option value="中秋节">中秋节</option>
                    <option value="国庆节">国庆节</option>
                </select>
            <%--</div>--%>
            </td>
        <%--</div>--%>
    </tr>
    <%--<tr>
            <div class="layui-input-block">
                <button class="layui-btn" lay-submit lay-filter="*" onclick="check()">立即提交</button>
            </div>
    </tr>--%>
</table>
<div class="layui-input-block">
    <button class="layui-btn" lay-submit lay-filter="*" onclick="check()" style="margin-left: 345px;">保存</button>
</div>
</body>
</html>
