<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/24
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>积分会费记录</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .page-bg {
            background-color: #57A5FA;
            padding: 30px 0;
            text-align: center;
        }

        .record-list {
            width: 120px;
        }

        table tr td {
            color: #777;
            font-size: 15px;
        }
    </style>
</head>
<body>
<div class="mui-content">

    <div class="page-bg">
        <p class="text-center" style="height: 60px;">
            <img src="${wxgh_user.avatar}" style="height: 100%;border-radius: 50%;">
        </p>
        <p class="text-center" style="color: white;">
            ${wxgh_user.name}
        </p>
    </div>
    <div class="mui-row" style="background-color: #FFFFFF;">
        <div class="mui-col-sm-6 mui-col-xs-6" style="padding: 6px;border-right: 1px solid gainsboro;">
            <p class="mui-text-center" style="font-size: 16px;font-weight: 600;margin-bottom: 0px;">总积分</p>
            <p class="mui-text-center"
               style="color: #FF9C00;font-size: 23px;font-weight: 600;margin-bottom: 0px;">${total.score}</p>
        </div>
        <div class="mui-col-sm-6 mui-col-xs-6" style="padding: 6px;">
            <a href="money_record.html?id=${param.id}">
                <p class="mui-text-center" style="font-size: 16px;font-weight: 600;margin-bottom: 0px;">剩余会费</p>
                <p class="mui-text-center"
                   style="color: #FF9C00;font-size: 23px;font-weight: 600;margin-bottom: 0px;">${total.money}</p>
            </a>
        </div>
    </div>
    <div id="refreshContainer" class="mui-scroll-wrapper" style="top: 240px;">
        <div class="mui-scroll">
            <ul class="mui-table-view">
            </ul>
        </div>
    </div>
</div>
<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <div class="mui-pull-left record-list">
                <p class="mui-ellipsis-2">{{=it.actName}}</p>
                <p>{{=it.addTime}}</p>
            </div>
            <div class="mui-media-body">
                <table class="mui-table">
                    <tr>
                        <td>会费</td>
                        <td>{{=it.money}}</td>
                    </tr>
                    <tr>
                        <td>积分</td>
                        <td>{{=it.score}}</td>
                    </tr>
                    <tr>
                        <td>活动情况</td>
                        <td>{{=it.statusText}}</td>
                    </tr>
                </table>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var groupId = '${param.id}';

    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            data: {groupId: groupId},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '您没有活动参与记录哦',
            bindFn: bindfn,
            errorFn: function (type) {
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['addTime'] = new Date(d.addTime).format('yyyy-MM-dd')
            if (d.money > 0) {
                d['money'] = '-' + d.money
            }
            if (d.score > 0) {
                d['score'] = '+' + d.score
            }
            d['statusText'] = get_status(d.status);
            var $item = refresh.getItem(d)
            return $item[0]
        }

        function get_status(status) {
            var str;
            if (status == 1) {
                str = '参加';
            } else if (status == 2) {
                str = '请假';
            } else if (status == 3) {
                str = '缺席';
            }
            return str;
        }
    })
</script>
</body>
</html>