<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/1
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>赛事添加</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet"
          href="${home}/libs/material-datetimepicker/css/bootstrap-material-datetimepicker.css"/>
    <style>
        .ui-img-div {
            padding: 15px;
            position: relative;
            max-height: 156px;
        }

        .ui-img-div input[type=file] {
            opacity: 0;
            background-color: transparent;
            width: 100%;
            height: 100%;
            position: absolute;
            top: 0;
            left: 0;
        }

        .ui-main {
            color: #646464;
        }

        .ui-main .mui-navigate-right span.fa {
            color: #0099e9;
            font-size: 16px;
        }

        .ui-main .mui-navigate-right {
            line-height: 38px;
        }

        .mui-table-view-cell {
            padding: 6px 15px;
            font-size: 15px;
        }

        .mui-table-view-cell > a:not(.mui-btn).mui-active {
            background-color: #fff;
        }

        .mui-table-view-cell input {
            display: inline-block;
            width: auto;
            margin-bottom: 0;
            border: none;
            text-align: right;
        }

        .mui-table-view-cell input {
            font-size: 15px;
            padding: 5px 8px;
            float: right;
            margin-right: 12px;
        }

        .mui-table-view-cell select {
            margin-bottom: 0;
            padding: 5px 8px;
            text-align: right;
            width: auto;
            float: right;
            margin-right: 12px;
        }

        .mui-table-view-cell textarea {
            font-size: 15px;
            padding: 8px 8px;
        }

        .mui-table-view-cell textarea {
            border: none;
            margin-bottom: 0;
        }

        .ui-textarea-small small {
            position: absolute;
            bottom: 0;
            right: 15px;
            color: #c7c7cc;
        }

        .ui-btn-div {
            padding: 10px 15px 15px 0;
        }

        .dtp-buttons {
            position: relative;
            top: -10px;
        }

        .mui-table-view-cell select {
             position: static;
             opacity: 1;
        }
    </style>
</head>

<body>
<div class="mui-content">
    <div class="ui-img-div">
        <img id="chooseImgBtn" src="${home}/weixin/image/union/race/race_plus.png"/>
        <%--<input id="headPicInput" name="img" type="file"/>--%>
    </div>
    <div class="ui-main">
        <ul class="mui-table-view">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    <span class="fa fa-trophy"></span> 比赛名称
                    <input name="name" type="text" placeholder="请输入比赛名称"/>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    <span class="fa fa-flag-checkered"></span> 比赛项目
                    <select name="cateType">
                        <option value="0">请选择</option>
                        <option value="1">羽毛球</option>
                        <option value="2">乒乓球</option>
                        <option value="3">篮球</option>
                        <option value="4">网球</option>
                    </select>
                </a>
            </li>
            <li id="raceTypeLi" class="mui-table-view-cell mui-hidden">
                <a class="mui-navigate-right">
                    <span class="fa fa-bookmark"></span> 比赛类型
                    <select name="raceType">
                        <option value="0">请选择</option>
                        <option value="1">单打</option>
                        <option value="2">双打</option>
                    </select>
                </a>
            </li>
            <li class="mui-table-view-cell ui-textarea-small">
                <a class="mui-navigate-right">
                    <textarea name="info" rows="3" placeholder="填写比赛描述，让更多的人参与比赛..."></textarea>
                    <small>0 / 400</small>
                </a>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    <span class="fa fa-phone"></span> 联系电话
                    <input value="${userList.mobile}" name="phone" type="text" placeholder="请输入电话号码"/>
                </a>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    <span class="fa fa-clock-o"></span> 开始时间
                    <input class="ui-time-input" name="startTime" type="text" placeholder="请选择" readonly/>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    <span class="fa fa-clock-o"></span> 结束时间
                    <input class="ui-time-input" name="endTime" type="text" placeholder="请选择" readonly/>
                </a>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-10">
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    报名截止
                    <input class="ui-time-input" name="entryTime" type="text" placeholder="请选择" readonly/>
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a class="mui-navigate-right">
                    人数限制
                    <input name="limitNum" type="number" class="mui-input-numbox" placeholder="无限制"/>
                </a>
            </li>
        </ul>

        <div class="mui-content-padded">
            <button id="fabuBtn" type="button" class="ui-btn mui-btn-primary mui-btn mui-btn-block ui-margin-top-15">
                发布
            </button>
        </div>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/moment-2.10.6/moment.min.js"></script>
