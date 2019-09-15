<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/26
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${a.name}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body,
        .mui-content {
            background-color: #fff;
        }

        .ui-img-div {
            height: 200px;
        }

        .ui-main {
            padding: 5px 0;
            background-color: #f0eff4;
        }

        .ui-act-name {
            color: #000;
            font-size: 18px;
            min-height: 60px;
        }

        .ui-act-name,
        .ui-act-info,
        .ui-act-content,
        .ui-act-remark,
        .ui-act-join,
        .ui-comms {
            padding: 5px 15px;
            background-color: #fff;
        }

        .ui-act-info p {
            font-size: 15px;
        }

        .ui-act-content h4,
        .ui-act-remark h4 {
            color: #333;
            font-weight: 500;
        }

        .ui-act-remark p,
        .ui-act-content p {
            margin: 0;
        }

        .ui-act-content .ui-more-btn {
            text-align: center;
            font-size: 15px;
            display: none;
        }

        .ui-act-remark,
        .ui-act-content,
        .ui-act-join,
        .ui-comms {
            margin-top: 12px;
        }

        .ui-act-remark,
        .ui-act-content {
            min-height: 80px;
        }

        .ui-act-join {
            display: flex;
        }

        .ui-act-join img {
            width: 25px;
            height: 25px;
            border-radius: 50%;
        }

        .ui-act-join .ui-imgs {
            flex: 1;
            text-align: right;
        }

        .ui-act-join .mui-icon {
            width: 24px
        }

        .ui-comm-btn {
            font-size: 14px;
            color: #0099e9;
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
            padding: 5px 10px;
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

        .ui-comm-div button {
            height: 30px;
            margin: 5px;
        }

        .ui-comment-more {
            text-align: center;
        }

        .mui-scroll-wrapper {
            bottom: 45px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-fixed-bottom ui-flex">
        <c:choose>
            <c:when test="${empty a.timeOut}">
                <c:choose>
                    <c:when test="${empty a.joinStatus or a.joinStatus eq 0}">
                        <button data-type="1" type="button" class="mui-btn mui-btn-primary">我要报名</button>
                        <button data-type="2" type="button" class="mui-btn mui-btn-danger">我要请假</button>
                    </c:when>
                    <c:otherwise>
                        <button data-type="1" type="button"
                                class="mui-btn mui-btn-primary${a.joinStatus eq 1?' mui-disabled':''}">${a.joinStatus eq 1?'已报名':'我要报名'}
                        </button>
                        <button data-type="2" type="button"
                                class="mui-btn mui-btn-danger${a.joinStatus eq 2?' mui-disabled':''}">${a.joinStatus eq 2?'已请假':'我要请假'}
                        </button>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <button type="button"
                        class="mui-btn mui-btn-danger mui-disabled">${a.timeOut eq 1?'活动进行中':'活动已结束'}</button>
            </c:otherwise>
        </c:choose>

    </div>

    <div class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <div class="ui-img-div">
                <c:choose>
                    <c:when test="${empty a.path}">
                        <img src="${home}/image/default/act.png"/>
                    </c:when>
                    <c:otherwise>
                        <img src="${home}${a.path}"/>
                    </c:otherwise>
                </c:choose>

            </div>
            <div class="ui-main">
                <div class="ui-act-name">
                    ${a.name}
                </div>
                <div class="ui-margin-top-10 ui-act-info">
                    <p>
                        <span class="fa fa-file-text-o"></span>&nbsp;&nbsp;${a.typeName}
                    </p>
                    <p>
                        <span class="fa fa-clock-o"></span>&nbsp;&nbsp;${a.timeStr}
                    </p>
                    <p>
                        <span class="fa fa-map-marker"></span>&nbsp;&nbsp;${a.address}
                    </p>
                    <p style="margin-bottom: 0;">
                        <span class="fa fa-phone"></span>&nbsp;&nbsp;${a.mobile}
                    </p>
                </div>
                <div class="ui-act-content">
                    <h4>活动详情</h4>
                    <p id="actInfo">${a.info}</p>
                    <div class="ui-more-btn">
                        <a class="btn btn-link">
                            <small>点击查看更多</small>
                            <span class="fa fa-angle-down"></span>
                        </a>
                    </div>
                </div>

                <div class="ui-act-remark">
                    <h4>备注</h4>
                    <p>
                        ${empty a.remark?'暂时没有备注':a.remark}
                    </p>
                </div>

                <div class="ui-act-join" id="joinListBtn">
                    <span>报名情况（${a.joinCount}人）</span>
                    <div class="ui-imgs">
                        <c:forEach items="${a.avatars}" var="j">
                            <img src="${j}"/>
                        </c:forEach>
                    </div>
                    <span class="mui-icon mui-icon-arrowright"></span>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script>
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005
    });
    wxgh.wxInit('${weixin}')

    var id = '${param.id}'

    $(function () {
        var $actInfo = $('#actInfo')
        var $moreBtn = $('.ui-more-btn')
        resize()

        var shareUrl = window.location.href
        wxgh.wxShareInit('${a.name}', shareUrl, '${a.path}', '${g.info}')

        $('.ui-fixed-bottom').on('tap', 'button', function () {
            var type = $(this).data('type')
            var msg;
            if (type == 1) {
                msg = '报名'
            } else {
                msg = '请假'
            }
            ui.confirm('是否' + msg + '呢？', function () {
                wxgh.request.post('api/join.json', {type: type, id: id}, function () {
                    ui.showToast(msg + '成功', function () {
                        window.location.reload(true)
                    })
                })
            })
        })

        $('#joinListBtn').on('tap', function () {
            mui.openWindow('join_list.html?id=' + id);
        })

        $moreBtn.on('tap', function () {
            var show = $(this).data('show')
            if (show) {
                $actInfo.removeClass('ui-ellipsis-3')
                $moreBtn.find('small').text('收起')
                $moreBtn.find('span.fa').removeClass('fa-angle-down').addClass('fa-angle-up')
                $moreBtn.data('show', false)
            } else {
                $actInfo.addClass('ui-ellipsis-3')
                $moreBtn.find('small').text('点击查看更多')
                $moreBtn.find('span.fa').removeClass('fa-angle-up').addClass('fa-angle-down')
                $moreBtn.data('show', true)
            }
        })

        $(window).on('resize', resize())

        function resize() {
            if ($actInfo.height() > 60) {
                $actInfo.addClass('ui-ellipsis-3')
                $moreBtn.data('show', true).show(100)
            } else {
                $moreBtn.hide(100)
            }
        }
    })
</script>
</body>
</html>
