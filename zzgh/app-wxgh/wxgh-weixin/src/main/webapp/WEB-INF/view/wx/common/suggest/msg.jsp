<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/8
  Time: 10:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <title>提案审核进度</title>
    <style>
        html, body {
            height: 100%;
        }

        #showUserPicker {
            height: 100%;
            background: transparent;
            border: none;
        }

        .select-div {
            height: 100%;
        }

        .mui-content {
            padding-top: 21px;
        }

        .fadeIn {
            -webkit-animation-name: fadeIn; /*动画名称*/
            -webkit-animation-duration: 1s; /*动画持续时间*/
            -webkit-animation-iteration-count: 1; /*动画次数*/
            -webkit-animation-delay: 0s; /*延迟时间*/
        }

        .add-input {
            height: 100%;
            width: 100%;
            position: absolute;
            top: 0px;
            left: 0px;
            z-index: 99;
            opacity: 0;
        }
    </style>

</head>


<body>

<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" id="a-back"></a>--%>

    <%--<h1 class="mui-title">提案审核进度</h1>--%>
<%--</header>--%>
<div id="bg" class="mui-content"
     style="position: absolute;   top: 0px;   z-index: 10;   width: 100%;height: 100%;background-color: black;opacity: 0.4;display: none;"></div>

<div class="mui-input-group" id="add-url" style="position: absolute;top: 0px;display: none;z-index: 11">
    <div class="mui-input-row">
        <label>标题</label>
        <input type="text" class="mui-input-clear" placeholder="请输入标题" id="add-url-title">
    </div>
    <div class="mui-input-row">
        <label>链接</label>
        <input type="text" class="mui-input-clear" placeholder="请输入链接" id="add-url-link">
    </div>
    <div class="mui-button-row">
        <button class="mui-btn mui-btn-primary" id="add-sure-url">确认</button>
        &nbsp;&nbsp;
        <button class="mui-btn mui-btn-primary" id="add-cancel-url">取消</button>
    </div>
</div>

<div class="mui-input-group " id="add-txtimg"
     style="position: absolute;top: 0px;display: none;z-index: 11;width: 100%;margin-left: 0px;">
    <div class="mui-input-row">
        <label id="img-name">标题</label>
        <input type="text" class="mui-input-clear" placeholder="请输入标题" id="add-img-title">
    </div>

    <div class="mui-row ui-choose-img ui-margin-top-15" style="background-color: #fff;" id="add-div">
        <div class="mui-col-sm-3 mui-col-xs-3 add-div-inner">
            <img src="${home}/weixin/image/union/innovation/icon/icon_add1.png" class="add-img" style="padding: 10px;"/>
            <input type="file" class="add-input">
        </div>
    </div>

    <div class="mui-button-row">
        <button class="mui-btn mui-btn-primary" id="add-sure-img">确认</button>
        &nbsp;&nbsp;
        <button class="mui-btn mui-btn-primary" id="add-cancel-img">取消</button>
    </div>
</div>

</body>

</html>
<jsp:include page="/comm/mobile/scripts.jsp"/>

<%--<script src="${home}/weixin/script/lib/mobileFix.mini.js"></script>--%>
<script src="${home}/weixin/script/lib/exif.js"></script>
<script src="${home}/weixin/script/lib/lrz.js"></script>
<script>

    $(function () {

        $("#add-cancel-url").click(function () {
            $("#bg").hide();
            $("#add-url").hide();
            $("#add-url input").val("");
        });
        $("#add-cancel-img").click(function () {
            $("#bg").hide();
            $("#add-txtimg").hide();
            $("#add-txtimg input").val("");
        });
        $("#add-sure-url").click(function () {
            var name = $("#add-url-title").val();
            var link = $("#add-url-link").val();
            if (!name || !link) {
                mui.toast("请把信息填写完整");
                return;
            }
            $.ajax({
                dataType: 'json',
                <%--url: '${home}/adminApply/publicity.json',--%>
                url: '${home}/wx/common/publicity/add_publicity_url.json',
                data: {
//                    action: 'addPublicityUrl',
                    type: 'url',
                    name: name,
                    content: link
                },
                success: function () {
                    backcall2()
                }
            });
            function backcall2() {
                mui.toast("添加成功");
                window.location.reload();
            }
        });
        $("#add-sure-img").click(function () {
            var name = $("#add-img-title").val();

            if (!name) {
                mui.toast("请把信息填写完整");
                return;
            }
            var imgList = "";
            var $imgList = $("#add-div img.haveImg");
            for (var i = 0; i < $imgList.length; i++) {
                var url = $($imgList[i]).attr("src");
                imgList = imgList + url + "-cut//cut-";
            }
            $.ajax({
                type: "post",
                dataType: "json",
                <%--url: "${home}/adminApply/publicity.json",--%>
                url: '${home}/wx/common/publicity/add_publicity.json',
                data: {
//                    action: "addPublicity",
                    imgs: imgList,
                    name: name
                },
                success: function (result) {
                    callback(result);
                }
            });
            function callback(result) {
                if ("success" == result.msg) {
                    mui.toast("添加成功");
                    window.location.reload();
                } else {
                    mui.toast("添加失败");
                }
            }
        });

    });
</script>
<script type="text/javascript">
    //启用双击监听
    mui.init({
        gestureConfig: {
            doubletap: true
        },
        subpages: [{
            url: 'msg/index.html',
            id: 'msg/index.html',
            styles: {
                top: '0px',
                bottom: '0px'
            }
        }]
    });

    var contentWebview = null;
    document.querySelector('header').addEventListener('doubletap', function () {
        if (contentWebview == null) {
            contentWebview = plus.webview.currentWebview().children()[0];
        }
        contentWebview.evalJS("mui('#pullrefresh').pullRefresh().scrollTo(0,0,100)");
    });
</script>
<script>
    $(document).ready(function () {

        var div_height = $("body").width() / 4;
        $(".add-div-inner").height(div_height);

        $("body").on("change", ".add-input", function () {
            var $file = $(this);
            lrz(this.files[0], {
                width: 400
            }, function (results) {
                $file.siblings("img").attr("src", results.base64).addClass("haveImg");
                $("#add-div").append('<div class="mui-col-sm-3 mui-col-xs-3 add-div-inner"  style="height:' + div_height + 'px;"><img src="${home}/weixin/image/union/innovation/icon/icon_add1.png" class="add-img" style="padding: 10px;"/><input type="file" class="add-input"></div>');
            });
        });
    });
</script>
