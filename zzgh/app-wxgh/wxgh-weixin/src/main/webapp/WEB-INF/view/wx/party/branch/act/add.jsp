<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/22
  Time: 17:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加活动</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet"
          href="${home}/libs/material-datetimepicker/css/bootstrap-material-datetimepicker.css"/>
    <style>
        .mui-table-view-cell span {
            color: #777;
            margin-right: 20px;
            position: relative;
            float: right;
            top: 4px;
            font-size: 13px;
        }

        .mui-table-view-cell input {
            position: absolute;
            left: 0;
            z-index: 9;
            width: 100%;
            margin: 0;
            text-align: right;
            border: none;
        }

        .mui-table-view-cell .ui-time-input {
            opacity: 0;
        }

        .mui-table-view-cell select {
            margin: 0;
            position: absolute;
            left: 0;
            top: 5px;
            opacity: 0;
        }

        .ui-join-type {
            padding: 8px 15px;
            background-color: #fff;
        }

        .mui-scroll-wrapper {
            bottom: 70px;
        }

        .ui-img-div {
            height: 150px;
            padding: 0 5px;
        }

        .weui_dialog_confirm {
            z-index: 9999;
        }

        .weui_mask, .weui_dialog {
            z-index: 999;
        }

        #typeNameInput {
            border-top: none;
            border-left: none;
            border-right: none;
            margin-bottom: 0;
            display: none;
        }

        .dtp .dtp-buttons {
            position: relative;
            top: -10px;
        }

        #typeNameLi {
            width: 100%;
        }

        #pageMap {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
        }

        .tangram-suggestion-main {
            z-index: 9999999;
        }
    </style>
</head>
<body>
<div id="pageMain">
    <div class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <div class="main-form">
                <div id="imgBtn" class="ui-img-div">
                    <img src="${home}/image/common/add_image.png"/>
                    <input type="hidden" name="imgUrl">
                </div>
                <div class="ui-content">
                    <h5 class="ui-title">活动主题</h5>
                    <div class="ui-text-div">
                        <textarea name="name" maxlength="100" rows="2" placeholder="活动主题名称"></textarea>
                    </div>
                </div>

                <ul class="mui-table-view ui-margin-top-15">
                    <li id="typeNameLi" class="mui-table-view-cell">
                        <a class="mui-navigate-right">
                            活动事项
                            <input type="hidden" name="typeName">
                            <span>请选择</span>
                        </a>
                    </li>
                    <li class="mui-table-view-cell">
                        <a class="mui-navigate-right">
                            <label style="position: relative;z-index: 10;">联系电话</label>
                            <input name="mobile" value="${wxgh_user.mobile}" type="number" class="mui-input-numbox"
                                   placeholder="请输入"/>
                        </a>
                    </li>
                    <li class="mui-table-view-cell">
                        <a class="mui-navigate-right">
                            开始时间
                            <input class="ui-time-input" type="text" name="startTime" placeholder="请选择" readonly>
                            <span>请选择</span>
                        </a>
                    </li>
                    <li class="mui-table-view-cell">
                        <a class="mui-navigate-right">
                            结束时间
                            <input class="ui-time-input" type="text" name="endTime" placeholder="请选择" readonly>
                            <span>请选择</span>
                        </a>
                    </li>
                    <li id="actAddressLi" class="mui-table-view-cell">
                        <a class="mui-navigate-right">
                            活动地址
                            <input type="hidden" name="address">
                            <input type="hidden" name="addrTitle">
                            <input type="hidden" name="lat">
                            <input type="hidden" name="lng">
                            <span>请选择</span>
                            <p style="display:none;" class="ui-address"></p>
                        </a>
                    </li>
                </ul>
                <ul class="mui-table-view ui-margin-top-10 no">
                    <li class="mui-table-view-cell">
                        <a class="mui-navigate-right ui-flex">
                            <span>报名方式</span>
                            <small class="ui-right">自愿参加</small>
                        </a>
                        <select name="joinType">
                            <option value="1">自愿参加</option>
                            <option value="2">必须参加</option>
                        </select>
                    </li>
                </ul>

                <div class="ui-content ui-margin-top-10">
                    <h5 class="ui-title">简介</h5>
                    <div class="ui-text-div">
                        <textarea name="info" maxlength="200" rows="2" placeholder="活动简介"></textarea>
                    </div>
                </div>
                <div class="ui-content ui-margin-top-10">
                    <h5 class="ui-title">备注</h5>
                    <div class="ui-text-div">
                        <textarea name="remark" maxlength="200" rows="2" placeholder="活动备注"></textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="ui-fixed-bottom">
        <button id="addBtn" class="mui-btn mui-btn-block ui-btn mui-btn-primary">立即发布</button>
    </div>
</div>

<div id="pageMap" style="display: none;z-index: 99999;background-color: #fff;">
    <div id="Map">
    </div>
    <div class="ui-fixed-bottom">
        <button id="okMapBtn" class="mui-btn mui-btn-block ui-btn mui-btn-primary">确定</button>
    </div>
</div>

<script type="text/template" id="confirmTmp">
    <ul class="mui-table-view mui-table-view-radio">
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                三会一课
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                飘亮活动
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                党性教育活动
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                队伍建设活动
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                其他
            </a>
        </li>
    </ul>
    <div>
        <input id="typeNameInput" type="text" placeholder="输入您的内容">
    </div>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/moment-2.10.6/moment.min.js"></script>
<script type="text/javascript"
        src="${home}/libs/material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
