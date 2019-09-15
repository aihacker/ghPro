<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/7
  Time: 9:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>上传作品</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/webuploader/css/webuploader.css">
    <style>
        .ui-video-div {
            background-color: #fff;
            margin-top: 15px;
            padding: 10px 15px;
        }

        .video-item .video-name {
            font-size: 14px;
            color: #777;
        }

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

        .upload-list {
            padding: 4px 0;
        }

        .video-item .ui-img-div {
            height: 84px;
        }

        .video-item .ui-img-div img {
            height: 100%;
            width: auto;
        }

        .video-item.ui-img {
            display: inline-block;
            width: 30%;
            border: 1px solid #ddd;
            margin: 2px;
        }

        .video-item.ui-img .mui-icon {
            position: absolute;
            top: -8px;
            right: -8px;
            color: #cf2d28;
        }

        .video-item .ui-progress {
            background-color: rgba(0, 0, 0, 0.5);
            position: absolute;
            bottom: 0;
            width: 100%;
            height: 20px;
        }

        .ui-progress .mui-progressbar {
            width: 90%;
            left: 5%;
            position: relative;
            top: 5px;
            height: 8px;
        }

        .ui-progress small {
            text-align: center;
            position: absolute;
            top: 0;
            color: #fff;
            left: 10px;
        }
    </style>
</head>
<body>

<div class="mui-content">
    <div class="ui-video-div">
        <span>上传作品：</span>
        <div id="uploader">
            <div class="upload-list">
            </div>
            <div>
                <div id="picker">选择作品</div>
            </div>
        </div>
    </div>

    <div class="ui-fixed-bottom">
        <button id="addBtn" type="button" class="mui-btn mui-btn-primary ui-btn mui-btn-block">立即上传</button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/webuploader/js/webuploader.js"></script>
<script>
    var matchId = '${param.id}';
    var joinType = '${param.joinType}';
    $(function () {

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
            server: homePath + '/wx/party/match/join/upload.json?action=&workid=' + matchId +
            '&worktype=' + joinType +
            '&mftype=2',
            pick: '#picker',
            resize: false,
            fileNumLimit: 5,
            fileSingleSizeLimit: 50 * 1024 * 1024,
            compress: false,
            accept: joinType == 1 ? imgAccept : videoAccept
        })

        var loading = ui.loading('发布中...')

        uploader.on('fileQueued', function (file) {
            console.log(uploader.getFiles())
            var $item = getItem(file)
            $fileList.append($item)

        })
        uploader.on('uploadProgress', function (file, percentage) {
            mui($('.video-item[data-id=' + file.id + ']').find('.mui-progressbar')[0]).progressbar().setProgress(percentage * 100)
        })
        uploader.on('error', function (type) {
            if (type == 'Q_EXCEED_NUM_LIMIT') {
                alert('上传文件不能超过5个哦！')
            } else if (type == 'F_EXCEED_SIZE') {
                alert('单个文件不能超过50M哦！')
            }
        })
        uploader.on('uploadSuccess', function (file) {
            var $item = $('.video-item[data-id=' + file.id + ']')
            if (joinType == 1) {
                $item.find('.ui-progress small').removeClass().text('上传成功')
            } else {
                $item.find('.sweet-tip .mui-icon').remove()
                $item.find('.sweet-tip small').addClass('ui-text-success').text('上传成功')
            }
            var numbs = uploader.getStats()
            if (numbs.uploadFailNum <= 0 && numbs.progressNum <= 0 && numbs.queueNum <= 0 && numbs.invalidNum <= 0) {
                ui.showToast('上传成功！', function () {
//                    mui.back()
                    mui.openWindow(homePath + "/wx/party/match/me/index.html?id=" + matchId);
                })
            }
        })
        uploader.on('uploadError', function (file, reason) {
            var $item = $('.video-item[data-id=' + file.id + ']')
            if (joinType == 1) {
                $item.find('.mui-icon').removeClass()
                    .addClass('mui-icon mui-icon-refreshempty')
                $item.find('.ui-progress small').addClass('ui-text-danger').text('上传失败')
            } else {
                $item.find('.sweet-tip .mui-icon')
                    .removeClass()
                    .addClass('mui-icon mui-icon-refreshempty')
                $item.find('.sweet-tip small').addClass('ui-text-danger').text('失败，点击重试')
            }
        })
        uploader.on('uploadComplete', function () {
            loading.hide()
        })
        uploader.on('uploadStart', function (file) {
            var $item = $('.video-item[data-id=' + file.id + ']')
            loading.show()
            if (joinType == 1) {
                $item.find('.mui-icon').removeClass()
                    .addClass('mui-icon mui-icon-close-filled')
                $item.find('.ui-progress small').removeClass().text('')
            } else {
                $item.find('.sweet-tip .mui-icon')
                    .removeClass()
                    .addClass('mui-icon mui-icon-closeempty')
                $item.find('.sweet-tip small').removeClass().text('删除')
            }
        })

        function initProgressbar($e) {
            $e.each(function () {
                mui(this).progressbar({
                    progress: this.getAttribute("data-progress")
                }).show();
            });
        }

        $('#addBtn').on('tap', function () {
            var cf = confirm('是否立即上传？')
            if (cf) {
                uploader.upload()
            }
        })

        function getItem(file) {
            var $item;
            var tipIcon;
            if (joinType == 1) {
                $item = $('<div data-id="' + file.id + '" class="video-item ui-img">' +
                    '<div class="ui-img-div"><img/></div>' +
                    '<div class="ui-progress">' +
                    '<p class="mui-progressbar mui-progressbar-success" data-progress="0"></p>' +
                    '<small></small>' +
                    '</div><span class="mui-icon mui-icon-close-filled"></span></div>');
                var $img = $item.find('img')
                uploader.makeThumb(file, function (error, src) {
                    if (error) {
                        $img.replaceWith('<span>不能预览</span>')
                        return;
                    }
                    $img.attr('src', src)
                }, 0.3, 80)
                tipIcon = '.mui-icon'
            } else {
                $item = $('<div data-id="' + file.id + '" class="video-item">' +
                    '<span class="video-name">' + file.name + '</span>' +
                    '<p class="mui-progressbar mui-progressbar-success" data-progress="0"></span></p>' +
                    '<span class="sweet-tip"><span class="mui-icon mui-icon-closeempty"></span><small>删除</small></span></div>')
                tipIcon = '.sweet-tip'
            }
            $item.data('id', file.id)

            $item.on('tap', tipIcon, function () {
                var $self = $(this)
                var $parent = $self.parent()
                var id = $parent.data('id')
                var closeClass = 'mui-icon-closeempty'
                var $icon = $self.find('.mui-icon')
                if (joinType == 1) {
                    closeClass = 'mui-icon-close-filled'
                    $icon = $self
                }
                if ($icon.hasClass(closeClass)) {
                    uploader.removeFile(id, true)
                    $parent.remove()
                } else if ($icon.hasClass('mui-icon-refreshempty')) {
                    uploader.retry()
                }
            })
            initProgressbar($item.find('.mui-progressbar'))
            return $item
        }
    })
</script>
</body>
</html>
