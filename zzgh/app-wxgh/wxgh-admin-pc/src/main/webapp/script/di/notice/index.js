$(function () {
    init();

    var msgtype = 'mpnews'

    function init() {

        window.um = UM.getEditor('noticeCnt', {
            /* 传入配置参数,可配参数列表看umeditor.config.js */
            toolbar: [
                'source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
                'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize',
                '| justifyleft justifycenter justifyright justifyjustify |',
                'link unlink | emotion image video ',
                '| horizontal print preview fullscreen', 'drafts', 'formula'
            ]
        });

        um.addListener('contentChange', function () {
            var txt = um.getContentTxt().substring(0, 54);
            $('textarea[name=digest]').val(txt)
            $prevInfo.text(txt)
        })


        $('.edui-container').css('z-index', '999');

        var $msgBody = $('#msgBody')
        var $prevTitle = $('#prevTitle')
        var $prevInfo = $('#prevInfo')
        var $preImage = $('#prevImag')

        //文字 文本内容
        $('#textContent').keyup(function () {
            var val = $(this).val();
            $msgBody.text(val)
            $('#textSize').text('还可以输入' + (600 - val.length) + '字')
            changeNotice()
            if (val.length > 600) {
                $(this).val(val.substring(0, 600))
                $('#textTip').removeClass('hidden')
            } else {
                $('#textTip').addClass('hidden')
            }
        })

        //图文，标题
        $('input[name=title]').keyup(function () {
            var val = $(this).val();
            $prevTitle.text(val)

            var $prev = $(this).prev();
            if (val.length > 64) {
                $(this).val(val.substring(0, 64))
                $prev.removeClass('hidden')
                $prev.text('标题不能超过64字符哦');
            } else if (val.length == 0) {
                $prevTitle.text('标题')
            } else {
                $prev.addClass('hidden')
            }
        })

        //图文 摘要
        $('textarea[name=digest]').keyup(function () {
            var val = $(this).val();
            $prevInfo.text(val)

            var $prev = $(this).prev();
            if (val.length > 100) {
                $(this).val(val.substring(0, 100))
                $prev.removeClass('hidden')
                $prev.text('摘要信息不能超过100字符哦')
            } else if (val.length == 0) {
                $prevInfo.text('摘要')
            } else {
                $prev.addClass('hidden')
            }
        })

        $('#uploadNewsImg').on('change', function (e) {
            var files = e.target.files;
            if (files && files.length > 0) {
                if (typeof FileReader != 'undefined') {
                    var reader = new FileReader();
                    reader.readAsDataURL(files[0]);
                    reader.onload = function () {
                        $preImage.attr('src', this.result)
                    }
                }
            } else {
                $preImage.attr('src', homePath + '/image/party/icon_preview.png')
            }
        })

        //图片消息
        $('#uploadimage').on('change', function (e) {
            var files = e.target.files;
            if (files && files.length > 0) {
                if (typeof FileReader != 'undefined') {
                    var reader = new FileReader();
                    reader.readAsDataURL(files[0]);
                    reader.onload = function () {
                        $msgBody.find('.thumbnail img').attr('src', this.result)
                    }
                }
                $(this).prev().text('已选图片')
            } else {
                $msgBody.find('.thumbnail img').attr('src', homePath + '/image/party/icon_preview.png')
                $(this).prev().text('上传图片')
            }
        })

        //是否显示封面
        $('#showPicBtn').on('change', function () {
            if (this.checked) {
                $('input[name=show_cover_pic]').val('1')
            } else {
                $('input[name=show_cover_pic]').val('0')
            }
        })

        //是否保密消息
        $('#safeMsgBtn').on('change', function () {
            if (this.checked) {
                $('input[name=safe]').val('1')
            } else {
                $('input[name=safe]').val('0')
            }
        })

        //文件消息
        $('#uploadfile').on('change', function (e) {
            var files = e.target.files;
            if (files && files.length > 0) {
                var filename = files[0].name;
                var file_size = files[0].size;
                if ((file_size / (1024 * 1024)) < 1) {
                    file_size = (file_size / 1024).toFixed(1) + 'Kb';
                } else {
                    file_size = (file_size / (1024 * 1024)).toFixed(1) + 'M';
                }
                $msgBody.html('<div class="ui-file-body">' +
                    '<span class="fa fa-file fa-4x"></span>' +
                    '<small id="fileName">' + filename + '</small>' +
                    '<span id="fileSize">' + file_size + '</span></div>')
            } else {
                $msgBody.html('')
            }
            changeNotice()
        })

        $('.ui-content').on('click', 'a[data-toggle="tab"]', function () {
            if (!$(this).parent().hasClass('active')) {
                var href = $(this).attr('href');

                $msgBody.html('')
                if (href == '#imageNotice') { //图片
                    $msgBody.html('<div class="thumbnail"><img src="' + homePath + '/image/party/icon_preview.png" /></div>')
                    msgtype = 'image'
                } else if (href == '#textNotice') { //文字
                    msgtype = 'text'
                } else if (href == '#fileNotice') { //文件
                    msgtype = 'file'
                }

                if (href == '#newsNotice') { //图文
                    $('.textPrev').addClass('hidden')
                    $('.newsPrev').removeClass('hidden')
                    $('.ui-news-prev .panel-body').height('')
                    msgtype = 'mpnews'
                } else {
                    $('.newsPrev').addClass('hidden')
                    $('.textPrev').removeClass('hidden')
                    changeNotice()
                }
            }
        })

        //发送按钮
        $('#sendBtn').click(function () {
            var $btn = $(this).button('loading')
            sendMessage(msgtype, function () {
                $btn.button('reset');
                window.location.reload();
            }, function () {
                $btn.button('reset');
            })
        })

        resize()
        $(window).on('resize', resize())

    }

    function sendMessage(type, func, efunc) {

        //var url = send_url + type
        //
        //if (type == 'text') { //发送文字消息
        //    var $form = $('#' + type + 'Form');
        //
        //    var info = $form.serializeJson();
        //    info['safe'] = $('input[name=safe]').val();
        //
        //    var verifyRes = verifyForm(type, info);
        //    if (verifyRes) {
        //        alert(verifyRes)
        //        if (efunc) efunc();
        //        return;
        //    }
        //
        //    $.post(url, info, function (res) {
        //        if (res.ok) {
        //            $form.clearForm();
        //            if (func) func();
        //        } else {
        //            alert(res.msg)
        //            if (efunc) efunc(res);
        //        }
        //    }, 'json')
        //} else if (type == 'image' || type == 'file') {
        //    var files = document.getElementById('upload' + type).files;
        //    if (files && files.length > 0) {
        //        $.ajaxFileUpload({
        //            url: url,
        //            dataType: 'json',
        //            secureuri: false,
        //            data: {safe: $('input[name=safe]').val()},
        //            fileElementId: 'upload' + type,
        //            success: function (data, status) {
        //                if (data.ok) {
        //                    if (func) func();
        //                } else {
        //                    if (efunc) efunc();
        //                    alert(data.msg)
        //                }
        //            }
        //        })
        //    }
        //} else if (type == 'mpnews') {
        //    var $form = $('#' + type + 'Form');
        //    var info = $form.serializeJson();
        //    info['type'] = $("#select-type option:selected").attr("value");
        //
        //    //正文
        //    var cnt = um.getContent();
        //    info['content'] = encodeURIComponent(cnt);
        //    info['safe'] = $('input[name=safe]').val();
        //    info['tuisong'] = $('#isTuisong').is(':checked') ? 1 : 0
        //
        //    //标题
        //    info['title'] = $('input[name=title]').val();
        //    //摘要
        //    info['briefInfo'] = $('textarea[name=digest]').val();
        //    //链接
        //    info['link'] = $('input[name=content_source_url]').val();
        //    //作者
        //    if (!info['author']) {
        //        info['author'] = '互联网工会';
        //    }
        //
        //    var verifyRes = verifyForm(type, info)
        //    if (verifyRes) {
        //        alert(verifyRes)
        //        if (efunc) efunc();
        //        return;
        //    }
        //
        //    $.ajaxFileUpload({
        //        url: url,
        //        dataType: 'json',
        //        data: info,
        //        secureuri: false,
        //        fileElementId: 'uploadNewsImg',
        //        success: function (data, status) {
        //            if (data.ok) {
        //                if (func) func();
        //            } else {
        //                if (efunc) efunc();
        //                alert(data.msg)
        //            }
        //        }
        //    })
        //}

        var url = homePath + '/admin/di/notice/mpnews.json';

        var $form = $('#mpnewsForm');
        var info = $form.serializeJson();
        info['type'] = $("#select-type option:selected").attr("value");

        //正文
        var cnt = um.getContent();
        info['content'] = encodeURIComponent(cnt);
        info['safe'] = $('input[name=safe]').val();
        info['tuisong'] = $('#isTuisong').is(':checked') ? 1 : 0

        //标题
        info['title'] = $('input[name=title]').val();
        //摘要
        info['briefInfo'] = $('textarea[name=digest]').val();
        //链接
        info['link'] = $('input[name=content_source_url]').val();
        //作者
        if (!info['author']) {
            info['author'] = '互联网工会';
        }

        var verifyRes = verifyForm("npnews", info)
        if (verifyRes) {
            alert(verifyRes)
            if (efunc) efunc();
            return;
        }

        console.log(JSON.stringify(info))

        $.ajaxFileUpload({
            url: url,
            dataType: 'json',
            data: info,
            secureuri: false,
            fileElementId: 'uploadNewsImg',
            success: function (data, status) {
                if (data.ok) {
                    if (func) func();
                } else {
                    if (efunc) efunc();
                    alert(data.msg)
                }
            }
        })
    }

    function verifyForm(type, info) {
        if (type == 'text') { //文字
            if (!info['content']) {
                return '文字内容不能为空哦';
            }
        } else if (type == 'npnews') {
            if (!info['title']) {
                return '标题不能为空哦';
            }
            if (!info['content']) {
                return '正文不能为空哦';
            }
            if (!info['digest']) {
                return '摘要不能为空哦'
            }
        }
    }

    function changeNotice() {
        $('.ui-news-prev .panel-body').height($('.ui-prev .ui-msg-body').outerHeight() + 15)
    }

    function resize() {
        var height = $(window).height() - 21;
    }
})