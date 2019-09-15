<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/24
  Time: 13:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>申请协会</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .input-row-span.ui-text-info {
            padding-right: 20px;
        }
    </style>
</head>

<body>

<div class="mui-content">
    <form id="applyForm">
        <div class="mui-input-group ui-margin-top-15">
            <div class="mui-input-row">
                <label>名&nbsp;&nbsp;&nbsp;&nbsp;称</label>
                <input name="name" type="text" class="mui-input-clear" placeholder="兴趣协会名称（3-20字符）">
            </div>
        </div>
        <div class="mui-input-group ui-margin-top-15">
            <div class="mui-input-row">
                <label>创建人</label>
                <span class="input-row-span ui-text-info mui-text-right">${empty wxgh_user.name?'未知':wxgh_user.name}</span>
            </div>
        </div>
        <div id="chooseImg" class="ui-margin-top-15" style="background-color: #fff;">

        </div>
        
        <div class="mui-input-group ui-margin-top-15">
            <h5 class="ui-h5-title" style="font-size: 16px;color: #000;margin-bottom: 0">介&nbsp;&nbsp;绍</h5>
            <div class="ui-textarea-div">
                <textarea name="info" rows="4" maxlength="600" placeholder="简单用几句话介绍你的协会吧"></textarea>
            </div>
        </div>
    </form>

    <div class="ui-fixed-bottom">
        <button id="subBtn" type="button" class="mui-btn mui-btn-primary">确定</button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/weixin/weixin.min.js"></script>
<script type="text/javascript">
    $(function () {
        wxgh.init('${weixin}')

        var load = ui.loading('请稍候...')
        var choose = wxgh.imagechoose($('#chooseImg'), {
            wx: {
                count: 1
            }, clear: true
        })

        $('#subBtn').on('tap', function () {
            var info = wxgh.serialize($('#applyForm')[0])
            var verify_res = verify(info)
            if (verify_res) {
                ui.alert(verify_res)
                return;
            }

            load.show()
            choose.upload(function (mediaIds) {
                if (mediaIds && mediaIds.length > 0) {
                    info['avatarId'] = mediaIds[0]
                }
                wxgh.request.post('/wx/union/group/add_api.json', info, function () {
                    wxgh.redirectTip(homePath, {
                        msg: '您的创建兴趣协会申请已提交，请静候管理员的审核吧~',
                        url: homePath + '/wx/union/group/index.html',
                        title: '申请已提交',
                        urlMsg: '跳转到兴趣协会列表'
                    })
                })
            })
        })

        function verify(info) {
            if (!info.name) {
                return "兴趣协会名称不能为空哦";
            }
            if (info.name.length < 3 || info.name.length > 20) {
                return "兴趣协会名称在3-20个字符哦";
            }
            if (!info.info) {
                return "兴趣协会简介不能为空哦";
            }
            if (info.info.length > 600) {
                return "兴趣协会简介有点长哦";
            }
        }
    })
</script>
</body>

</html>
