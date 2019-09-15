/**
 * Created by Administrator on 2017/7/19.
 */
$(function () {
    var viewApi = mui('#app').view({
        defaultPage: '#addActPage'
    });

    var mapInits = false, ruleInits = false;
    var oldBack = mui.back;
    mui.back = function () {
        if (viewApi.canBack()) {
            viewApi.back()
        } else {
            oldBack();
        }
    }
    var view = viewApi.view
    view.addEventListener('pageBeforeShow', function (e) {
        var id = e.detail.page.id
        if (id == 'pageBaidu') {
            if (!mapInits) {
                baiduPageInit()
                mapInits = true
            }
        } else if (id == 'rulePage') {
            if (!ruleInits) {
                ruleInit()
                ruleInits = true
            }
        }
    })

    /**
     * 选择开始结束时间
     */
    $('a[data-options]').on('tap', function () {
        var _self = this;
        var $self = $(this)
        var $input = $self.find('input[name]')
        if (!_self.picker) {
            var options = actType == 1 ? {type: 'time'} : {}
            _self.picker = new mui.DtPicker(options)
        }
        _self.picker.show(function (rs) {
            var time = rs.text
            if ($input.attr('name') == 'startTime') {
                var otherTime = $('input[name=endTime]').val()
                if (otherTime && time >= otherTime) {
                    ui.alert('开始时间不能大于或等于结束时间')
                    return
                }
            } else {
                var otherTime = $('input[name=startTime]').val()
                if (otherTime && time <= otherTime) {
                    ui.alert('结束时间不能小于或等于开始时间')
                    return
                }
                var now = new Date();
                var end = new Date(time);
                if(end <= now){
                    ui.alert('结束时间不能小于或等于当前时间')
                    return
                }
            }
            $self.find('.ui-right').text(time)
            $input.val(time)
        })
    })

    /**
     * 是否推送
     */
    /*var $remindUl = $('#remindUl')
    mui('.mui-input-group').on('toggle', '.mui-switch', function (e) {
        var isActive = e.detail.isActive;
        var $input = $(this).next()
        $input.val(isActive ? 1 : 0)
        if (isActive) {
            $remindUl.show()
        } else {
            $remindUl.hide()
            var $remind = $('select[name=remind]')
            var $option = $remind.find('option[value=0]')
            $remind.parent().find('.ui-right').text($option.text())
            $option.prop('selected', true)
        }
    })*/

    /**
     * 是否有积分
     */
    var $scoreRuleLi = $('#scoreRuleLi')
    $('select[name=hasScore]').on('change', function () {
        var val = $(this).val()
        if (val == 1) {
            $scoreRuleLi.show()
        } else {
            $scoreRuleLi.hide()
            $('input[name=scoreRule]').val('')
        }
    })

    //选择图片
    $('#actImgBtn').on('tap', function () {
        var $self = $(this)
        wx.chooseImage({
            count: 1, // 默认9
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                if (localIds) {
                    var id = localIds[0];
                    wxgh.get_wx_img(id, function (src) {
                        $self.find('img').data('localId', id).attr('src', src);
                    });
                }
            }
        });
    })

    //上传图片
    function uploadImg(func) {
        var imgSrc = $('#actImgBtn img').data('localId');
        if (!imgSrc) {
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

    // 多图上传
    var options = {
        wx: {
            count: 9,
            sizeType: ['original', 'compressed'],
            sourceType: ['album', 'camera'],
        },
        clear: false
    }
    var $upload = wxgh.imagechoose($("#chooseBtn"), options);

    /**
     * 发布活动按钮
     */
    $('#addActBtn').on('tap', function () {

        var info = wxgh.serialize($('#addActPage')[0])
        var verifyRes = verify(info)
        if (verifyRes) {
            ui.alert(verifyRes)
            return;
        }

        var self = this;
        if (!self.loading) {
            self.loading = ui.loading('活动发布中...');
        }
        self.loading.show();

        //上传图片开始
        /*
        uploadImg(function (img) {
            info['imgId'] = img
            info['groupId'] = groupId
            info['regular'] = actType
            wxgh.request.post('add.json', info, function (data) {
                ui.showToast('发布成功', function () {
                    if (subjectType == 1) // 团队
                        mui.openWindow(homePath + "/wx/entertain/act/team/show.html?id=" + data)
                    else  // 协会
                        mui.openWindow(homePath + "/wx/entertain/act/show.html?id=" + data)
                })
            })
        })*/

        $upload.upload(function (mediaIds) {
            info['imgId'] = mediaIds.toString()
            info['groupId'] = groupId
            info['regular'] = actType
            wxgh.request.post('add.json', info, function (data) {
                ui.showToast('发布成功', function () {
                    if (subjectType == 1) // 团队
                        mui.openWindow(homePath + "/wx/entertain/act/team/show.html?id=" + data)
                    else  // 协会
                        mui.openWindow(homePath + "/wx/entertain/act/show.html?id=" + data)
                })
            })
        })
    })

    function verify(info) {
        if (!info['name']) {
            return '活动名称不能为空'
        }
        if (!info['phone']) {
            return '联系电话不能为空'
        }
        if (!info['startTime']) {
            return '请选择活动开始时间'
        }
        if (!info['endTime']) {
            return '请选择活动结束时间'
        }
        if (!info['address']) {
            return '请选择活动地址'
        }
        if (info['hasScore'] == 1 && !info['scoreRule']) {
            return '请选择积分规则哦'
        }
        if (!info['info']) {
            return '活动介绍不能为空'
        }
        if (!info['money']) {
            info['money'] = 0
        }
        if (actType == 1) {
            info['startTime'] = '0000-00-00 ' + info.startTime
            info['endTime'] = '0000-00-00 ' + info.endTime
        }
    }

    /**
     * 初始化score rule页面
     */
    function ruleInit() {
        var $ruleBtn = $('#ruleOkBtn')

        var $ruleList = $('#rulePage .score-rule')
        var $ruleForm = $('#addRulePopover form')

        $ruleBtn.on('tap', function () {
            mui.back()
        })

        $('#addRuleBtn').on('tap', function () {
            var score = wxgh.serialize($ruleForm[0])
            if (!score['score'] || isNaN(score.score)) {
                ui.alert('参加积分不能为空或数值不合法')
                return
            }
            if (!score['leaveScore'] || isNaN(score.leaveScore)) {
                ui.alert('请假积分不能为空或数值不合法')
                return
            }
            if (!score['outScore'] || isNaN(score.outScore)) {
                ui.alert('缺席积分不能为空或数值不合法')
                return
            }
            var scoreName = score['score'] + '-' + score['leaveScore'] + '-' + score['outScore']
            var $as = $ruleList.find("a[name='" + scoreName + "']")
            if ($as.length > 0) {
                window.location.href = '#' + scoreName
                mui('#addRulePopover').popover('hide');
            } else {
                wxgh.request.post('/wx/entertain/act/rule/add.json', score, function () {
                    ui.alert('添加成功')
                    mui('#addRulePopover').popover('hide');
                    request_rule()
                })
            }
        })

        request_rule()

        function request_rule() {
            $ruleList.append('<li class="mui-table-view-cell mui-text-center">' + wxgh.LOADING_HTML + '</li>')
            wxgh.request.getURL('/wx/entertain/act/rule/list.json', function (data) {
                $ruleList.empty()
                if (data && data.length > 0) {
                    $ruleList.addClass('mui-table-view-radio')
                    for (var i in data) {
                        var d = data[i]
                        var $item = $('<li class="mui-table-view-cell"><a name="' + (d.score + '-' + d.leaveScore + '-' + d.outScore) + '" class="mui-navigate-right">' + d.name + ' <p>'
                            + '<span>参加：' + d.score + '</span>'
                            + '<span>请假：' + d.leaveScore + '</span>'
                            + '<span>缺席：' + d.outScore + '</span>'
                            + '</p></a></li>')
                        $item.data('data', d)

                        //item 选中事件，修改scoreRule的值
                        $item.on('selected', function (e) {
                            var $self = $(e.originalEvent.detail.el)
                            $('input[name=scoreRule]').val($self.data('data').ruleId)
                            $('input[name=scoreRule]').prev().text($self.data('data').name)
                            $ruleBtn.text('确定选择')
                        })
                        $ruleList.append($item)
                    }
                } else {
                    $ruleList.removeClass('mui-table-view-radio')
                    $ruleList.append('<li class="mui-table-view-cell mui-text-center">暂无积分规则哦</li>')
                    $ruleBtn.text('返回')
                }
            })
        }
    }
})