<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/24
  Time: 11:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的充值记录</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-content {
            background-color: #fff;
        }

        table.mui-table {
            background-color: #efeff4;
        }

        table.mui-table td {
            text-align: center;
            font-size: 15px;
            padding: 8px 0;
        }
    </style>
</head>
<body>
<div class="mui-content">

    <div style="height: 150px;background-color: #57A5FA;">
        <p style="padding: 20px;color: #FFFFFF;">我的会费（元）</p>
        <p style="color: #FFFFFF;text-align: center;font-size: 35px;margin: 0px;height: 50px;margin-top: -14px;">${money}</p>
    </div>
    <div style="padding: 2px 10px">
        <h5 class="ui-title">充值记录</h5>
    </div>
    <div id="refreshContainer" class="mui-scroll-wrapper" style="top: 188px;">
        <div class="mui-scroll">
            <table class="mui-table">
                <tbody id="recordList">
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/template" id="itemTpl">
    <tr>
        <td>{{=it.addTime}}</td>
        <td style="color: #FF9C00;">交易成功</td>
        <td>{{=it.money}}元</td>
    </tr>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var groupId = '${param.id}';

    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: homePath + '/wx/union/group/pay/list.json',
            data: {groupId: groupId},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            dataEl: '#recordList',
            emptyHtml: '<tr><td class="ui-empty" colspan="2">您没有充值记录哦</td></tr>',
            bindFn: bindfn,
            errorFn: function (type) {
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['addTime'] = new Date(d.addTime).format('yyyy-MM-dd')
            var $item = refresh.getItem(d)
            return $item[0]
        }
    })
</script>
</body>
</html>