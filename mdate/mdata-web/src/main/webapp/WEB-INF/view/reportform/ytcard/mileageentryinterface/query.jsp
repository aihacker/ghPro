<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="app://pub.form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>车辆里程信息录入</title>
    <jsp:include page="/common/styles.jsp"/>
    <jsp:include page="/common/scripts.jsp?query"/>
    <script src="${home}/script/lib/My97DatePicker/WdatePicker.js"></script>
    <link rel="stylesheet" href="${home}/script/layui/css/layui.css">
    <style>
        body{padding: 50px 50px;}
        .layui-inline{margin-right: 15px;
        }
        .spanfont{
            font-size: 22px;
            font-family: "宋体";
            padding: 6px;
        }
        .end{
            margin-top: 15px;
        }
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
            margin-left: 20px;
        }
        .layui-input-inline{
            margin-left: 20px;
        }
        .layui-anim-upbit{
            margin-left: 20px;
        }
        .imagesee{
            padding: 17px 10px;
        }
        .carnumber{
            margin-left: 20px;
            height: 40px;
            width: 180px;
            border: solid #e6e6e6 1px;
        }
    </style>
</head>
<body>
<div class="mainon">
    <%--<div class="layui-form-item">
        <label class="layui-form-label">车牌号:</label>
        <div class="layui-input-block">
            <input type="text" name="title" lay-verify="required|title" required placeholder="请选择车牌号" autocomplete="off" class="layui-input carnumber" id="carnumber">
        </div>
    </div>--%>
        <%--<div class="layui-form layui-form-pane1">--%>
            <div class="layui-form-item">
                <label class="layui-form-label">车牌号:</label>
                <div class="layui-input-inline">
                    <select name="interest-search" lay-filter="interest-search" lay-search id="carnumber" onchange="conType(this)" class="carnumber">
                        <%--<select name="provid" id="holidaytype" lay-filter="provid" onchange="conType(this)">--%>
                        <option value="1">请选择</option>
                        <c:forEach items="${allCarNumber}" var="acnList" varStatus="status">
                            <option value=${acnList.totalRepair} <c:if test="${param.params == acnList.totalRepair}" > selected="selected"</c:if>>${acnList.totalRepair}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        <%--</div>--%>

    <div class="layui-form-item">
        <label class="layui-form-label">车辆性质:</label>
        <div class="layui-input-block">
            <input type="text" name="title" lay-verify="required|title" autocomplete="off" class="layui-input carnature" id="carnature" value="${carNatureStr}" readonly="readonly"><%--carNatureStr--%>
        </div>
    </div>

    <div class="layui-form layui-form-pane1">
        <div class="layui-form-item">
            <label class="layui-form-label">月份:</label>
            <div class="layui-input-inline">
                <select name="interest-search" lay-filter="interest-search" lay-search id="monthval">
                    <option value=""></option>
                    <option value="01">1月</option>
                    <option value="02">2月</option>
                    <option value="03">3月</option>
                    <option value="04">4月</option>
                    <option value="05">5月</option>
                    <option value="06">6月</option>
                    <option value="07">7月</option>
                    <option value="08">8月</option>
                    <option value="09">9月</option>
                    <option value="10">10月</option>
                    <option value="11">11月</option>
                    <option value="12">12月</option>
                </select>
            </div>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">已行驶里程:</label>
        <div class="layui-input-block">
            <input type="text" name="title" lay-verify="required|title" required placeholder="请输入月行驶里程" autocomplete="off" class="layui-input monthlymileage" id="monthlymileage">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">油耗(每百公里油耗):</label>
        <div class="layui-input-block">
            <input type="text" name="title" lay-verify="required|title" required placeholder="请输入油耗" autocomplete="off" class="layui-input oilconsumption" id="oilconsumption" value="${carOil}" readonly="readonly">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label">车桥费用:</label>
        <div class="layui-input-block">
            <input type="text" name="title" lay-verify="required|title" required placeholder="请输入车桥现金费用" autocomplete="off" class="layui-input costaxle" id="costaxle" class="costaxle">
        </div>
    </div>

        <div class="layui-form-item">
            <label class="layui-form-label">月定行驶里程:</label>
            <div class="layui-input-block">
                <input type="text" name="title" lay-verify="required|title" autocomplete="off" class="layui-input monthlymileage" id="monthlystipulatemileage" class="monthlystipulatemileage" readonly="readonly" value="${MonthOnMileageStr}">
            </div>
        </div>

    <div class="layui-form-item">
        <div class="layui-input-block" style="margin-left: 130px">
            <button class="layui-btn" lay-submit lay-filter="*" onclick="sava()">保存</button>
            <button type="reset" class="layui-btn layui-btn-primary" onclick="reset()">重置</button>
        </div>
    </div>
