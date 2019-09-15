<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/11
  Time: 10:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <meta charset="utf-8">

    <title>发布比赛</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>

    <link href="${home}/libs/mui-v3.7.0/css/mui.poppicker.css" rel="stylesheet"/>
    <link href="${home}/libs/bootstrap-3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${home}/libs/font-awesome-4.7.0/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="${home}/libs/bootstrap-datetimepicker/css/bootstrap-datetimepicker.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${home}/style/common/ui-pagination.css"/>
    <link rel="stylesheet" href="${home}/libs/umeditor1.2.3-utf8-jsp/themes/default/css/umeditor.css"/>
    <!--[if lt IE 9]>
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css" rel="stylesheet"/>


    <style>
        ul mui-table-view-cell.li {
            height: 50px;
            line-height: 29px;
        }

        #btn-save {
            color: green;
        }

        /*placeholder字体颜色*/

        ::-webkit-input-placeholder {
            /* WebKit browsers */
            text-align: right;
        }

        :-moz-placeholder {
            /* Mozilla Firefox 4 to 18 */
            text-align: right;
        }

        ::-moz-placeholder {
            /* Mozilla Firefox 19+ */
            text-align: right;
        }

        :-ms-input-placeholder {
            /* Internet Explorer 10+ */
            text-align: right;
        }

        input {
            color: #999999;
        }

        .ui-choose-img div img {
            width: 100%;
            height: 100%;
        }

        .ui-choose-img div span {
            display: none;
        }

        .mui-table-view-cell select{
            opacity: 1;
            position:static;
        }
    </style>

</head>

<body class="mui-scroll-wrapper">

<div role="form" class="mui-scroll">
    <ul class="mui-table-view ul-1" style="margin-top: 0px;">
        <li class="mui-table-view-cell">
            <div class="mui-input-row">
                <label for="name">比赛名称</label>
                <input type="text" class="mui-text-right" placeholder="请输入名称" id="name" name="name">
            </div>
        </li>
        <li class="mui-table-view-cell" style="height: 128px;">
            <div class="mui-input-row" style="height: 100%;">
                <label style="line-height: 2.1;" for="">封面</label>

                <div style="width: 65%;height: 100%;float: left;" class="ui-choose-img mui-row">
                    <div class="" id="chooseBtn">
                        <%--<img src="${home}/image/common/icon_add1.png"/>--%>
                    </div>
                    <%--<div class="ui-img-div mui-col-sm-3 mui-col-xs-3 mui-hidden" id="previewImg">--%>
                        <%--<img src=""/>--%>
                    <%--</div>--%>
                </div>
            </div>
        </li>

        <li class="mui-table-view-cell">
            <div class="mui-input-row">
                <label for="tel">联系电话</label>
                <input maxlength="13" type="text" class="mui-text-right" id="tel" name="tel"
                       value="${user.mobile}">
            </div>
        </li>
        <li class="mui-table-view-cell">
            <div class="mui-input-row">
                <label for="startTime">开始时间</label>
                <input readonly="readonly" type="text" class="mui-text-right" id="startTime" name="startTime"
                       placeholder="请选择开始时间">
            </div>
        </li>
        <li class="mui-table-view-cell">
            <div class="mui-input-row">
                <label for="endTime">结束时间</label>
                <input readonly="readonly" type="text" class="mui-text-right" id="endTime" name="endTime"
                       placeholder="请选择结束时间">
            </div>
        </li>

        <li class="mui-table-view-cell">
            <div class="mui-input-row">
                <label for="rule">比赛规则</label>
            </div>
            <div class="ui-textarea-div">
                <textarea name="rule" id="rule" rows="4" maxlength="600" placeholder="请输入比赛规则"></textarea>
            </div>
        </li>

        <li class="mui-table-view-cell" style="height: initial;">
            <div class="mui-input-row">
                <label>比赛内容</label>
            </div>
            <div class="ui-textarea-div">
                <textarea name="content" id="content" rows="4" maxlength="600" placeholder="请输入比赛内容"></textarea>
            </div>
        </li>

        <li class="mui-table-view-cell" style="height: initial;">
            <div class="mui-input-row">
                <label>作品类型</label>
                <select name="worksType" class="mui-text-right" style="text-align: right;  float: right">
                    <option value="1">照片</option>
                    <option value="2">視頻</option>
                </select>
            </div>
        </li>

    </ul>
    <button id="btn-submit" class="mui-btn-blue mui-btn-blue mui-btn-block"
            style="padding: 8px 0;z-index: 2;border-radius: 0px;margin-bottom: 0px;">发布
    </button>

</div>

</body>

</html>


