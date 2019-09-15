<%--
  Created by IntelliJ IDEA.
  User: hhl
  Date: 2017-07-27
  Time: 17:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>添加档案</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <jsp:include page="/comm/mobile/styles.jsp"></jsp:include>
    <style>
        .img-item {
            display: inline-block;
            margin: 5px;
            position: relative;
        }

        .img-item input[type=file] {
            opacity: 0;
            background-color: transparent;
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
        }

        .plus-img-div img,
        .img-item img {
            width: 100px;
            height: 100px;
        }

        .img-item .fa-minus-circle {
            content: '';
            position: absolute;
            top: -1px;
            right: -4px;
            color: #a94442;
            background-color: #fff;
            -moz-border-radius: 20px;
            -webkit-border-radius: 20px;
            border-radius: 20px;
        }

        .img-item .fa-minus-circle:hover,
        .img-item .fa-minus-circle:focus {
            color: #843534;
        }
    </style>
</head>

<body class="mui-fullscreen">
<%--<header class="mui-bar mui-bar-nav ui-head">--%>
<%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>

<%--<h1 class="mui-title">添加档案</h1>--%>
<%--<button id="addBtn" type="button" class="mui-btn mui-btn-link mui-pull-right">添加</button>--%>
<%--</header>--%>
<div class="ui-fixed-bottom">
    <button id="addActBtn" type="button" class="mui-btn mui-btn-primary">确定添加</button>
</div>
<div class="mui-content">
    <div class="mui-content-padded">
        <div>
            <label>档案名称</label>
            <input id="archiveName" type="text" placeholder="给档案取个名字吧" name="archiveName"/>
        </div>

        <div id="chooseBtn"></div>
        <div class="mui-row ui-choose-img ui-margin-top-15">
            <%--<div class="mui-col-sm-3 mui-col-xs-3" id="chooseBtn">--%>
                <%--<img src="/wxgh/image/default/icon_add1.png"/>--%>
            <%--</div>--%>
        </div>
        <%--<div class="select-image-div">--%>
        <%--<div class="img-item">--%>
        <%--<input id="selectImage" name="files[]" type="file" multiple="multiple" accept="image/*">--%>
        <%--<img src="${home}/image/xlkai/icon_add1.png"/>--%>
        <%--</div>--%>
        <%--</div>--%>

    </div>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"></jsp:include>
