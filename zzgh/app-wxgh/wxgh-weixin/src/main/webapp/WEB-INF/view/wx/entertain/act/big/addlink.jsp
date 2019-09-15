<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/19
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>活动发布</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.picker.min.css">
    <style>
        html,
        body {
            background-color: #efeff4;
        }

        .mui-views,
        .mui-view,
        .mui-pages,
        .mui-page,
        .mui-page-content {
            position: absolute;
            left: 0;
            right: 0;
            top: 0;
            bottom: 0;
            width: 100%;
            height: 100%;
            background-color: #efeff4;
        }

        .mui-pages {
            height: auto;
        }

        .mui-scroll-wrapper,
        .mui-scroll {
            background-color: #efeff4;
        }

        .mui-page.mui-transitioning {
            -webkit-transition: -webkit-transform 300ms ease;
            transition: transform 300ms ease;
        }

        .mui-page-left {
            -webkit-transform: translate3d(0, 0, 0);
            transform: translate3d(0, 0, 0);
        }

        .mui-ios .mui-page-left {
            -webkit-transform: translate3d(-20%, 0, 0);
            transform: translate3d(-20%, 0, 0);
        }

        .mui-page-shadow {
            position: absolute;
            right: 100%;
            top: 0;
            width: 16px;
            height: 100%;
            z-index: -1;
            content: '';
        }

        .mui-page-shadow {
            background: -webkit-linear-gradient(left, rgba(0, 0, 0, 0) 0, rgba(0, 0, 0, 0) 10%, rgba(0, 0, 0, .01) 50%, rgba(0, 0, 0, .2) 100%);
            background: linear-gradient(to right, rgba(0, 0, 0, 0) 0, rgba(0, 0, 0, 0) 10%, rgba(0, 0, 0, .01) 50%, rgba(0, 0, 0, .2) 100%);
        }

        .mui-page {
            display: none;
        }

        .mui-pages .mui-page {
            display: block;
        }
    </style>
    <style>
        .bg-img {
            background-color: #fff;
            padding: 10px 10px 0 10px;
            max-height: 150px;
            min-height: 140px;
        }

        .bg-img img {
            width: 100%;
        }

        .mui-table-view-cell > a:not(.mui-btn) {
            margin: -14px -15px;
        }

        .mui-table-view:after,
        .mui-table-view:before,
        .mui-input-group:after,
        .mui-input-group:before {
            height: 0;
        }

        #mapDiv {
            width: 100%;
            height: 200px;
        }

        .mui-slider-group .mui-control-content {
            background-color: #fff;
        }

        .mui-scroll .mui-loading {
            padding: 10px;
        }

        #baiduSearchInput {
            margin-bottom: 0;
        }

        .score-rule p > span {
            margin: 0 8px;
        }

        #addRulePopover form {
            padding: 10px;
        }

        #addRulePopover .mui-input-row > label {
            display: inline-block;
        }

        #addRulePopover .rule-add-btn {
            position: absolute;
            bottom: 0;
            width: 100%;
            padding: 0 10px;
            z-index: 4;
        }

        .rule-add-btn button {
            width: 100%;
            padding: 6px 0;
        }
    </style>
</head>
<body class="mui-fullscreen">

<div id="app" class="mui-views">
    <div class="mui-view">
        <div class="mui-pages">
        </div>
    </div>
</div>

