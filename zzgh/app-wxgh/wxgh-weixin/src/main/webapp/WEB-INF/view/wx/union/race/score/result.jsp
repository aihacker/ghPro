<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/31
  Time: 10:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <title></title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
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

        table thead {
            background-color: #0099e9;
        }

        thead th {
            color: #fff;
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

        tr td:nth-child(1),
        tr th:nth-child(1) {
            min-width: 60px;
        }

        tr td:nth-child(2),
        tr th:nth-child(2) {
            min-width: 50px;
        }

        td input[type=text] {
            margin-bottom: 0;
            height: 30px;
            text-align: center;
        }

        td input[readonly] {
            border: none;
        }

        #tabDiv {
            bottom: 45px;
        }

        .ui-dialog-div {
            text-align: center;
        }

        .ui-dialog-div input {
            width: 30%;
            text-align: center;
        }
    </style>
</head>

<body>

<div class="mui-content">
    <div id="tabDiv"
         class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
        <div class="mui-scroll">
            <div class="ui-table-div">
                <table id="resultTable">
                </table>
            </div>
        </div>
    </div>

    <c:if test="${show}">
        <div class="ui-fixed-bottom">
            <button id="editScoreBtn" type="button" class="ui-btn mui-btn mui-btn-primary mui-btn-block">手动编辑比分</button>
        </div>
    </c:if>
</div>

<div class="weui_dialog_confirm" style="display: none;">
    <div class="weui_mask" style="z-index: 9999;"></div>
    <div class="weui_dialog" style="z-index: 9999;">
        <div class="weui_dialog_hd"><strong class="weui_dialog_title">修改分数</strong></div>
        <div class="weui_dialog_bd">
            <div class="ui-dialog-div">
                <input class="mui-input-numbox" type="number">
                <input class="mui-input-numbox" type="number">
            </div>
        </div>
        <div class="weui_dialog_ft">
            <a href="javascript:;" class="weui_btn_dialog default">取消</a>
            <a href="javascript:;" class="weui_btn_dialog primary">确定</a>
        </div>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">

    var raceId = '${param.id}'

    $(function () {
        $(window).on('resize', initSize())

        var $table = $('#resultTable')
        var $confirm = $('.weui_dialog_confirm')

        var heads = ['场序', '编排轮数', '甲队', '乙队', '第一局', '第二局', '第三局', '总比分']

        request()

        $('#editScoreBtn').on('tap', function () {
            var status = $(this).data('status')
            if (!status) {
                // $table.find('input.ui-input').prop('readonly', false)
                $table.find('input.ui-input').css('border', '1px solid rgba(0,0,0,.2)').addClass('ui-border')
                $(this).text('完成编辑')
                $(this).data('status', true)
                mui('#tabDiv').scroll().scrollTo(-480, 0, 200)
            } else { //完成编辑
                //$table.find('input.ui-input').prop('readonly', true)
                $table.find('input.ui-input').css('border', 'none').removeClass('ui-border')
                $(this).data('status', false)
                $(this).text('手动编辑比分')

                var res = []
                $.each($('.ui-input'), function () {
                    var id = $(this).parent().parent().data('id')
                    var score = $(this).data('score')
                    var lun = $(this).data('lun')

                    var val = $(this).val()
                    if (val != score) {
                        var info = {}
                        info['id'] = id
                        info['score'] = val;
                        info['lun'] = lun
                        res.push(info)
                    }
                })
                if (res.length > 0) {
                    var self = this
                    if (!self.loading) {
                        self.loading = ui.loading('正在修改...')
                    }
                    self.loading.show()
                    var url = homePath + '/wx/union/race/score/api/edit.json'
                    mui.post(url, {action: 'edit', json: JSON.stringify(res)}, function (res) {
                        self.loading.hide()
                        if (res.ok) {
                            ui.showToast('编辑成功', function () {
                                window.location.reload()
                            })
                        } else {
                            alert('编辑失败')
                        }
                    })
                } else {
                    alert('您没有做任何修改哦')
                }

            }
        })

        $confirm.on('tap', 'a.weui_btn_dialog', function () {
            var score1 = $confirm.find('.ui-dialog-div input:eq(0)').val()
            var score2 = $confirm.find('.ui-dialog-div input:eq(1)').val()
            $confirm.data('input').val(score1 + ':' + score2)
            $confirm.fadeOut(300)
        })

        function request() {
            if (!this.loading) {
                this.loading = ui.loading('加载中...')
            }
            this.loading.show()
            var self = this
            var url = homePath + '/wx/union/race/score/api/list.json'
            mui.getJSON(url, {action: 'list', id: raceId}, function (res) {
                self.loading.clear()
                if (res.ok) {
                    createTable(res.data)
                } else {
                    alert(res.msg)
                }
            })
        }

        function createTable(d) {
            var type = d.type
            var rs = d.results

            $table.empty()

            var $thead = $('<thead></thead>')
            for (var i in heads) {
                $thead.append('<th>' + heads[i] + '</th>')
            }
            if (type == 2) {
                $thead.find('th:eq(1)').text('组别')
            }
            $table.append($thead)

            var $tbody = $('<tbody></tbody>')
            if (rs && rs.length > 0) {
                for (var i in rs) {
                    var r = rs[i]
                    var $tr = $('<tr></tr>')
                    $tr.data('id', r.id)
                    $tr.append('<td>' + r.siteNum + '</td>')
                    $tr.append('<td>' + r.remark + '</td>')
                    $tr.append('<td>' + r.jia + '</td>')
                    $tr.append('<td>' + r.yi + '</td>')

                    var $input = $('<td><input class="ui-input" type="text" readonly></td>')

                    var $input1 = $input.clone();
                    $input1.find('input')
                        .data('score', r.score1)
                        .data('lun', 1)
                        .val(r.score1)

                    var $input2 = $input.clone();
                    $input2.find('input')
                        .data('score', r.score2)
                        .data('lun', 2)
                        .val(r.score2)

                    $input.find('input')
                        .data('score', r.score3)
                        .data('lun', 3)
                        .val(r.score3)

                    $tr.append($input1)
                    $tr.append($input2)
                    $tr.append($input)
                    $tr.append('<td>' + r.totalScore + '</td>')

                    $tbody.append($tr)

                    initTableEvent($tr)
                }
            } else {
                $tbody.append('<tr><td colspan="' + heads.length + '"></td></tr>')
            }
            $table.append($tbody)

            initSize()
        }

        function initTableEvent($tr) {
            $tr.on('tap', '.ui-input', function () {
                var $self = $(this)
                if (!$self.hasClass('ui-border')) {
                    return;
                }
                var scors = $self.val().split(':')

                $confirm.find('.ui-dialog-div input:eq(0)').val(scors[0])
                $confirm.find('.ui-dialog-div input:eq(1)').val(scors[1])
                $confirm.fadeIn(300)
                $confirm.data('input', $self)
            })
        }

        function initSize() {
            var $tabDiv = $('#tabDiv')
            $tabDiv.css('height', $tabDiv.find('.ui-table-div').outerHeight())
        }
    })
</script>
</body>

</html>
