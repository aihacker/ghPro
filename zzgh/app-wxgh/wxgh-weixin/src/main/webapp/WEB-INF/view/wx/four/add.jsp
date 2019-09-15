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
    <title>新增需求申请</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
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
        }

        .weui_dialog_bd {
            max-height: 280px;
            overflow-y: auto;
        }

        .mui-navigate-right select {
            opacity: 0;
            width: 100%;
            position: absolute;
            left: 0;
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
    </style>
</head>

<body>

<div class="mui-content">
    <ul id="mainUl" class="mui-table-view">
        <li id="addType" class="mui-table-view-cell mui-hidden">
            <a class="mui-navigate-right">
                <span>需求状态：</span>
                <small class="ui-select-small">请选择</small>
                <select name="addType">
                    <option value="1">新增</option>
                    <option value="2">更换</option>
                </select>
            </a>
        </li>
        <li data-type="dept" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>区公司：</span>
                <small class="ui-select-small">请选择</small>
                <select name="deptname">
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
                <small>请选择</small>
            </a>
        </li>
        <li style="display: none;" data-type="id" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>更换的台账：</span>
                <small>请选择</small>
            </a>
        </li>
        <li data-type="fp" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>项&nbsp;&nbsp;目：</span>
                <small>请选择</small>
            </a>
        </li>
        <li data-type="fpc" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>项目内容：</span>
                <small>请选择</small>
            </a>
        </li>
        <li data-type="brand" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>品牌：</span>
                <small>请选择</small>
            </a>
        </li>
        <li data-type="modal" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>规格型号：</span>
                <small>请选择</small>
            </a>
        </li>
        <li data-type="numb" class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>数量：</span>
                <small>请输入</small>
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
        <li class="mui-table-view-cell">
            <div class="textare-div">
                <label>备注说明</label>
                <textarea name="remark" rows="4" placeholder="备注说明（50字以内）"></textarea>
            </div>
        </li>
    </ul>
    <input type="hidden" name="id">
    <div class="ui-fixed-bottom">
        <button id="addBtn" type="button" class="mui-btn ui-btn mui-btn-primary mui-btn-block">立即发布</button>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/weixin/script/xlkai/confirm.js"></script>
<script>
    var _self = homePath + '/wx/four/add1.json'

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

        $('select[name=deptname]').on('change', function () {
            var val = $(this).val()
            if (val != 0) {
                $('li[data-type=dept]').data('data', val)
            } else {
                $('li[data-type=dept]').removeData('data')
            }
            var txt = $(this).find('option[value=' + val + ']').text()
            $(this).prev().text(txt)
        })

        var $addType = $('select[name=addType]')
        $addType.data('type', 1)
        /* $addType.prev().text('新增')
         $addType.on('change', function () {
         var val = $(this).val()
         $addType.data('type', val)
         $('#addType').find('small').text($(this).find('option[value=' + val + ']').text())
         if (val == 1) {
         $('li[data-type=fp]').show()
         $('li[data-type=fpc]').show()
         $('li[data-type=brand]').show()
         $('li[data-type=modal]').show()
         $('li[data-type=id]').hide()
         } else {
         $('li[data-type=fp]').hide()
         $('li[data-type=fpc]').hide()
         $('li[data-type=brand]').hide()
         $('li[data-type=modal]').hide()
         $('li[data-type=id]').show()
         }
         $.each($('#mainUl li[data-type]'), function () {
         $(this).removeData('data')
         $(this).find('small').text('请选择')
         })
         $('input[name=price]').val('').prop('readonly', false)
         $('input.ui-unit').prop('readonly', false)
         })*/

        var loadingHtml = '<div class="mui-loading"><div class="mui-spinner"></div></div>'

        var marketFm = createConfrim('选择营销中心', 'market', '暂无营销中心')

        var fpFm = createConfrim('选择四小项目', 'fp', '暂无四小项目')

        var fpcFm = createConfrim('选择项目内容', 'fpc', '暂无项目内容', '请输入新增项目内容')

        var brandFm = createConfrim('选择品牌', 'brand', '暂无品牌', '请输入新增品牌')

        var modalFm = createConfrim('选择规格型号', 'modal', '暂无规格型号', '请输入新增规格型号')

        var outFm = new Confirm('选择需要更换台账', loadingHtml, function ($dialog) {
            var $li = $dialog.find('.mui-table-view.mui-table-view-radio .mui-table-view-cell.mui-selected')
            if ($li.length > 0) {
                var d = $li.data('data')

                var txt = d.fpName + '-' + d.fpcName
                var $mli = $('li[data-type=id]')
                $mli.find('small').text(txt)
                $mli.show()
                $mli.data('data', d)

                var $numbLi = $('li[data-type=numb]')
                $numbLi.data('data', {numb: d.numb, unit: d.unit})
                $numbLi.find('small').text(d.numb + d.unit)

                $('input[name=price]').val(d.price).prop('readonly', true)
                $('input.ui-unit').prop('readonly', true)
                $('input[name=id]').val(d.id)
            } else {
                alert('请选择台账')
            }
        }, function (cfm) {
            var mid = $('li[data-type=market]').data('data').id
            mui.getJSON(homePath + '/wx/four/list_out.json', {id: mid}, function (res) {
                if (res.ok) {
                    var ds = res.data
                    if (ds && ds.length > 0) {
                        var $ul = $('<ul class="mui-table-view mui-table-view-radio"></ul>')
                        for (var i in ds) {
                            var d = ds[i]
                            var $li = $('<li class="mui-table-view-cell"><a class="mui-navigate-right">' +
                                '<p class="mui-ellipsis">' + d.fpcName +
                                '<span class=" mui-pull-right">品牌：' + (d.brand ? d.brand : '无') + '</span>' +
                                '</p><p class="mui-ellipsis">' +
                                '<span class="buytime-span">项目：' + d.fpName + '</span>' +
                                '</p><p class="mui-ellipsis">' +
                                '<span class="buytime-span">型号：' + d.modelName + '</span>' +
                                '</p><p class="mui-ellipsis">' +
                                '<span class="">采购时间：' + d.buyTime + '</span>' +
                                '</p></a></li>')
                            $li.data('data', d)
                            $ul.append($li)
                        }
                        cfm.setContent($ul)
                    } else {
                        cfm.setContent('<div class="mui-content-padded mui-text-center">该营销中心今年无可更换设备哦</div>')
                    }
                }
            })
        })

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

                setTotalPrice()
            }
        }, function (cfm) {
            var data = $('li[data-type=numb]').data('data')
            if (data) {
                cfm.$dialog.find('input.ui-numb').val(data.numb)
                cfm.$dialog.find('input.ui-unit').val(data.unit)
            }
        })

        $('#addBtn').on('tap', function () {
            var deviceStatus = $addType.data('type')

            var info = {}
            info['deviceStatus'] = deviceStatus

            var midData = $('li[data-type=market]').data('data')
            if (midData) {
                info['mid'] = midData.id ? midData.id : 0;
            } else {
                alert('请选择营销中心')
                return
            }
            if (deviceStatus == 1) {
                var fpIdData = $('li[data-type=fp]').data('data')
                if (fpIdData) {
                    info['fpId'] = fpIdData.id ? fpIdData.id : 0
                } else {
                    alert('请选择四小项目')
                    return
                }

                var fpcData = $('li[data-type=fpc]').data('data')
                if (fpcData) {
                    info['fpcId'] = fpcData.id ? fpcData.id : 0
                    info['fpcName'] = fpcData.name
                } else {
                    alert('请选择项目内容')
                    return
                }

                var brandData = $('li[data-type=brand]').data('data')
                if (brandData) {
                    info['brand'] = brandData.name
                } else {
                    alert('请选择品牌')
                    return
                }

                var modelData = $('li[data-type=modal]').data('data')
                if (modelData) {
                    info['modelName'] = modelData.name
                } else {
                    alert('请输入规格型号')
                    return
                }
            } else {
                var deId = $('input[name=id]').val()
                if (deId) {
                    info['deId'] = deId?deId:0
                } else {
                    alert('请选择需要更换的设备')
                    return
                }
            }

            var numbData = $('li[data-type=numb]').data('data')
            if (numbData) {
                info['numb'] = numbData.numb
                info['unit'] = numbData.unit
            } else {
                alert('请输入数量')
                return
            }

            var price = $('input[name=price]').val()
            if (price) {
                info['budget'] = price
            } else {
                alert('请输入价格')
                return
            }

            var self = this
            if (!self.loading) {
                self.loading = ui.loading('添加中...')
            }
            self.loading.show()

            var reamark = $('textarea[name=remark]').val()
            info['remark'] = reamark

           // info['action'] = 'add'
            mui.post(homePath + '/wx/four/add_propagate.json', info, function (res) {
                self.loading.hide()
                if (res.ok) {
                    ui.showToast('申请成功，请静候管理员审核~', function () {
                        //window.location.reload(true)
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
            } else if (type == 'brand') {
                brandFm.show()
            } else if (type == 'modal') {
                modalFm.show()
            } else if (type == 'numb') {
                numbFm.show()
            } else if (type == 'id') {
                outFm.show()
            }
        })

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

                var status = $addType.data('type')
                //如果为更换
                if (type == 'market' && status == 2) {
                    outFm.show()
                }
            }, function (cfm) {
                var status = $addType.data('type')

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
                    info['brand'] = $('li[data-type=brand]').data('data').name
                }
                var url = homePath + '/wx/four/list_'+type+'.json';
                mui.getJSON(url, info, function (res) {
                    if (res.ok) {
                        var ms = res.data
                        cfm.setContent('')
                        if (status == 1 && (type != 'market' && type != 'fp')) {
                            cfm.setContent(htm)
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
    })
</script>
</body>
</html>
