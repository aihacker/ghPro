<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>意见发布</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .tip{
            float: left;
            color: red;
            font-size: 15px;
        }
        .mui-h6,h6{
            line-height: 22px;
        }
    </style>
</head>

<body>

<div class="mui-content">
    <form id="form">

        <div class="ui-content ui-margin-top-10">
            <h6 class="tip">*</h6><h5 class="ui-title">标题</h5>
            <div class="ui-text-div">
                <textarea name="title" id="title" maxlength="50" rows="1"
                          placeholder="标题 (小于50个字符)"></textarea>
            </div>
        </div>

        <div class="ui-content ui-margin-top-10">
            <h5 class="ui-title">内容</h5>
            <div class="ui-text-div">
                <textarea name="content" id="content" maxlength="2000" rows="30"
                          placeholder="你想要说点什么呢（小于2000字符）"></textarea>
            </div>
        </div>

        <input type="hidden" name="opinionId" value="${param.id}">

    </form>
    <div class="ui-fixed-bottom">
        <button id="addBtn" type="button" class="mui-btn mui-btn-block mui-btn-primary ui-btn">立即发布</button>
    </div>
</div>

<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript"></script>
<jsp:include page="/comm/mobile/scripts.jsp"/>

<script>
    $(document).ready(function () {
        wxgh.autoTextarea($('.ui-text-div textarea'));
    });
</script>
<script type="text/javascript">

    $(document).ready(function () {
        var addBtn = ghmui.getElement('addBtn')
        addBtn.addEventListener('tap', function () {

            var info = $("#form").serializeJson();
            var verify_res = verify(info)
            if (verify_res) {
                ui.alert(verify_res)
                return
            }
            var loading = ui.loading('发布中...')
            loading.show()
            wxgh.request.post("api/add.json", info, function () {
                loading.hide()
                ghmui.end_progress(addBtn)
                ui.showToast('恭喜你，发布成功!', function () {
                    mui.back()
                })
            })
        })

        function verify(info) {
            if (!info['title']) {
                return "标题不能为空哦";
            }
            if (!info['content']) {
                return "说点内容吧";
            }
        }

    })



</script>
</body>

</html>
