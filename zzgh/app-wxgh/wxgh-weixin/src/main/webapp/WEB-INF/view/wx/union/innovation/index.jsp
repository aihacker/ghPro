<%--
  Created by IntelliJ IDEA.
  User: Ape
  Date: 2017-7-19
  Time: 09:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

<head>
    <title>岗位创新</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .result-div span {
            padding: 4px 15px;
            font-size: 16px;
        }

        .mui-slider .mui-slider-group .mui-slider-item img {
            height: 210px;
        }

        .ui-icon {
            width: 18px;
            height: 18px;
            display: inline-block;
            background-repeat: no-repeat;
            background-position: center;
            background-size: 100% 100%;
            vertical-align: top;
            margin-top: 2px;
        }

        .ui-icon-suggest {
            background-image: url(${home}/weixin/image/union/innovation/icon/icon_suggest.png);
        }

        .ui-icon-group {
            background-image: url(${home}/weixin/image/union/innovation/icon/icon_group.png);
        }

        .ui-icon-project-team {
            background-image: url(${home}/weixin/image/union/innovation/icon/project-tem.png);
        }

        .ui-icon-result {
            background-image: url(${home}/weixin/image/union/innovation/icon/icon_result.png);
        }

        .ui-icon-honor {
            background-image: url(${home}/weixin/image/union/innovation/icon/icon_honor.png);
        }

        .ui-icon-innovate-status {
            background-image: url(${home}/weixin/image/union/innovation/icon/status.png);
        }

        .ui-icon-advice {
            background-image: url(${home}/weixin/image/union/innovation/icon/icon_advice.png);
        }

        .ui-icon-notice {
            background-image: url(${home}/weixin/image/union/innovation/icon/notice.png);
        }

        .mui-table-view-cell:after {
            background-color: white;
        }
    </style>
</head>

<body>
<%--<header class="mui-bar mui-bar-nav ui-head">--%>
<%--&lt;%&ndash;<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>&ndash;%&gt;--%>

<%--<h1 class="mui-title">岗位创新</h1>--%>
<%--&lt;%&ndash;<a href="${home}/union/work/index.html" class="mui-btn mui-btn-link mui-pull-right">我的工坊</a>&ndash;%&gt;--%>
<%--</header>--%>

