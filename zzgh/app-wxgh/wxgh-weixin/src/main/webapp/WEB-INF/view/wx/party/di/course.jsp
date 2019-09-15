<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/28
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>学习课程</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view-cell:after {
            background-color: initial;
        }

        .mui-card {
            margin: 10px 0;
        }

        p.p-status {
            height: 35px;
            text-indent: 10px;
            font-size: 15px;
            color: black;
            line-height: 35px;
        }

        .mui-card {
            padding: 0px;
        }

        .mui-card-footer {
            padding-left: 0px;
            padding-right: 0px;
        }

        .p-name {
            height: 30px;
            font-size: 16px;
            line-height: 30px;
            padding-left: 10px;
            color: white;
            background-color: black;
            opacity: 0.5;
            margin-top: -30px;
        }

        .li-item {
            padding: 0px;
            margin-bottom: 15px;
        }

        #ul-list {
            padding: 0 12px;
            background-color: #efeff4;
        }
    </style>
</head>
<body>
<div id="pullrefresh" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view" id="ul-list" style="background-color: #efeff4;">
        </ul>
    </div>
</div>
<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell li-item">
        <div class="mui-card">
            <div></div>
            <div class="mui-card-content ui-img-div" style="height: 180px;">
                <img src="${home}{{=it.path}}">
            </div>
            <p class="mui-ellipsis p-name">{{=it.name}}</p>
        </div>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>

    var groupId = '${param.id}';

    $(function () {
        var refresh = window.refresh('#pullrefresh', {
            url: homePath + '/wx/party/di/api/course_list.json',
            data: {groupId: groupId},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '暂无学习资料哦',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow(homePath + '/wx/party/di/show.html?id=' + d.id)
            })
            return $item[0]
        }
    })
</script>
</body>
</html>