<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/7
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>编排详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet"
          href="${home}/libs/material-datetimepicker/css/bootstrap-material-datetimepicker.css"/>
    <style>
        *,
        body {
            color: #5b5b5b;
        }

        .ui-white-bg,
        table {
            background-color: #fff;
        }

        .ui-white-bg {
            padding: 2px 5px;
        }

        .ui-table-div table {
            width: auto;
        }

        table td,
        table th {
            border: solid #cfcfcf;
            border-width: 0px 0.5px 0.5px 0px;
            padding: 10px 10px;
            min-width: 120px;
            word-break: break-all;
            word-wrap: break-word;
        }

        table {
            border: solid #cfcfcf;
            border-width: 0.5px 0px 0px 0.5px;
        }

        td[rowspan] {
            min-width: 60px;
        }

        .ui-title {
            padding-bottom: 8px;
        }

        #lunkongDiv {
            margin-top: 20px;
            padding-bottom: 45px;
        }

        #lunkongDiv table tr td:first-child,
        #lunkongDiv table tr th:first-child {
            width: 80px;
            text-align: center;
        }

        td input[type=text],
        th input[type=text] {
            text-align: center;
            margin-bottom: 0;
            padding: 0;
            border: none;
            height: 35px;
        }

        td input[type=text].ui-active,
        th input[type=text].ui-active {
            border: 1px solid rgba(0, 0, 0, .2);
        }
    </style>
</head>

<body>

<div class="mui-content-padded">
    <h4 class="mui-text-center ui-title">编排详情</h4>
    <div id="tabDiv"
         class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
        <div class="mui-scroll">
            <div class="ui-table-div">
                <table id="mainTable">
                </table>
            </div>
        </div>
    </div>
    <div id="lunkongDiv" style="display: none;">
        <h4 class="mui-text-center ui-title">轮空名单</h4>
        <table class="mui-table">
        </table>
    </div>

    <c:if test="${userIs}">
        <div id="editBtn" class="ui-fixed-bottom">
            <button type="button" class="mui-btn mui-btn-block mui-btn-success ui-btn">编辑时间、场地</button>
        </div>
    </c:if>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/moment-2.10.6/moment.min.js"></script>
<script type="text/javascript"
        src="${home}/libs/material-datetimepicker/js/bootstrap-material-datetimepicker.js"></script>
<script type="text/javascript">

    var id = '${param.id}'
    var userIs = '${userIs}'

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005,
        scrollX: true,
        scrollY: false,
        indicators: false
    });

    $(function () {

        var $table = $('#mainTable')
        var isEdit = false

        var $dateSelct;

        request()

        initSize()
        $(window).on('resize', initSize())

        $('#editBtn').on('tap', 'button', function () {
            if (!$(this).hasClass('ui-click')) {
                $(this).addClass('ui-click')
                $(this).text('完成编辑')
                $table.find('input.ui-table-time').addClass('ui-active')
                $table.find('input.ui-table-address').addClass('ui-active').prop('readonly', false)
                isEdit = true
                $dateSelct = $('.ui-table-time').bootstrapMaterialDatePicker({
                    date: false,
                    clearButton: true,
                    format: 'HH:mm',
                    cancelText: '上一步',
                    okText: '确定',
                    clearText: '清除'
                })
            } else {
                if (!this.loading) {
                    this.loading = ui.loading('编辑中...')
                }
                this.loading.show()
                $.each($table.find('input[class^=ui-table]'), function () {
                    var old = $(this).attr('data-old')
                    var newsE = $(this).val()
                    var type = 1;
                    if ($(this).hasClass('ui-table-time')) {
                        old = $(this).attr('data-data') + ' ' + old
                        type = 2;
                    }
                    if (old != newsE) {
                        edit_request(type, old, newsE)
                    }
                })
                this.loading.hide()
                ui.showToast('编辑成功', function () {
                    isEdit = false
                    window.location.reload()
                })
//                $(this).removeClass('ui-click')
//                $(this).text('编辑时间、场地')
//                $table.find('input.ui-table-time').removeClass('ui-active')
//                $table.find('input.ui-table-address').removeClass('ui-active').prop('readonly', true)
            }
        })

        function request() {
            var url = homePath + '/wx/union/race/arrange/api/get.json'
            var info = {id: id, action: 'get'}
            wxgh.request.post(url, info, function (res) {
                initList(res.bianpais)
                initLunkong(res.lunkongs)
                initSize()
            })
        }

        function initLunkong(datas) {
            if (datas) {
                $('#lunkongDiv').show()
                var $table = $('#lunkongDiv table')
                $table.append('<tr><th>轮数</th><th>参赛队员</th></tr>')
                for (var k in datas) {
                    $table.append('<tr><td>第' + k + '轮</td><td>' + datas[k] + '</td></tr>')
                }
            } else {
                $('#lunkongDiv').hide()
            }
        }

        function initList(result) {
            var addrs = result.address
            var days = result.resultDays

            //标题
            var $titleTr = $('<tr></tr>')
            $table.append($titleTr)
            $titleTr.append('<th colspan="2">时间</th>')
            for (var i in addrs) {
                $titleTr.append('<th><input type="text" data-old="' + addrs[i] + '" class="ui-table-address" value="' + addrs[i] + '" readonly></th>')
            }

            //内容
            for (var j in days) {
                //时间
                var times = days[j].times

                var $tr = $('<tr></tr>')
                $table.append($tr)
                var dayStr = days[j].day
                $tr.append('<th rowspan="' + times.length + '">' + dayStr + '</th>')

                for (var a in times) {
                    var $mtr;
                    if (a == 0) {
                        $mtr = $tr
                    } else {
                        $mtr = $('<tr></tr>')
                        $table.append($mtr)
                    }
                    $mtr.append('<th> <input type="text" data-data="' + dayStr + '" data-old="' + times[a].time + '" class="ui-table-time" value="' + times[a].time + '" readonly></th>')
                    var arras = times[a].arrgs
                    for (var b in arras) {
                        var ite = arras[b]
                        var u1 = ite.user1
                        var u2 = ite.user2
                        if (!u1 && !u2) {
                            $mtr.append('<td>&nbsp;</td>')
                        } else {
                            $mtr.append('<td>' + (u1 ? u1 : '无') + ' VS ' + (u2 ? u2 : '无') + '</td>')
                        }
                    }
                }
            }
            if (userIs) {
                initEvent()
            }
        }

        function initSize() {
            var $tabDiv = $('#tabDiv')
            $tabDiv.css('height', $tabDiv.find('.ui-table-div').outerHeight())
        }

        function initEvent() {

            $table.on('tap', '.ui-table-time', function () {
                if (!isEdit) {
                    return false
                }
                var txt = $(this).text()
            })
            $table.on('tap', '.ui-table-address', function () {
                if (!isEdit) {
                    return false
                }
            })
        }

        function edit_request(type, old, newEdit) {
            var url = homePath + '/wx/union/race/arrange/api/edit.json'
            $.ajax({
                url: url,
                async: false,
                type: 'POST',
                dataType: 'json',
                data: {
                    action: 'edit',
                    id: id, old: old, edit: newEdit, type: type
                },
                success: function (res) {
                    if (!res.ok) {
                        alert('修改失败')
                    }
                }
            })
        }
    })
</script>
</body>

</html>
