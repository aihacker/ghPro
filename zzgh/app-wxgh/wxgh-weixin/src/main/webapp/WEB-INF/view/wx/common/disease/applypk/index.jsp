<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/20
  Time: 19:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>困难家庭资助申请</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>
    <link href="${home}/libs/mui-v3.7.0/css/mui.poppicker.css" rel="stylesheet"/>
</head>

<body>
<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

    <%--<h1 class="mui-title">困难家庭资助申请</h1>--%>
    <%--<a id="nextBtn" class="mui-btn mui-btn-link mui-pull-right">提交</a>--%>
<%--</header>--%>

<div class="ui-fixed-bottom">
    <button id="nextBtn" type="button" class="mui-btn mui-btn-primary">提交</button>
</div>

<div class="mui-content">
    <form id="applyForm">
        <div class="mui-input-group ui-margin-top-10">
            <div class="mui-input-row">
                <label>资助类别</label>
                <select name="pkCate">
                    <option value="0">请选择</option>
                    <option>患病类</option>
                    <option>意外类</option>
                </select>
            </div>
        </div>
        <div class="mui-input-group ui-margin-top-15">
            <div class="mui-input-row">
                <label style="width:50% !important;">本人年收入</label>
                <input  style="width:50% !important;" name="ownerIncome" type="text" class="mui-input-clear mui-input-numbox" placeholder="单位：元">
            </div>
            <div class="mui-input-row">
                <label  style="width:50% !important;">家庭年收入</label>
                <input  style="width:50% !important;" name="familyIncome" type="text" class="mui-input-clear mui-input-numbox" placeholder="单位：元">
            </div>
        </div>

        <div class="mui-input-group ui-margin-top-15">
            <div class="textare-div">
                <label>困难情况</label>
                <textarea name="info" rows="6"
                          placeholder="请详细描述，如因病致疾，请说明所患疾病情况，治疗所需医疗费用，扣除社保企业补充医保互助会资助后，个人承担医疗费用；如突发意外事故、家庭面临的困境"></textarea>
            </div>
        </div>

        <div class="mui-input-group ui-margin-top-15" style="padding-top: 5px;">

            <div class="select-image-div">

                <label>附件上传</label>

                <div class="img-item">
                    <input id="accessoryFileInput" name="files[]" type="file" multiple="multiple" accept="image/*">
                    <img src="${home}/image/common/icon_add1.png"/>
                </div>
            </div>
        </div>
    </form>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.poppicker.js"></script>
<script src="${home}/libs/upload/ajaxfileupload.js"></script>
<script type="text/javascript">
    mui.init()

    var homePath = '${home}'
    var applyId = '${param.id}';

    var page = {
        init: function () {
            wxgh.getElement('nextBtn').addEventListener('tap', function () {
                page.form.request()
            })

            this.form.init()
            this.imgInput.init()
        },
        form: {
            init: function () {
                this.form = wxgh.getElement('applyForm');
            },
            request: function () {
                var url = homePath + '/wx/common/disease/applypk/add.json'

                var info = wxgh.serialize(this.form)
                info['applyId'] = applyId;
                var verify_res = this.verify(info)
                if (verify_res) {
                    mui.toast(verify_res)
                    return;
                }

                if (!this.loading) {
                    this.loading = new ui.loading('申请中...');
                }
                this.loading.show()

                var self = this
                $.ajaxFileUpload({
                    url: url,
                    data: info,
                    secureuri: false,
                    fileElementId: 'accessoryFileInput',
                    dataType: 'json',
                    success: function (res) {
                        self.loading.hide();
                        if (res.ok) {
                            wxgh.clearForm(self.form);
                            wxgh.redirectTip(homePath, {
                                msg: '申请提交完成，请耐心等候管理员审核通过',
                                title: '提交成功'
                            })
                        } else {
                            mui.toast(res.msg);
                        }
                    },
                    error: function (res) {
                        self.loading.hide();
                        mui.toast(res);
                    }
                });
            },
            verify: function (info) {
                if (info.pkCate == 0) {
                    return "请选择资助类别"
                }
                if (!info.ownerIncome) {
                    return "请输入您的年收入"
                }
                if (!info.familyIncome) {
                    return "请输入家庭年收入"
                }
                if (!info.info) {
                    return "困难情况详细介绍"
                }
            }
        },
        imgInput: {
            init: function () {
                var self = wxgh.query(".select-image-div");
                var e_input = self.querySelector("input[type=file]");
                e_input.addEventListener("change", this.change);

                this.self = self;
            },
            change: function (e) {
                var files = this.files;
                if (files && files.length > 0) {
                    for (var i = 0; i < files.length; i++) {
                        var fileReader = new FileReader();
                        fileReader.readAsDataURL(files[i]);
                        fileReader.onload = function (e) {
                            page.imgInput.createImg(this.result);
                        };
                    }
                }
            },
            createImg: function (resutl) {

                var e_item = document.createElement("div");
                e_item.className = "img-item";

                e_item.innerHTML = '<img src="' + resutl + '" />';
                this.self.appendChild(e_item);
            }
        }
    }

    window.onload = page.init()
</script>
</body>

</html>
