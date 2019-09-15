<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/1
  Time: 18:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>"2018健康生活"员工体检活动</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.poppicker.css" rel="stylesheet"/>
    <style>
        #fuli{
            display: none;
        }
        #plus{
            display:none
        }
    </style>

</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
<%--&lt;%&ndash;<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>&ndash;%&gt;--%>

<%--<h1 class="mui-title">基本信息填写</h1>--%>
<%--<a id="nextBtn" class="mui-btn mui-btn-link mui-pull-right">下一步</a>--%>
<%--</header>--%>

<div class="ui-fixed-bottom">
    <button id="nextBtn" type="button" class="mui-btn mui-btn-primary">预约</button>
</div>

<header class="mui-bar mui-bar-nav">
    <a class="mui-icon mui-icon-bars mui-pull-left mui-plus-visible"></a>
    <a id="info" class="mui-icon mui-icon-info-filled mui-pull-right" style="color: #999;"></a>
    <h1 class="mui-title">选择套餐</h1>
</header>

<div class="mui-content">
    <form id="applyForm">

        <div class="mui-input-group ui-margin-top-10">
            <div class="mui-input-row">
                <label>姓&nbsp;&nbsp;&nbsp;&nbsp;名</label>
                <span class="input-row-span ui-text-info" id="name">${wxgh_user.name}</span>
            </div>
            <div class="mui-input-row">
                <label>性&nbsp;&nbsp;&nbsp;&nbsp;别</label>
                <span class="input-row-span ui-text-info" id="gender">${param.gender eq 1?"男":"女"}</span>
            </div>
            <%--<div class="mui-input-row">--%>
            <%--<label>部&nbsp;&nbsp;&nbsp;&nbsp;门</label>--%>
            <%--<span class="input-row-span ui-text-info">${deptname}</span>--%>
            <%--</div>--%>
            <div class="mui-input-row">
                <label>联系电话</label>
                <span class="input-row-span ui-text-info" id="mobile">${param.mobile}</span>
            </div>
            <div class="mui-input-row">
                <label>部&nbsp;&nbsp;&nbsp;&nbsp;门</label>
                <span class="input-row-span ui-text-info" id="dept">${param.dept}</span>
            </div>
            <div class="mui-input-row">
                <label>身份证号</label>
                <span class="input-row-span ui-text-info" id="idcard">${param.idcard}</span>
            </div>
            <div class="mui-input-row">
                <label>年龄</label>
                <span class="input-row-span ui-text-info" id="age">${param.age}</span>
            </div>
            <div class="mui-input-row">
                <label>婚姻状况</label>
                <span class="input-row-span ui-text-info" id="marriage">${param.marrige eq 1?"未婚":"已婚"}</span>
            </div>
            <div class="mui-input-row">
                <label>套餐额度</label>
                <span class="input-row-span ui-text-info" id="quote"></span>
            </div>
            <%--<div class="mui-input-row">--%>
            <%--<label>年&nbsp;&nbsp;&nbsp;&nbsp;龄</label>--%>
            <%--<span class="input-row-span ui-text-info">${wxgh_user.gender eq 1?"男":"女"}</span>--%>
            <%--</div>--%>
        </div>
        <div class="mui-input-group ui-margin-top-10">
            <div class="mui-input-row" style="height: 80px">
                <label>选择套餐</label>
                    <input name="category" type="hidden" id="cateInput"/>
                    <span id="cateSpan" class="input-row-span ui-text-info">选择套餐</span>
            </div>
            <div class="mui-input-row" id="fuli">
                <label>福利金额</label>
                <input name="extramoney" value="" type="number" class="mui-input-clear mui-input-numbox"
                       placeholder="请输入弹性福利金额（可不填）" >
            </div>
            <div class="mui-input-row" id="plus">
                <label>加检套餐</label>
                <select name="plus" id="pluss">
                    <option value="0">不选择</option>
                    <option value="1">肝脏疾病套餐(800)</option>
                    <option value="2">心脑疾病套餐(800)</option>
                </select>
            </div>
            <div class="mui-input-row">
                <label>额度提示</label>
                <span class="input-row-span ui-text-info" id="money"></span>
            </div>
        </div>

    </form>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.poppicker.js"></script>

<script src="${home}/script/consts.js"></script>

