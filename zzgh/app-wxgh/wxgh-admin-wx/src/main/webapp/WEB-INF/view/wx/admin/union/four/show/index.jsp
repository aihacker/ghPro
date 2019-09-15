<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/20
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>台账申请详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet"
          href="${home}/libs/material-datetimepicker/css/bootstrap-material-datetimepicker.css"/>
    <style>
        .mui-table-view .mui-table-view-cell {
            padding: 5px 15px;
        }

        .mui-table-view-cell small {
            float: right;
        }

        .mui-content {
            padding-bottom: 45px;
        }

        .ui-fixed-bottom {
            z-index: 1;
        }

        .dtp-buttons {
            position: relative;
            top: -10px;
        }

        .ui-textarea-div {
            border: 1px solid #ddd;
        }

        .weui_dialog_bd .mui-table-view .mui-table-view-cell {
            padding: 8px 15px;
        }

        .weui_dialog_bd .mui-table-view-cell small {
            position: relative;
            top: 2px;
            margin-right: 15px;
        }

        .weui_dialog_bd .mui-table-view-cell select {
            position: absolute;
            left: 0;
            opacity: 0;
            padding: 0;
            margin-bottom: 0;
        }

        .weui_dialog_bd .mui-table-view-cell .mui-numbox {
            float: right;
        }

        .weui_dialog_bd .mui-table-view-cell .ui-textarea-div {
            margin-top: 4px;
        }

        .weui_dialog_bd .mui-table-view-cell .ui-textarea-div textarea {
            font-size: 12px;
        }

        .weui_dialog_bd .mui-table-view-cell:before,
        .weui_dialog_bd .mui-table-view-cell:after,
        .weui_dialog_bd .mui-table-view:before,
        .weui_dialog_bd .mui-table-view:after {
            height: 0;
        }

        .ui-top {
            position: relative;
            top: 5px;
        }

        .weui_dialog_bd .mui-table-view-cell .ui-right-input {
            width: 50%;
            margin-bottom: 0;
            float: right;
            text-align: right;
            padding: 0;
            height: 35px;
            border: none;
            font-size: 12px;
        }

        li.mui-table-view-cell.mui-active.ui-no {
            background: #fff;
        }

        .weui_dialog .weui_dialog_bd {
            max-height: 250px;
            overflow: auto;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="mui-content-padded mui-text-center">
        <span class="fa fa-building"></span> ${p.marketing}
    </div>

    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <span>四小项目</span>
            <small>${p.fpName}</small>
        </li>
        <li class="mui-table-view-cell">
            <span>项目内容</span>
            <small>${p.fpcName} x${p.numb}</small>
        </li>
        <li class="mui-table-view-cell">
            <span>品牌</span>
            <small>${empty p.brand?'无':p.brand}</small>
        </li>
        <li class="mui-table-view-cell">
            <span>规格型号</span>
            <small>${empty p.modelName?'无':p.modelName}</small>
        </li>
        <li class="mui-table-view-cell">
            <span>预算单价</span>
            <small>${p.budget}元/${p.unit}</small>
        </li>
    </ul>
    <ul class="mui-table-view ui-margin-top-15">
        <li class="mui-table-view-cell">
            <span>申请类型</span>
            <small>${p.deviceStatus eq 1 ? '新增':'更换'}</small>
        </li>
        <li class="mui-table-view-cell">
            <span>申请人</span>
            <small>${p.username} - ${p.deptName}</small>
        </li>
        <li class="mui-table-view-cell">
            <span>备注</span>
            <p>${empty p.remark?'无备注':p.remark}</p>
        </li>
    </ul>

    <c:if test="${p.status gt 0}">
        <ul class="mui-table-view ui-margin-top-15">
            <c:if test="${p.status eq 1}">
                <li class="mui-table-view-cell">
                    <span>是否入库</span>
                    <small>${p.addIs eq 1?'<b class="ui-text-success">已入库</b>':'<b class="ui-text-danger">未入库</b>'}</small>
                </li>
            </c:if>
            <li class="mui-table-view-cell">
                <span>审核结果</span>
                <small>${p.status eq 1?'<b class="ui-text-success">已通过</b>':'<b class="ui-text-danger">未通过</b>'}</small>
            </li>
            <li class="mui-table-view-cell">
                <span>审核意见</span>
                <p>${empty p.suggest?'无审核意见':p.suggest}</p>
            </li>
        </ul>
    </c:if>
</div>

<%--<c:choose>--%>
    <%--<c:when test1="${p.status eq 1}">--%>
        <%--<c:if test1="${p.addIs eq 0}">--%>
            <%--<div class="ui-fixed-bottom">--%>
                <%--<button id="addBtn" type="button" class="mui-btn mui-btn-block mui-btn-primary ui-btn">台账入库</button>--%>
            <%--</div>--%>
        <%--</c:if>--%>
    <%--</c:when>--%>
    <%--<c:otherwise>--%>
        <div class="ui-bottom-btn-group">
            <button data-status="2" type="button" class="mui-btn mui-btn-danger">审核失败</button>
            <button data-status="1" type="button" class="mui-btn mui-btn-success">审核通过</button>
        </div>
    <%--</c:otherwise>--%>
<%--</c:choose>--%>

<script type="text/template" id="addConfirm">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>购置时间：</span>
                <input name="buyTime" readonly style="margin-right: 15px;" type="text"
                       class="ui-time-input ui-right-input"
                       placeholder="请选择">
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>设备情况</span>
                <small>良好</small>
                <select name="condit">
                    <option value="良好">良好</option>
                    <option value="可以使用">可以使用</option>
                    <option value="需要更换">需要更换</option>
                </select>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>资产所属</span>
                <small>请选择</small>
                <select name="condStr">
                    <option>请选择</option>
                    <option value="工会">工会</option>
                    <option value="企业">企业</option>
                </select>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <span>资金来源</span>
                <small>请选择</small>
                <select name="priceSource">
                    <option>请选择</option>
                    <option value="福利费">福利费</option>
                    <option value="工会经费">工会经费</option>
                    <option value="资本投资">资本投资</option>
                </select>
            </a>
        </li>
        <li class="mui-table-view-cell ui-no">
            <span class="ui-top">更新年限</span>
            <div class="mui-numbox" data-numbox-min='0'>
                <button class="mui-btn mui-numbox-btn-minus" type="button">-</button>
                <input value="${d.planUpdate}" name="planUpdate" class="mui-numbox-input" type="number"/>
                <button class="mui-btn mui-numbox-btn-plus" type="button">+</button>
            </div>
        </li>
        <li class="mui-table-view-cell ui-no">
            <span class="ui-top">使用年限</span>
            <div class="mui-numbox" data-numbox-min='0'>
                <button class="mui-btn mui-numbox-btn-minus" type="button">-</button>
                <input value="${d.usefulLife}" name="usefulLife" class="mui-numbox-input" type="number"/>
                <button class="mui-btn mui-numbox-btn-plus" type="button">+</button>
            </div>
        </li>
        <li class="mui-table-view-cell ui-no">
            <span class="ui-top">预算单价</span>
            <input value="${d.price}" name="price" type="number" class="mui-numbox-input ui-right-input"
                   placeholder="请输入"/>
        </li>
        <li class="mui-table-view-cell ui-no">
            <span>备注</span>
            <div class="ui-textarea-div">
                <textarea name="remark" rows="2" placeholder="请输入备注信息">${d.remark}</textarea>
                <small>0 / 100</small>
            </div>
        </li>
    </ul>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/script/confirm.js"></script>
<script type="text/javascript" src="${home}/libs/moment-2.10.6/moment.min.js"></script>
<script type="text/javascript"
        src="${home}/libs/material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
<script>
    var id = '${param.id}'
    var deviceStatus = '${param.deviceStatus}'

    var condStr = '${d.condStr}'
    var priceSource = '${d.priceSource}'
    $(function () {
        var html = '<div class="ui-textarea-div">' +
            '<textarea rows="4" placeholder="您的审核意见"></textarea>' +
            '<small>0 / 200</small></div>'
        var applyConfirm = new Confirm('审核意见', html, function ($dialog) {
            var $input = $dialog.find('textarea')
            apply($input.val(), $dialog.data('status'))
        }, function (cfm) {
            if (!cfm.init) {
                wxgh.autoTextarea(cfm.$dialog.find('.ui-textarea-div textarea'), 200)
                cfm.init = true
            }
        })
        <c:if test="${p.status eq 1}">
        var addConfirm = new Confirm('添加台账', $('#addConfirm').html(), function ($dialog) {
            var info = wxgh.serialize($dialog[0])
            var verify_res = verify(info)
            if (verify_res) {
                alert(verify_res)
                return
            }
            info['deviceStatus'] = deviceStatus
            info['id'] = id

            var url = homePath + '/wx/admin/union/four/show/add.json'
//            info['action'] = 'add'
            mui.post(url, info, function (res) {
                addConfirm.hide()
                if (res.ok) {
                    ui.showToast('台账入库成功', function () {
                        window.location.reload(true)
                    })
                } else {
                    console.log(res.msg)
                    alert('入库失败')
                }
            }, 'json')

        }, function (cfm) {
            if (!cfm.init) {
                wxgh.autoTextarea(cfm.$dialog.find('.ui-textarea-div textarea'), 100)
                cfm.$dialog.find('.ui-time-input').bootstrapMaterialDatePicker({
                    minDate: new Date(),
                    clearButton: true,
                    format: 'YYYY-MM-DD',
                    cancelText: '上一步',
                    okText: '确定',
                    clearText: '取消',
                    time: false
                })
                cfm.$dialog.on('change', 'select', function () {
                    var val = $(this).val()
                    $(this).prev().text(val)
                })

                if (condStr) {
                    var $condStrSeclect = cfm.$dialog.find('select[name=condStr]')
                    $condStrSeclect.find('option').attr('selected', false)
                    $condStrSeclect.find('option[value=' + condStr + ']').attr('selected', true)
                    $condStrSeclect.prev().text(condStr)
                }
                if (priceSource) {
                    var $priceSourceSelect = cfm.$dialog.find('select[name=priceSource]')
                    $priceSourceSelect.find('option').attr('selected', false)
                    $priceSourceSelect.find('option[value=' + priceSource + ']').attr('selected', true)
                    $priceSourceSelect.prev().text(priceSource)
                }
                mui('.mui-numbox').numbox();
                cfm.init = true
            }
        })
        $('#addBtn').on('tap', function () {
            addConfirm.show(false)
        })

        function verify(info) {
            if (!info['buyTime']) {
                return '请选择购置时间'
            } else if (!info['condit']) {
                return '请选择设备情况'
            } else if (info['condStr'] == '请选择') {
                return '请选择资产所属'
            } else if (info['priceSource'] == '请选择') {
                return '请选择资金来源'
            } else if (!info['planUpdate']) {
                return '请输入更新年限'
            } else if (!info['usefulLife']) {
                return '请输入使用年限'
            } else if (!info['price']) {
                return '请输入预算单价'
            }
        }

        </c:if>

        $('.ui-bottom-btn-group').on('tap', 'button', function () {
            var status = $(this).attr('data-status')
            var msg
            if (status == 1) {
                msg = '是否通过该审核？'
            } else {
                msg = '是否不通过该审核？'
            }
            if (confirm(msg)) {
                applyConfirm.$dialog.data('status', status)
                applyConfirm.show()
            }
        })

        function apply(suggest, status) {
            var url = homePath + '/wx/admin/union/four/show/apply.json'
            var info = {
//                action: 'apply',
                id: id,
                suggest: suggest,
                status: status
            }
            mui.post(url, info, function (res) {
                if (res.ok) {
                    ui.showToast('审核成功', function () {
                        window.location.reload()
                    })
                } else {
                    alert('审核失败')
                }
            }, 'json')
        }
    })
</script>
</body>
</html>
