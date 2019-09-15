<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/14
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>【饭堂】${g.name}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .group-head .ui-img-div {
            height: 180px;
            position: relative;
        }

        .group-head .mui-backdrop {
            height: 180px;
            z-index: 1;
        }

        .mui-bar-nav {
            -webkit-box-shadow: none;
            box-shadow: none;
        }

        .mui-bar-nav a {
            color: #fff;
        }

        .mui-bar-nav a.mui-icon-more {
            font-size: 24px;
        }

        .mui-table-view-cell small {
            float: right;
            margin-right: 20px;
            position: relative;
            top: 4px;
            color: #777;
        }

        .ui-user-avatars {
            display: inline-block;
        }

        .ui-user-avatars img {
            width: 40px;
            height: 40px;
            -webkit-border-radius: 50%;
            border-radius: 50%;
            margin: 0 5px;
        }

        .ui-user span.mui-icon {
            position: relative;
            top: -10px;
            font-size: 25px;
        }

        .ui-user small {
            top: 14px;
        }

        @media (max-width: 330px) {
            .ui-user-avatars img {
                width: 30px;
                height: 30px;
            }

            .ui-user span.mui-icon {
                top: -5px;
            }

            .ui-user small {
                top: 6px;
            }
        }

        .mui-grid-view.mui-grid-9 {
            background-color: #fff;
        }

        .mui-table-view.ui-margin-top-10:before,
        .mui-table-view.ui-margin-top-10:after {
            height: 0;
        }

        .group-head {
            position: relative;
        }

        .ui-change-avatar {
            position: absolute;
            z-index: 8;
            color: #fff;
            font-size: 13px;
            width: 100%;
            height: 100%;
            text-align: center;
            line-height: 180px;
            top: 0;
            left: 0;
        }

        .ui-team-name {
            position: absolute;
            z-index: 6;
            bottom: 0;
            padding: 0 10px 5px 10px;
            width: 100%;
        }

        .ui-team-name h5 {
            font-size: 16px;
            color: #fff;
        }

        .ui-team-info {
            background-color: #fff;
            padding: 0 15px;
        }

        .ui-team-info h5 {
            font-size: 16px;
            color: inherit;
            padding: 8px 0 4px 0;
        }

        .mui-grid-view.mui-grid-9 .mui-table-view-cell {
            padding: 0;
            border: none;
        }

        .mui-grid-view.mui-grid-9 .mui-table-view-cell .mui-badge {
            background: red;
            color: #fff;
            position: absolute;
            top: -2px;
            right: -8px;
        }
    </style>
</head>
<body>

<div class="mui-content">
    <header class="mui-bar mui-bar-nav mui-bar-transparent">
        <%--<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>--%>
        <c:if test="${g.join}">
            <a id="moreBtn" class="mui-pull-right mui-icon mui-icon-more-filled"></a>
        </c:if>
    </header>
    <div class="group-head">
        <div class="ui-img-div">
            <img id="avatarImg" src="${home}${g.path}"/>
        </div>
        <div class="mui-backdrop"></div>
        <c:if test="${hasPermission}">
            <div class="ui-change-avatar">点击更换饭堂头像</div>
        </c:if>
        <div class="ui-team-name">
            <h5>${g.name}</h5>
        </div>
    </div>

    <c:if test="${g.join}">
        <div>
            <ul class="mui-table-view mui-grid-view mui-grid-9">
                <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                    <a href="${home}/wx/canteen/act/index.html?id=${param.id}">
							<span class="fa-stack fa-lg">
							  <i class="fa fa-circle fa-stack-2x"></i>
							  <i class="fa fa-flag fa-stack-1x fa-inverse"></i>
							</span>
                        <div class="mui-media-body">每日活动</div>
                    </a>
                </li>
                <c:if test="${hasPermission}">
                    <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                        <a href="${home}/wx/canteen/apply/index.html?id=${param.id}">
							<span class="fa-stack fa-lg">
							  <i class="fa fa-circle fa-stack-2x"></i>
							  <i class="fa fa-bell fa-stack-1x fa-inverse"></i>
                                <c:if test="${g.applyCount gt 0}">
                                    <span class="mui-badge">${g.applyCount}</span>
                                </c:if>
							</span>
                            <div class="mui-media-body">新成员审核</div>
                        </a>
                    </li>
                </c:if>
                <%--<li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">--%>
                    <%--<a href="${home}/wx/union/group/record/index.html?id=${param.id}">--%>
							<%--<span class="fa-stack fa-lg">--%>
							  <%--<i class="fa fa-circle fa-stack-2x"></i>--%>
							  <%--<i class="fa fa-rmb fa-stack-1x fa-inverse"></i>--%>
							<%--</span>--%>
                        <%--<div class="mui-media-body">积分会费</div>--%>
                    <%--</a>--%>
                <%--</li>--%>
                <%--<li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">--%>
                    <%--<a href="${home}/wx/union/group/pay/record.html?id=${param.id}">--%>
							<%--<span class="fa-stack fa-lg">--%>
							  <%--<i class="fa fa-circle fa-stack-2x"></i>--%>
							  <%--<i class="fa fa-file-text-o fa-stack-1x fa-inverse"></i>--%>
							<%--</span>--%>
                        <%--<div class="mui-media-body">充值记录</div>--%>
                    <%--</a>--%>
                <%--</li>--%>
            </ul>
        </div>
    </c:if>

    <ul class="mui-table-view ui-margin-top-10">
        <c:if test="${g.join}">
            <li class="mui-table-view-cell">
                <a href="${home}/wx/pub/user/show.html?userid=${g.userid}" class="mui-navigate-right">
                    <span>创建人</span>
                    <small>${g.username}</small>
                </a>
            </li>
        </c:if>
        <li id="groupUserLi" class="mui-table-view-cell">
            <a class="mui-navigate-right ui-user">
                <span class="mui-icon mui-icon-contact"></span>
                <div class="ui-user-avatars">
                    <c:forEach items="${g.avatars}" var="m">
                        <c:choose>
                            <c:when test="${empty m}">
                                <img src="${home}/image/default/user.png">
                            </c:when>
                            <c:otherwise>
                                <img src="${m}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:if test="${hasPermission}">
                        <img id="addUserBtn" src="${wx}/image/union/group/input-plus.png"/>
                    </c:if>
                </div>
                <small>${g.userNumb}名成员</small>
            </a>
        </li>
    </ul>

    <div class="ui-team-info ui-margin-top-10">
        <h5>饭堂介绍</h5>
        <small class="ui-text-info">本饭堂创建于<c:choose><c:when
                test="${empty g.addTime}">未知时间</c:when><c:otherwise><fmt:formatDate value="${g.addTime}"
                                                                                    pattern="yyyy年-MM月-dd日"/></c:otherwise></c:choose></small>
        <p>${g.info}</p>
    </div>
    <div class="ui-fixed-bottom">
        <c:if test="${!g.join}">
            <button data-status="${u.status}" id="applyBtn" type="button"
                    class="ui-btn mui-btn mui-btn-danger mui-btn-block ${(!empty u and u.status eq 0)?'mui-disabled':''}">
                <c:choose>
                    <c:when test="${empty u}">
                        申请加入
                    </c:when>
                    <c:otherwise>
                        <c:if test="${u.status eq 0}">
                            申请已提交
                        </c:if>
                        <c:if test="${u.status eq 2}">
                            重新申请
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </button>
        </c:if>
    </div>
