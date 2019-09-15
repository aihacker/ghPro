<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/24
  Time: 17:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>【${a.name}】详情</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .bg-img {
            height: 200px;
        }

        .mui-table-view-cell {
            padding: 10px 15px;
        }

        .mui-slider .mui-slider-group .mui-slider-item img {
            height: 210px;
        }
    </style>
</head>
<body>
<div class="mui-scroll-wrapper" style="bottom: 60px;">
    <div class="mui-scroll">
        <div class="mui-content">
            <div>
                <%--<div class="bg-img ui-img-div">--%>
                    <%--<c:choose>--%>
                        <%--<c:when test1="${empty a.path}">--%>
                            <%--<img src="${home}/image/default/act.png"/>--%>
                        <%--</c:when>--%>
                        <%--<c:otherwise>--%>
                            <%--<img src="${home}${a.path}"/>--%>
                        <%--</c:otherwise>--%>
                    <%--</c:choose>--%>
                <%--</div>--%>
                    <div class="bg-img ui-img-div">
                        <c:choose>
                            <c:when test="${empty a.covers}">
                                <img src="${home}/image/default/act.png"/>
                            </c:when>
                            <c:otherwise>
                                <div id="slider" class="mui-slider">
                                    <div class="mui-slider-group mui-slider-loop">
                                        <c:choose>
                                            <c:when test="${empty a.covers}">
                                                <img src="${home}/image/default/act.png"/>
                                            </c:when>
                                            <c:otherwise>
                                                <!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
                                                <div class="mui-slider-item mui-slider-item-duplicate">
                                                    <c:forEach items="${a.covers}" var="n" varStatus="i">
                                                        <c:if test="${i.last}">
                                                            <a href="#">
                                                                <img src="${home}${n}">
                                                            </a>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                                <c:forEach items="${a.covers}" var="n" varStatus="i">
                                                    <div class="mui-slider-item">
                                                        <a href="#">
                                                            <img src="${home}${n}">
                                                        </a>
                                                    </div>
                                                </c:forEach>
                                                <!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
                                                <div class="mui-slider-item mui-slider-item-duplicate">
                                                    <c:forEach items="${a.covers}" var="n" varStatus="i">
                                                        <c:if test="${i.first}">
                                                            <a href="#">
                                                                <img src="${home}${n}">
                                                            </a>
                                                        </c:if>
                                                    </c:forEach>
                                                </div>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="mui-slider-indicator mui-text-right">
                                        <div class="mui-indicator mui-active"></div>
                                        <c:forEach items="${a.covers}" var="n" step="1" begin="1" varStatus="i">
                                            <div class="mui-indicator"></div>
                                        </c:forEach>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>

                    </div>
                    <div id="actName" style="max-height: 80px;min-height: 50px;" class="ui-content mui-ellipsis-2">
                        ${a.name}
                    </div>

            <ul class="mui-table-view no ui-margin-top-10">
                <li class="mui-table-view-cell no">
                    <a class="ui-flex">
                        <span class="fa fa-clock-o"></span>
                        <span>活动时间</span>
                        <small class="ui-right">${a.time}</small>
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a id="actAddress" data-lat="${a.lat}" data-lng="${a.lng}" class="mui-navigate-right ui-flex">
                        <span class="fa fa-map-marker"></span>
                        <span>活动地点</span>
                        <small class="ui-right">${a.address}</small>
                    </a>
                </li>
                <li class="mui-table-view-cell">
                    <a class="mui-navigate-right ui-flex" href="${home}/wx/pub/user/show.html?userid=${a.userid}">
                        <span class="fa fa-flag"></span>
                        <span>主办方</span>
                        <small class="ui-right">${a.username}</small>
                    </a>
                </li>
            </ul>
            <div class="ui-content ui-margin-top-10">
                <h5 class="ui-title">活动介绍</h5>
                <p id="actInfo" title="${a.info}" class="mui-ellipsis-2">
                    ${a.info}
                </p>
                <a href="${home}/wx/canteeen/act/detail.html?id=${a.id}">查看详情</a>
            </div>

            <ul class="mui-table-view ui-margin-top-10 no">
                <li class="mui-table-view-cell">
                    <a href="${home}/wx/canteeen/act/join.html?id=${a.id}" class="mui-navigate-right ui-flex">
                        <span>报名情况</span>
                        <small class="ui-right">${empty a.joinNumb?0:a.joinNumb}人报名，${empty a.leaveNumb?0:a.leaveNumb}人请假</small>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>

<div class="ui-fixed-bottom ui-flex">
    <c:if test="${a.status eq 1}">
        <button type="button" data-type="2" class="mui-btn mui-btn-danger${a.joinType eq 2?' mui-disabled':''}"
                style="flex: 1;">${a.joinType eq 2?'你已请假':'我要请假'}
        </button>
        <button type="button" data-type="1" class="mui-btn mui-btn-primary${a.joinType eq 1?' mui-disabled':''}"
                style="flex: 2;">${a.joinType eq 1?'你已报名':'我要参加'}
        </button>
    </c:if>
    <c:if test="${a.status eq 2}">
        <button type="button" class="mui-btn mui-btn-danger mui-disabled">活动进行中</button>
    </c:if>
    <c:if test="${a.status eq 3 and a.regular eq 0}">
        <button type="button" class="mui-btn mui-btn-danger mui-disabled">活动已结束</button>
    </c:if>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script>

    var actId = '${param.id}';

    $(function () {
        mui('.mui-scroll-wrapper').scroll({
            deceleration: 0.0005
        });
        wxgh.init('${weixin}')
//        var imgSrc = $('.bg-img img').attr('src')
        // 新增轮播图后获取第一张
        var imgSrc = $('.mui-slider').find("img:first").attr('src')
        wxgh.wxShareInit($('#actName').text(), window.location.href, imgSrc, $('#actInfo').attr('title'))

        $('#actAddress').on('tap', function () {
            var $self = $(this)
            var lat = $self.data('lat')
            var lng = $self.data('lng')
            var address = $self.find('.ui-right').text()
            if (lat && lng) {
                mui.openWindow('http://api.map.baidu.com/marker?location=' + lat + ',' + lng + '&title=活动地址&content=' + address + '&output=html')
            } else {
                ui.alert('未知地址')
            }
        })

        var loading = ui.loading('操作中...')
        $('.ui-fixed-bottom').on('tap', 'button[data-type]', function () {
            var type = $(this).data('type')
            var typeName = type == 1 ? '报名' : '请假'
            ui.confirm('是否' + typeName, function () {
                loading.show()
                wxgh.request.post('${home}/wx/canteen/act/act_join_by_team.json', {id: actId, type: type}, function () {
                    ui.showToast(typeName + '成功！', function () {
                        window.location.reload()
                    })
                })
            })
        })
    })
</script>
</body>
</html>