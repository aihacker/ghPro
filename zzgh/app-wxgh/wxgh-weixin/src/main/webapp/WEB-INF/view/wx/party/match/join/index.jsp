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
    <div class="ui-body">
        <ul class="mui-table-view" style="margin: 0;">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    <div class="ui-input-div">
                        <span>作品名称：</span>
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
        <button id="addBtn" type="button" class="mui-btn mui-btn-primary ui-btn mui-btn-block">立即发布</button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/webuploader/js/webuploader.js"></script>
<script>
    // 报名id
    var matchId = '${param.id}';
    var joinType = '${param.joinType}';
    $(function () {

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
            server: homePath + '/wx/party/match/join/upload.json?workid=' + matchId +
            '&worktype=' + joinType +
            '&mftype=2',
            pick: '#picker',
            resize: false,
            fileNumLimit: 5,
            fileSingleSizeLimit: 50 * 1024 * 1024,
            compress: false,
            accept: joinType == 1 ? imgAccept : videoAccept
        })

        var loading = new ui.loading('发布中...')

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
                var url = homePath + '/wx/party/match/join/success.json'
                mui.getJSON(url, {action: '', mid: matchId, type: joinType}, function (res) {
                    if (res.ok) {
                        loading.hide()
                        ui.showToast('发布成功！', function () {
//                            mui.back()
                            mui.openWindow(homePath + "/wx/party/match/me/index.html?id=" + matchId);
                        })
                    }
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
            var info = wxgh.serialize($('.ui-body')[0])
            var verifyRes = verify(info)
            if (verifyRes) {
                alert(verifyRes)
                return
            }
            var url = homePath + '/wx/party/match/join/join.json'
//            info['action'] = 'join'
            info['id'] = matchId
            mui.post(url, info, function (res) {
                if (res.ok) {
                    uploader.upload()
                }
            }, 'json')

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
                console.log('tap')
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
