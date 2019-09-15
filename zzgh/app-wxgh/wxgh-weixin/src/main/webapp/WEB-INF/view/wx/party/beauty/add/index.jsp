<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/5
  Time: 14:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>作品发布</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/webuploader/css/webuploader.css">
    <style>
        .mui-table-view-cell.mui-active,
        .mui-navigate-right.mui-active {
            background-color: #fff;
        }

        .ui-textarea-div {
            margin-top: 0;
        }

        .ui-select-div,
        .ui-input-div {
            position: relative;
        }

        .ui-select-div select {
            position: absolute;
            width: 100%;
            left: 0;
            margin-bottom: 0;
            padding: 0;
            opacity: 0;
            z-index: 9;
        }

        .ui-select-div small {
            float: right;
            margin-right: 18px;
            color: #777;
            position: relative;
            top: 2px;
        }

        .ui-input-div span {
            position: relative;
            z-index: 9;
            background-color: #fff;
        }

        .ui-input-div input {
            position: absolute;
            left: 0;
            border: none;
            top: -4px;
            margin: 0;
            font-size: 14px;
            height: 35px;
            padding: 5px 15px 5px 10px;
            text-align: right;
        }

        .ui-video-div {
            background-color: #fff;
            margin-top: 15px;
            padding: 10px 15px;
        }

        .video-item .video-name {
            font-size: 14px;
            color: #777;
        }

        /*
        .video-item {
            position: relative;
        }

        .video-item .sweet-tip {
            position: absolute;
            right: 0;
            top: 2px;
            color: #777;
        }

        .video-item .sweet-tip small {
            position: relative;
            top: -2px;
        }
        */
        .upload-list {
            padding: 4px 0;
        }
    </style>
    <link rel="stylesheet" href="${home}/libs/webuploader/css/d.css"/>
</head>
<body>

<div class="mui-content">
    <div class="ui-body">
        <ul class="mui-table-view" style="margin: 0;">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    <div class="ui-select-div">
                        <span>作品类型：</span>
                        <select name="type">
                            <option value="1">图片</option>
                            <option value="2">视频</option>
                        </select>
                        <small>图片</small>
                    </div>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    <div class="ui-input-div">
                        <span>名&nbsp;&nbsp;称：</span>
                        <input name="name" type="text" placeholder="请输入"/>
                    </div>
                </a>
            </li>
        </ul>
        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell">
                <span>简&nbsp;&nbsp;介：</span>
                <div class="ui-textarea-div">
                    <textarea name="remark" rows="2" maxlength="200"></textarea>
                </div>
            </li>
        </ul>
    </div>

    <div class="ui-video-div">
        <span id="uploadTitle">上传图片：</span>
        <div id="uploader">
            <div>
                <div id="picker">选择图片</div>
            </div>
            <div class="upload-list" style="padding-bottom: 20px;">
            </div>
        </div>
    </div>

    <div class="ui-fixed-bottom">
        <button data-loading-text="发布中..." id="addBtn" type="button"
                class="mui-btn mui-btn-primary ui-btn mui-btn-block">提交审核
        </button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/webuploader/js/webuploader.js"></script>

