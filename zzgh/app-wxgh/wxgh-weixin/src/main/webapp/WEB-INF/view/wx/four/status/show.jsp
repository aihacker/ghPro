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

<c:if test="${p.status eq 0}">
    <div class="ui-fixed-bottom">
        <button id="addBtn" type="button" class="mui-btn mui-btn-block mui-btn-danger ui-btn">删除申请</button>
    </div>
</c:if>


<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    var id = '${param.id}'

    $(function () {
        <c:if test="${p.status eq 0}">
        $('#addBtn').on('tap', function () {
            var cf = confirm('是否删除呢？')
            if (cf) {
                mui.getJSON(homePath + '/wx/four/status/del.json', {action: 'del', id: id}, function (res) {
                    if (res.ok) {
                        wxgh.showToast('删除成功', function () {
                            mui.back()
                        })
                    } else {
                        alert('删除失败')
                    }
                })
            }
        })
        </c:if>
    })
</script>
</body>
</html>
