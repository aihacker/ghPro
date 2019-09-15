<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

</head>

<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
<%--&lt;%&ndash;<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>&ndash;%&gt;--%>

<%--<h1 class="mui-title">基本信息填写</h1>--%>
<%--<a id="nextBtn" class="mui-btn mui-btn-link mui-pull-right">下一步</a>--%>
<%--</header>--%>



<header class="mui-bar mui-bar-nav">
    <a class="mui-icon mui-icon-bars mui-pull-left mui-plus-visible"></a>
    <a id="info" class="mui-icon mui-icon-info-filled mui-pull-right" style="color: #999;"></a>
    <h1 class="mui-title">基本信息录入</h1>
</header>
<div class="mui-content">
    <c:choose>
        <c:when test="${parentid != 4}">
            <div>
                预约已结束
            </div>
        </c:when>
        <c:otherwise>
            <form id="applyForm">

                <div class="mui-input-group ui-margin-top-10">
                    <div class="mui-input-row">
                        <label>姓&nbsp;&nbsp;&nbsp;&nbsp;名</label>
                        <input name="name" type="text" value="${wxgh_user.name}" class="mui-input-clear" placeholder="联系人姓名"/>
                    </div>
                    <div class="mui-input-row">
                        <label>性&nbsp;&nbsp;&nbsp;&nbsp;别</label>
                        <select name="gender">
                            <option value="0">请选择</option>
                            <option value="1">男</option>
                            <option value="2">女</option>
                        </select>
                    </div>
                        <%--<div class="mui-input-row">--%>
                        <%--<label>部&nbsp;&nbsp;&nbsp;&nbsp;门</label>--%>
                        <%--<span class="input-row-span ui-text-info">${deptname}</span>--%>
                        <%--</div>--%>
                    <div class="mui-input-row">
                        <label>联系电话</label>
                        <input name="mobile" value="${wxgh_user.mobile}" type="number" class="mui-input-clear mui-input-numbox"
                               placeholder="联系人电话">
                    </div>
                    <div class="mui-input-row">
                        <label>部&nbsp;&nbsp;&nbsp;&nbsp;门</label>
                        <input name="dept" type="text" class="" value="${deptname}"/>
                    </div>
                    <div class="mui-input-row">
                        <label>身份证号</label>
                        <input name="idcard" id="idcard" type="text" class="" placeholder="请填写正确的身份证号码"/>
                    </div>
                    <div class="mui-input-row">
                        <label>婚姻状况</label>
                        <select name="marriageId">
                            <option value="0">请选择</option>
                            <option value="1">未婚</option>
                            <option value="2">已婚</option>
                        </select>
                    </div>
                        <%--<div class="mui-input-row">--%>
                        <%--<label>年&nbsp;&nbsp;&nbsp;&nbsp;龄</label>--%>
                        <%--<span class="input-row-span ui-text-info">${wxgh_user.gender eq 1?"男":"女"}</span>--%>
                        <%--</div>--%>
                </div>
                    <%--<div class="mui-input-group ui-margin-top-15">--%>

                    <%--<div class="mui-input-row">--%>
                    <%--<label>资助类别</label>--%>
                    <%--<select name="cateId">--%>
                    <%--<option value="0">请选择</option>--%>
                    <%--<option value="jb">疾病资助</option>--%>
                    <%--<option value="jy">教育资助</option>--%>
                    <%--<option value="zc">因公致残资助</option>--%>
                    <%--<option value="qs">去世资助</option>--%>
                    <%--<option value="zh">自然灾害资助</option>--%>
                    <%--<option value="zx">直系亲属去世慰问</option>--%>
                    <%--<option value="pk">困难家庭资助</option>--%>
                    <%--</select>--%>
                    <%--</div>--%>
                    <%--<div class="mui-input-row">--%>
                    <%--<label>申请金额</label>--%>
                    <%--<input name="applyMoney" type="number" onblur="f(this)" class="mui-input-clear mui-input-numbox" placeholder="申请资助金额">--%>
                    <%--</div>--%>
                    <%--</div>--%>
                    <%--<div class="mui-input-group ui-margin-top-15">--%>
                    <%--<div class="textare-div">--%>
                    <%--<label>备注</label>--%>
                    <%--<textarea name="remark" rows="5" placeholder="备注点什么呢？"></textarea>--%>
                    <%--</div>--%>
                    <%--</div>--%>
            </form>
            <div class="ui-fixed-bottom">
                <button id="nextBtn" type="button" class="mui-btn mui-btn-primary">下一步</button>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script type="text/javascript">
    mui.init()

    var homePath = '${home}';


//    function f(obj){
//        var v = obj.value;
//        var patrn=/^([1-9]\d*|0)(\.\d*[1-9])?$/;
//        if (!patrn.exec(v)) {
//            mui.toast("请输入正确金额哦~");
//        }
//    }

    var e_nextBtn = wxgh.getElement("nextBtn");
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

//            form.request(info);
            window.location.href = homePath +"/wx/common/bodycheck/apply.html?age="+form.IdCard(info.idcard)+
                "&marrige="+info.marriageId+"&mobile="+info.mobile+"&dept="+info.dept+"&gender="+info.gender+"&idcard="+info.idcard
        },
        //根据身份证号码获取年龄
        IdCard:function (UUserCard){
            var myDate = new Date();
            var month = myDate.getMonth() + 1;
            var day = myDate.getDate();
            var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1;
            if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) {
                age++;
            }else if(UUserCard.substring(6, 10) == 1978){
                age = 40
            }
            return age;
        },
//        request: function (info) {
//            var url = homePath + "/wx/common/disease/add.json";
//            mui.post(url, info, function (res) {
//                form.loading.hide();
//                wxgh.end_progress(e_nextBtn);
//                if (res.ok) {
//                    wxgh.clearForm(form.self);
//                    if (res.data) {
//                        mui.openWindow(res.data);
//                    }
//                } else {
//                    mui.toast(res.msg);
//                }
//            });
//        },
        verify: function (info) {
            if (!info.name) {
                return "姓名不能为空哦";
            }
            if (!info.mobile) {
                return "联系人号码不能为空哦";
            }
//            if (info.cateId == 0) {
//                return "请选择申请资助金类别";
//            }
//            var patrn=/^([1-9]\d*|0)(\.\d*[1-9])?$/;
//            if (!patrn.exec(info.applyMoney)) {
//                return "请输入正确金额哦~";
//            }
            if (!info.dept) {
                return "部门不能为空哦";
            }
            if (!info.idcard) {
                return "身份证号码不能为空";
            }
            if(info.marriageId == 0){
                return "请选择婚姻状况"
            }
            if(info.gender == 0){
                return "请选择性别"
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
        $("input[name=idcard]").change(function(){
            console.log($(this).val())
        });

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
