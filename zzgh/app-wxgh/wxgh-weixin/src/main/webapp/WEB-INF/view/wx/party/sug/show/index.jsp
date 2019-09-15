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
    <title>【总经理直通车】${s.username}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
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

        .ui-comment-header {
            padding: 5px 15px;
        }

        .ui-comment-header h5 {
            padding: 2px 5px;
            border-left: 4px solid orange;
            color: #000;
            font-size: 17px;
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

        .ui-comm-div button {
            height: 30px;
            margin: 5px;
        }

        .mui-card-header.mui-card-media .mui-media-body {
            font-size: 16px;
        }

        .mui-card-content,
        .ui-card .ui-text-body {
            font-size: 16px;
        }

        .ui-comment {
            border-top: 1px solid #ddd;
        }

        .ui-footer {
            text-align: right;
        }

        #replyBtn {
            font-size: 15px;
            color: #777;
        }

        .mui-card-header:after{
            height: 0px;
        }

    </style>
</head>
<body>

<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <div class="ui-card">
            <div class="mui-card-header mui-card-media">
                <img src="${s.avatar}">
                <div class="mui-media-body">
                    ${s.username}
                    <small class="mui-pull-right">${s.timeStr}</small>
                    <p>${s.deptname}</p>
                </div>
            </div>
            <div class="mui-card-content">
                <p class="ui-text-body">
                    ${s.content}
                </p>
            </div>
            <div class="ui-footer">
                <a id="replyBtn"><span class="fa fa-share"></span> 转发</a>
            </div>
        </div>
        <div class="ui-comment">
            <div class="ui-comment-header">
                <h5>最新回复</h5>
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
    <textarea id="sendCont" rows="1" placeholder="写回复..."></textarea>
    <button id="sendBtn" type="button" class="mui-btn mui-btn-blue">发表</button>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript" ></script>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {
        var _self = homePath + '/wx/common/bbs/article'
        var id = '${param.id}'

        wxgh.wxInit('${weixin}')

        var info = {
            action: 'comm_list',
            articleId: id,
            type: 2
        }
        var refresh = window.refresh('#refreshContainer', {
            url: _self + "/comm_list.json",
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
                        mui.getJSON(_self + "/add_zan.json", {
                            action: 'add_zan',
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
                refresh.addItem('<div class="mui-content-padded mui-text-center">暂无回复哦</div>')
            }
        }

        var loadign = ui.loading('转发中...')

        /**
         * 转发
         */
        $('#replyBtn').on('tap', function () {
            wxgh.wxContactOpen('${wx_contact}', function (all, users) {
                if (all) {
                    alert('不能转发给全部人哦！')
                }

                if (users && users.length > 0) {
                    var tipStr = ''
                    var userids = []
                    for (var i in users) {
                        var userid = users[i].id
                        var name = users[i].name
                        tipStr += name + ','
                        userids.push(userid)
                    }
                    tipStr = tipStr.substring(0, tipStr.length - 1)

                    var cf = confirm('是否转发给以下用户：' + tipStr + '？')
                    if (cf) {
                        loadign.show()
                        mui.post(homePath + '/wx/party/sug/show/tran.json', {
                            action: 'tran',
                            id: id,
                            users: userids.toString()
                        }, function (res) {
                            loadign.hide()
                            if (res.ok) {
                                alert('转发成功！')
                            } else {
                                alert('转发失败！')
                            }
                        }, 'json')
                    }
                }
            })
        })
        /**
         * 点赞
         */
        $('#zanBtn').on('tap', function () {
            var $self = $(this)
            mui.getJSON(_self + "/add_zan.json", {action: 'add_zan', id: id, type: 1}, function (res) {
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
                alert('请输入回复内容哦')
                return
            }
            var info = {action: 'add_comm'}
            info['atlComment'] = txt
            info['atlId'] = id
            info['type'] = 2
            mui.post(_self + "/add_comm.json", info, function (res) {
                if (res.ok) {
                    refresh.request(refresh, 1, 'down')
                    $sendCont.val('')
                } else {
                    alert('回复失败')
                }
            })
        })
    })
</script>
</body>
</html>
