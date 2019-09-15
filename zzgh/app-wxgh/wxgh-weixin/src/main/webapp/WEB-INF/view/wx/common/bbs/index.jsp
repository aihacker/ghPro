<%--
  Created by IntelliJ IDEA.
  User: xuybang zhb
  Date: 2016/6/23
  Time: 8:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
    <title>工会活动</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        #refreshContainer {
            top: 55px;
        }

        #refreshContainer img.mui-media-object {
            margin-top: 4px;
            width: 60px;
            max-width: 60px;
            height: 60px;
        }

        .ui-add-btn {
            position: fixed;
            right: 20px;
            bottom: 20px;
            z-index: 2;
        }

        .ui-add-btn img {
            width: 40px;
            height: 40px;
        }

        .ui-bbs-search .mui-search {
            display: inline-block;
            width: 100%;
        }
    </style>
</head>
<body>
<input type="hidden" value="${wxgh_user.userid}" id="loginUserId">
<div class="mui-content">
    <div class="mui-content-padded">
        <div class="ui-bbs-search">
            <div class="mui-input-row mui-search">
                <input id="searchInputs" type="search" class="mui-input-clear" placeholder="搜索">
            </div>
            <button style="display: none;" id="cancelBtn" type="button" class="mui-btn mui-btn-link">取消</button>
        </div>
    </div>
    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view">
            </ul>
        </div>
    </div>

    <div class="ui-add-btn">
        <img src="${home}/image/common/addArt.png"/>
    </div>
</div>

<script type="text/html" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <img class="mui-media-object mui-pull-left" src="${home}{{=it.image}}">
        <div class="mui-media-body">{{=it.name}}
            <small class="mui-pull-right ui-text-info">{{=it.createTime}}</small>
            <p class="mui-ellipsis">{{=it.content}}</p>
        </div>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var info = {status: 1}
        var refresh = window.refresh('#refreshContainer', {
            data: info,
            url: 'list.json',
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
            d['createTime'] = ui.timeAgo(d.createTime);
            d['image'] = d.image ? d.image : '/image/default/bbs.png';
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow('article/show.html?id=' + d.id);
            })

            return $item[0]
        }

        $('#searchInputs').keyup(function () {
            var val = $(this).val()
            if (val) {
                refresh.setParam({searchKey: val})
                refresh.request(refresh, 1, 'down')
            }
        }).on('focus', function () {
            $('#cancelBtn').show()
            $(this).parent().css('width', '80%')
        }).on('blur', function () {
            $(this).parent().css('width', '100%')
        })
        $('#cancelBtn').on('tap', function () {
            $('#searchInputs').val('').blur()
            refresh.initParam(info)
        })

        $('.ui-add-btn').on('tap', function () {
            mui.openWindow('add.html')
        })
    })
</script>
</body>

</html>
