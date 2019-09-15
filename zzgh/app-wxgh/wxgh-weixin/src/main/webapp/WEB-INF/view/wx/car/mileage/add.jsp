<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="wxgh.app.sys.variate.GlobalValue" %>
<!DOCTYPE HTML>
<html>

<head>
    <title>上传里程</title>
    <meta http-equiv="Access-Control-Allow-Origin" content="*">
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css">
</head>
<body class="mui-fullscreen">
<div class="mui-page">
    <div class="mui-page-content">
        <div class="mui-scroll-wrapper">
            <div class="mui-scroll">
                <form id="applyForm">
                    <input name="uploader" type="text" class="mui-input-clear" value="${empty wxgh_user.name?'':wxgh_user.name}" hidden>
                    <div class="mui-input-group ui-margin-top-5">
                        <div class="mui-input-row">
                            <label>&nbsp;车&nbsp;&nbsp;牌&nbsp;&nbsp;&nbsp;&nbsp;粤&nbsp;-&nbsp;</label>
                            <input name="carNumber" type="text" class="mui-input-clear" placeholder="">
                        </div>
                    </div>
                    <div class="mui-input-group ui-margin-top-15">
                        <div class="mui-input-row">
                            <label>&nbsp;里&nbsp;&nbsp;程&nbsp;</label>
                            <input name="mileage" type="number" class="mui-input-clear" placeholder="">
                        </div>
                    </div>
                    <div class="ui-content ui-margin-top-10">
                        <h5 class="ui-title">里程照片</h5>
                        <div class="ui-text-div">
                            <input id="file" type="file" name="file" onchange="changeUploadImg(this)" hidden/>
                            <img src="${home}/image/common/icon_add.png" alt="" onclick="onUploadImg(this)" height="100%" width="100%">
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="ui-fixed-bottom">
            <button id="subBtn" type="submit" class="mui-btn mui-btn-primary">确定</button>
        </div>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.view.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script type="text/javascript" src="${home}/libs/weixin/weixin.min.js"></script>
<script>

    mui.init({
        swipeBack: true
    })
    mui('.mui-scroll-wrapper').scroll();
    wxgh.init('${weixin}')

    function onUploadImg(obj){
        obj.parentNode.children[0].click();
    }

    function changeUploadImg(obj){
        obj.parentNode.children[1].src = URL.createObjectURL($(obj)[0].files[0])
    }

</script>
<script type="text/javascript">

    $(function () {
        var load = ui.loading('请稍候...')

        $('#subBtn').on('tap', function () {
            var info = wxgh.serialize($('#applyForm')[0])
            var verify_res = verify(info)
            if (verify_res) {
                ui.alert(verify_res)
                return;
            }
            var formData = new FormData();
            formData.append("file", $('#file')[0].files[0]);
            formData.append("uploader", info.uploader)
            formData.append("carNumber", '粤-' + info.carNumber)
            formData.append("mileage", info.mileage)
            load.show();

            $.ajax({
                url: '${GlobalValue.mileagehost}/mileageload/mileage/upload.json',
                type:'post',
                processData:false,
                contentType:false,
                data:formData,
                success:function (result) {
                    wxgh.redirectTip(homePath, {
                        msg: '您已上传了车辆里程',
                        url: homePath + '/wx/car/mileage/add.html',
                        title: '已上传',
                        urlMsg: '返回页面'
                    })
                },
                error:function (result) {
                    ui.alert("上传失败，请联系管理员");
                }
            })
        })

        function verify(info) {
            if (!info.uploader) {
                return "上传人不能为空";
            }
            if (!info.carNumber) {
                return "车牌号不能为空";
            }
            if (info.carNumber.length != 6) {
                return "车牌号长度错误";
            }
            if (!info.mileage) {
                return "兴趣协会简介不能为空哦";
            }
        }

    })

</script>
</body>
</html>
