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
    </style>
</head>
<body>
<%--<header class="mui-bar mui-bar-nav">
  <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
  <h1 class="mui-title">发布作品</h1>
</header>--%>
<div class="mui-content mui-slider" style="height: 100%;">
    <div class="myform">
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
            <div class="file" style="margin-top: 5px">
                <%--<div class="mui-input-row file">--%>
                <label>选择上传作品</label>
                <input id="uploadfile" type="file" accept="image/*" name="uploadfile" multiple="multiple"/>
                <input id="uploadvideo" type="file" accept="video/*" name="uploadvideo" style="display: none"/>
                <%--<input id="uploadvideo" type="file" multiple="true" name="uploadfile" multiple="multiple"/>--%>
            </div>
            <span id="videoShow"></span>

            <div class="img_info" style="margin-left: 20px;padding-top: 5px;">
                <%--<img src="../dimg/d1.jpg" style="height: 80px;width: 80px;">--%>
                <%--<img src="../dimg/d1.jpg" style="height: 80px;width: 80px;">--%>
            </div>
        </ul>
        <div class="mui-input-row">
            <label>作品说明:</label>
        </div>
        <textarea class="remark" rows="5" placeholder="请输入作品说明"></textarea>

    </div>


    <button type="button" class="sure_btn mui-btn mui-btn-blue" style="position: fixed;bottom: 0px;margin-bottom: 0px;">
        保存
    </button>


</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>

<script type="text/javascript" src="${home}/libs/upload/ajaxfileupload.js"></script>
<script type="text/javascript">
    mui.init({
        swipeBack: true //启用右滑关闭功能
    });

    var homePath = "${home}";

    var workName;
    var workType;
    var workRemark;
    var files;
    var video;

    $(function () {

        //缩略图
        $("#uploadfile").change(function () {

            if (typeof (FileReader) != "undefined") {
                var dvPreview = $(".img_info");
                dvPreview.html("");
                var regex = /(.jpg|.jpeg|.gif|.png|.bmp)$/;
                $($(this)[0].files).each(function () {
                    var file = $(this);
                    if (regex.test(file[0].name.toLowerCase())) {
                        var reader = new FileReader();
                        reader.onload = function (e) {
                            var img = $("<img />");
                            img.attr("src", e.target.result);
                            dvPreview.append(img);
                        }
                        reader.readAsDataURL(file[0]);
                    } else {
                        mui.toast("您选择的上传文件不是有效的图片文件");
                    }
                });
            } else {
                mui.toast("暂不支持缩略显示");
            }
        });

        $("#uploadvideo").change(function () {
            var filePath = $(this).val();
            var regex = /(.avi|.rmvb|.rm|.asf|.div|.mpg|.mpeg|.mpe|.wmv|.mp4|.mkv|.vob)$/;
            var fname = filePath.substr(filePath.indexOf("."));

            if (regex.test(fname.toLowerCase())) {
                $("#videoShow").html("");
                $("#videoShow").html("您已上传1个视频文件").show();
            } else {
                mui.toast("您未上传文件，或者您上传文件类型有误");
                $("#videoShow").html("");
                $("#videoShow").html("您未上传文件，或者您上传文件类型有误！").show();
                return false;
            }
        });

        //选择cat
        $('.cat').change(function () {
//      alert($(this).children('option:selected').attr("data"));
            workType = $(this).children('option:selected').val();//这就是selected的值
//      alert($(this).children('option:selected').val());
            //1图片 2视频
//            alert(workType)
            if (workType == "2") {
                $('#uploadfile').hide();
//        $('#uploadvideo').removeClass('mui-hidden');
                $('#uploadvideo').show();
            } else {
                $('#uploadfile').show();
//        $('#uploadvideo').addClass('mui-hidden');
                $('#uploadvideo').hide();
            }
        });

        var loading = new ui.loading('加载中...')

        $('.sure_btn').on('tap',function () {
//            var mask = mui.createMask();//callback为用户点击蒙版时自动执行的回调；
//            mask.show();//显示遮罩
            loading.show()

            workName = $('.work_name').val();
            workRemark = $('.remark').val();

            files = document.getElementById('uploadfile').files;


            if (!workName) {
                mui.alert("请填写作品名称");
                return;
            }
            if (workType == null || workType == "") {
                mui.alert("请选择作品类别");
                return;
            }
            if (workType == 1) {
                if (!files || files.length <= 0) {
                    mui.alert("请上传图片作品");
                    return;
                } else {
                    if (files.length > 2) {
                        mui.alert("上传的文件数量超过2个了！请重新选择！");
                        return;
                    }
                }
            }
                if (workType == 2) {
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
                                mui.toast("作品上传成功");
                                window.location.href = '${home}/wx/party/beauty/index.html';
                            } else {
                                loading.hide();
                                mui.toast("作品上传失败！");
                            }
                        }
                    });
                } else {
                    var url = "${home}/beauty/add/add.json";
                    $.ajaxFileUpload({
                        url: url,
                        dataType: 'json',
                        secureuri: false,
                        data: {
                            name: workName,
                            type: workType,
                            remark: workRemark
                        },
                        fileElementId: 'uploadfile',
                        success: function (data) {
                            if (data.ok) {
                                mui.toast("作品上传成功");
                                window.location.href = '${home}/wx/party/beauty/index.html';
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
