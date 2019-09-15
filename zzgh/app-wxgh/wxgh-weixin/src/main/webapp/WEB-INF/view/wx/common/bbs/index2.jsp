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
    <title>热点论坛</title>
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
        <img src="${home}/weixin/image/common/bbs/addArt.png"/>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>

<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
       <a href="javascript:;">
            <img class="mui-media-object mui-pull-left" src="{{=it.img}}">
            <div class="mui-media-body">{{=it.atlName}}
                <p class="mui-ellipsis">{{=it.atlContent}}</p>
                <p class="mui-pull-right">
                    <span class="fa fa-comment-o"></span>
                    {{=it.commNum}}人评论</p>
            </div>
       </a>
    </li>
</script>

<script>
    $(function () {
        var info = { status: 1}
        var refresh = window.refresh('#refreshContainer', {
//            url: homePath + '/wx/common/bbs/index.json',
            url: 'index.json',
            data: { pageIs: true, status: 1},
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '暂无帖子哦',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
            }
        })

        function bindfn(d) {
            if(d.img)
                img = d.img.split(',')[0]
            else
                img = homePath + '/image/common/nopic.gif'
            d['img'] = wxgh.get_image(img);
            var $item = refresh.getItem(d)
            return $item[0]
//            var arts = d
//            if (arts && arts.length > 0) {
//                for (var i in arts) {
//                    var a = arts[i]
//                    var img = a.fileIds
//                    if (!img) {
//                        img = homePath + '/image/common/nopic.gif'
//                    } else {
//                        img = img.split(',')[0]
//                    }
//
//                    var eli = document.createElement('li')
//                    eli.className = ''
//                    var $item = $('<li class="mui-table-view-cell mui-media">' +
//                        '<a href="javascript:;">' +
//                        '<img class="mui-media-object mui-pull-left" src="' + wxgh.get_image(img) + '">' +
//                        '<div class="mui-media-body"> ' + a.atlName +
//                        '<p class="mui-ellipsis">' + a.atlContent + '</p>' +
//                        '<p class="mui-pull-right"><span class="fa fa-comment-o"></span> ' + a.commNum + '人评论</p>' +
//                        '</div></a></li>')
//                    $item.data('id', a.atlId)
//                    refresh.addItem($item[0])
//                    $item.on('tap', function () {
//                        var id = $(this).data('id')
//                        mui.openWindow(homePath + '/wx/common/bbs/article/show.html?id=' + id)
//                    })
//                }
//            } else {
//                refresh.addItem('<li class="mui-table-view-cell"><div class="mui-text-center">暂无帖子哦</div></li>')
//            }
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
            mui.openWindow(homePath + '/wx/common/bbs/add.html')
        })
    })
</script>
</body>

</html>
