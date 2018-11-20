<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>车辆出入停车场信息</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp?query"/>
    <script src="${home}/script/lib/My97DatePicker/WdatePicker.js"></script>
    <style>
        table  {font-size: 13px !important;
            white-space:nowrap;}
        table th {
            text-align: center;
        }
        .form-group{
            margin-top: 35px;
        }
        .clearfix {
            text-align: center;
        }
        .a,.b,.c,.d,.e{
            margin-left: -64px;
            margin-top: 10px;
            padding: 13px;
        }
        .carnumber,.startmileage,.endmileage,.carnature,.mileage{
            margin-right: -44px;
            width: 193px;
        }
        .e{
            margin-left: -78px;
        }
        .centre{
            margin-top: 5px;
        }
        .btn-success{
            margin-left: 550px;
        }
        .ret {
            border:none;
            background-color: #f5f5f5;
            color: #428bca;
            float: left;
            line-height: 30px;
        }
        .ret:hover, .ret:focus {
            color: #2a6496;
            text-decoration: underline;
        }
    </style>
    <script>
        function check() {

            var startTime = $("#startTime").val();
            var carNumber = $("#carnumber").val();
            var startMileage = $("#startmileage").val();
            var endMileage = $("#endmileage").val();
            var carNature = $("#carnature").val();
            var mileageDou = $("#mileage").val();
            var arr = [];
            arr.push(startTime,carNumber,startMileage,endMileage,carNature,mileageDou);
            var res = arr.toString();
            var url =  "query.html?params="+res;
            window.location.href = url;
        }
        function imgChange(obj) {

            var file =document.getElementById("file");
            var imgUrl =window.URL.createObjectURL(file.files[0]);
            var img =document.getElementById('showImg');
            img.setAttribute('src',imgUrl);
        };
    </script>


</head>

<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row" align="center">
                <ol class="breadcrumb">
                    <li><b><span style="font-size: 22px;color: #666600;">里程录入界面</span></b></li>
                    <button id="ret" class="ret" onclick="history.back()">返回</button>
                </ol>
            </div>
        </div>
    </div>
    <div class="clearfix">

        <%--<label>车牌号:</label><input type="text" id="carnumber"><button onclick="chect()">查询</button>--%>
        <div class="form-inline clearfix">
            <div class="form-group col-xs-12">
                <label  class="font-color" for="startTime">开始日期：</label>
                <input  type="text"  value="${param.startTime}" id="startTime" name="startTime" class="Wdate" onFocus="WdatePicker({lang:'zh-cn',maxDate:'#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd'})" placeholder="开始日期"/>&nbsp;&nbsp;
            </div>
        </div>
        <div class="centre">
        <label class="d">车辆性质:</label>
            <select id="carnature" class="carnature" style="height: 26px;">
                <option value ="请选择">请选择</option>
                <option value ="生产用车">生产用车</option>
                <option value="综合用车">综合用车</option>
            </select><br>
        <label class="a">汽车牌号:</label><input type="text" id="carnumber" class="carnumber"><br>
        <label class="b">初始里程:</label><input type="text" id="startmileage" class="startmileage"><br>
        <label class="c">最终里程:</label><input type="text" id="endmileage" class="endmileage"><br>
        <label class="e">里程耗油量:</label><input type="text" id="mileage" class="mileage">
        </div>
    </div>
    <button type="submit" class="btn btn-success" onclick="check();">保存</button>
    <input type="file" id="file"  name="file" onchange="imgChange(this);">
    <img id="showImg">
</div>
</body>
</html>
