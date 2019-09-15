<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/6
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>赛事编排</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet"
          href="${home}/libs/material-datetimepicker/css/bootstrap-material-datetimepicker.css"/>
    <style>
        .mui-content > .mui-table-view:first-child {
            margin-top: 0;
        }

        .ui-input-lable {
            padding: 4px 10px;
            display: block;
        }

        .mui-content-padded .mui-input-group {
            margin: 8px 0;
        }

        .mui-table-view-cell {
            padding: 8px 15px;
        }

        .mui-table-view-cell label {
            display: inline-block;
        }

        .mui-table-view-cell select,
        .mui-table-view-cell input[type=number],
        .mui-table-view-cell input {
            margin: 0;
            float: right;
            display: inline-block;
            width: auto;
            margin-right: 12px;
        }

        .mui-table-view-cell input[type=number],
        .mui-table-view-cell input {
            width: 50%;
            border: none;
            text-align: right;
        }

        .dtp-buttons {
            position: relative;
            top: -10px;
        }

        .ui-race-info span.fa {
            color: #0099e9;
            font-size: 16px;
        }

        .ui-race-info .mui-table-view-cell {
            padding: 6px 8px;
        }
        .mui-table-view-cell select {
            position: static;
            opacity: 1;
        }
    </style>
    <style>

        .ui-group-div .ui-white-bg,
        table {
            background-color: #fff;
        }

        .ui-group-div .ui-white-bg {
            padding: 2px 5px;
        }

        .ui-group-div .ui-table-div table {
            width: auto;
        }

        .ui-group-div table td,
        .ui-group-div table th {
            border: solid #cfcfcf;
            border-width: 0px 0.5px 0.5px 0px;
            padding: 10px 10px;
            min-width: 120px;
            word-break: break-all;
            word-wrap: break-word;
        }

        .ui-group-div table {
            border: solid #cfcfcf;
            border-width: 0.5px 0px 0px 0.5px;
        }

        .ui-group-div tr td:first-child {
            min-width: 60px;
        }
    </style>
</head>

<body>

<div class="mui-content ui-arrange-div">
    <ul class="mui-table-view ui-race-info">
        <li class="mui-table-view-cell">
            <label class="ui-input-lable"><span class="fa fa-trophy"></span> 比赛名称：</label>
            <small class="ui-text-info">${r.name}</small>
        </li>
        <li class="mui-table-view-cell">
            <label class="ui-input-lable"><span class="fa fa-flag-checkered"></span> 比赛项目：</label>
            <small class="ui-text-info">
                <small><c:if test="${r.cateType eq 1}">羽毛球 - ${r.raceType eq 1?'单打':'双打'}</c:if>
                    <c:if test="${r.cateType eq 2}">乒乓球 - ${r.raceType eq 1?'单打':'双打'}</c:if>
                    <c:if test="${r.cateType eq 3}">篮球</c:if>
                    <c:if test="${r.cateType eq 4}">网球</c:if></small>
            </small>
        </li>
        <li class="mui-table-view-cell">
            <label class="ui-input-lable"><span class="fa fa-tag"></span> 报名人数：</label>
            <small class="ui-text-info">${joinCount}${r.raceType eq 1?'人':'队'}</small>
        </li>
    </ul>

    <ul class="mui-table-view ui-race-bianpai ui-margin-top-15">
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <label class="ui-input-lable">选择赛制</label>
                <select name="saizhi">
                    <option value="0">请选择</option>
                    <option value="1">单循环</option>
                    <%--<option value="3">单淘汰</option>--%>
                    <option value="2">分组循环</option>
                </select>
            </a>
        </li>
        <li class="mui-table-view-cell mui-hidden" id="xunhuanType">
            <a class="mui-navigate-right">
                <label class="ui-input-lable">编排方式</label>
                <select name="xunhuan">
                    <option value="1">固定轮转</option>
                    <option value="2">贝格尔</option>
                </select>
            </a>
        </li>
        <li class="mui-table-view-cell mui-hidden" id="groupLi">
            <a class="mui-navigate-right">
                <label class="ui-input-lable ui-text-primary">分组抽签</label>
                <input type="hidden" name="groupIs" value="${r.groupIs}">
                <small class="mui-pull-right ui-text-info"
                       style="margin-right: 20px;margin-top: 8px;">${r.groupIs eq 1 ? '已分组':'未分组'}</small>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <label class="ui-input-lable">场地数量</label>
                <input name="addrNumb" type="number" class="mui-input-numbox" placeholder="请输入"/>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <label class="ui-input-lable">比赛开始日期</label>
                <input name="startTime" readonly class="ui-time-input" type="text" placeholder="请选择"/>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <label class="ui-input-lable">上午开始时间</label>
                <input name="amTime" type="text" readonly class="ui-time-select" placeholder="请选择">
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right">
                <label class="ui-input-lable">下午开始时间</label>
                <input name="pmTime" type="text" readonly class="ui-time-select" placeholder="请选择">
            </a>
        </li>
        <%--<li class="mui-table-view-cell">--%>
        <%--<a class="mui-navigate-right">--%>
        <%--<label class="ui-input-lable">每场地所需时长</label>--%>
        <%--<input name="time" type="number" class="mui-input-numbox" placeholder="请输入"/>--%>
        <%--</a>--%>
        <%--</li>--%>
    </ul>


    <div class="mui-content-padded">
        <button id="arrangeBtn" type="button" class="mui-btn mui-btn-primary mui-btn-block ui-btn">开始编排</button>
    </div>