<script type="text/javascript" src="${home}/libs/weixin/weixin.min.js"></script>
<script src="${home}/comm/mobile/js/confirm.js"></script>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=uOy28xEGgK5vrqIG4gGTw7PgAmu667Ua"></script>
<script src="${home}/comm/js/baidu_map.js"></script>
<script>
    var groupId = '${param.id}'

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005
    });

    wxgh.init('${weixin}')

    $(function () {

        var $pageMap = $('#pageMap')
        var $pageMain = $('#pageMain')
        var baiduMap = new window.Baidu('Map')

        //时间选择框初始化
        var today = new Date()
        $('.ui-time-input').bootstrapMaterialDatePicker({
            minDate: today,
            clearButton: true,
            format: 'YYYY-MM-DD HH:mm',
            cancelText: '上一步',
            okText: '确定',
            clearText: '取消'
        }).on('change', function (e, date) {
            $(this).next('span').text(moment(date).format('YYYY-MM-DD HH:mm'))
        })

        //选择封面图片
        $('#imgBtn').on('tap', function () {
            var $self = $(this)
            wx.chooseImage({
                count: 1,
                sizeType: ['original', 'compressed'],
                sourceType: ['album', 'camera'],
                success: function (res) {
                    var localIds = res.localIds;
                    if (localIds && localIds.length > 0) {
                        wxgh.get_wx_img(localIds[0], function (src) {
                            $self.find('img').attr('src', src)
                            $('input[name=imgUrl]').val(localIds[0])
                        });
                    }
                }
            });
        })

        //选择活动事项
        var typenameConfim = new Confirm('选择事项', $('#confirmTmp').html(), function ($dialog) {
            var $selecte = $dialog.find('.mui-table-view-radio li.mui-table-view-cell.mui-selected')
            var val
            if ($selecte.length > 0) {
                if ($selecte.text().trim() == '其他') {
                    val = $('#typeNameInput').val()
                    if (!val) {
                        alert('请输入其他事项内容')
                        return
                    }
                } else {
                    val = $selecte.text().trim()
                }
            }
            var $input = $('input[name=typeName]')
            $input.val(val)
            $input.next('span').text(val)
        }, function (cfm) {
            if (!cfm.init) {
                cfm.$dialog.on('selected', '.mui-table-view.mui-table-view-radio', function (e) {
                    var $self = $(e.target)
                    if ($self.text().trim() == '其他') {
                        $('#typeNameInput').show()
                    }
                })
                cfm.init = true
            }
        })
        $('#typeNameLi').on('tap', function () {
            typenameConfim.show()
        })

        //选择地址
        $('#actAddressLi').on('tap', function () {
            $pageMap.show(200)
            $pageMain.hide()
        })
        var $addrP = $('#actAddressLi p.ui-address')
        $('#okMapBtn').on('tap', function () {
            $pageMap.hide()
            $pageMain.show(200)
            var ad = baiduMap.getSelected()
            if (ad) {
                $pageMain.find('input[name=address]').val(ad.address)
                $pageMain.find('input[name=lat]').val(ad.point.lat)
                $pageMain.find('input[name=lng]').val(ad.point.lng)
                $pageMain.find('input[name=addrTitle]').val(ad.title)
                $addrP.show()
                $addrP.text(ad.title ? ad.title : ad.address)
            }
        })

        //报名方式
        $('select[name=joinType]').on('change', function () {
            var $self = $(this)
            var txt = $self.find('option[value=' + $self.val() + ']').text()
            $self.prev('span').text(txt)
        })

        var loading = ui.loading('请稍候...')

        //立即发布按钮
        $('#addBtn').on('tap', function () {
            var info = wxgh.serialize($pageMain.find('.main-form')[0])
            var verify_res = verify(info)
            if (verify_res) {
                alert(verify_res)
                return
            }
            loading.show()

            info['partyAct'] = 1
            info['groupid'] = groupId

            wx.uploadImage({
                localId: info['imgUrl'], // 需要上传的图片的本地ID，由chooseImage接口获得
                isShowProgressTips: 0,// 默认为1，显示进度提示
                success: function (res) {
                    var serverId = res.serverId; // 返回图片的服务器端ID
                    if (serverId) {
                        info['imgUrl'] = serverId
                        console.log(info)
                        wxgh.request.post('api/add.json', info, function (res) {
                            ui.showToast('发布成功', function (data) {
                                if (res.data) {
                                    mui.openWindow('show.html?id=' + data)
                                } else {
                                    window.location.reload(true)
                                }
                            })
                        })
                    } else {
                        ui.alert('活动封面上传失败');
                    }
                }
            });
        })

        function verify(info) {
            if (!info['imgUrl']) {
                return '请选择活动封面'
            }
            if (!info['name']) { //活动主题
                return '请输入活动主题哦'
            }
            if (!info['typeName']) {
                return '请选择活动事项'
            }
            if (!info['mobile']) {
                return '请输入联系电话'
            }
            if (!wxgh.checkPhoneNum(info['mobile'])) {
                return '手机号码格式不正确'
            }
            if (!info['startTime']) {
                return '请选择开始时间'
            }
            if (!info['endTime']) {
                return '请选择结束时间'
            }
            if (info['startTime'] >= info['endTime']) {
                return '活动开始时间不能大于等于结束时间'
            }
            if (!info['address']) {
                return '请选择活动地址'
            }
            if (!info['joinType']) {
                return '请选择报名方式'
            }
            if (!info['info']) {
                return '请输入活动简介'
            }
        }
    })
</script>
</body>
</html>