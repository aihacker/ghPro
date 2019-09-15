<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/29
  Time: 20:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>论坛发布</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css"/>
</head>

<body>

<div class="mui-content">
    <form id="applyForm">

        <div class="ui-content ui-margin-top-10">
            <h5 class="ui-title">标题</h5>
            <div class="ui-text-div">
                <textarea name="atlName" id="atlName" maxlength="50" rows="1"
                          placeholder="*标题 (小于50个字符)"></textarea>
            </div>
        </div>

        <div class="ui-content ui-margin-top-10">
            <h5 class="ui-title">简介</h5>
            <div class="ui-text-div">
                <textarea name="atlContent" id="atlContent" maxlength="200" rows="3"
                          placeholder="你想要说点什么呢（小于200字符）"></textarea>
            </div>
        </div>
        <div class="ui-margin-top-15" id="chooseBtn">
        </div>
    </form>
    <div class="ui-fixed-bottom">
        <button id="addBtn" type="button" class="mui-btn mui-btn-block mui-btn-primary ui-btn">立即发布</button>
    </div>
</div>

<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript"></script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>

<script>
    $(document).ready(function () {
        wxgh.autoTextarea($('.ui-text-div textarea'));
    });
</script>
<script type="text/javascript">
    var $uploadImage;
    var page = {
        init: function () {
            wxgh.wxInit('${weixin}')
            $uploadImage = wxgh.imagechoose($("#chooseBtn"), {})
            wxgh.previewImageInit();
            var addBtn = ghmui.getElement('addBtn')
            addBtn.addEventListener('tap', function () {
                page.form.request()
            })
            this.addBtn = addBtn

            this.form.init()
        },
        form: {
            init: function () {
                var self = ghmui.getElement('applyForm')

                this.self = self
            },
            request: function () {
                var info = wxgh.serialize(page.form.self)
                var verify_res = this.verify(info)
                if (verify_res) {
                    mui.toast(verify_res)
                    return
                }

                if (!this.loading) {
                    this.loading = ui.loading('发布中...')
                }
                ghmui.start_progress(page.addBtn)
                this.loading.show()

                //上传图片
                var self = this
                $uploadImage.upload(function (mediaIds) {
                    var url = homePath + '/wx/common/bbs/article/add.json'
                    if (mediaIds) {
                        info['fileIds'] = mediaIds.toString()
                    }
                    wxgh.request.post(url, info, function () {
                        self.loading.hide()
                        ghmui.end_progress(page.addBtn)
                        ui.showToast('请静候管理员审核哦', function () {
                            mui.back()
                        })
                    }, function () {
                        mui.toast(res.msg)
                    })
                })
            },
            verify: function (info) {
                if (!info['atlName']) {
                    return "标题不能为空哦";
                }
                if (!info['atlContent']) {
                    return "说点内容吧";
                }
            }
        }
    }


    window.onload = page.init()


</script>
</body>

</html>
