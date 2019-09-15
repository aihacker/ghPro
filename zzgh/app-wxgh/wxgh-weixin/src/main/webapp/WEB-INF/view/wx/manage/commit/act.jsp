<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/18
  Time: 14:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

<head>
    <title>活动详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"></jsp:include>
    <style>
        .bg-img {
            height: 220px;
        }

        .mui-table-view-cell {
            padding: 8px 15px;
        }

        .mui-table-view-cell > a:not(.mui-btn) {
            margin: -8px -15px;
        }

        .comm-item {
            position: relative;
            padding: 8px 10px;
            background-color: #fff;
        }

        .comm-item img.ui-circle {
            width: 40px;
            height: 40px;
        }

        .comm-item .comm-body {
            margin-left: 50px;
        }
    </style>
</head>

<body>

<div class="ui-fixed-bottom">
    <button id="joinBtn" data-status="${act.joinIs}" type="button"
            class="mui-btn mui-btn-primary${act.joinIs eq 0?'':' mui-disabled'}">${act.btnStr}</button>
</div>
<div class="mui-scroll-wrapper">
    <div class="mui-scroll">
        <div>
            <div class="bg-img ui-img-div">
                <%--<img src="${home}${!empty act.thumb?act.thumb:act.path}"/>--%>
                <img src="${home}${act.path}"/>
            </div>
            <div style="max-height: 80px;min-height: 50px;" class="ui-content mui-ellipsis-2">
                ${act.theme}
            </div>

            <ul class="mui-table-view ui-margin-top-10 no">
                <li class="mui-table-view-cell">
                    <a class="ui-flex">
                        <span class="fa fa-clock-o"></span>
                        <span>活动时间</span>
                        <small class="ui-right">${act.timeStr}</small>
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="http://api.map.baidu.com/marker?location=${act.lat},${act.lng}&title=活动地点&content=${act.address}&output=html"
                       class="ui-flex mui-navigate-right">
                        <span class="fa fa-map-marker"></span>
                        <span>地&nbsp;点</span>
                        <small class="ui-right">${act.address}</small>
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a href="${home}/wx/pub/user/show.html?userid=${act.userid}" class="ui-flex mui-navigate-right">
                        <span class="fa fa-user"></span>
                        <span>联系人</span>
                        <small class="ui-right">${act.username}</small>
                    </a>
                </li>
            </ul>

            <c:if test="${!empty act.content}">
                <div class="ui-content ui-margin-top-10">
                    <h5 class="ui-title">活动简介</h5>
                    <p class="mui-ellipsis-2">
                            ${act.content}
                    </p>
                    <a href="info.html?id=${param.id}&type=1">查看详情</a>
                </div>
            </c:if>

            <c:if test="${!empty act.remark}">
                <div class="ui-content ui-margin-top-10">
                    <h5 class="ui-title">活动备注</h5>
                    <p class="mui-ellipsis-2">
                            ${act.remark}
                    </p>
                    <a href="info.html?id=${param.id}&type=2">查看详情</a>
                </div>
            </c:if>

            <ul class="mui-table-view ui-margin-top-10 no">
                <li class="mui-table-view-cell">
                    <a href="join_list.html?id=${param.id}"
                       class="mui-navigate-right ui-flex">
                        <span>报名情况</span>
                        <small class="ui-right">${act.joinNumb}已报名 / 限${act.totalNumber}人报名</small>
                    </a>
                </li>
            </ul>

            <ul class="mui-table-view ui-margin-top-10">
                <li class="mui-table-view-cell">
                    <a href="comment.html?id=${param.id}"
                       class="mui-navigate-right ui-flex">
                        <span>评论（${act.commNumb}）</span>
                        <small class="ui-right"><span style="font-size: 15px;" class="mui-icon mui-icon-chat"></span>
                            立即评论
                        </small>
                    </a>
                </li>
            </ul>
            <div id="commList">
                <div style="padding: 15px;" class="mui-loading">
                    <div class="mui-spinner"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/html" id="tplComm">
    <div class="comm-item">
        <img class="ui-circle mui-pull-left" src="{{=it.avatar}}"/>
        <div class="comm-body">
            {{=it.username}}
            <small class="mui-pull-right">{{=it.addTime}}</small>
            <p>{{=it.content}}</p>
            {{? it.imgs && it.imgs.length > 0}}
            <ul class="mui-table-view mui-grid-view">
                {{ for(var i in it.imgs){}}
                <li class="mui-table-view-cell mui-media mui-col-xs-6">
                    <a href="javascript:;">
                        <img class="mui-media-object" src="${home}{{=it.imgs[i]}}">
                    </a>
                </li>
                {{}}}
            </ul>
            {{?}}
        </div>
    </div>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"></jsp:include>
<script>
    var actId = '${param.id}';
    $(function () {
        mui('.mui-scroll-wrapper').scroll({
            deceleration: 0.0005
        });

        var $commList = $('#commList')
        var tpl = doT.template($('#tplComm').html())
        $('#tplComm').remove()

        list_comm()

        $('#joinBtn').on('tap', function () {
            var status = $(this).data('status');
            if (status == 0) {
                ui.confirm('是否报名？', function () {
                    wxgh.request.post('../commit/api/join.json', {actId: actId}, function () {
                        ui.alert('报名成功', function () {
                            window.location.reload();
                        });
                    });
                });
            }
        })

        function list_comm() {
            wxgh.request.post('../commit/api/comm_list.json', {id: actId}, function (comms) {
                $commList.empty()
                if (comms && comms.length > 0) {
                    for (var i in comms) {
                        var $item = tpl(comms[i])
                        $commList.append($item)
                    }
                } else {
                    $commList.append('<div class="comm-item ui-empty">暂无评论</div>')
                }
            })
        }
    })

</script>
</body>

</html>



