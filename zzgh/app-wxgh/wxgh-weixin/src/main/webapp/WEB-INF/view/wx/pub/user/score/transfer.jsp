<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/22
  Time: 10:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>积分转移</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .ui-bg {
            position: relative;
        }

        .ui-bg img {
            width: 100%;
        }

        .ui-bg .ui-score-total {
            font-family: "微软雅黑";
            color: #fff;
            font-size: 28px;
            width: 100%;
            height: 100%;
            position: absolute;
            left: 0;
            top: 0;
            text-align: center;
            top: 50%;
            margin-top: -25px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-fixed-bottom">
        <button id="addBtn" type="button" class="mui-btn mui-btn-primary">立即转移</button>
    </div>

    <div class="ui-bg">
        <img src="${home}/weixin/image/score/score_bg.png"/>
        <div class="ui-score-total">${score}</div>
    </div>

    <ul id="mainUl" class="mui-table-view no">
        <li class="mui-table-view-cell no">
            <a class="ui-flex">
                积分类型
                <small class="ui-right">个人积分</small>
            </a>
        </li>
        <li id="toUserLi" class="mui-table-view-cell">
            <a class="ui-flex mui-navigate-right">
                接收用户
                <input type="hidden" name="toUserid">
                <small class="ui-right">请选择</small>
            </a>
        </li>
        <li class="mui-table-view-cell no">
            <a class="mui-navigate-right ui-flex">
                <span>转移积分数</span>
                <input type="number" class="mui-input-numbox mui-input-clear ui-right" name="score" value="0"
                       placeholder="请输入"/>
            </a>
        </li>
    </ul>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script>
    $(function () {
        wxgh.wxInit('${weixin}');

        var $toUserid = $('input[name=toUserid]');

        $('#toUserLi').on('tap', function () {
            wxgh.wxContactOpen('${wx_contact}', function (all, users) {
                if (all == true) {
                    ui.alert('不支持全选！');
                    return;
                }
                $toUserid.val(users[0].id);
                $toUserid.next().text(users[0].name);
            }, {mode: 'single'})
        })

        var load = ui.loading('请稍候...');

        $('#addBtn').on('tap', function () {
            var info = wxgh.serialize(document.getElementById('mainUl'));
            var verifyRes = verify(info);
            if (verifyRes) {
                ui.alert(verifyRes);
                return;
            }
            ui.confirm('你是否转移' + info['score'] + '积分给' + $toUserid.next().text() + '，确认后将无法回退哦！', function () {
                load.show();
                wxgh.request.post('/wx/pub/user/score/api/transfer.json', info, function () {
                    ui.showToast('积分转移成功', function () {
                        window.location.reload();
                    })
                })
            })
        })

        function verify(info) {
            if (!info['toUserid']) {
                return '请选择转移接收人哦';
            }
            if (!info['score']) {
                return '请输入转移积分';
            }
            var scor = Number(info['score'])
            if (scor > 800) {
                return '转移分数不能大于800分哦'
            }
            if (scor <= 0) {
                return '转移分数不能小于1分哦';
            }
        }
    })
</script>
</body>
</html>