<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript" ></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.poppicker.js"></script>
<script src="${home}/libs/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<%--<script src="${home}/libs/upload/ajaxfileupload.js"></script>--%>
<script src="${home}/libs/bootstrap-datetimepicker/js/bootstrap-datetimepicker.min.js"></script>
<%--<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=uOy28xEGgK5vrqIG4gGTw7PgAmu667Ua"></script>--%>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<script>
    mui.init();
    wxgh.init('${weixin}')

    var $upload = wxgh.imagechoose($('#chooseBtn'),{
            wx: {count: 1}, clear: true
        }
    );

//    var $prevImg = $('#previewImg')
//    $('#chooseBtn').on('tap', function () {
//        wx.chooseImage({
//            count: 1, // 默认9
//            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
//            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
//            success: function (res) {
//                var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
//                if (localIds && localIds.length > 0) {
//                    $prevImg.removeClass('mui-hidden')
//                    $prevImg.find('img').attr('src', localIds[0])
//                }
//            }
//        });
//    })

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    var nowDate = new Date();
    var options = {
        type: 'date',
        beginYear: nowDate.getFullYear() - 90,
        endYear: nowDate.getFullYear(),
        value: nowDate.format('yyyy-MM-dd')
    }

    document.getElementById("startTime").addEventListener("tap", function () {
        var dtPicker = new mui.DtPicker(options);
        dtPicker.show(function (selectItems) {
            console.log(selectItems.text);
            document.getElementById("startTime").value = selectItems.text;
        })
    });

    document.getElementById("endTime").addEventListener("tap", function () {
        var startTime = document.getElementById("startTime").value;
        if (!startTime) {
            ui.alert("请先选择开始时间");
            return;
        }
        var dtPicker = new mui.DtPicker(options);
        dtPicker.show(function (selectItems) {
            console.log(selectItems.text);
            // 获取某个时间格式的时间戳

            var startTimestamp = Date.parse(new Date(startTime));
            startTimestamp = startTimestamp / 1000;
            //2014-07-10 10:21:12的时间戳为：1404958872
            console.log(startTimestamp + "的时间戳为：" + startTimestamp);

            var endTimestamp = Date.parse(new Date(selectItems.text));
            endTimestamp = endTimestamp / 1000;
            //2014-07-10 10:21:12的时间戳为：1404958872
            console.log(endTimestamp + "的时间戳为：" + endTimestamp);

            if (startTimestamp >= endTimestamp) {
                ui.alert("活动结束时间要大于开始时间哦~");
                return;
            }

            document.getElementById("endTime").value = selectItems.text;
        })
    });

    $("#coverFile").change(function () {
        var src = getObjectURL(this.files[0]);
        $("#img-cover").attr("src", src);
    })

    function getObjectURL(file) {
        var url = null;
        if (window.createObjectURL != undefined) {
            url = window.createObjectURL(file)
        } else if (window.URL != undefined) {
            url = window.URL.createObjectURL(file)
        } else if (window.webkitURL != undefined) {
            url = window.webkitURL.createObjectURL(file)
        }
        return url
    };

    document.getElementById("btn-submit").addEventListener("tap", function () {
//        var localId = $prevImg.find('img').attr('src')
//        if(!localId){
//            alert("请选择图片")
//            return;
//        }
//        wx.uploadImage({
//            localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
//            isShowProgressTips: 0,// 默认为1，显示进度提示
//            success: function (res) {
//                var mediaIds = res.serverId; // 返回图片的服务器端ID

        var name = $("#name").val();
        var tel = $("#tel").val();
        var startTime = $("#startTime").val();
        var endTime = $("#endTime").val();
        var content = $("#content").val();
        var rule = $("#rule").val();
        var worksType = $("select[name=worksType]").val()

        if (!rule || !name || !tel || !startTime || !endTime || !content || !worksType) {
            ui.alert("请把信息填写完整");
            return;
        }

        var nowDate = new Date().format('yyyy-MM-dd')
        if (startTime < nowDate) {
            ui.alert('开始时间不能小于当前时间哦')
            return
        }
        if (startTime >= endTime) {
            ui.alert('开始时间不能大于或等于结束时间哦')
            return
        }

        $upload.upload(function (mediaIds) {

            var data = {
                name: name,
                tel: tel,
                startTime: startTime,
                endTime: endTime,
                content: content,
                rule: rule,
                worksType: worksType
            }

            if (mediaIds) {
                data['cover'] = mediaIds.toString()
            } else {
                ui.alert('请选择图片')
                return ;
            }

            var url = homePath + "/wx/admin/union/photo/match/add.json";
                var loading = ui.loading('加载中...')
                loading.show();
                wxgh.request.post(url, data, function (res) {
                    ui.alert("发布成功", function () {
                        wx.closeWindow();
                        window.location.reload()
                    });
                })

        })
    })

</script>
