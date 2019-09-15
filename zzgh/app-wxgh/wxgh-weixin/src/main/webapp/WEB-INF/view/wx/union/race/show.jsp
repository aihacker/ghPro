<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/2
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>“${r.name}”比赛</title>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body {
            background-color: #efeff4;
        }

        .ui-bg.ui-img-div {
            max-height: 180px;
        }

        .ui-bg img {
            width: 100%;
        }

        .ui-info {
            color: #646464;
            margin: 0;
            padding: 5px 10px;
            background-color: #fff;
            text-indent: 15px;
            min-height: 50px;
        }

        .ui-info-list {
            color: #7c7b7b;
        }

        .ui-info-list li.mui-table-view-cell {
            padding: 8px 15px;
        }

        .ui-info-list.mui-table-view:after,
        .ui-info-list.mui-table-view:before {
            height: 0;
        }

        .ui-info .mui-btn-link {
            text-indent: 0;
        }

        .mui-scroll-wrapper {
            bottom: 45px;
        }

        .ui-choose-baoming.mui-table-view:after,
        .ui-choose-baoming.mui-table-view:before {
            height: 0;
        }

        .ui-choose-baoming .mui-navigate-right small {
            float: right;
            margin-right: 18px;
            position: relative;
            top: 3px;
        }
    </style>
</head>

<body>
<div class="mui-scroll-wrapper" style="overflow:scroll;">
    <div class="mui-scroll">
        <div class="ui-img-div ui-bg">
            <img src="${r.img}"/>
        </div>
        <div class="mui-content-padded ui-info">
            <small style="font-size: 15px;">${r.info}</small>
            <div class="mui-text-center">
                <a class="mui-btn mui-btn-link mui-hidden">点击查看更多 <span class="fa fa-angle-down"></span></a>
            </div>
        </div>
        <ul class="mui-table-view ui-info-list ui-margin-top-10">
            <li class="mui-table-view-cell">
                <span>赛事名称 : </span>
                <small>${r.name}</small>
            </li>
            <li class="mui-table-view-cell">
                <span>赛事项目：</span>
                <small><c:if test="${r.cateType eq 1}">羽毛球 - ${r.raceType eq 1?'单打':'双打'}</c:if>
                    <c:if test="${r.cateType eq 2}">乒乓球 - ${r.raceType eq 1?'单打':'双打'}</c:if>
                    <c:if test="${r.cateType eq 3}">篮球</c:if>
                    <c:if test="${r.cateType eq 4}">网球</c:if></small>
            </li>
            <li class="mui-table-view-cell">
                <span>赛事状态：</span>
                <small>
                    <c:if test="${status eq 1}">报名中</c:if>
                    <c:if test="${status eq 2}">进行中</c:if>
                    <c:if test="${status eq 3}">已结束</c:if>
                    <c:if test="${status eq 4}">名额已满</c:if>
                    <c:if test="${status eq 5}">已报名</c:if>
                    <c:if test="${status eq 6}"><a href="${home}/race/show.wx?id=${r.id}">点击登录</a></c:if>
                    <c:if test="${status eq 7}">报名截止</c:if>
                </small>
            </li>
            <li class="mui-table-view-cell">
                <span>比赛时间 : </span>
                <small><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${r.startTime}"/> 至 <fmt:formatDate
                        pattern="yyyy-MM-dd HH:mm" value="${r.endTime}"/></small>
            </li>
            <li class="mui-table-view-cell ui-text-danger">
                <span>报名截止时间 : </span>
                <small><fmt:formatDate pattern="yyyy-MM-dd HH:mm"
                                       value="${r.entryTime}"/> ${status eq 7?'已截止报名':''}</small>
            </li>
        </ul>
        <ul class="mui-table-view ui-info-list ui-margin-top-10">
            <li class="mui-table-view-cell">
                <span>发起人 : </span>
                <small>${r.username}</small>
            </li>
            <li class="mui-table-view-cell">
                <span>联系电话 : </span>
                <small>${r.phone}</small>
            </li>
        </ul>

        <ul class="mui-table-view ui-info-list ui-margin-top-10">
            <c:if test="${r.bianpaiIs eq 0 and userid}">
                <li class="mui-table-view-cell" data-type="1" id="bianpaiBtn">
                    <a class="mui-navigate-right">
                        <span>比赛编排：</span>
                        <span class="mui-pull-right ui-text-secondary" style="margin-right: 18px;">进入编排</span>
                    </a>
                </li>
            </c:if>
            <c:if test="${r.bianpaiIs eq 1}">
                <li class="mui-table-view-cell" data-type="2" id="bianpaiBtn">
                    <a class="mui-navigate-right">
                        <span>比赛编排：</span>
                        <span class="mui-pull-right ui-text-secondary" style="margin-right: 18px;">查看编排</span>
                    </a>
                </li>
                <li class="mui-table-view-cell" data-type="2" id="resultBtn">
                    <a class="mui-navigate-right">
                        <span>比赛结果：</span>
                        <span class="mui-pull-right ui-text-secondary" style="margin-right: 18px;">查看结果</span>
                    </a>
                </li>
            </c:if>
        </ul>
    </div>
