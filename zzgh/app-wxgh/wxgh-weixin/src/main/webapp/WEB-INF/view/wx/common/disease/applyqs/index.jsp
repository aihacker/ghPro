<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/2
  Time: 14:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>去世资助申请</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.poppicker.css" rel="stylesheet"/>

    <style>

        .select-image-div label {
            display: block;
            position: relative;
            top: 0px;
            width: 100%;
        }

        .select-image-div {
            padding-top: 10px;
        }
    </style>
</head>

<body>
<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

    <%--<h1 class="mui-title">去世资助申请</h1>--%>
    <%--<a id="nextBtn" class="mui-btn mui-btn-link mui-pull-right">提交</a>--%>
<%--</header>--%>

<div class="ui-fixed-bottom">
    <button id="nextBtn" type="button" class="mui-btn mui-btn-primary">提交</button>
</div>

<div class="mui-content">
    <form id="applyForm">
        <div class="mui-input-group ui-margin-top-10">
            <div class="mui-input-row">
                <label>去世时间</label>
                <input name="happenTime" type="hidden" id="timeInput"/>
                <span id="selectTime" class="input-row-span ui-text-info">选择时间</span>
            </div>
            <div class="textare-div">
                <label>去世原因</label>
                <textarea name="info" rows="4" placeholder="去世的原因简介"></textarea>
            </div>
        </div>
        <div class="mui-input-group ui-margin-top-15">
            <div class="select-image-div">
                <label>附件上传</label>

                <div class="img-item">
                    <input id="applyFileInput" type="file" name="files[]" multiple="multiple" accept="image/*">
                    <img src="${home}/image/common/icon_add1.png"/>
                </div>
            </div>
        </div>
    </form>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.poppicker.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/upload/ajaxfileupload.js"></script>

<script type="text/javascript">
    mui.init()

    var homePath = '${home}';
    var applyId = '${param.id}';

    var e_nextBtn = wxgh.getElement("nextBtn");

    //时间选择
    var selectTime = {
        init: function () {
            var self = wxgh.getElement("selectTime");
            self.addEventListener("tap", selectTime.click);

            selectTime.input = wxgh.getElement("timeInput");
            selectTime.self = self;

            var nowDate = new Date();
            selectTime.picker = new mui.DtPicker({
                type: 'date',
                beginYear: nowDate.getFullYear() - 90,
                endYear: nowDate.getFullYear(),
                value: nowDate.format('yyyy-MM-dd')
            });
        },
        click: function (e) {
            selectTime.picker.show(function (rs) {
                selectTime.input.value = rs.text;
                selectTime.self.innerText = rs.text;
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
            info['applyId'] = applyId;
            var url = homePath + "/wx/common/disease/applyqs/add.json";
            $.ajaxFileUpload({
                url: url,
                data: info,
                secureuri: false,
                fileElementId: 'applyFileInput',
                dataType: 'json',
                success: function (res) {
                    form.loading.hide();
                    wxgh.end_progress(e_nextBtn);
                    if (res.ok) {
                        wxgh.clearForm(form.self);
                        wxgh.redirectTip(homePath, {
                            msg: '申请提交完成，请耐心等候管理员审核通过',
                            title: '提交成功'
                        })
                    } else {
                        mui.toast(res.msg);
                    }
                },
                error: function (res) {
                    wxgh.end_progress(e_nextBtn);
                    form.loading.hide();
                    mui.toast(res);
                }
            });
        },
        verify: function (info) {
            if (!info.happenTime) {
                return "去世时间不能为空";
            }
            if (!info.info) {
                return "简单写两句死亡原因吧";
            }
        }
    };

    var imgInput = {
        init: function () {
            var self = wxgh.query(".select-image-div");
            var e_input = self.querySelector("input[type=file]");
            e_input.addEventListener("change", imgInput.change);

            imgInput.self = self;
        },
        change: function (e) {
            var files = this.files;
            if (files && files.length > 0) {
                for (var i = 0; i < files.length; i++) {
                    var fileReader = new FileReader();
                    fileReader.readAsDataURL(files[i]);
                    fileReader.onload = function (e) {
                        imgInput.createImg(this.result);
                    };
                }
            }
        },
        createImg: function (resutl) {

            var e_item = document.createElement("div");
            e_item.className = "img-item";

            e_item.innerHTML = '<img src="' + resutl + '" />';
            imgInput.self.appendChild(e_item);
        }
    };

    selectTime.init();
    form.init();
    imgInput.init();

    e_nextBtn.addEventListener("tap", function () {
        form.submit();
    });
</script>
</body>

</html>
