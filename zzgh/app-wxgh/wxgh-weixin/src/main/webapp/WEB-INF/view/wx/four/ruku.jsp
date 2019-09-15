<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/12
  Time: 15:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>台账入库申请</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet"
          href="${home}/libs/material-datetimepicker/css/bootstrap-material-datetimepicker.css"/>
    <style>
        .mui-table-view select,
        input[type=number] {
            margin-bottom: 0;
        }

        .mui-navigate-right small {
            float: right;
            margin-right: 20px;
            color: #777;
        }

        .mui-navigate-right input {
            width: 50%;
            float: right;
            border: none;
            text-align: right;
            margin-right: 5px;
            margin-bottom: 0;
        }

        .weui_dialog_bd {
            max-height: 280px;
            overflow-y: auto;
        }

        .mui-navigate-right select {
            opacity: 0;
        }

        .ui-select-small {
            position: absolute;
            right: 20px;
            top: 20px;
        }

        .ui-confirm-plus {
            position: fixed;
            top: 55px;
            left: 0;
            z-index: 99;
            text-align: center;
            width: 90%;
            margin-left: 5%;
        }

        .ui-confirm-plus input {
            margin-bottom: 0;
            border: none;
            border-bottom: 1px solid rgba(0, 0, 0, .5);
        }

        .weui_dialog_confirm .weui_dialog .weui_dialog_bd {
            position: relative;
        }

        .ui-confirm-numb input {
            display: inline-block;
            width: 35%;
            margin-bottom: 0;
        }

        .ui-confirm-numb input:first-child {
            margin-left: 14%;
        }

        .ui-confirm-numb input:last-child {
            margin-left: 2%;
        }

        .ui-fixed-bottom {
            z-index: auto;
        }

        .textare-div {
            margin: 0;
        }

        .textare-div textarea {
            margin-bottom: 0;
        }

        #mainUl {
            padding-bottom: 45px;
        }

        .dtp-buttons {
            position: relative;
            top: -10px;
        }

        .ui-images div img {
            width: 100%;
            height: 100%;
        }

        #chooseBtn {
            display: inline-block;
        }

        #chooseBtn img {
            width: 80px;
            height: 80px;
        }

        .ui-images .ui-img-div {
            width: 80px;
            height: 80px;
            display: inline-block;
            margin: 2px;
        }
    </style>
</head>

<body>

<div class="mui-content">
    <ul id="mainUl" class="mui-table-view">
        <li data-type="dept" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>区公司：</span>
                <small class="ui-select-small">请选择</small>
                <select name="deptName">
                    <option value="0">请选择</option>
                    <c:forEach items="${depts}" var="d">
                        <option value="${d.deptid}">${d.name}</option>
                    </c:forEach>
                </select>
            </a>
        </li>
        <li data-type="market" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>营销中心：</span>
                <input type="hidden" name="mid">
                <small>请选择</small>
            </a>
        </li>
        <li data-type="fp" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>项&nbsp;&nbsp;目：</span>
                <input type="hidden" name="fpId">
                <small>请选择</small>
            </a>
        </li>
        <li data-type="fpc" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>项目内容：</span>
                <input type="hidden" name="fpcId">
                <input type="hidden" name="fpcName">
                <small>请选择</small>
            </a>
        </li>
        <li data-type="brand" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>品牌：</span>
                <input type="hidden" name="brand">
                <small>请选择</small>
            </a>
        </li>
        <li data-type="modal" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>规格型号：</span>
                <input type="hidden" name="modelName">
                <small>请选择</small>
            </a>
        </li>
        <li data-type="numb" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>数量：</span>
                <input type="hidden" name="numb">
                <input type="hidden" name="unit">
                <small>请输入</small>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>购置时间：</span>
                <input name="buyTime" type="text" class="mui-input-numbox ui-time-input" placeholder="请选择"/>
            </a>
        </li>
        <li class="mui-table-view-cell ui-selc">
            <a class="mui-navigate-right">
                <span>设备情况：</span>
                <small class="ui-select-small">请选择</small>
                <select name="condit">
                    <option>请选择</option>
                    <option>良好</option>
                    <option>可以使用</option>
                    <option>需要更换</option>
                </select>
            </a>
        </li>
        <li class="mui-table-view-cell ui-selc">
            <a class="mui-navigate-right">
                <span>资产所属：</span>
                <small class="ui-select-small">请选择</small>
                <select name="condStr">
                    <option>请选择</option>
                    <option>工会</option>
                    <option>企业</option>
                </select>
            </a>
        </li>
        <li class="mui-table-view-cell ui-selc">
            <a class="mui-navigate-right">
                <span>资金来源：</span>
                <small class="ui-select-small">请选择</small>
                <select name="priceSource">
                    <option>请选择</option>
                    <option>福利费</option>
                    <option>工会经费</option>
                    <option>资本投资</option>
                </select>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>计划更新时间：</span>
                <input name="planUpdate" type="text" class="mui-input-numbox ui-time-input" placeholder="请选择"/>
                <%--<input name="planUpdate" type="number" class="mui-input-numbox" placeholder="请输入"/>--%>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>使用年限：</span>
                <input name="usefulLife" type="number" class="mui-input-numbox" placeholder="请输入"/>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>预算单价<i style="font-size: 13px;color: #777;">(元)</i>：</span>
                <input name="price" type="number" class="mui-input-numbox" placeholder="请输入"/>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>预算总价：</span>
                <small id="totalPrice">0元</small>
            </a>
        </li>
        <li class="mui-table-view-cell" style="height: initial; ">
            <a>
                <span>台账图片</span>
                <div class="ui-images">
                    <div id="chooseBtn" class="mui-col-sm-3 mui-col-xs-3">
                        <img src="${home}/weixin/image/four/icon_add1.png"/>
                    </div>
                </div>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <div class="textare-div">
                <label>备注说明</label>
                <textarea name="remark" rows="4" placeholder="备注说明（50字以内）"></textarea>
            </div>
        </li>
    </ul>
    <input type="hidden" name="id">
    <div class="ui-fixed-bottom">
        <button id="addBtn" type="button" class="mui-btn ui-btn mui-btn-primary mui-btn-block">立即申请</button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/weixin/script/xlkai/confirm.js"></script>
