<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/18
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>成员列表</title>
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
            <input id="searchInput" type="search" class="mui-input-clear" placeholder="搜索成员">
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
        <div class="mui-slider-handle"><img class="mui-media-object mui-pull-left"
                                            src="{{=it.avatar}}"
                                            data-preview-src="{{=it.avatar}}"
                                            data-preview-group="user_avatar">
            <div class="mui-media-body"><p class="mui-ellipsis"><span class="mui-badge mui-badge-yellow">{{=it.typeName}}</span><span
                    class="ui-name-span">{{=it.username}}</span></p></div>
        </div>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        wxgh.previewImageInit()

        var groupId = '${groupId}';

        var refresh = window.refresh('#refreshContainer', {
            data: {status: 1, groupId: groupId},
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
            d['typeName'] = get_type_name(d.type)
            var $item = refresh.getItem(d)
            $item.on('tap', '.mui-table-view-cell', function () {
                mui.openWindow(homePath + "/wx/pub/user/show.html?userid=" + d.userid)
            });
            return $item[0]
        }

        function get_type_name(type) {
            var str;
            if (type == 1) {
                str = '会长'
            } else if (type == 2) {
                str = '副会长'
            } else if (type == 3) {
                str = '理事'
            } else if (type == 4) {
                str = '会员'
            } else {
                str = '其他'
            }
            return str
        }
    })
</script>
</body>
</html>