<script type="text/javascript">
    mui.init()

    var homePath = '${home}';

    var age = '${param.age}'
    var gender = '${param.gender}'
    var hospital = null

    function count(age,gender){
        if(gender == 1 && age<40){
            return 500
        }else if(gender == 1 && age >= 40){
            return 600
        }else if(gender !=1 && age <40){
            return 620
        }else {
            return 720
        }
    }

    $("#quote").html(count(age,gender))


    //    function f(obj){
    //        var v = obj.value;
    //        var patrn=/^([1-9]\d*|0)(\.\d*[1-9])?$/;
    //        if (!patrn.exec(v)) {
    //            mui.toast("请输入正确金额哦~");
    //        }
    //    }

    var e_nextBtn = wxgh.getElement("nextBtn");
    var cate = {
        init: function () {
            var self = wxgh.getElement("cateSpan");
            self.addEventListener("tap", cate.click);

            cate.self = self;

            cate.picker = new mui.PopPicker({
                layer: 2
            });

            cate.picker.setData(cate.hospital)
            cate.input = wxgh.getElement("cateInput");
        },
        hospital:[
            {text:"佛山市禅城中心医院",value:1,children:[
                {value:500,text:"男（500）"},
                {value:600,text:"男（600）"},
                {value:806,text:"男（806）免辐射"},
                {value:1222,text:"男（1222）"},
                {value:1790,text:"男（1790）"},
                {value:2790,text:"男（2790）"},
                {value:620,text:"未婚、已婚女（620）"},
                {value:720,text:"已婚女（720）"},
                {value:817,text:"女（817）免辐射"},
                {value:1212,text:"未婚女（1212）"},
                {value:1210,text:"已婚女（1210）"},
                {value:1846,text:"女（1846）"},
                {value:2954,text:"女（2954）"}
            ]},
            {text:"佛山市妇幼保健院",value:2,children:[
                {value:500,text:"男（500）"},
                {value:600,text:"男（600）"},
                {value:620,text:"女 （620）"},
                {value:720,text:"女（720）"},
            ]},
            {text:"佛山市美年大健康鑫和综合门诊部",value:3,children:[
                {value:620,text:"（女未婚620）套餐A"},
                {value:620,text:"（女已婚620）套餐A"},
                {value:720,text:"（女未婚720）套餐B"},
                {value:720,text:"（女已婚720）套餐B"},
                {value:1398,text:"（女未婚1398）备孕套餐"},
                {value:1398,text:"（女已婚1398）备孕套餐"},
                {value:1250,text:"（女未婚1250）套餐C"},
                {value:1350,text:"（女已婚1350）套餐C"},
                {value:2200,text:"（女2200）套餐D"},
                {value:500,text:"（男500）套餐A"},
                {value:600,text:"（男600）套餐B"},
                {value:1200,text:"（男1200）套餐C"},
                {value:1398,text:"（男1398）备孕套餐"},
                {value:1800,text:"（男1800）套餐D"},
            ]},
            {text:"佛山市第一人民医院",value:4,children:[
                {value:620,text:"女未婚（620）"},
                {value:620,text:"女已婚（620）"},
                {value:720,text:"女(40岁以上）（720）"},
                {value:964,text:"女未婚（964元A套餐）"},
                {value:981,text:"女已婚（981元A套餐）"},
                {value:1045,text:"女已婚（1045元A套餐）"},
                {value:1079,text:"女未婚（1079元B套餐）"},
                {value:1140,text:"女已婚（1140元B套餐）"},
                {value:1144,text:"女未婚（1144元A套餐）"},
                {value:1175,text:"女未婚（1175元A套餐）"},
                {value:1205,text:"女未婚（1205元B套餐）"},
                {value:1261,text:"女已婚（1261元A套餐）"},
                {value:1267,text:"女已婚（1267元B套餐）"},
                {value:1283,text:"女未婚（1283元A套餐）"},
                {value:1391,text:"女未婚（1391元B套餐）"},
                {value:1449,text:"女已婚（1449元A套餐）"},
                {value:1462,text:"女已婚（1462元B套餐）"},
                {value:1720,text:"女未婚（1720元A套餐）"},
                {value:1753,text:"女未婚（1753元B套餐）"},
                {value:1763,text:"女已婚（1763元B套餐）"},
                {value:1889,text:"女已婚（1889元A套餐）"},
                {value:2622,text:"女未婚（2622元A套餐）"},
                {value:2632,text:"女未婚（2632元A套餐）"},
                {value:2664,text:"女已婚（2664元B套餐）"},
                {value:2685,text:"女已婚（2685元A套餐）"},
                {value:500,text:"男（500）"},
                {value:600,text:"男(40岁以上）（600）"},
                {value:861,text:"男（861元A套餐）"},
                {value:1048,text:"男（1048元A套餐）"},
                {value:1097,text:"男（1097元B套餐）"},
                {value:1136,text:"男体检套餐（1136元A套餐）"},
                {value:1223,text:"男（1223元B套餐）"},
                {value:1270,text:"男（1270元A套餐）"},
                {value:1408,text:"男（1408元B套餐）"},
                {value:1721,text:"男（1721元A套餐）"},
                {value:1771,text:"男（1771元B套餐）"},
                {value:2661,text:"男（2661元A套餐）"},
                {value:2629,text:"男（2629元B套餐）"},
            ]},
            {text:"顺德区第一人民医院",value:5,children:[
                {value:0,text:"体检套餐"}
            ]},
        ],
        click: function () {
            $("input[name=extramoney]").val(0)
            $("#pluss").val(0)
            var quote = count(age,gender)
            cate.picker.show(function (items) {
                var val = items[0].text + "-" + items[1].text;
                hospital = items[0].text
                if(items[0].value == 2) {
                    $("#fuli").show()
                    $("#plus").hide()
                    $('input[name=extramoney]').bind('input propertychange', function () {
                        var temp = parseInt($("input[name=extramoney]").val())
                        if(temp){
                            var num = parseInt(items[1].value)
                            var quote2 = parseInt(quote)
                            var tempNum = (quote+temp)-num
                            if(tempNum >= 0){
                                $("#money").html("在免费额度范围内")
                            }else{
                                $("#money").html("需要额外补费用"+(-tempNum)+"元")
                            }
                        }else{
                            if(quote - items[1].value < 0){
                                $("#money").html("需要额外补费用"+(items[1].value-quote)+"元")
                            }else{
                                $("#money").html("在免费额度范围内")
                            }
                        }
                    })
                }else if(items[0].value == 3){
                    $("#fuli").hide()
                    $("#plus").show()
                    $("#plus").change(function () {
                        if($('#plus option:selected') .val()!=0){
                            if(quote - (items[1].value+800) < 0){
                                $("#money").html("需要额外补费用"+(items[1].value+800-quote)+"元")
                            }else{
                                $("#money").html("在免费额度范围内")
                            }
                        }else{
                            if(quote - items[1].value < 0){
                                $("#money").html("需要额外补费用"+(items[1].value-quote)+"元")
                            }else{
                                $("#money").html("在免费额度范围内")
                            }
                        };
                    })
                }else{
                    $("#fuli").hide()
                    $("#plus").hide()
                }
                cate.input.value = val;
                cate.self.innerText = val;
                if(quote - items[1].value < 0){
                    $("#money").html("需要额外补费用"+(items[1].value-quote)+"元")
                }else{
                    $("#money").html("在免费额度范围内")
                }

            });
        }
    }
    var form = {
        init: function () {
            var self = wxgh.getElement("applyForm");
            form.self = self;
        },
        submit: function () {
            var info = wxgh.serialize(form.self);
            var verify_res = form.verify(info);
            if (verify_res) {
                mui.toast(verify_res);
                return;
            }
            if (!form.loading) {
                form.loading = new ui.loading("提交中，请稍候...");
            }

            wxgh.start_progress(e_nextBtn);
            form.loading.show();

            info.hospital = hospital
            info.name = $("#name").html();
            info.age = $("#age").html()
            info.dept=$("#dept").html()
            info.gender=$("#gender").html()
            var extra = $("#money").html()
            if(extra == "在免费额度范围内"){
                info.extra="在免费额度范围内"
            }else{
                info.extra="需额外补费用"
                info.encore = extra.slice(7,extra.length-1)
            }
            info.quote=$("#quote").html()
            info.marriage=$("#marriage").html()
            info.mobile = $("#mobile").html()
            info.idcard = $("#idcard").html()
            var fuli = ($("input[name='extramoney']").val())
            if(fuli){
                info.fuli = fuli
            }else{
                info.fuli = 0
            }

            var plus = $('#pluss option:selected') .val();
            if(plus){
                if(plus == 0){
                    info.plus = "无"
                }else if(plus == 1){
                    info.plus = "肝脏疾病套餐(800)"
                }else if(plus == 2){
                    info.plus = "心脑疾病套餐(800)"
                }
            }else{
                info.plus = "无"
            }


            form.request(info);
        },
        request: function (info) {
            var url = homePath + "/wx/common/bodycheck/add.json";
            $.post(url, info, function (res) {
                form.loading.hide();
                wxgh.end_progress(e_nextBtn);
                if (res.ok) {
                    window.location.href=homePath+"/wx/common/bodycheck/show.html"
//                    wxgh.clearForm(form.self);
//                    if (res.data) {
//                        mui.openWindow(res.data);
//                    }
                } else {
                    mui.toast(res.msg);
                }
            });
        },
        verify: function (info) {
            if(!info.category){
                return "请先选择体检套餐"
            }
        }
    };

    form.init();
    cate.init()
    e_nextBtn.addEventListener("tap", function () {
        form.submit();
    });

</script>
<script>
    $(function(){

//        $("input[name=idcard]").change(function(){
//            console.log($(this).val())
//        });
//        //根据身份证号码获取年龄
//        function IdCard(UUserCard){
//            var myDate = new Date();
//            var month = myDate.getMonth() + 1;
//            var day = myDate.getDate();
//            var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1;
//            if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) {
//                age++;
//            }
//            return age;
//        }
//
//        $("input[name=mobile]").keyup(function () {
//            var val = $(this).val();
//            if (val.length > 11) {
//                $(this).val(val.substr(0, 11));
//                mui.toast("请填写11个数字哦~");
//            }
//        });
    });
</script>
</body>

</html>

