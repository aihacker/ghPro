<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <title>成员列表</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <style>
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
            <ul class="mui-table-view no">
            </ul>
        </div>
    </div>
</div>
<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell">
        <div class="mui-slider-right mui-disabled">
            <a class="mui-btn mui-btn-grey ui-see">查看</a>
            <c:if test="${!empty permission and permission eq 1}">
                <a class="mui-btn mui-btn-red ui-del">删除</a>
            </c:if>
        </div>
        <div class="mui-slider-handle">
            <img class="mui-media-object ui-circle mui-pull-left"
                 src="{{=it.avatarSrc}}"
                 data-preview-src="{{=it.avatar}}"
                 data-preview-group="user_avatar">
            <div class="mui-media-body">
                {{=it.username}}
                <p class="mui-ellipsis">
                    {{=it.deptname}}
                </p>
            </div>
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
            url: 'api/user_list.json',
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
            d['avatarSrc'] = wxgh.get_avatar(d.avatar);
            var $item = refresh.getItem(d)


            $item.on('tap', '.ui-see', function () {
                mui.openWindow(homePath + '/wx/pub/user/show.html?userid=' + d.userid);
            })

            $item.on('tap', '.ui-del', function () {
                ui.confirm('是否删除？', function () {
                    wxgh.request.post('api/del.json', {id: d.id}, function () {
                        $item.remove();
                    })
                })
            })
            return $item[0]
        }
    })
</script>
</body>
</html>