<!-- 添加活动 -->
<div id="addActPage" class="mui-page">
    <div class="mui-page-content">
        <div class="ui-fixed-bottom">
            <button id="addActBtn" type="button" class="mui-btn mui-btn-primary">立即发布</button>
        </div>
        <div class="mui-scroll-wrapper">
            <div class="mui-scroll">
                <%--<div id="actImgBtn" class="bg-img ui-img-div">--%>
                <%--<img src="${home}/image/common/add_image.png"/>--%>
                <%--</div>--%>
                <div class="mui-row ui-choose-img ui-margin-top-15">
                    <div class="" id="chooseBtn">
                    </div>
                </div>

                <ul class="mui-table-view ui-margin-top-10">
                    <li class="mui-table-view-cell">
                        <a class="mui-navigate-right ui-flex">
                            <span>活动类型</span>
                            <small class="ui-right">总公司</small>
                        </a>
                        <select name="actType">
                            <option value="3">工会小组</option>
                            <%--<option value="4">区分公司</option>--%>
                            <option value="5">公司</option>
                        </select>
                    </li>
                    <li class="mui-table-view-cell no">
                        <a class="ui-flex">
                            <span>活动名称</span>
                            <input class="ui-right" name="name" type="text" placeholder="请输入"/>
                        </a>
                    </li>
                    <li class="mui-table-view-cell no">
                        <a class="ui-flex">
                            <span>活动链接</span>
                            <input class="ui-right" name="link" type="text" value=""
                                   placeholder="请输入活动链接"/>
                        </a>
                    </li>
                    <%--<li class="mui-table-view-cell no">--%>
                        <%--<a class="ui-flex">--%>
                            <%--<span>限制报名人数</span>--%>
                            <%--<input class="ui-right" name="actNumb" type="text"--%>
                                   <%--placeholder="无限制"/>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                </ul>

                <%--<ul class="mui-table-view ui-margin-top-10">--%>
                    <%--<li class="mui-table-view-cell">--%>
                        <%--<a data-options="{}" class="mui-navigate-right ui-flex">--%>
                            <%--<span>开始时间</span>--%>
                            <%--<small class="ui-right">选择时间</small>--%>
                            <%--<input type="hidden" name="startTime">--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <%--<li class="mui-table-view-cell">--%>
                        <%--<a data-options="{}" class="mui-navigate-right ui-flex">--%>
                            <%--<span>结束时间</span>--%>
                            <%--<small class="ui-right">选择时间</small>--%>
                            <%--<input type="hidden" name="endTime">--%>
                        <%--</a>--%>
                    <%--</li>--%>
                <%--</ul>--%>

                <ul class="mui-table-view ui-margin-top-10">
                    <%--<li class="mui-table-view-cell">--%>
                        <%--<a href="#pageBaidu" class="mui-navigate-right ui-flex">--%>
                            <%--<span>活动地点</span>--%>
                            <%--<small id="addressSmall" class="ui-right">选择地点</small>--%>
                            <%--<input type="hidden" name="address">--%>
                            <%--<input type="hidden" name="lat">--%>
                            <%--<input type="hidden" name="lng">--%>
                            <%--<input type="hidden" name="addrTitle">--%>
                        <%--</a>--%>
                    <%--</li>--%>
                    <%--<li class="mui-table-view-cell no">--%>
                        <%--<a>--%>
                            <%--<div class="ui-content">--%>
                                <%--<h5 class="ui-title">地址备注</h5>--%>
                                <%--<div class="ui-text-div">--%>
                                    <%--<textarea name="addrRemark" maxlength="50" rows="1"></textarea>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                        <%--</a>--%>
                    <%--</li>--%>
                </ul>

                <%--<ul class="mui-table-view ui-margin-top-10" id="remindUl">--%>
                    <%--<li class="mui-table-view-cell">--%>
                        <%--<a class="mui-navigate-right ui-flex">--%>
                            <%--<span>推送提醒</span>--%>
                            <%--<small class="ui-right">提前30分钟</small>--%>
                        <%--</a>--%>
                        <%--<select name="remind">--%>
                            <%--<option value="0.5">提前30分钟</option>--%>
                            <%--<option value="1">提前1小时</option>--%>
                            <%--<option value="2">提前2小时</option>--%>
                            <%--<option value="12">提前12小时</option>--%>
                            <%--<option value="18">提前18小时</option>--%>
                            <%--<option value="0">不提醒</option>--%>
                        <%--</select>--%>
                    <%--</li>--%>
                <%--</ul>--%>

                <ul class="mui-table-view ui-margin-top-10">
                    <li class="mui-table-view-cell no">
                        <a>
                            <div class="ui-content">
                                <h5 class="ui-title">活动介绍</h5>
                                <div class="ui-text-div">
                                    <textarea name="info" maxlength="500" rows="2"></textarea>
                                </div>
                            </div>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.view.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.picker.min.js"></script>
<%--<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=uOy28xEGgK5vrqIG4gGTw7PgAmu667Ua"></script>--%>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=3NDsGBtCfU8VlHvwz64jibfzthKlbyya"></script>
<script src="${wx}/script/union/act/act_baidu.js"></script>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script>
    var groupId = '${param.id}';
    mui.init({
        swipeBack: true
    })
    mui('.mui-scroll-wrapper').scroll();
    wxgh.init('${weixin}')
</script>
<script>
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
                var options = {}
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
        /*
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
         }*/

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
//            uploadImg(function (img) {
            $upload.upload(function (mediaIds) {
                info['imgId'] = mediaIds.toString()
                info['groupId'] = groupId
                wxgh.request.post('add_api.json', info, function (data) {
                    wxgh.clearForm($('#addActPage')[0])
                    ui.showToast('发布成功', function () {
                        wxgh.redirectTip(homePath, {
                            msg: '大型活动申请已提交，请静候管理员的审核吧~',
                            title: '申请已提交'
                        });
                    });
                })
            })
        })

        //判断link是否合法
        function checkURL(URL){
            var str=URL;
//判断URL地址的正则表达式为:http(s)?://([\w-]+\.)+[\w-]+(/[\w- ./?%&=]*)?
//下面的代码中应用了转义字符"\"输出一个字符"/"
            var Expression=/http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
            var objExp=new RegExp(Expression);
            if(objExp.test(str)==true){
                return true;
            }else{
                return false;
            }
        }

        function verify(info) {
            if (!info['name']) {
                return '活动名称不能为空'
            }
            if (!info['link']) {
                return '活动链接不能为空'
            }else{
                var link = info['link']
                if(!checkURL(link)){
                    return '活动链接不合法'
                }
            }
            if (!info['info']) {
                return '活动介绍不能为空'
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
</script>
</body>
</html>
