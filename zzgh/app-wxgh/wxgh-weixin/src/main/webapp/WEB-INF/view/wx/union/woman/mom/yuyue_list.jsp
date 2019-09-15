<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/14
  Time: 15:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>小屋预约情况</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .table-div {
            padding: 10px 15px;
        }

        .table-div h4 {
            text-align: center;
            padding: 10px 0;
        }

        .table {
            background-color: #fff;
        }

        table td,
        table th {
            border: solid #646464;
            border-width: 1px 1px 1px 1px;
            padding: 5px 0px;
            min-width: 110px;
            position: relative;
        }

        table tr td {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-fixed-bottom">
        <button id="yuyueBtn" type="button" class="mui-btn mui-btn-primary">立即预约</button>
    </div>
    <div class="table-div">
        <h4>预约列表</h4>
        <table class="table mui-table">
            <thead>
            <tr>
                <th colspan="2">时间</th>
                <th>预约用户</th>
            </tr>
            </thead>
            <tbody id="yuyueList">
            </tbody>
        </table>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    var momId = '${param.id}';
    $(function () {
        $('#yuyueBtn').on('tap', function () {
            mui.openWindow('yuyue.html?id=' + momId);
        });
        var $yuyue = $('#yuyueList');
        var load = ui.loading('加载中...');
        load.show();
        wxgh.request.getURL('api/yuyue_list.json?id=' + momId, function (yuyues) {
            if (yuyues && yuyues.length > 0) {
                for (var i in yuyues) {
                    var y = yuyues[i],
                        us = y.yuyues;
                    $yuyue.append(create_tr(us[0], us.length, y.weekName + '<small>（' + y.time + '）</small>'));
                    for (var j = 1, len = us.length; j < len; j++) {
                        $yuyue.append(create_tr(us[j]));
                    }
                }
            } else {
                $yuyue.append('<tr><td class="ui-empty" colspan="3">暂无预约</td></tr>')
            }
        });

        function create_tr(u, len, week) {
            var $tr = $('<tr></tr>');
            if (len) {
                $tr.append('<td rowspan="' + len + '">' + week + '</td>');
            }
            $tr.append('<td>' + u.time + '</td>');
            $tr.append('<td>' + u.username + '</td>');
            return $tr;
        }
    })
</script>
</body>
</html>