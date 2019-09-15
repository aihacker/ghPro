<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/19
  Time: 14:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <title>评价</title>
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

<body>

<div class="mui-content">
    <div class="ui-fixed-bottom">
        <button class="mui-btn mui-btn-primary" id="addBtn">发表评价</button>
    </div>

    <div class="mui-content-padded" style="background-color: white;margin: 0px;">
        <textarea style="border: none;margin: 10px 0px 0px 0px;" rows="5" id="content"
                  placeholder="亲，点击这里可以输入您的评价哦~"></textarea>

        <div id="chooseBtn"></div>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"></jsp:include>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script type="text/javascript">

    $(function () {
        wxgh.wxInit('${weixin}')
        //添加照片
        var img = wxgh.imagechoose($("#chooseBtn"));

        //初始化info队列
        var load = ui.loading('请稍候...')

        $("#content").keyup(function () {
            var val = $(this).val();
            if (val.length > 100) {
                $(this).val(val.substr(0, 100));
                mui.toast("请填写100个字符以内哦~");
            }
        });

        $("#addBtn").on("tap", function () {
            var info = {}
            info["content"] = $("#content").val();
            if (!info.content) {
                ui.alert('请输入评论内容！')
                return
            }
            info["actId"] = ${param.id};
            var d = '${param.id}'

            load.show()
            //上传图片
            img.upload(function (mediaIds) {
                if (mediaIds) {
                    info['img'] = mediaIds.toString();
                }
                wxgh.request.post('../commit/api/add_comment.json', info, function (data) {
                    ui.showToast('发布成功', function (data) {
                        mui.openWindow(homePath + '/wx/manage/commit/act.html?id=' + d)
                    })
                })

            })
        })
    })

</script>
</body>

</html>