<script type="text/javascript"
        src="${home}/libs/material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {

        wxgh.wxInit('${weixin}');

        autoTextarea(document.querySelectorAll('textarea'))

        var today = new Date()

        $('.ui-time-input').bootstrapMaterialDatePicker({
            minDate: today,
            clearButton: true,
            format: 'YYYY-MM-DD HH:mm',
            cancelText: '上一步',
            okText: '确定',
            clearText: '取消'
        })

        $('#fabuBtn').on('tap', function () {
            var info = wxgh.serialize(wxgh.query('.ui-main'))
            var verifyRes = verify(info)
            if (verifyRes) {
                alert(verifyRes)
                return
            }

            if (!this.load) {
                this.load = ui.loading('发布中...')
            }
            this.load.show()
            var self = this;

            uploadImg(function (mediaId) {
                if (mediaId) {
                    var url = homePath + '/wx/union/race/api/add.json'
                    info['action'] = 'add'
                    info['img'] = mediaId
                    wxgh.request.post(url, info, function (res) {
                        self.load.hide()
                        ui.showToast('发布比赛成功', function () {
                            wxgh.clearForm(document.querySelector('.ui-main'))
                            wxgh.openWindow(homePath + '/wx/union/race/list.html')
                        })
                    }, 'json')
                } else {
                    self.load.hide()
                    alert('请选择比赛封面图片哦')
                }
            })

        })

        $('textarea[name=info]').keyup(function () {
            var val = $(this).val()
            var limit = 400
            var $small = $(this).next()
            if (val.length > limit) {
                $(this).val(val.substring(0, limit))
                alert('比赛描述不能超过400字符哦')
                return
            }
            $small.text(val.length + ' / ' + limit)
        })

        //比赛项目
        $('select[name=cateType]').on('change', function () {
            var val = $(this).val()
            if (val == 1 || val == 2) {
                $('#raceTypeLi').removeClass('mui-hidden')
            } else {
                $('#raceTypeLi').addClass('mui-hidden')
            }
        })

        //选择图片
        $('#chooseImgBtn').on('tap', function () {
            var $self = $(this)
            wx.chooseImage({
                count: 1, // 默认9
                sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                success: function (res) {
                    var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                    if (localIds) {
                        $self.attr('src', localIds);
                    }
                }
            });
        })

        //上传图片
        function uploadImg(func) {
            var imgSrc = $('#chooseImgBtn').attr('src');
            if (imgSrc == homePath + '/weixin/image/union/race/race_plus.png') {
                func('');
            } else {
                wx.uploadImage({
                    localId: imgSrc, // 需要上传的图片的本地ID，由chooseImage接口获得
                    isShowProgressTips: 0,// 默认为1，显示进度提示
                    success: function (res) {
                        var serverId = res.serverId; // 返回图片的服务器端ID
                        func(serverId);
                    }
                });
            }
        }

        var $startInput = $('input[name=startTime]')
        var $endInput = $('input[name=endTime]')
        var $entryInput = $('input[name=entryTime]')

        $('.ui-time-input').on('change', function () {
            var start = $startInput.val()
            var end = $endInput.val()
            var entry = $entryInput.val()

            if (start && end) {
                if (start > end) {
                    alert('比赛开始时间不能大于结束时间哦')
                    $endInput.focus()
                    $endInput.select()
                    return
                }
            } else if (start && entry) {
                if (entry > start) {
                    alert('报名截止时间必须小于比赛开始时间哦')
                    $entryInput.val('')
                    return
                }
            }
        })

        function verify(info) {
            if (!info['name']) {
                return '请输入比赛名称';
            }
            if (info['cateType'] == 0) {
                return '请选择比赛项目'
            }

            if (info['cateType'] == 1 || info['cateType'] == 2) {
                if (info['raceType'] == 0) {
                    return '请选择比赛类型'
                }
            } else {
                info['raceType'] = 0;
            }

            if (!info['info']) {
                return '填写比赛描述，让更多人参加哦'
            }
            if (!info['phone']) {
                return '手机号码不能为空哦'
            }
            if (!wxgh.checkPhoneNum(info['phone'])) {
                return '请输入合法手机号码'
            }
            if (!info['startTime']) {
                return '请选择比赛开始时间'
            }
            if (!info['endTime']) {
                return '请选择比赛结束时间'
            }
            if (!info['entryTime']) {
                return '请选择比赛截止报名时间'
            }
            if (info['startTime'] >= info['endTime']) {
                return '比赛开始时间不能大于结束时间'
            }
            if (info['entryTime'] > info['startTime']) {
                return '报名截止时间必须小于比赛开始时间哦'
            }
            if (!info['limitNum']) {
                info['limitNum'] = -1
            }
            if (isNaN(info['limitNum'])) {
                $('input[limitNum]').text('')
                return '数量限制输入不合法哦'
            }
        }
    })
</script>
</body>

</html>