</div>

<div id="quitSheet" class="mui-popover mui-popover-bottom mui-popover-action ">
    <!-- 可选择菜单 -->
    <ul class="mui-table-view">
        <li class="mui-table-view-cell ui-text-danger">
            <a href="javascript:;" id="quitBtn">${hasPermission?'解散饭堂':'退出饭堂'}</a>
        </li>
    </ul>
    <!-- 取消菜单 -->
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a href="#quitSheet"><b>取消</b></a>
        </li>
    </ul>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script>
    wxgh.wxInit('${weixin}')
    var groupId = '${param.id}'
    $(function () {
        var shareUrl = window.location.href
        wxgh.wxShareInit('${g.name}', shareUrl, '${g.path}', '${g.info}')

        //团队拉人
        $('#addUserBtn').on('tap', function (e) {
            e.stopPropagation()

            wxgh.wxContactOpen('${wx_contact}', function (all, users) {
                if (all) {
                    alert('不支持全选哦')
                    return
                }
                if (users && users.length > 0) {
                    var info = {}
                    info['users'] = users
                    info['id'] = groupId
                    info['all'] = all
                    var self = this
                    if (!self.loading) {
                        self.loading = ui.loading('添加中...')
                    }
                    self.loading.show()
                    wxgh.request.postJson('show/add_user.json', info, function () {
                        ui.showToast('添加成员成功', function () {
                            window.location.reload(true)
                        })
                    })
                }

            })
        })

        $('#moreBtn').on('tap', function () {
            mui('#quitSheet').popover('toggle')
        })

        //退出团队
        $('#quitBtn').on('tap', function () {
            wxgh.request.post('/wx/canteen/show/out.json', {id: groupId}, function (msg) {
                ui.showToast(msg, function () {
                    mui.openWindow(homePath + '/wx/canteen/index.html')
                })
            })
        })

        //申请加入
        $('#applyBtn').on('tap', function () {
            var stats = $(this).attr('data-status')
            var cfm
            if (stats && stats == 2) {
                cfm = confirm('是否重新申请加入该饭堂')
            } else {
                cfm = confirm('是否申请加入该饭堂')
            }
            if (cfm) {
                wxgh.request.post('/wx/canteen/show/apply.json', {id: groupId}, function () {
                    ui.showToast('申请成功', function () {
                        window.location.reload(true)
                    })
                })
            }
        })

        //查看群成员
        $('#groupUserLi').on('tap', function (e) {
            mui.openWindow(homePath + '/wx/canteen/user/index.html?id=' + groupId)
        })

        //更换协会头像
        $('.ui-change-avatar').on('tap', function () {
            wx.chooseImage({
                count: 1, // 默认9
                sizeType: ['original', 'compressed'],
                sourceType: ['album', 'camera'],
                success: function (res) {
                    var localIds = res.localIds;
                    if (localIds && localIds.length > 0) {
                        var cfm = confirm('是否修改协会头像？');
                        var $avatarImg = $('#avatarImg');
                        var oldSrc = $avatarImg.attr('src');
                        wxgh.get_wx_img(localIds[0], function (src) {
                            $avatarImg.attr('src', src)
                        });
                        if (cfm) {
                            var self = this
                            if (!self.loading) {
                                self.loading = ui.loading('修改中...')
                            }
                            self.loading.show()
                            wx.uploadImage({
                                localId: localIds[0],
                                isShowProgressTips: 0,
                                success: function (res) {
                                    var serverId = res.serverId; // 返回图片的服务器端ID
                                    if (serverId) {
                                        editGroup({avatarId: serverId}, self)
                                    }
                                }
                            })
                        } else {
                            $avatarImg.attr('src', oldSrc);
                        }
                    }
                }
            })
        })

        function editGroup(info, self) {
            info['id'] = groupId
            wxgh.request.post('/wx/canteen/show/edit.json', info, function () {
                ui.showToast('修改成功')
            })
        }
    })
</script>
</body>
</html>
