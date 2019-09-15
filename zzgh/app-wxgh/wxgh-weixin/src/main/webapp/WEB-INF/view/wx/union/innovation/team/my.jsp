<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/20
  Time: 16:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的团队</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view .mui-media-object {
            width: 60px;
            height: 60px;
            max-width: 60px;
            -webkit-border-radius: 50%;
            border-radius: 50%;
            border: 1px solid gainsboro;
            padding: 1px;
        }

        .mui-media-body {
            margin-top: 5px;
        }

        .mui-media-body p {
            margin-top: 10px;
        }

        .mui-table-view:before {
            height: 0;
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

<script type="text/template" id="itemTpl">
    <li data-href="{{=it.url}}"
        class="mui-table-view-cell" style="background-color: white; margin-bottom: 7px;">
        <a style="color: #000;">
            <img src="{{=it.avatar}}" style="float: left;width: 25%;height: 65px;">
            <div style="float: left;width: 75%;">
                <div style="padding-left:10px;">
                    <p class="mui-ellipsis"
                       style="font-weight: 600;color: black;font-size: 15px;">
                        {{=it.name}}</p>
                    <p class="mui-ellipsis-2">
                        {{=it.info}}
                    </p>
                </div>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: homePath + '/wx/union/innovation/team/list.json',
            data: {status: 1},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '你未加入任何团队',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['info'] = d.info ? d.info : '暂无简介'
            d['avatar'] = d.avatar ? (wxgh.get_image(d.avatar)) : homePath + '/image/default/chat.png'
            d['url'] = homePath + '/wx/union/innovation/team/show.html?id=' + d.id;
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow(homePath + '/wx/union/innovation/team/show.html?id=' + d.id)
            })
            return $item[0];
        }
    })
</script>
</body>
</html>