</div>
<div class="ui-fixed-bottom">
    <button id="joinBtn" type="button"
            class="ui-btn mui-btn-primary mui-btn mui-btn-block${status gt 1?' mui-disabled':''}">${btn}</button>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function () {

        var id = '${param.id}'
        var raceType = '${r.raceType}'
        var userId = '${userList.userid}'

        <%--wxgh.wxInit('${weixin}')--%>
        <%--console.log('${weixin}')--%>

        mui.init()
        mui('.mui-scroll-wrapper').scroll({
            deceleration: 0.0005
        });

        if (raceType == 2) {
            wxgh.wxInit('${weixin}')
        }

        var $moreText = $('.ui-info .mui-btn-link')
        var $smal = $('.ui-info small')
        var infoTxt = $smal.text()
        initMore()
        $moreText.on('tap', function () {
            if ($(this).hasClass('ui-has')) {
                $smal.text(infoTxt)
                $(this).removeClass('ui-has')
            } else {
                initMore()
            }
        })

        $('#bianpaiBtn').on('tap', function () {
            var type = $(this).attr('data-type')
            if (type == 1) {
                mui.openWindow(homePath + '/wx/union/race/arrange/arrange.html?id=' + id)
            } else {
                mui.openWindow(homePath + '/wx/union/race/arrange/result.html?id=' + id)
            }
        })

        $('#resultBtn').on('tap', function () {
            mui.openWindow(homePath + '/wx/union/race/score/result.html?id=' + id)
        })

        var evalWXjsApi = function (jsApiFun) {
            if (typeof WeixinJSBridge == "object" && typeof WeixinJSBridge.invoke == "function") {
                jsApiFun();
            } else {
                document.attachEvent && document.attachEvent("WeixinJSBridgeReady", jsApiFun);
                document.addEventListener && document.addEventListener("WeixinJSBridgeReady", jsApiFun);
            }
        }

        $('#joinBtn').on('tap', function () {
            var msg, title
            if (raceType == 1 || raceType == 0) {
                msg = '是否报名？'
                title = '系统提示'
            } else {
                title = '选择队友'

                msg = '<ul class="mui-table-view ui-choose-baoming">'
                    + '<li class="mui-table-view-cell">'
                    + '<a id="chooseUserBtn" class="mui-navigate-right">比赛队友：'
                    + '<small>请选择</small></a>'
                    + '</li></ul>'

                //  msg = '<div class="mui-content-padded mui-navigate-right">报名队友：<small class="mui-pull-right">徐朗凯</small><div/>'
            }
            ui.wxconfirm(title, msg, null, function (index) {
                console.log(index)
                if (index == 1) {
                    var userids
                    if (raceType == 2) {
                        var userid = $('#chooseUserBtn').data('userid').id
                        if (!userid) {
                            alert('比赛为双打，请先选择一个队友哦')
                            return;
                        }
                        userids = userid
                    }
                    if (!this.load) {
                        this.load = ui.loading('请稍候...')
                    }
                    this.load.show()
                    var self = this
                    var url = homePath + '/wx/union/race/api/join.json'
                    wxgh.request.post(url, {action: 'join', id: id, userid: userids}, function (res) {
                        self.load.hide()
                        ui.showToast('报名成功', function () {
                            window.location.reload()
                        })
                    }, 'json')
                }
            }, function () {
                if (raceType == 2) {
                    var $username = $('#chooseUserBtn')
                    $('#chooseUserBtn').on('tap', function () {
                        wxgh.wxContactOpen('${wx_contact}',function(all,users){
                            if(users){
                                if (users && users.length > 0) {
                                    if (users[0].id == userId) {
                                        alert('不能选择自己哦')
                                        return;
                                    }
                                    $username.data('userid', users[0])
                                    $username.find('small').text(users[0].name)
                                } else {
                                    alert('请选择一个队友');
                                    return;
                                }
                            }
                        },{mode: 'single'})
                        <%--evalWXjsApi(function () {--%>
                                <%--WeixinJSBridge.invoke("openEnterpriseContact", {--%>
                                    <%--"groupId": "${wx_contact.groupId}",    // 必填，管理组权限验证步骤1返回的group_id--%>
                                    <%--"timestamp": "${wx_contact.timestamp}",    // 必填，管理组权限验证步骤2使用的时间戳--%>
                                    <%--"nonceStr": "${wx_contact.nonceStr}",    // 必填，管理组权限验证步骤2使用的随机字符串--%>
                                    <%--"signature": "${wx_contact.signature}",  // 必填，管理组权限验证步骤2生成的签名--%>
                                    <%--"params": {--%>
                                        <%--'departmentIds': 0,    // 非必填，可选部门ID列表（如果ID为0，表示可选管理组权限下所有部门）--%>
                                        <%--'tagIds': [],    // 非必填，可选标签ID列表（如果ID为0，表示可选所有标签）--%>
                                        <%--'mode': 'signle',    // 必填，选择模式，single表示单选，multi表示多选--%>
                                        <%--'type': ['user'],    // 必填，选择限制类型，指定department、tag、user中的一个或者多个--%>
                                        <%--'selectedDepartmentIds': [],    // 非必填，已选部门ID列表--%>
                                        <%--'selectedTagIds': [],    // 非必填，已选标签ID列表--%>
                                        <%--'selectedUserIds': []    // 非必填，已选用户ID列表--%>
                                    <%--}--%>
                                <%--}, function (res) {--%>
                                    <%--if (res.err_msg.indexOf('function_not_exist') > 0) {--%>
                                        <%--alert('版本过低请升级');--%>
                                    <%--} else if (res.err_msg.indexOf('openEnterpriseContact:fail') > 0) {--%>
                                        <%--return;--%>
                                    <%--}--%>

                                    <%--var result = JSON.parse(res.result);    // 返回字符串，开发者需自行调用JSON.parse解析--%>

                                    <%--if (result.selectAll) {--%>
                                        <%--alert('请选择一个队友');--%>
                                        <%--return;--%>
                                    <%--}--%>

                                    <%--var users = result.userList--%>

                                    <%--if (users && users.length > 0) {--%>
                                        <%--if (users[0].id == userId) {--%>
                                            <%--alert('不能选择自己哦')--%>
                                            <%--return;--%>
                                        <%--}--%>
                                        <%--$username.data('userid', users[0])--%>
                                        <%--$username.find('small').text(users[0].name)--%>
                                    <%--} else {--%>
                                        <%--alert('请选择一个队友');--%>
                                        <%--return;--%>
                                    <%--}--%>
                                <%--});--%>
                            <%--}--%>
                        <%--)--%>
                    })
                    if ($username.data('userid')) {
                        $username.find('small').text($username.data('userid').name)
                    }
                }
            })

        })

        function initMore() {
            if (infoTxt.length > 90) {
                $moreText.removeClass('mui-hidden')
                $moreText.addClass('ui-has')
                $smal.text(infoTxt.substring(0, 90) + "...")
            } else {
                $moreText.addClass('mui-hidden')
            }
        }
    })
</script>
</body>

</html>
