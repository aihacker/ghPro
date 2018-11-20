<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>车辆规则信息界面</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp?query"/>
    <link rel="stylesheet" href="${home}/script/layui/css/layui.css">
    <style>
        table  {font-size: 13px !important;
            white-space:nowrap;}
        table th {
            text-align: center;
        }
        .layui-form-label{
            padding: 10px 3px;
            width: 90px;
        }
        .layui-input{
            width: 95%;
        }
        .upform{
            padding: 75px 0px;
        }
    </style>


</head>

<body>
<div class="main">
    <div class="">
        <div class="three-nav col-xs-12">
            <div class="row" align="center">
                <ol class="breadcrumb">
                    <li><b><span style="font-size: 22px;color: #666600;">车辆规则录入界面</span></b></li>
                    <button id="ret" class="ret" onclick="history.back()">返回</button>
                </ol>
            </div>
        </div>
    </div>
</div>
<div class="upform">
<div class="layui-form-item">
    <label class="layui-form-label">车牌号:</label>
    <div class="layui-input-block">
        <input type="text" name="title" lay-verify="required|title" required placeholder="请输入车牌号" autocomplete="off" class="layui-input carnumber" id="carnumber">
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">投产时间:</label>
    <div class="layui-input-block">
        <div class="layui-inline">
            <input type="text" class="layui-input productiontime" id="productiontime" required placeholder="请选择投产时间">
        </div>
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">车型:</label>
    <div class="layui-input-block">
        <input type="text" name="title" lay-verify="required|title" required placeholder="请输入车型号" autocomplete="off" class="layui-input cartype" id="cartype">
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">汽车排量:</label>
    <div class="layui-input-block">
        <input type="text" name="title" lay-verify="required|title" required placeholder="请输入车耗" autocomplete="off" class="layui-input carconsumption" id="carconsumption">
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">月规定里程:</label>
    <div class="layui-input-block">
        <input type="text" name="title" lay-verify="required|title" required placeholder="请输入月行驶里程" autocomplete="off" class="layui-input monthlymileage" id="monthlymileage">
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">油耗(升/百公里):</label>
    <div class="layui-input-block">
        <input type="text" name="title" lay-verify="required|title" required placeholder="请输入油耗" autocomplete="off" class="layui-input oilconsumption" id="oilconsumption">
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">修车费用:</label>
    <div class="layui-input-block">
        <input type="text" name="title" lay-verify="required|title" required placeholder="请输入修车费用" autocomplete="off" class="layui-input repairs" id="repairs">
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">修车次数:</label>
    <div class="layui-input-block">
        <input type="text" name="title" lay-verify="required|title" required placeholder="请输入修车次数" autocomplete="off" class="layui-input repairstimes" id="repairstimes">
    </div>
</div>

<div class="layui-form-item">
    <div class="layui-input-block">
        <button class="layui-btn" lay-submit lay-filter="*" onclick="check()">保存</button>
        <button type="reset" class="layui-btn layui-btn-primary" onclick="reset()">重置</button>
    </div>
</div>
</div>

