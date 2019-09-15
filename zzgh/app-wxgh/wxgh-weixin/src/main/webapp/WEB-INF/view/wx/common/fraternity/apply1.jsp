<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/29
  Time: 9:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>个人基本信息填写</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>

<body>
<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--&lt;%&ndash;<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>&ndash;%&gt;--%>

    <%--<h1 class="mui-title">个人基本信息填写</h1>--%>
    <%--<a id="nextBtn" class="mui-btn mui-btn-link mui-pull-right">下一步</a>--%>
<%--</header>--%>

<div class="ui-fixed-bottom">
    <button id="nextBtn" type="button" class="mui-btn mui-btn-primary">下一步</button>
</div>

<div class="mui-content">
    <form id="applyForm">
        <div class="mui-input-group ui-margin-top-10">
            <div class="mui-input-row">
                <label>姓&nbsp;&nbsp;&nbsp;&nbsp;名</label>
                <input type="text" name="name" value="${userinfo.user.name}" class="mui-input-clear" placeholder="你的姓名">
            </div>
            <div class="mui-input-row">
                <label>性&nbsp;&nbsp;&nbsp;&nbsp;别</label>
                <select name="sex">
                    <option value="0">请选择性别</option>
                    <c:choose>
                        <c:when test="${userinfo.user.gender eq 1}">
                            <option value="1" selected>男</option>
                        </c:when>
                        <c:otherwise>
                            <option value="1">男</option>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${userinfo.user.gender eq 2}">
                            <option value="2" selected>女</option>
                        </c:when>
                        <c:otherwise>
                            <option value="2">女</option>
                        </c:otherwise>
                    </c:choose>
                </select>
            </div>
            <div class="mui-input-row">
                <label>民&nbsp;&nbsp;&nbsp;&nbsp;族</label>
                <select name="nation" id="nationSelect">
                    <option>请选择</option>
                </select>
            </div>
            <div class="mui-input-row">
                <label>出生日期</label>
                <input value="${userinfo.birthStr}" name="birth" id="birthInput" type="hidden"/>
                <span id="selectBirth"
                      class="input-row-span ui-text-info">${empty userinfo.birthStr?'选择出生日期':userinfo.birthStr}</span>
            </div>
            <div class="mui-input-row">
                <label>联系电话</label>
                <input name="mobile" value="${userinfo.user.mobile}" type="number"
                       class="mui-input-clear mui-input-numbox" placeholder="联系电话">
            </div>
            <div class="mui-input-row">
                <label>身份证</label>
                <input name="idcard" type="number" value="${userinfo.idcard}" class="mui-input-clear mui-input-numbox"
                       placeholder="身份证号码">
            </div>
            <div class="textare-div">
                <label>家庭住址：</label>
                <textarea name="address" rows="3" placeholder="你的家庭住址（40字以内）">${userinfo.address}</textarea>
            </div>

            <%--<div class="textare-div">--%>
            <%--<label>个人简历：</label>--%>
            <%--<textarea name="resume" rows="8" placeholder="从大学阶段开始填写"></textarea>--%>
            <%--</div>--%>
            <br>
        </div>

    </form>
</div>


<jsp:include page="/comm/mobile/scripts.jsp"/>
<link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>

<script type="text/javascript" src="${home}/script/consts.js"></script>

<script type="text/javascript">
    mui.init()

    var homePath = '${home}';
    var nationStr = '${userinfo.nation}'

    var e_nextBtn = wxgh.getElement("nextBtn");

    //Select初始化相关工作
    var selectInit = {
        init: function () {
            selectInit.nationInit();
        },
        nationInit: function () {
            var self = wxgh.getElement("nationSelect");
            selectInit.create_option(nations, self);
        },

        create_option: function (names, select) {
            for (var i in names) {
                var e_option = document.createElement("option");
                e_option.value = names[i];
                e_option.innerText = names[i];
                if (nationStr && nationStr == names[i]) e_option.selected = true
                select.appendChild(e_option);
            }
        }
    };

    //出生日期选择
    var selectBirth = {
        init: function () {
            var self = wxgh.getElement("selectBirth");
            self.addEventListener("tap", selectBirth.click);
            var nowDate = new Date();
            var options = {
                type: 'date',
                beginYear: 1920,
                endYear: nowDate.getFullYear()
            };
            selectBirth.picker = new mui.DtPicker(options);
            selectBirth.input = wxgh.getElement("birthInput");
        },
        click: function () {
            var self = this;
            selectBirth.picker.show(function (res) {
                selectBirth.input.value = res.text;
                self.innerText = res.text;
            });
        }
    };

    var form = {
        init: function () {
            var self = wxgh.getElement("applyForm");

            form.self = self;
        },
        submit: function () {
            var info = wxgh.serialize(form.self);
            console.log(info);
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

            var url = homePath + '/wx/common/fraternity/apply/add1.json';
            mui.post(url, info, function (res) {
                form.loading.hide();
                wxgh.end_progress(e_nextBtn);
                if (res.ok) {
                    mui.openWindow(homePath + "/wx/common/fraternity/apply2.html");
                } else {
                    mui.toast(res.msg);
                }
            }, 'json');
        },
        verify: function (info) {
            if (!info.name) {
                return "总得要告诉我你的姓名吧";
            }
            if (info.sex == 0) {
                return "没有选择性别哦";
            }
            if (!info.nation) {
                return "你是什么民族的呢？";
            }
            if (!info.birth) {
                return "出生日期不能为空";
            }
            if (!info.mobile) {
                return "手机号码不能为空";
            }
            if (!info.idcard) {
                return "身份证号码不能为空";
            }
            if (!wxgh.checkIdcard(info.idcard)) {
                return "身份证格式不正确哦";
            }
            if (!info.address) {
                return "告诉我你的家庭地址吧";
            }
            if(info.address.length>40){
                return "家庭地址有点长哦";
            }
//            if (!info.resume) {
//                return "简历不能为空哦";
//            }
//            if (info.resume.length <= 10) {
//                return "简历太少了吧";
//            }
        }
    };

    selectInit.init();
    selectBirth.init();
    form.init();

    e_nextBtn.addEventListener("tap", function () {
        form.submit();
    });
</script>
</body>

</html>