</div>
</div>

<div class="mui-content ui-group-div" style="display: none;">
    <div class="mui-content-padded">
        <button id="groupBtn" type="button" class="mui-btn mui-btn-primary">分组抽签</button>
    </div>
    <div id="tabDiv"
         class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
        <div class="mui-scroll">
            <div class="ui-table-div">
                <table id="mainTable">
                </table>
            </div>
        </div>
    </div>
    <div class="mui-content-padded">
        <button id="groupOkBtn" type="button" class="mui-btn ui-btn mui-hidden mui-btn-primary mui-btn-block">确定分组
        </button>
    </div>
    <div class="mui-content-padded">
        <p>
            抽签规则介绍：<br>
            1.系统随机打乱参赛者的顺序，并采用蛇形分组法
        </p>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/moment-2.10.6/moment.min.js"></script>
<script type="text/javascript"
        src="${home}/libs/material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
<script type="text/javascript">

    var raceId = '${param.id}'
    var joinCount = '${joinCount}'
    var raceType = '${r.raceType}'

    var $arrange = $('.ui-arrange-div')
    var $group = $('.ui-group-div')

    window.addEventListener('popstate', function () {
        if ($group.css('display') != 'none') {
            $group.hide()
            $arrange.show()
        }
    })

    $(function () {
        $('.ui-time-input').bootstrapMaterialDatePicker({
            minDate: new Date(),
            clearButton: true,
            time: false,
            format: 'YYYY-MM-DD',
            cancelText: '上一步',
            okText: '确定',
            clearText: '取消'
        })
        $('.ui-time-select').bootstrapMaterialDatePicker({
            date: false,
            clearButton: true,
            format: 'HH:mm',
            cancelText: '上一步',
            okText: '确定',
            clearText: '取消'
        })
        $('#groupLi').on('tap', function () {
            $group.show()
            $arrange.hide()
            var url = window.location.href
            if (url.charAt(url.length - 1) != '#') {
                var state = {
                    title: "title",
                    url: "#"
                };
                window.history.pushState(state, "title", "#");
            }
            initSize()
        })
        $('select[name=saizhi]').on('change', function () {
            if ($(this).val() == 1) {
                $('#xunhuanType').removeClass('mui-hidden')
            } else {
                $('#xunhuanType').addClass('mui-hidden')
            }
            if ($(this).val() == 2) {
                $('#groupLi').removeClass('mui-hidden')
            } else {
                $('#groupLi').addClass('mui-hidden')
            }
        })

        $('#arrangeBtn').on('tap', function () {
            if (joinCount < 2) {
                alert('报名人数不能少于2人哦')
                return
            }

            var info = wxgh.serialize(wxgh.query('.mui-table-view.ui-race-bianpai'))
            var verifyRes = verify(info)
            if (verifyRes) {
                alert(verifyRes)
                return;
            }
            var url = homePath + '/wx/union/race/arrange/api/bianpai.json'
            info['action'] = 'bianpai'
            info['raceId'] = raceId
            mui.post(url, info, function (res) {
                if (res.ok) {
                    weui.showToast('编排成功', function () {
                        mui.openWindow(homePath + '/wx/union/race/arrange/result.html?id=' + raceId)
                    })
                } else {
                    alert(res.msg)
                }
            }, 'json')
        })

        function verify(info) {
            if (info['saizhi' == 0]) {
                return '请选择赛制哦'
            }
            if (!info['addrNumb']) {
                return '请输入场地数量'
            }
        }

        /**
         * 分组抽签
         */
        mui('.mui-scroll-wrapper').scroll({
            deceleration: 0.0005,
            scrollX: true,
            scrollY: false,
            indicators: false
        });
        $(window).on('resize', initSize())

        var $table = $('#mainTable')

        request('list')

        $('#groupBtn').on('tap', function () {
            request('group')
        })

        $('#groupOkBtn').on('tap', function () {
            $('input[name=groupIs]').val(1)
                .next().text('已分组')
            $group.hide()
            $arrange.show()
            var url = window.location.href
            if (url.charAt(url.length - 1) == '#') {
                window.history.back()
            }
        })

        function request(action) {
            var url = homePath + '/wx/union/race/arrange/api/'+action+'.json'
            mui.getJSON(url, {id: raceId, action: action}, function (res) {
                if (res.ok) {
                    var d = res.data
                    $table.empty()
                    if (d && d.length > 0) {
                        createTable(d)
                        $('#groupOkBtn').removeClass('mui-hidden')
                    } else {
                        $table.append('<tr><td class="mui-text-center" colspan="3">暂未分组哦</td></tr>')
                        $('#groupOkBtn').addClass('mui-hidden')
                    }
                    if (action != 'list') {
                        initSize()
                    }
                }
            })
        }

        function createTable(d) {
            for (var i in d) {
                var $tr = $('<tr></tr>')
                var des = d[i].details
                var g = d[i].group
                $tr.append('<td>' + g.name + '</td>')
                for (var j in  des) {
                    $tr.append('<td>' + des[j].name + '</td>')
                }
                $table.append($tr)
            }
        }

        function initSize() {
            var $tabDiv = $('#tabDiv')
            $tabDiv.css('height', $tabDiv.find('.ui-table-div').outerHeight())
        }
    })
</script>
</body>

</html>
