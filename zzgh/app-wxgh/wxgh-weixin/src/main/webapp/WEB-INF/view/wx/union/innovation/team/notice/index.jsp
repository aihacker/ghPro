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
    <meta charset="UTF-8">
    <title>发布公告</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>

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

        .mui-table-view-cell select {
            opacity: 1;
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

    <ul class="mui-table-view" style="margin-top: 0px;">

        <li class="mui-table-view-cell mui-input-row">
            <label class="ui-li-label ui-text-info" style="padding-left: 0px;width: 40%;">公告标题</label>
            <input id="title" name="title" type="text" placeholder="20个字符以内" style="width: 60%;"/>
        </li>
        <li class="mui-table-view-cell mui-input-row">
            <label class="ui-li-label ui-text-info" style="padding-left: 0px;width: 40%;">公告类型</label>
            <select style="width: 60%;" id="select-type">
                <option value="1">活动公告</option>
                <option value="2">招募公告</option>
            </select>
        </li>

        <li class="mui-table-view-cell mui-input-row" id="li-act">
            <label class="ui-li-label ui-text-info" style="padding-left: 0px;width: 40%;">活动选择</label>
            <select style="width: 60%;" class="mui-ellipsis" id="select-act">
                <option value="">请选择活动</option>
                <c:forEach items="${actlist}" var="item">
                    <option value="${item.id}">${item.name}</option>
                </c:forEach>
            </select>
        </li>
    </ul>
    <div class="mui-content-padded" style="background-color: white;margin: 0px;">
        <textarea style="border: none;margin: 10px 0px 0px 0px;" rows="5" id="content"
                  placeholder="点击这里可以输入公告详情~" name="content"></textarea>

        <div class="mui-row ui-choose-img ui-margin-top-15">
            <div class="mui-col-sm-3 mui-col-xs-3" id="chooseBtn">
                <img src="${home}/image/common/icon_add1.png"/>
            </div>
            <div class="ui-img-div mui-col-sm-3 mui-col-xs-3 mui-hidden" id="previewImg">
                <img src=""/>
            </div>
        </div>

    </div>

    <div class="ui-fixed-bottom">
        <button class="mui-btn mui-btn-primary mui-btn-block ui-btn" id="addBtn">发布</button>
    </div>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript"></script>
<script type="text/javascript">

    var homePath = "${home}";

    var type = "";
    var pid = "";
    var groupId = "${param.groupId}";

    wxgh.wxInit('${weixin}')

    mui.init()

    var $prevImg = $('#previewImg')
    $('#chooseBtn').on('tap', function () {
        wx.chooseImage({
            count: 1, // 默认9
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                if (localIds && localIds.length > 0) {
                    wxgh.get_wx_img(localIds[0], function (src) {
                        $prevImg.removeClass('mui-hidden')
                        $prevImg.find('img').attr('src', src)
                    });
                }
            }
        });
    })

    window.onload = function () {
//        var options = {
//            wx: {
//                count: 1,
//                sizeType: ['original', 'compressed'],
//                sourceType: ['album', 'camera'],
//            },
//            clear: false
//        }
//        var $upload = wxgh.imagechoose($("#chooseBtn"), options);

        //发布
        document.getElementById("addBtn").addEventListener('tap', function () {
            var self = this;
            var content = document.getElementById("content").value;
            if (content.trim() == "") {
                mui.toast("内容不能为空哦");
                return;
            }

            var title = document.getElementById("title").value;

            if (title.trim() == "") {
                mui.toast("标题不能为空哦");
                return;
            }


            var type = $("#select-type option:selected").attr("value");

            var pid = $("#select-act option:selected").attr("value");
            if (type == 2)
                pid = groupId;

            if (!pid) {
                mui.alert("请选择活动");
                return;
            }

            var upload_data = {
                content: content,
                title: title,
                groupId: '${param.id}',
                type: type,
                pid: pid
            };

            if (!this.loading) {
                this.loading = ui.loading('请稍候...')
            }

            this.loading.show()

            var localId = $prevImg.find('img').attr('src')

//            $upload.upload(function (mediaIds) {
            wx.uploadImage({
                localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
                isShowProgressTips: 0,// 默认为1，显示进度提示
                success: function (res) {
                    var mediaIds = res.serverId; // 返回图片的服务器端ID

                    if (mediaIds) {
                        console.log(mediaIds)
                        upload_data['img'] = mediaIds.toString()
                    }
                    var url = homePath + '/wx/union/innovation/team/notice/add.json'
                    mui.post(url, upload_data, function (res) {
                        self.loading.hide()
                        if (res.ok) {
                            ui.showToast('发布成功', function () {
                                window.history.go(-1);
                            })
                        } else {
                            mui.toast(res.msg)
                        }
                    }, 'json')
                }
            })
        });
    }

    $(function () {
        $("input[name=title]").keyup(function () {
            var val = $(this).val();
            if (val.length > 20) {
                $(this).val(val.substr(0, 20));
                mui.toast("请填写100个字符以内哦~");
            }
        });
    })

    $("#select-type").change(function () {
        var $this = $(this);
        var $firstLi = $("#li-act option:first-child");
        type = $this.children("option:selected").attr("value");
        var $li_act = $("#li-act");
        if (type == 1) {
            $li_act.show();
            $firstLi.attr("selected", "selected");
            pid = $firstLi.attr("value");
        } else {
            $li_act.hide();
            pid = "${param.id}";
        }
    });

    $("#li-act").change(function () {
        var $this = $(this);
        pid = $this.children("option:selected").attr("value");
    });

</script>
</body>

</html>