<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script type="text/javascript">
    var homePath = "${home}";

    wxgh.wxInit('${weixin}')

    mui.init();

    $(function () {
        //wxgh.imagechoose().init();
        //添加照片
        var img = wxgh.imagechoose($("#chooseBtn"));

        //初始化info队列
        var info = wxgh.serialize($("#addActBtn")[0]);

        $("#addActBtn").on("tap",function () {
            info["name"] = $("#archiveName").val();

            //上传图片
            img.upload(function(mediaIds){
                if(mediaIds && mediaIds.length>0){
                    var self = this;
                    if (!self.loading) {
                        self.loading = ui.loading('档案添加中...');
                    }
                    self.loading.show();
                    info['imgIds'] = mediaIds.toString();
                    var addUrl = homePath + "/wx/pub/archive/get.json"
                    wxgh.request.post(addUrl, info, function (data) {
                        ui.showToast('发布成功', function (data) {
                            mui.openWindow(homePath + '/wx/pub/archive/list.html')
                            console.log(data)
                        })
                    })
                }else{
                    ui.alert("健康档案图片不能为空")
                }

            })

        })
    })




    <%--//添加档案--%>
    <%--document.getElementById("addBtn").addEventListener('tap', function () {--%>
    <%--var self = this;--%>
    <%--var archiveName = document.getElementById("archiveName").value;--%>
    <%--if (archiveName.trim() == "") {--%>
    <%--mui.toast("档案名称不能为空哦");--%>
    <%--return;--%>
    <%--}--%>

    <%--//        var files = document.getElementById("selectImage").files;--%>
    <%--//        if (!files || files.length <= 0) {--%>
    <%--//            mui.toast("请先选择档案图片哦");--%>
    <%--//            return;--%>
    <%--//        }--%>

    <%--var upload_data = {--%>
    <%--name: archiveName--%>
    <%--};--%>

    <%--if (!this.loading) {--%>
    <%--this.loading = new ui.loading('请稍候...')--%>
    <%--}--%>

    <%--this.loading.show()--%>

    <%--wxgh.chooseImg.upload(function (mediaIds) {--%>
    <%--console.log(mediaIds)--%>
    <%--if (mediaIds && mediaIds.length > 0) {--%>
    <%--var url = homePath + '/archive/add.json?action=add'--%>
    <%--upload_data['imgIds'] = mediaIds.toString()--%>
    <%--mui.post(url, upload_data, function (res) {--%>
    <%--self.loading.hide()--%>
    <%--if (res.ok) {--%>
    <%--ui.showToast('添加成功', function () {--%>
    <%--mui.openWindow(homePath + '/archive/list.wx?userid=${userList.userid}')--%>
    <%--})--%>
    <%--} else {--%>
    <%--mui.toast(res.msg)--%>
    <%--}--%>
    <%--}, 'json')--%>
    <%--} else {--%>
    <%--self.loading.hide();--%>
    <%--mui.toast('请上传健康档案图片');--%>
    <%--}--%>
    <%--})--%>

    <%--&lt;%&ndash;wxgh.start_progress(self); //开始显示进度&ndash;%&gt;--%>

    <%--&lt;%&ndash;$.ajaxFileUpload({&ndash;%&gt;--%>
    <%--&lt;%&ndash;url: homePath + '/archive/add.json?action=add',&ndash;%&gt;--%>
    <%--&lt;%&ndash;data: upload_data,&ndash;%&gt;--%>
    <%--&lt;%&ndash;secureuri: false,&ndash;%&gt;--%>
    <%--&lt;%&ndash;fileElementId: 'selectImage',&ndash;%&gt;--%>
    <%--&lt;%&ndash;dataType: 'json',&ndash;%&gt;--%>
    <%--&lt;%&ndash;success: function (res) {&ndash;%&gt;--%>
    <%--&lt;%&ndash;wxgh.end_progress(self);&ndash;%&gt;--%>
    <%--&lt;%&ndash;if (res.ok) {&ndash;%&gt;--%>
    <%--&lt;%&ndash;mui.toast("添加成功");&ndash;%&gt;--%>
    <%--&lt;%&ndash;mui.openWindow(homePath + "/archive/list.html?userid=${userList.userid}");&ndash;%&gt;--%>
    <%--&lt;%&ndash;} else {&ndash;%&gt;--%>
    <%--&lt;%&ndash;mui.toast(res.msg);&ndash;%&gt;--%>
    <%--&lt;%&ndash;}&ndash;%&gt;--%>
    <%--&lt;%&ndash;},&ndash;%&gt;--%>
    <%--&lt;%&ndash;error: function (res) {&ndash;%&gt;--%>
    <%--&lt;%&ndash;wxgh.end_progress(self);&ndash;%&gt;--%>
    <%--&lt;%&ndash;mui.toast(res);&ndash;%&gt;--%>
    <%--&lt;%&ndash;}&ndash;%&gt;--%>
    <%--&lt;%&ndash;});&ndash;%&gt;--%>
    <%--});--%>
    <%--}--%>


    <%--//    var $selectImg = document.querySelector(".select-image-div");--%>
    <%--//    document.getElementById("selectImage").addEventListener('change', function () {--%>
    <%--//        var files = this.files;--%>
    <%--//        if (files && files.length > 0) {--%>
    <%--//--%>
    <%--//            mui.each(document.querySelectorAll(".img-view"), function () {--%>
    <%--//                $selectImg.removeChild(this);--%>
    <%--//            });--%>
    <%--//--%>
    <%--//            for (var i = 0; i < files.length; i++) {--%>
    <%--//                var file = files[i];--%>
    <%--//                var reader = new FileReader();--%>
    <%--//--%>
    <%--//                reader.readAsDataURL(file);--%>
    <%--//                reader.onload = function (e) {--%>
    <%--//--%>
    <%--//                    var $myin = document.createElement("div");--%>
    <%--//                    $myin.className = "img-item img-view";--%>
    <%--//--%>
    <%--//                    var e_img = document.createElement("img");--%>
    <%--//                    e_img.src = this.result;--%>
    <%--//                    $myin.appendChild(e_img);--%>
    <%--//--%>
    <%--//                    $selectImg.appendChild($myin);--%>
    <%--//                };--%>
    <%--//            }--%>
    <%--//        }--%>
    <%--//    });--%>

    <%--$("#archiveName").keyup(function () {--%>
    <%--var val = $(this).val();--%>
    <%--if (val.length > 10) {--%>
    <%--$(this).val(val.substr(0, 10));--%>
    <%--mui.toast("请填写10个字符以内哦~");--%>
    <%--}--%>
    <%--});--%>

</script>
</body>

</html>