<script src="${home}/script/layui/layui.js" charset="utf-8"></script>
<script>
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //单控件
        laydate.render({
            elem: '#productiontime'
            ,isInitValue: false
            ,format: 'yyyy-MM-dd'
            ,ready: function(date){
                console.log(date);
            }
            ,done: function(value, date, endDate){
                console.log(value, date, endDate);
            }
        });

    });
    /*function check() {
        var carNumber = $("#carnumber").val();//车牌号
        var productionTime = $("#productiontime").val();//投产时间
        var carType = $("#cartype").val();//车型
        var carConsumption = $("#carconsumption").val();//车耗
        var monthlyMileage = $("#monthlymileage").val();//月不能超过行驶的里程
        var oilConsumption = $("#oilconsumption").val();//油耗
        var repairs = $("#repairs").val();//修车费用
        var repairsTimes = $("#repairstimes").val();//修车次数
        if(carNumber==null || carNumber==""){
            alert("请输入车牌号");
            return false;
        }
        if(productionTime==null || productionTime==""){
            alert("请选择投产时间");
            return false;
        }
        if(carType==null || carType==""){
            alert("请输入车型");
            return false;
        }
        if(carConsumption==null || carConsumption==""){
            alert("请输入车耗");
            return false;
        }
        if(monthlyMileage==null || monthlyMileage==""){
            alert("请输入月行驶里程");
            return false;
        }
        if(oilConsumption==null || oilConsumption==""){
            alert("请输入油耗");
            return false;
        }
        if(repairs==null || repairs==""){
            alert("请输入修车费用");
            return false;
        }
        if(repairsTimes==null || repairsTimes==""){
            alert("请输入修车次数");
            return false;
        }
        var arr = [];
        arr.push(carNumber,productionTime,carType,carConsumption,monthlyMileage,oilConsumption,repairs,repairsTimes);
        var res = arr.toString();
        var url =  "query.html?params="+res;
        window.location.href = url;
    }*/
        function check(){
            layui.use('layer', function(){
                var layer = layui.layer;

                /*layer.open({
                      title: '信息'
                      ,content: '保存成功'
                      ,icon: 6
                });*/
                layer.alert('确定保存吗', {
                    skin: 'layui-layer-molv' //样式类名  自定义样式
                    ,closeBtn: 1    // 是否显示关闭按钮
                    ,anim: 1 //动画类型
                    ,btn: ['确定','取消'] //按钮
                    ,icon: 6    // icon
                    ,yes:function(){
                        var carNumber = $("#carnumber").val();//车牌号
                        var productionTime = $("#productiontime").val();//投产时间
                        var carType = $("#cartype").val();//车型
                        var carConsumption = $("#carconsumption").val();//车耗
                        var monthlyMileage = $("#monthlymileage").val();//月不能超过行驶的里程
                        var oilConsumption = $("#oilconsumption").val();//油耗
                        var repairs = $("#repairs").val();//修车费用
                        var repairsTimes = $("#repairstimes").val();//修车次数
                        if(carNumber==null || carNumber==""){
                            layer.alert('请输入车牌号', {icon: 3});
                            return false;
                        }
                        if(productionTime==null || productionTime==""){
                            layer.alert('请选择投产时间', {icon: 3});
                            return false;
                        }
                        if(carType==null || carType==""){
                            layer.alert('请输入车型', {icon: 3});
                            return false;
                        }
                        if(carConsumption==null || carConsumption==""){
                            layer.alert('请输入车耗', {icon: 3});
                            return false;
                        }
                        if(monthlyMileage==null || monthlyMileage==""){
                            layer.alert('请输入月行驶里程', {icon: 3});
                            return false;
                        }
                        if(oilConsumption==null || oilConsumption==""){
                            layer.alert('请输入油耗', {icon: 3});
                            return false;
                        }
                        if(repairs==null || repairs==""){
                            layer.alert('请输入修车费用', {icon: 3});
                            return false;
                        }
                        if(repairsTimes==null || repairsTimes==""){
                            layer.alert('请输入修车次数', {icon: 3});
                            return false;
                        }
                        var arr = [];
                        arr.push(carNumber,productionTime,carType,carConsumption,monthlyMileage,oilConsumption,repairs,repairsTimes);
                        var res = arr.toString();
                        var url =  "query.html?params="+res;
                        window.location.href = url;
                    }
                    ,btn2:function(){
                        layer.msg('好的,暂时不给您保存');
                    }
                });

            });

        }
    function reset() {
        $("#carnumber").val("");//车牌号
        $("#productiontime").val("");//投产时间
        $("#cartype").val("");//车型
        $("#carconsumption").val("");//车耗
        $("#monthlymileage").val("");//月不能超过行驶的里程
        $("#oilconsumption").val("");//油耗
        $("#repairs").val("");//修车费用
        $("#repairstimes").val("");//修车次数
    }
</script>
</body>
</html>
