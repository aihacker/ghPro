<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/21
  Time: 16:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>报名列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <style>
        .mui-table-view .mui-media-object {
            width: 40px;
            height: 40px;
            max-width: 40px;
            -webkit-border-radius: 50%;
            border-radius: 50%;
        }

        .mui-media-body p {
            margin-top: 8px;
            color: #000;
        }

        .mui-media-body p .mui-badge {
            margin-right: 6px;
        }

        .mui-table-view:before {
            height: 0;
        }

        .mui-content > .mui-table-view:first-child {
            margin-top: 0;
        }

        .ui-search {
            margin: 5px 10px 0 10px;
        }

        .search_box {
            border: 1px solid red;
        }

        .search_box a {
            display: block;
            height: 32px;
            line-height: 32px;
            text-align: center;
            border-radius: 4px;
            margin: 8px 12px;
            background-color: #D8D8DC;
        }

        .search_box a img {
            height: 18px;
            width: 18px;
            vertical-align: middle;
            margin: 0 4px;
        }

        .search_box span {
            color: darkgray;
            font-size: .9em;
        }

        #refreshContainer {
            top: 48px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-search">
        <div class="mui-input-row mui-search">
            <input id="searchInput" type="search" class="mui-input-clear" placeholder="搜索">
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
    <li class="mui-table-view-cell">
        <div class="mui-slider-right mui-disabled">
            <a class="mui-btn mui-btn-success see">查看</a>
        </div>
        <div class="mui-slider-handle"><img class="mui-media-object mui-pull-left"
                                            src="{{=it.avatar}}"
                                            data-preview-src="{{=it.avatarSrc}}"
                                            data-preview-group="user_avatar">
            <div class="mui-media-body"><p class="mui-ellipsis"><span
                    class="ui-name-span">{{=it.username}}</span></p></div>
        </div>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    var groupId = '${param.id}';
    $(function () {
        wxgh.previewImageInit()

        var refresh = window.refresh('#refreshContainer', {
            data: {groupId: groupId},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['avatarSrc'] = wxgh.get_avatar(d.avatar, false);
            d['avatar'] = wxgh.get_avatar(d.avatar);
            var $item = refresh.getItem(d)
            $item.on('tap', '.see', function () {
                mui.openWindow(homePath + '/wx/pub/user/show.html?userid=' + d.userid);
            })

            return $item[0]
        }

        var $searchInput = $('#searchInput')

        //搜索框搜索事件
        $searchInput.keyup(function () {
            var val = $(this).val().trim()
            if (val) {
                refresh.refresh({name: val});
            }
        })

    })
</script>
</body>
</html>
