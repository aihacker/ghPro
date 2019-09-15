<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/25
  Time: 17:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>【${a.actName}】积分结算</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .user-avatar-list {
            display: flex;
            min-height: 74px;
        }

        .mui-loading {
            width: 100%;
        }

        .mui-loading .mui-spinner {
            position: relative;
            bottom: -30px;
        }

        .user-avatar {
            text-align: center;
            padding: 0 5px;
        }

        .user-avatar img {
            width: 35px;
            height: 35px;
            border-radius: 50%;
        }

        .user-info {
            font-size: 13px;
        }

        .user-info * {
            display: block;
        }

        .user-info small {
            font-size: 12px;
        }

        .ui-content p {
            margin-bottom: 2px;
        }

        .mui-table-view-cell > a:not(.mui-btn).mui-active {
            background-color: transparent;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-fixed-bottom">
        <button id="accountBtn" type="button" class="mui-btn mui-btn-primary">开始结算</button>
    </div>

    <ul class="mui-table-view no">
        <li class="mui-table-view-cell">
            <a href="account/users.html?id=${param.id}" class="mui-navigate-right ui-flex">
                <span>协会成员（${a.numb}人）</span>
                <small style="color: #0193E0;" class="ui-right">查看全部</small>
            </a>
            <div class="user-avatar-list">

            </div>
        </li>
    </ul>

    <ul class="mui-table-view ui-margin-top-10 no">
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right ui-flex">
                <span>活动总费用<small>（元）</small></span>
                <input id="totalPay" type="number" ${a.joinNumb gt 0?'':'readonly'} class="mui-input-numbox ui-right"
                       placeholder="${a.joinNumb gt 0?'请输入':'无人报名，无法结算'}"/>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a class="ui-flex">
                <span class="ui-right">每人费用：<span id="everyPay" data-numb="${a.joinNumb}" style="color: red;">0.0</span> 元</span>
            </a>
        </li>
    </ul>

    <div class="ui-content ui-margin-top-10">
        <h5 style="color: #0193E0;" class="ui-title">【活动信息】</h5>
        <p>活动名称：${a.actName}</p>
        <p>积分规则：</p>
        <p>&nbsp;&nbsp;1. 参加活动获得积分：${a.score} 分</p>
        <p>&nbsp;&nbsp;2. 请假获得积分：${a.leaveScore} 分</p>
        <p>&nbsp;&nbsp;3. 缺席获得积分：${a.outScore} 分</p>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    var actId = '${param.id}';

    $(function () {
        var $everyPay = $('#everyPay')
        $('#totalPay').on('keyup', function () {
            try {
                var val = $(this).val();
                if (val) {
                    console.log(val);
                    val = $.trim(Number(val));
                    if (val < 0) {
                        mui.toast('费用不能小于0哦！')
                        return
                    }
                    var pay = ( val / Number($everyPay.data('numb'))).toFixed(2)
                    $everyPay.text(pay)
                }
            } catch (err) {
                ui.alert('非法数字')
            }
        })

        var load = ui.loading('结算中时间较长，请等待...')
        $('#accountBtn').on('tap', function () {
            if ($everyPay.data('numb') <= 0) {
                ui.alert('无人报名，无法结算！');
                return;
            }
            var total = $.trim($('#totalPay').val())
            ui.confirm('活动总费用：' + (total ? total : 0) + ' 元', function () {
                load.show()
                wxgh.request.post('/wx/union/group/account/start.json', {id: actId, total: total}, function () {
                    ui.showToast('活动结算成功！', function () {
                        mui.back();
                    })
                })
            }, '', '活动费用确认')
        })

        var $userList = $(".user-avatar-list")
        $userList.append(wxgh.LOADING_HTML)
        wxgh.request.post('/wx/union/group/account/list_user.json', {actId: actId, rowsPerPage: 6}, function (d) {
            $userList.empty()
            if (d && d.length > 0) {
                for (var i in d) {
                    var u = d[i]
                    var $item = $('<div class="user-avatar">' +
                        '<img src="' + u.avatar + '"/>' +
                        '<div class="user-info">' +
                        '<span>' + u.username + '</span>'
                        + get_type_name(u.type) +
                        '</div> </div>')
                    $userList.append($item)
                }
            } else {

            }
        })

        function get_type_name(type) {
            var str, color;
            if (type == 1) {
                str = '参加'
                color = 'dodgerblue'
            } else if (type == 2) {
                str = '请假'
                color = 'darkgreen'
            } else {
                str = '缺席'
                color = 'darkred'
            }
            return '<small style="color: ' + color + ';">' + str + '</small>'
        }
    })
</script>
</body>
</html>