<div class="mui-scroll-wrapper">
    <div class="mui-scroll">
        <div id="slider" class="mui-slider">
            <div class="mui-slider-group mui-slider-loop">
                <!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
                <div class="mui-slider-item mui-slider-item-duplicate">
                    <a href="#">
                        <img src="${home}/weixin/image/union/innovation/slide/a4.png">

                        <p class="mui-slider-title">静静看这世界</p>
                    </a>
                </div>
                <div class="mui-slider-item">
                    <a href="#">
                        <img src="${home}/weixin/image/union/innovation/slide/a1.png">

                        <p class="mui-slider-title">“工作坊”充满温暖和力量的团队</p>
                    </a>
                </div>
                <%--<div class="mui-slider-item">--%>
                    <%--<a href="#">--%>
                        <%--<img src="${home}/weixin/image/union/innovation/slide/a2.png">--%>

                        <%--<p class="mui-slider-title">技能比拼大赛，拼出精彩</p>--%>
                    <%--</a>--%>
                <%--</div>--%>
                <div class="mui-slider-item">
                    <a href="#">
                        <img src="${home}/weixin/image/union/innovation/slide/a3.png">

                        <p class="mui-slider-title">“微创新”打造用户心动一瞬间</p>
                    </a>
                </div>
                <%--<div class="mui-slider-item">--%>
                    <%--<a href="#">--%>
                        <%--<img src="${home}/weixin/image/union/innovation/slide/a4.png">--%>

                        <%--<p class="mui-slider-title">追求卓越，永争第一</p>--%>
                    <%--</a>--%>
                <%--</div>--%>
                <!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
                <div class="mui-slider-item mui-slider-item-duplicate">
                    <a href="#">
                        <img src="${home}/weixin/image/union/innovation/slide/a1.png">

                        <p class="mui-slider-title">“工作坊”充满温暖和力量的团队</p>
                    </a>
                </div>
            </div>
            <div class="mui-slider-indicator mui-text-right">
                <div class="mui-indicator mui-active"></div>
                <%--<div class="mui-indicator"></div>--%>
                <%--<div class="mui-indicator"></div>--%>
                <div class="mui-indicator"></div>
            </div>
        </div>

        <div class="ui-margin-top-10">
            <ul class="mui-table-view" style="background-color: #efeff4;">
                <li class="mui-table-view-cell" style="margin-bottom: 7px;background-color: white;">
                    <a href="${home}/wx/union/innovation/notice/index.html" class="mui-navigate-right">
                        <span class="ui-icon ui-icon-notice"></span>&nbsp;公告
                    </a>
                </li>
                <li class="mui-table-view-cell" style="margin-bottom: 7px;background-color: white;">
                    <a href="#typePopover" class="mui-navigate-right">
                        <span class="ui-icon ui-icon-suggest"></span>&nbsp;申报创新项目
                    </a>
                </li>
                <li class="mui-table-view-cell" style="margin-bottom: 7px;background-color: white;">
                    <a href="${home}/wx/union/innovation/advice/index.html?status=3" class="mui-navigate-right">
                        <span class="ui-icon ui-icon-advice"></span>&nbsp;创新建议
                        <%--<span class="mui-badge mui-badge-success">进行中</span>--%>
                    </a>
                </li>

                <li class="mui-table-view-cell" style="margin-bottom: 7px;background-color: white;">
                    <a href="${home}/wx/union/innovation/member/index.html" class="mui-navigate-right">
                        <span class="ui-icon ui-icon-innovate-status"></span>&nbsp;个人审核进度
                    </a>
                </li>
                <li class="mui-table-view-cell" style="margin-bottom: 7px;background-color: white;">
                    <a href="${home}/wx/union/innovation/team/index.html" class="mui-navigate-right">
                        <span class="ui-icon ui-icon-project-team"></span>&nbsp;项目团队
                    </a>
                </li>
                <li class="mui-table-view-cell" style="margin-bottom: 7px;background-color: white;">
                    <a href="${home}/wx/union/innovation/result/index.html" class="mui-navigate-right">
                        <span class="ui-icon ui-icon-result"></span>&nbsp;成果展示
                    </a>
                </li>
                <%--<li class="mui-table-view-cell">--%>
                <%--<a href="${home}/union/work/add.html" class="mui-navigate-right">--%>
                <%--<span class="mui-icon mui-icon-plusempty"></span>&nbsp;创建工作坊--%>
                <%--</a>--%>
                <%--</li>--%>
                <li class="mui-table-view-cell" style="margin-bottom: 7px;background-color: white;">
                    <a href="${home}/wx/union/innovation/honor/index.html" class="mui-navigate-right">
                        <span class="ui-icon ui-icon-honor"></span>&nbsp;荣誉墙
                    </a>
                </li>
                <li class="mui-table-view-cell" style="margin-bottom: 7px;background-color: white;">
                    <a href="${home}/wx/union/innovation/work/index.html" class="mui-navigate-right">
                        <span class="ui-icon ui-icon-group"></span>&nbsp;工作坊
                    </a>
                </li>

            </ul>
        </div>

        <%--<div class="ui-margin-top-15">
            <div class="mui-text-center result-div">
                <span class="mui-badge"><i class="mui-icon mui-icon-flag ui-text-primary"></i>最新成果</span>
            </div>
            <c:choose>
                <c:when test1="${empty results}">
                    <div class="mui-content-padded mui-text-center">
                        暂无最新创新成果哦
                    </div>
                </c:when>
                <c:otherwise>
                    <ul class="mui-table-view ui-margin-top-10">
                        <c:forEach items="${results}" var="item">
                            <li class="mui-table-view-cell mui-media">
                                <a href="${home}/union/work/resultinfo.html?id=${item.id}&workType=${item.workType}">
                                    <c:choose>
                                        <c:when test1="${item.imgAvatar != null && item.imgAvatar != ''}">
                                            <img style="width: 37px;" class="mui-media-object mui-pull-left"
                                                 src="${item.imgAvatar}">
                                        </c:when>
                                        <c:otherwise>
                                            <img style="width: 37px;" class="mui-media-object mui-pull-left"
                                                 src="${home}/image/common/nopic.gif">
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="mui-media-body">项目名：${item.itemName}
                                        <span class="mui-right mui-pull-right"> </span>

                                        <p class="mui-ellipsis">
                                            <fmt:formatDate value="${item.addTime}"
                                                            pattern="yyyy-MM-dd HH:mm"></fmt:formatDate>
                                            <span class="mui-right mui-pull-right"></span>
                     WorkHonorService                   </p>
                                    </dWorkHonorServiceiv>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>
        </div>--%>
    </div>
</div>

<%--弹出菜单--%>
<div id="typePopover" class="mui-popover">
    <ul class="mui-table-view" id="ul-type">
        <%--<li class="mui-table-view-cell"><a type="1">竞赛</a></li>--%>
        <li class="mui-table-view-cell"><a type="4">创新建议</a></li>
        <%--<li class="mui-table-view-cell"><a type="3">微创新</a></li>--%>
        <li class="mui-table-view-cell"><a type="2">工作坊</a></li>
    </ul>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    mui("#slider").slider({
        interval: 5000
    });
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005,
        indicators: false
    });
    $(function () {
        $("#ul-type a").on('tap', function () {
            var type = $(this).attr("type");
            if (type == 2) {
                mui.openWindow(homePath + '/wx/union/innovation/declare/work.html')
            } else if (type == 4) {
                mui.openWindow(homePath + '/wx/union/innovation/declare/advice.html')
            } else if (type == 1) {
                mui.openWindow(homePath + '/innovation/race/index.wx')
            } else if (type == 3) {
                mui.openWindow(homePath + '/innovation/innovate_micro.wx')
            }
        });
    })
</script>
</body>

</html>