<script type="text/javascript">
    var _self = homePath + '/wx/party/beauty/add'
    $(function () {
        initProgressbar($('.upload-list .mui-progressbar'))

        wxgh.autoTextarea($('.ui-textarea-div textarea'))

        var videoAccept = {
            title: 'Videos',
            extensions: '',
            mimeTypes: 'video/*'
        }
        var imgAccept = {
            title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/*'
        }

        var $fileList = $('.upload-list')
        var uploader = WebUploader.create({
            swf: '${home}/libs/webuploader/js/Uploader.swf',
            server: '',
            pick: '#picker',
            resize: false,
            fileNumLimit: 5,
            fileSingleSizeLimit: 50 * 1024 * 1024,
            compress: false,
            accept: {}
        })

        var $uploadTitle = $('#uploadTitle')
        var $picker = $('#picker')
        var fileType = 1;

        var $addBtn = $('#addBtn')

        mui.later(function () {
            $picker.find('input[name=file]').attr('accept', imgAccept.mimeTypes)
            uploader.option('accept', imgAccept)
        }, 200)

        $('select[name=type]').on('change', function () {
            var type = $(this).val()
            var txt = $(this).find('option[value=' + type + ']').text()
            var title, btntxt, mime, accept, fileLimit
            uploader.reset()
            $fileList.empty()
            if (type == 1) {
                title = '上传图片'
                btntxt = '选择图片'
                mime = 'image/*'
                accept = imgAccept
                fileLimit = 5
            } else if (type == 2) {
                title = '上传视频'
                btntxt = '选择视频'
                mime = 'video/*'
                accept = videoAccept
                fileLimit = 1
            }
            $uploadTitle.text(title)
            $picker.find('.webuploader-pick').text(btntxt)
            $picker.find('input[name=file]').attr('accept', mime)
            $(this).next().text(txt)
            uploader.option('accept', accept)
            uploader.option('fileNumLimit', fileLimit)
            fileType = type
        })

        var loading = ui.loading('发布中...')
        var workId;

        uploader.on('fileQueued', function (file) {
            console.log(uploader.getFiles())

//            var $item = $('<div data-id="' + file.id + '" class="video-item">' +
//                '<span class="video-name">' + file.name + '</span>' +
//                '<p class="mui-progressbar mui-progressbar-success" data-progress="0"></span></p>' +
//                '<span class="sweet-tip"><span class="mui-icon mui-icon-closeempty"></span><small>删除</small></span></div>')

            // new item start
            var $item = $('<div data-id="' + file.id + '"  class="file-item thumbnail d-image video-item">' +
            '<img >' +
            '<p class="mui-progressbar mui-progressbar-success" data-progress="0"></p>' +
            '<div class="sweet-tip"><span class="mui-icon mui-icon-closeempty"></span><small>删除</small></div>' +
            '</div>');

            var $img = $item.find('img');
            uploader.makeThumb( file, function( error, src ) {
                if ( error ) {
                    $img.replaceWith('<span>名称：'+file.name+'，不能预览</span>');
                    return;
                }
                $img.attr( 'src', src );
            }, 130, 130 );
            // new item end

            $item.data('id', file.id)
            $fileList.append($item)
            $('.file-item').each(function (i){
                $(this).css("margin-bottom", "10px")
            })
            $fileList.find(".file-item:last-child").css("margin-bottom", "80px");

            initProgressbar($item.find('.mui-progressbar'))

            $item.on('tap', '.sweet-tip', function () {
                var $self = $(this)
                var $parent = $self.parent()
                var id = $parent.data('id')
                var $icon = $self.children('.mui-icon')
                if ($icon.hasClass('mui-icon-closeempty')) {
                    uploader.removeFile(id, true)
                    $parent.remove()
                } else if ($icon.hasClass('mui-icon-refreshempty')) {
                    uploader.retry()
                    loading.show()
                }
            })
        })
        uploader.on('uploadProgress', function (file, percentage) {
            mui($('.video-item[data-id=' + file.id + ']').find('.mui-progressbar')[0]).progressbar().setProgress(percentage)
        })
        uploader.on('error', function (type) {
            if (type == 'Q_EXCEED_NUM_LIMIT') {
                alert('上传文件不能超过' + (fileType == 1 ? 5 : 1) + '个哦！')
            } else if (type == 'F_EXCEED_SIZE') {
                alert('单个文件不能超过50M哦！')
            }
            console.log(type)
        })
        uploader.on('uploadSuccess', function (file) {
            var $item = $('.video-item[data-id=' + file.id + ']')
            $item.find('.sweet-tip .mui-icon').remove()
            $item.find('.sweet-tip small').addClass('ui-text-success').text('上传成功')
            var numbs = uploader.getStats()
            if (numbs.uploadFailNum == 0 && numbs.progressNum == 0 && numbs.queueNum == 0 && numbs.invalidNum == 0) {
                mui.getJSON(_self + "/success.json", {action: 'success', id: workId}, function (res) {
                    if (res.ok) {
                        workId = 0
                        loading.hide()
                        ui.showToast('发布成功！请等待结果', function () {
                            mui.back()
                        })
                    }
                })
            }
        })
        uploader.on('uploadAccept', function (object, ret) {
            return ret.ok
        })
        uploader.on('uploadError', function (file, reason) {
            loading.hide()
            var $item = $('.video-item[data-id=' + file.id + ']')
            $item.find('.sweet-tip .mui-icon')
                .removeClass()
                .addClass('mui-icon mui-icon-refreshempty')
            $item.find('.sweet-tip small').addClass('ui-text-danger').text('失败，点击重试')
        })
        uploader.on('uploadStart', function (file) {
            var $item = $('.video-item[data-id=' + file.id + ']')
            $item.find('.sweet-tip .mui-icon')
                .removeClass()
                .addClass('mui-icon mui-icon-closeempty')
            $item.find('.sweet-tip small').removeClass().text('删除')
        })

        function initProgressbar($e) {
            $e.each(function () {
                mui(this).progressbar({
                    progress: this.getAttribute("data-progress")
                }).show();
            });
        }

        $('#addBtn').on('tap', function () {
            var info = wxgh.serialize($('.ui-body')[0])
            var verifyRes = verify(info)
            if (verifyRes) {
                alert(verifyRes)
                return
            }
//            info['action'] = 'save'
            loading.show()
            mui($addBtn[0]).button('loading')

            var uploadUrl = homePath + '/wx/party/beauty/add/upload.json'
            var saveUrl = homePath + '/wx/party/beauty/add/save.json'

            mui.post(saveUrl, info, function (res) {
                if (res.ok) {
                    loading.hide()
                    workId = res.data
                    uploader.option('server', uploadUrl + '?workid=' + workId + '&worktype=' + info['type'] + '&action=')
                    uploader.upload()
                } else {
                    loading.hide()
                    mui($addBtn[0]).button('reset')
                }
            }, 'json')

        })

        function verify(info) {
            if (!info['name']) {
                return '请输入作品名称哦！'
            }
            if (!info['remark']) {
                return '请输入作品简介哦！'
            }
            if (uploader.getFiles().length <= 0) {
                return '请选择上传作品哦！'
            }
        }
    })
</script>
</body>
</html>
