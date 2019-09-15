<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019/3/20
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>生日慰问福利</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.poppicker.css" rel="stylesheet"/>
</head>
<body>

<div class="ui-fixed-bottom">
    <button id="nextBtn" type="button" class="mui-btn mui-btn-primary">提交</button>
</div>

<header class="mui-bar mui-bar-nav">
    <a class="mui-icon mui-icon-bars mui-pull-left mui-plus-visible"></a>
    <a id="info" class="mui-icon mui-icon-info-filled mui-pull-right" style="color: #999;"></a>
    <h1 class="mui-title">选择商家</h1>
</header>

<div class="mui-content">
    经过前期投票最终确定三家饼家作为今年生日福利采购方，分别为华之屋、采蝶轩、美心，现请本部员工进行饼店选择上报，谢谢。
    <form id="applyForm">

        <div class="mui-input-group ui-margin-top-10">
            <div class="mui-input-row">
                <label>姓&nbsp;&nbsp;&nbsp;&nbsp;名</label>
                <span class="input-row-span ui-text-info" id="name" >${wxgh_user.name}</span>
            </div>
            <div class="mui-input-row">
                <label>电&nbsp;&nbsp;&nbsp;&nbsp;话</label>
                <input name="mobile" value="${wxgh_user.mobile}" type="number" class="mui-input-clear mui-input-numbox"
                       placeholder="联系人电话">
            </div>
            <div class="mui-input-row">
                <label>出生年</label>
                <input name="year" value="" type="number" class="mui-input-clear mui-input-numbox"
                       placeholder="请填写四位数出生年份">
            </div>
            <div class="mui-input-row">
                <label>出生月</label>
                <select name="month" id="month">
                    <option value="0">请选择</option>
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

        <div class="mui-input-group ui-margin-top-15">
            <div class="mui-input-row">
                <label>选择商家</label>
                <select name="cate" id="cate">
                    <option value="0">请选择</option>
                    <option value="华之屋">华之屋</option>
                    <option value="彩蝶轩">彩蝶轩</option>
                    <option value="美心">美心</option>
                </select>
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

    var b =' ${b}'
    if(b!=null){
        $("input[name='mobile']").val(${b.mobile})
        $("input[name='year']").val(${b.year})
        $("#month").val('${b.month}')
        $("#cate").val('${b.cate}')
        if('${b.cate}'!=''){
            $("#nextBtn").html("提交修改")
        }

    }

    var e_nextBtn = wxgh.getElement("nextBtn");
    var form = {
        init: function () {
            var self = wxgh.getElement("applyForm");
            form.self = self;
        },
        submit: function () {
            var info = wxgh.serialize(form.self);
            info.name = $("#name").html()
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

            form.request(info);
        },
        request: function (info) {
            var url = homePath + "/wx/common/bread/add.json";
            mui.post(url, info, function (res) {
                form.loading.hide();
                wxgh.end_progress(e_nextBtn);
                if (res.ok) {
                    mui.toast("提交成功")
                    window.location.reload()
                } else {
                    mui.toast("提交失敗");
                }
            });
        },
        verify: function (info) {
            if (!info.name) {
                return "姓名不能为空哦";
            }
            if (!info.mobile) {
                return "联系人号码不能为空哦";
            }
            if (info.cateId == 0) {
                return "请选择商家";
            }
            if (info.month == 0) {
                return "出生月不能为空哦";
            }
            if (!info.year) {
                return "出生年不能为空哦";
            }
            if (info.year.length!=4) {
                return "出生年份格式不正确";
            }
        }
    };

    form.init();
    e_nextBtn.addEventListener("tap", function () {
        form.submit();
    });

</script>
<script>
    $(function(){
        $("input[name=mobile]").keyup(function () {
            var val = $(this).val();
            if (val.length > 11) {
                $(this).val(val.substr(0, 11));
                mui.toast("请填写11个数字哦~");
            }
        });
    });
</script>


</body>
</html>