</div>
<div class="imagesee">
<input type="file" id="file"  name="file" onchange="imgChange(this);">
<img id="showImg">
</div>
<div  class="mainup">
    <span class="spanfont">历史记录</span>
    <div class="add-table col-xs-12" style="margin-top: 15px;">

        <table class="table table-bordered table-hover">
            <thead align="center">
            <tr align="center" width="100%" style="height:20px;background-color: #ffffff;">
                <th>序号</th>
                <th>车牌号</th>
                <th>车辆性质</th>
                <th>行驶时间</th>
                <th>行驶里程</th>
                <th>油耗</th>
                <th>车桥现金费用</th>
                <th>距上个月里程差</th>
            </tr>

            </thead>

            <tbody>

            <c:forEach items="${queryResult.rows}" var="row" varStatus="status" >
                <tr class="${row.locked == 1? 'locked': ''}" align="center">
                    <td>${row.id}</td>
                    <td>${row.license_plate_number}</td>
                    <td>${row.car_nature}</td>
                    <td>${row.mileage_driven_time}</td><%--aaaa--%>
                    <%--<td><fmt:parseDate value="${fn:substring(row.mileage_driven_time,0,16)}" pattern="yyyy-MM-dd HH:mm" var="test"/>   </td>--%>
                    <td>${row.month_driving_mileage}</td>
                    <td>${row.oil_consumption}</td>
                    <td>${row.bridge_cash_charge}</td>
                    <td>${row.month_mileage_difference}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <jsp:include page="/common/pager.jsp"/>

    </div>
</div>

<script src="${home}/script/layui/layui.js" charset="utf-8"></script>
<script>
    <%--$('#costaxle').val('${carNatureStr}');--%>
    layui.use('form', function(){
        var form = layui.form;
    });
    function sava(){
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.alert('确定保存吗', {
                skin: 'layui-layer-molv' //样式类名  自定义样式
                ,closeBtn: 1    // 是否显示关闭按钮
                ,anim: 1 //动画类型
                ,btn: ['确定','取消'] //按钮
                ,icon: 6    // icon
                ,yes:function(){
                    debugger;
                    var carNumber = $("#carnumber").val();//车牌号
                    var carNature = $("#carnature").val();//车辆性质
                    var monthVal = $("#monthval").val();//月份
                    var monthLyMileage = $("#monthlymileage").val();//本地里程
                    var oilConsumption = $("#oilconsumption").val();//油耗
                    var costAxle = $("#costaxle").val();//车桥现金费用
                    if(carNumber==null || carNumber==""){
                        layer.alert('请输入车牌号', {icon: 3});
                        return false;
                    }
                    if(monthVal==null || monthVal=="" ||monthVal=="1"){
                        layer.alert('请选择月份', {icon: 3});
                        return false;
                    }
                    if(monthLyMileage==null || monthLyMileage==""){
                        layer.alert('请输入本月里程', {icon: 3});
                        return false;
                    }
                    if(oilConsumption==null || oilConsumption==""){
                        layer.alert('请输入油耗', {icon: 3});
                        return false;
                    }
                    if(costAxle==null || costAxle==""){
                        layer.alert('请输入车桥现金费用', {icon: 3});
                        return false;
                    }
                    var arr = [];
                    arr.push(carNumber,carNature,monthVal,monthLyMileage,oilConsumption,costAxle);
                    var res = arr.toString();
                    var url =  "query.html?arraystr="+res;
                    window.location.href = url;
                }
                ,btn2:function(){
                    layer.msg('好的,暂时不给您保存');
                }
            });

        });
    }

    function conType(obj) {
        var carNumberStr = obj.value;
        var url = "query.html?params=" + carNumberStr;
        window.location.href = url;
    }
    function imgChange(obj) {

        var file =document.getElementById("file");
        var imgUrl =window.URL.createObjectURL(file.files[0]);
        var img =document.getElementById('showImg');
        img.setAttribute('src',imgUrl);
    };
    function reset() {
        $("#carnumber").val("");
        $("#carnature").val("");
        $("#monthval").val("");
        $("#monthlymileage").val("");
        $("#oilconsumption").val("");
        $("#costaxle").val("");
        $("#monthlystipulatemileage").val("");
    }


</script>
</body>
</html>
