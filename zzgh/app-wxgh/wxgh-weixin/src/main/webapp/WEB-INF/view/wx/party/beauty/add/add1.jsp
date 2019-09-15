<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/17
  Time: 14:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>发布作品</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .footer {
            /*position:absolute;
            bottom:0;*/
            background-color: white;
            z-index: 99999;
            position: fixed;
            bottom: 0;
            left: 0;
            width: 100%;
            _position: absolute;
            /* for IE6 */
            _top: expression(documentElement.scrollTop + documentElement.clientHeight-this.offsetHeight);
            /* for IE6 */
            overflow: visible;
        }

        .add_btn, .sure_btn {
            color: #fff;
            width: 100%;
            height: 44px;
        }

        ul {
            margin-top: 0;
        }

        .ul_group {
            margin-bottom: 0px;
            /*height: 400px;*/
        }

        .remark {
            margin-bottom: 40px;
        }

        .img_info img {
            width: 80px;
            height: 80px;
            margin-right: 1px;
            margin-left: 1px;
            padding-bottom: 10px;
        }

        .file {
            position: relative;
            display: inline-block;
            background: #D0EEFF;
            border: 1px solid #99D3F5;
            border-radius: 4px;
            padding: 4px 12px;
            overflow: hidden;
            color: #1E88C7;
            text-decoration: none;
            text-indent: 0;
            line-height: 20px;
        }

        .file input {
            position: absolute;
            font-size: 100px;
            right: 0;
            top: 0;
            opacity: 0;
        }

        .file:hover {
            background: #AADFFD;
            border-color: #78C3F3;
            color: #004974;
            text-decoration: none;
        }


        .ui-choose-img  img{
            height: initial;
        }
        .feedback .image-list {
            width: 100%;
            height: auto;
            background-size: cover;
            padding: 10px 10px;
            overflow: hidden;
        }
        .image-list div{
          margin-bottom:25px;
            margin-top: 5px;
        }
    </style>

</head>
<body>
<%--<header class="mui-bar mui-bar-nav">
  <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
  <h1 class="mui-title">发布作品</h1>
</header>--%>
<div class="mui-conten mui-scroll-wrapper" style="height: 100%;">
    <div class="myform mui-slider">
        <ul class="mui-input-group" style="margin-bottom: 10px;">
            <div class="mui-input-row">
                <label>作品名称:</label>
                <input class="mui-input-clear work_name" type="text" placeholder="请输入作品名称">
            </div>
            <div class="mui-input-row">
                <label>作品分类:</label>
                <select class="cat">
                    <option value="">------请选择------</option>
                    <option value="1">图片</option>
                    <option value="2">视频</option>
                </select>
            </div>
            <div class="mui-input-row">
                <label>发布人:</label>
                <input class="mui-input-clear" type="text" value="${user.name}" disabled="disabled">
            </div>
        </ul>
        <ul class="mui-input-group ul_group">

            <div id="imgdiv">
                <%--<ul class="mui-input-group" style="margin-top: 0px;">--%>
                        <li class="mui-input-group" style="height: 100%px; ">
                            <div style="padding-bottom: 50px;">
                                <label style="line-height: 2.1;" for="">作品图片：</label>

                                <div class="mui-row ui-choose-img image-list" style="width: 100%;">
                                    <div class="mui-col-sm-3 mui-col-xs-3" id="chooseBtn">
                                        <img src="${home}/image/common/icon_add1.png"/>
                                    </div>
                                </div>
                            </div>
                        </li>
                <%--</ul>--%>
            </div>

            <div id="videodiv" style="display: none">
                <div class="file" style="margin-top: 5px">
                    <label>选择上传视频作品</label>
                    <input id="uploadvideo" type="file" accept="video/*" name="uploadvideo"/>
                </div>
                <span id="videoShow"></span>
            </div>
        </ul>
        <div class="mui-input-row">
            <label>作品说明:</label>
        </div>
        <textarea class="remark" rows="5" placeholder="请输入作品说明"></textarea>

    </div>
    <button type="button" class="sure_btn mui-btn mui-btn-blue"
            style="position: fixed;bottom: 0px;margin-bottom: 0px;z-index: 999;">
        保存
    </button>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="${home}/libs/upload/ajaxfileupload.js"></script>
<script type="text/javascript">
    var homePath = "${home}";

    wxgh.wxInit('${weixin}')

    mui.init({
        swipeBack: true //启用右滑关闭功能
    });

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    var workName;
    var workType;
    var workRemark;
    var video;

    $(function () {
//        wxgh.chooseImg.init();

        var $upload = wxgh.imagechoose($('#chooseBtn'))

        $("#uploadvideo").change(function () {
            var src = getObjectURL(this.files[0]);
            var ps = '<video src="' + src + '" style="height: 58px;width: 58px;"></video>';
            $("#videoShow").html(ps);
        });
        //建立一個可存取到該file的url
        function getObjectURL(file) {
            var url = null;
            if (window.createObjectURL != undefined) { // basic
                url = window.createObjectURL(file);
            } else if (window.URL != undefined) { // mozilla(firefox)
                url = window.URL.createObjectURL(file);
            } else if (window.webkitURL != undefined) { // webkit or chrome
                url = window.webkitURL.createObjectURL(file);
            }
            return url;
        }

        //选择cat
        $('.cat').change(function () {
            workType = $(this).children('option:selected').val();//这就是selected的值
            //1图片 2视频
            if (workType == "2") {
                $('#imgdiv').hide();
                $('#videodiv').show();
            } else {
                $('#imgdiv').show();
                $('#videodiv').hide();
            }
        });

        var loading = ui.loading('提交中...');

        $('.sure_btn').on('tap', function () {

            workName = $('.work_name').val();
            workRemark = $('.remark').val();

            if (!workName) {
                mui.alert("请填写作品名称");
                return;
            }
            if (workType == null || workType == "") {
                mui.alert("请选择作品类别");
                return;
            }

            loading.show();

            if (workType == 1) {
                var upload_data = {
                    name: workName,
                    type: workType,
                    remark: workRemark
                }

                //选择图片
                $upload.upload(function (mediaIds) {
                    console.log(mediaIds)
                    if (mediaIds && mediaIds.length > 0) {
                        var url = homePath + '/beauty/add/add.json';
                        upload_data['files'] = mediaIds.toString()
                        mui.post(url, upload_data, function (res) {
                            loading.hide();
                            if (res.ok) {
                                ui.showToast('添加成功', function () {
                                    mui.openWindow(homePath + '/wx/party/beauty/index.html');
                                })
                            } else {
                                mui.toast(res.msg)
                            }
                        }, 'json')
                    } else {
                        loading.hide();
                        mui.toast('请上传图片');
                    }
                });

            } else {
                var url2 = "${home}/beauty/add/add2.json";
                $.ajaxFileUpload({
                    url: url2,
                    dataType: 'json',
                    secureuri: false,
                    data: {
                        name: workName,
                        type: workType,
                        remark: workRemark
                    },
                    fileElementId: 'uploadvideo',
                    success: function (data) {
                        if (data.ok) {
                            ui.showToast('添加成功， 您的作品将被审核', function () {
                                mui.openWindow(homePath + '/wx/party/beauty/index/html');
                            });
                        } else {
                            loading.hide();
                            mui.toast("作品上传失败！");
                        }
                    }
                });
            }

        });
    });
</script>
</body>
</html>