<script type="text/javascript" src="${home}/libs/moment-2.10.6/moment.min.js"></script>
<script type="text/javascript"
        src="${home}/libs/material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script>


    $(function () {

        wxgh.init('${weixin}')


        $('textarea[name=remark]').keyup(function () {
            var val = $(this).val()
            if (val.length > 50) {
                $(this).val(val.substring(0, 50))
                alert('不能超过50字符哦')
            }
        })

        var $totalPrice = $('#totalPrice')
        $('input[name=price]').keyup(function () {
            setTotalPrice()
        })

        function setTotalPrice() {
            var val = $('input[name=price]').val()
            var numData = $('li[data-type=numb]').data('data')
            if (numData && val) {
                $totalPrice.text(Number(val) * Number(numData.numb) + '元')
            }
        }

        $('select[name=deptName]').on('change', function () {
            var val = $(this).val()
            if (val != 0) {
                $('li[data-type=dept]').data('data', val)
            } else {
                $('li[data-type=dept]').removeData('data')
            }

            var $mli = $('li[data-type=market]')
            $mli.find('small').text('请选择')
            $mli.find('input[name=mid]').val('')

            var txt = $(this).find('option[value=' + val + ']').text()
            $(this).prev().text(txt)
        })

        $('.ui-time-input').bootstrapMaterialDatePicker({
            minDate: new Date(),
            clearButton: true,
            format: 'YYYY-MM-DD',
            cancelText: '上一步',
            okText: '确定',
            clearText: '取消',
            time: false
        })

        var addType = 1
        var $uplImgs = $('.ui-images')

        var loadingHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>'

        var marketFm = createConfrim('选择营销中心', 'market', '暂无营销中心')

        var fpFm = createConfrim('选择四小项目', 'fp', '暂无四小项目')

        var fpcFm = createConfrim('选择项目内容', 'fpc', '暂无项目内容', '请输入新增项目内容')

        var brandFm = createConfrim('选择品牌', 'brand', '暂无品牌', '请输入新增品牌')

        var modalFm = createConfrim('选择规格型号', 'modal', '暂无规格型号', '请输入新增规格型号')

        var numHtml = '<div class="ui-confirm-numb">' +
            '<input class="ui-numb mui-input-numbox" type="number" placeholder="数量"/>' +
            '<input class="ui-unit" type="text" placeholder="单位"/>' +
            '</div>'
        var numbFm = new Confirm('输入数量', numHtml, function ($dialog) {
            var num = $dialog.find('input.ui-numb').val()
            var unit = $dialog.find('input.ui-unit').val()
            if (num && unit) {
                var $mli = $('li[data-type=numb]')
                $mli.data('data', {numb: num, unit: unit})
                $mli.find('small').text(num + unit)
                $mli.find('input[name=numb]').val(num)
                $mli.find('input[name=unit]').val(unit)

                setTotalPrice()
            }
        }, function (cfm) {
            var data = $('li[data-type=numb]').data('data')
            if (data) {
                cfm.$dialog.find('input.ui-numb').val(data.numb)
                cfm.$dialog.find('input.ui-unit').val(data.unit)
            }
        })

        $('.ui-selc').on('change', 'select[name]', function () {
            var $self = $(this)
            $self.prev('small').text($self.val())
        })

        var loading = ui.loading('添加中...')
        $('#addBtn').on('tap', function () {
            var info = wxgh.serialize($('#mainUl')[0])
            var verify_res = verify(info)
            if (verify_res) {
                alert(verify_res)
                return
            }

            loading.show()


            //上传图片
            var localIds = []
            $.each($uplImgs.find('.ui-img-div img'), function () {
                var id = $(this).attr('src')
                if (id) localIds.push(id)
            })
            if (localIds.length > 0) {
                var mediaIds = []
                uploadImgs(mediaIds, localIds, function () {
                    info['imgs'] = mediaIds.toString()
                    requestAdd(info)
                })
            } else {
                requestAdd(info)
            }
        })

        $('#mainUl').on('tap', '.mui-table-view-cell', function () {
            var type = $(this).attr('data-type')
            if (type != 'numb' && !verifyChoose($(this))) {
                alert('请补充前面信息')
                return
            }
            if (type == 'market') {
                marketFm.show()
            } else if (type == 'fp') {
                fpFm.show()
            } else if (type == 'fpc') {
                fpcFm.show()
                // fpc_list();
            } else if (type == 'brand') {
                brandFm.show()
            } else if (type == 'modal') {
                modalFm.show()
            } else if (type == 'numb') {
                numbFm.show()
            }
        })

        var $fpc_input;
//        function fpc_list() {
//            var $mli = $('li[data-type=fpc]')
//            var _self = homePath + '/wx/four/list_fpc.json'
//        }

        function createConfrim(title, type, noTip, inputTip) {

            var htm = '<div class="ui-confirm-plus"><input type="text" placeholder="' + (inputTip ? inputTip : '') + '"/></div>'
            var marketFm = new Confirm(title, loadingHtml, function ($dialog) {
                var $li = $dialog.find('.mui-table-view.mui-table-view-radio .mui-table-view-cell.mui-selected')
                var $mli = $('li[data-type=' + type + ']')
                var $input = $dialog.find('.ui-confirm-plus input')
                var id, name
                if ($li.length > 0) {
                    id = $li.data('id')
                    name = $li.text()
                } else if ($input.length > 0 && $input.val()) {
                    name = $input.val()
                    id = ''
                }
                $mli.data('data', {id: id, name: name})
                $mli.find('small').text(name)
                if (type == 'fpc') {
                    if (!id) id = 0;
                    $mli.find('input[name=fpcId]').val(id)
                    $mli.find('input[name=fpcName]').val(name)
                } else if(type == 'brand'){
                    $mli.find('input[name=brand]').val(name)
                }else {
                    $mli.find('input').val(id == '' ? name : id)
                }
            }, function (cfm) {
                var status = addType
                var _self = homePath + '/wx/four/list_' + type + '.json'
                var info = {}
                if (status == 2 && type != 'market') {
                    info['mid'] = $('li[data-type=market]').data('data').id
                }
                if (type == 'market') {
                    info['deptid'] = $('li[data-type=dept]').data('data')
                }
                if (type == 'fpc') { //项目内容
                    info['fpId'] = $('li[data-type=fp]').data('data').id
                } else if (type == 'brand') {
                    info['fpId'] = $('li[data-type=fp]').data('data').id
                    var fpcData = $('li[data-type=fpc]').data('data')
                    info['fpcId'] = fpcData.id
                    info['name'] = fpcData.name
                } else if (type == 'modal') {
                    info['fpId'] = $('li[data-type=fp]').data('data').id
                    var fpcData = $('li[data-type=fpc]').data('data')
                    info['fpcId'] = fpcData.id
                    info['name'] = fpcData.name
                    console.log($('li[data-type=brand]'))
                    info['brand'] = $('li[data-type=brand]').data('data').name
                }

                mui.getJSON(_self, info, function (res) {
                    if (res.ok) {
                        var ms = res.data
                        cfm.setContent('')
                        if (status == 1 && (type != 'market' && type != 'fp')) {
                            cfm.setContent(htm)
                            // 输入框对象
//                            var $input = $(htm).find('.ui-confirm-plus input')
//                            if(type == 'fpc'){
//                                $fpc_input = $input;
//
//                            }

                        }
                        if (ms && ms.length > 0) {
                            var $ul = $('<ul class="mui-table-view mui-table-view-radio"></ul>')
                            for (var i in ms) {
                                var $li = $('<li class="mui-table-view-cell"></li>')
                                $li.html('<a class="mui-navigate-right">' + ms[i].name + '</a>')
                                $li.data('id', ms[i].id)
                                $ul.append($li)
                            }
                            cfm.appendContent($ul)
                        } else {
                            cfm.appendContent('<div class="mui-content-padded mui-text-center">' + noTip + '</div>')
                        }
                    }
                })
            })
            return marketFm
        }

        function verify(info) {
            if (!info['mid']) {
                return '请选择营销中心'
            }
            if (!info['fpId']) {
                return '请选择项目'
            }
            if (!info['fpcId'] || !info['fpcName']) {
                return '请选择项目内容'
            }
            if (!info['brand']) {
                return '请输入品牌'
            }
            if (!info['modelName']) {
                return '请输入规格型号'
            }
            if (!info['numb']) {
                return '请输入数量'
            }
            if (!info['unit']) {
                return '请输入数量单位'
            }
            if (!info['buyTime']) {
                return '请选择购置时间'
            }
            if (info['condit'] == '请选择') {
                return '请选择设备情况'
            }
            if (info['condStr'] == '请选择') {
                return '请选择资产所属'
            }
            if (info['priceSource'] == '请选择') {
                return '请选择资金来源'
            }
            if (!info['planUpdate']) {
                return '请输入更新年限'
            }
            if (!info['usefulLife']) {
                return '请输入使用年限'
            }
            if(info['usefulLife'] > 50){
                return '使用年限最多50年哦'
            }
            if (!info['price']) {
                return '请输入预算单价'
            }
        }

        function verifyChoose($li) {
            if (!$li.attr('data-type')) {
                return true
            }
            var res = true
            $.each($li.prevAll('li[data-type]'), function () {
                var type = $(this).attr('data-type')
                if (type == 'id') {
                    return true
                }
                if (!$(this).data('data')) {
                    res = false
                    return false
                }
            })
            return res
        }

        //上传图片相关
        $('#chooseBtn').on('tap', function () {
            wx.chooseImage({
                count: 9, // 默认9
                sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
                sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
                success: function (res) {
                    var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
                    $uplImgs.find('.ui-img-div').remove()
                    for (var i in localIds) {
                        createImgItem(localIds[i])
                    }
                }
            });
        })
        function createImgItem(localId) {
            var $item = $('<div class="ui-img-div"><img /></div>')
            wxgh.get_wx_img(localId, function (src) {
                $item.find('img').attr('src', src);
                $uplImgs.append($item)
            });
        }

        function uploadImgs(mediaIds, localIds, func) {

            var localId = localIds.pop()
            wx.uploadImage({
                localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
                isShowProgressTips: 0,// 默认为1，显示进度提示
                success: function (res) {
                    var serverId = res.serverId; // 返回图片的服务器端ID
                    if (serverId) {
                        mediaIds.push(serverId)
                        if (localIds.length > 0) {
                            uploadImgs(mediaIds, localIds, func)
                        } else {
                            if (func) func()
                        }
                    }
                }
            });
        }

        function requestAdd(info) {
            mui.post(homePath + '/wx/four/add_ruku.json', info, function (res) {
                loading.hide()
                if (res.ok) {
                    ui.showToast('请静候管理员审核~', function () {
//                        window.location.reload(true)
                        wxgh.redirectTip(homePath, {
                            msg: '您的申请已提交，请静候管理员的审核吧~',
                            //url: homePath + '',
                            title: '申请已提交',
                            urlMsg: '关闭页面'
                        });
                    })
                } else {
                    alert(res.msg)
                }
            })
        }
    })
</script>
</body>
</html>
