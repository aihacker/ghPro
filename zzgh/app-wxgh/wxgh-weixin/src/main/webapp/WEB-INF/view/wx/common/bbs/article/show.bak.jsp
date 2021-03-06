<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/24
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${a.atlName}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <style>
        body,
        .mui-content {
            background-color: #fff;
        }

        .ui-card {
            padding: 10px 15px;
        }

        .ui-card .ui-text-body {
            margin-bottom: 0;
            padding-top: 10px;
        }

        .mui-card-header > img:first-child {
            width: 40px;
            height: 40px;
            border-radius: 50%;
        }

        .mui-table-view.mui-grid-view:before,
        .mui-table-view.mui-grid-view:after {
            height: 0;
        }

        .mui-card-footer small {
            font-size: 14px;
        }

        .ui-comment-header {
            padding: 5px 15px;
        }

        .ui-comment-header h5 {
            padding: 2px 5px;
            border-left: 4px solid orange;
            color: #000;
        }

        .ui-comment-list {
            padding-left: 20px;
        }

        .ui-comment-list .mui-spinner {
            display: inline-block;
        }

        .ui-comment-list .mui-loading {
            text-align: center;
        }

        .ui-comment-list .mui-loading small {
            position: relative;
            top: -7px;
            padding-left: 5px;
        }

        .ui-comment-item {
            padding: 5px 15px;
        }

        .ui-comment-item .mui-media-object {
            width: 40px;
            height: 40px;
            border-radius: 50%;
        }

        .ui-comment-item .mui-media-body {
            margin-left: 48px;
        }

        .ui-comment-item .mui-media-body p {
            margin-bottom: 0;
        }

        .ui-article-title {
            font-size: 20px;
            color: #000;
        }

        .ui-img-div {
            height: 100px;
        }

        .mui-table-view.mui-grid-view .mui-table-view-cell .ui-img-div .mui-media-object {
            height: 100%;
            width: auto;
            max-width: none;
        }

        #refreshContainer {
            bottom: 38px;
        }

        .ui-comm-div {
            position: fixed;
            bottom: 0;
            width: 100%;
            border-top: 1px solid #ddd;
            background-color: #fff;
            z-index: 9999;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .ui-comm-div textarea {
            margin: 5px;
            padding: 5px 10px;
            border: none;
            max-height: 90px;
        }

        .mui-card-header:after{
            position: absolute;
            top: 0;
            right: 0;
            left: 0;
            /* height: 1px; */
            content: '';
            -webkit-transform: scaleY(.5);
            transform: scaleY(.5);
            background-color: #c8c7cc;
        }

        .ui-comm-div button {
            height: 30px;
            margin: 5px;
        }
    </style>
</head>
<body>

<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <div class="ui-card">
            <div class="ui-article-title">
                ${a.atlName}
            </div>
            <div class="mui-card-header mui-card-media" style="border:0;">
                <img src="${a.avatar}">
                <div class="mui-media-body">
                    ${a.userName}
                    <small class="mui-pull-right">${a.createFormatDate}</small>
                    <p>${a.deptname}</p>
                </div>
            </div>
            <div class="mui-card-content">
                <p class="ui-text-body">
                    ${a.atlContent}
                </p>
                <c:if test="${!empty a.fileList}">
                    <ul class="mui-table-view mui-grid-view">
                        <c:forEach items="${a.fileList}" var="f" varStatus="i">
                            <li class="mui-table-view-cell mui-media mui-col-xs-4${i.count gt 9?' mui-hidden':''}">
                                <a href="javascript:;">
                                    <div class="ui-img-div">
                                        <img data-preview-group="article_imgs" data-preview-src=""
                                             class="mui-media-object" src="${home}${f.path}">
                                    </div>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:if>
            </div>
            <div class="mui-card-footer">
                <small><span class="fa fa-eye"></span> ${a.seeNum}</small>
                <small id="zanBtn"><span
                        class="fa ${!isZan?'fa-heart-o':'fa-heart ui-text-danger'}"></span> <span
                        class="ui-num">${a.zanNum}</span></small>
                <small><span class="fa fa-comment-o"></span> ${a.commNum}</small>
            </div>
        </div>
        <div class="ui-comment">
            <div class="ui-comment-header">
                <h5>热门评论</h5>
            </div>
            <div class="ui-comment-list">
                <div class="mui-loading">
                    <div class="mui-spinner"></div>
                    <small>加载中...</small>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="ui-comm-div">
    <textarea id="sendCont" rows="1" placeholder="写评论..."></textarea>
    <button id="sendBtn" type="button" class="mui-btn mui-btn-success ">发表</button>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script src="${home}/comm/mobile/js/refresh.js"></script>

<script type="text/javascript">
    $(function () {
        var _self = homePath + '/wx/common/bbs/article/comm_list.json'
        var _self_zan = homePath + '/wx/common/bbs/article/add_zan.json'
        var _self_add_com = homePath + '/wx/common/bbs/article/add_comm.json'
        var id = '${param.id}'

        wxgh.previewImageInit();

        var info = {
            articleId: id
        }
        var refresh = window.refresh('#refreshContainer', {
            url: _self,
            data: info,
            ispage: true,
            bindFn: bindfn,
            dataEl: '.ui-comment-list',
            errorFn: function (type) {
                console.log(type)
            }
        })

        function bindfn(d) {
            var comms = d.comments
            if (comms && comms.length > 0) {
                for (var i in comms) {
                    var c = comms[i]
                    var img = c.headimg
                    if (!img) {
                        img = homePath + '/image/default/user.png'
                    }
                    var cls = c.isZan ? 'fa-thumbs-up' : 'fa-thumbs-o-up'
                    var $item = $('<div class="ui-comment-item mui-media">' +
                        '<img class="mui-media-object mui-pull-left" src="' + img + '">' +
                        '<div class="mui-media-body">' +
                        '<span class="ui-text-primary">' + c.userName + '</span>' +
                        '<small class="mui-pull-right ui-zan"><span class="fa ' + cls + '"></span> <span class="ui-num">' + c.zanNum + '</span></small>' +
                        '<p class="ui-text-dan-black">' + c.atlComment + '</p>' +
                        '<p><small>' + c.createFormatDate + '</small>' +
                        '</p></div></div>'
                    )
                    $item.data('id', c.comId)
                    refresh.addItem($item[0])

                    $item.on('tap', '.ui-zan', function () {
                        var $self = $(this)
                        mui.getJSON(_self_zan, {
                            id: $self.parent().parent().data('id'),
                            type: 0
                        }, function (res) {
                            if (res.ok) {
                                var d = res.data
                                var $fa = $self.find('.fa')
                                if (d.type == 1) {
                                    $fa.removeClass('fa-thumbs-o-up').addClass('fa-thumbs-up')
                                } else {
                                    $fa.removeClass('fa-thumbs-up').addClass('fa-thumbs-o-up')
                                }
                                $self.find('.ui-num').text(d.num)

                            } else {
                                alert('点赞失败，请重试')
                            }
                        })
                    })
                }
            } else {
                refresh.addItem('<div class="mui-content-padded mui-text-center">暂无评论哦</div>')
            }
        }

        $('#zanBtn').on('tap', function () {
            var $self = $(this)
            mui.getJSON(_self_zan, {action: 'add_zan', id: id, type: 1}, function (res) {
                if (res.ok) {
                    var d = res.data
                    var $fa = $self.find('.fa')
                    if (d.type == 1) {
                        $fa.removeClass('fa-heart-o').addClass('fa-heart ui-text-danger')
                    } else {
                        $fa.removeClass('fa-heart ui-text-danger').addClass('fa-heart-o')
                    }
                    $self.find('.ui-num').text(d.num)

                } else {
                    alert('点赞失败，请重试')
                }
            })
        })

        var $sendCont = $('#sendCont')
        $sendCont.keyup(function () {
            var height = $(this).parent().outerHeight()
            $('#refreshContainer').css('bottom', height + 'px')
        })
        autoTextarea($sendCont)
        $('#sendBtn').on('tap', function () {
            var txt = $sendCont.val()
            if (!txt) {
                alert('请输入评论内容哦')
                return
            }
            var info = {action: 'add_comm'}
            info['atlComment'] = txt
            info['atlId'] = id
            mui.post(_self_add_com, info, function (res) {
                if (res.ok) {
                    refresh.request(refresh, 1, 'down')
                    $sendCont.val('')
                } else {
                    alert('评论失败')
                }
            })
        })
    })
</script>
</body>
</html>
