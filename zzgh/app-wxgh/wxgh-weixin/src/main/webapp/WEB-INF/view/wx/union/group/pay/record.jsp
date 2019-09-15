<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/21
  Time: 17:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的充值记录</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-content .ui-flex span {
            flex: 1;
            text-align: center;
        }

        #refreshContainer {
            top: 35px;
        }

        .mui-table-view-cell {
            padding: 6px 15px;
        }
    </style>
</head>
<body>

<div class="mui-content">
    <div class="mui-row">
        <div class="ui-flex">
            <span style="color: #E62824;">充值时间</span>
            <span style="color: #F69400;">充值金额</span>
        </div>
    </div>
    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view">
            </ul>
        </div>
    </div>
</div>
<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell ui-flex">
        <span>{{=it.addTime}}</span>
        <span>{{=it.money}}</span>
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
            emptyText: '您没有充值记录哦',
            bindFn: bindfn,
            errorFn: function (type) {
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['addTime'] = new Date(d.addTime).format('yyyy-MM-dd')
            if (d.money > 0) {
                d['money'] = '+' + d.money
            }
            var $item = refresh.getItem(d)
            return $item[0]
        }
    })
</script>
</body>
</html>
