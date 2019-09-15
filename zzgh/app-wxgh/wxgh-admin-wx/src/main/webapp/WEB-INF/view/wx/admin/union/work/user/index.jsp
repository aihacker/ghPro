<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/8
  Time: 10:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>项目成员</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view .mui-media-object {
            max-width: 50px;
            width: 50px;
            height: 50px;
        }
    </style>
</head>
<body>

<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view">
        </ul>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/js/refresh.js"></script>
<script>
    var workId = '${param.id}'
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: homePath + '/wx/admin/union/work/user/list.json',
            data: {action: 'list', workId: workId},
            ispage: true,
            bindFn: bindfn,
            errorFn: function (type) {
                console.error(type)
            }
        })

        function bindfn(d) {
            var users = d.users;
            if (users && users.length > 0) {
                for (var i = 0; i < users.length; i++) {
                    var d = users[i]
                    var img = d.userImg
                    if (!img) {
                        img = homePath + '/image/default/user.png'
                    }
                    if(d.deptname)
                        var deptname = '-' + d.deptname;
                    else
                        var deptname = '';
                    var $item = $('<li class="mui-table-view-cell mui-media"> <a href="javascript:;">' +
                        '<img class="mui-media-object mui-pull-left" src="' + img + '">' +
                        '<div class="mui-media-body">' +
                        d.name + ' <p> ' + d.typeName + deptname + ' </p> </div> </a> </li>')
                    refresh.addItem($item[0])
                }
            } else {
                refresh.addItem('<li class="mui-table-view-cell mui-text-center">暂无项目队员哦</li>')
            }
        }
    })
</script>
</body>
</html>
