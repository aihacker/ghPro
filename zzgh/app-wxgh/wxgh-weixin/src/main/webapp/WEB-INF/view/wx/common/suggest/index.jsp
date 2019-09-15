<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/17
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>会员提案</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        html,
        body {
            background-color: #efeff4;
        }

        .mui-bar ~ .mui-content .mui-fullscreen {
            top: 42px;
            height: auto;
        }

        .mui-pull-top-tips {
            position: absolute;
            top: -20px;
            left: 50%;
            margin-left: -25px;
            width: 40px;
            height: 40px;
            border-radius: 100%;
            z-index: 1;
        }

        .mui-bar ~ .mui-pull-top-tips {
            top: 24px;
        }

        .mui-pull-top-wrapper {
            width: 42px;
            height: 42px;
            display: block;
            text-align: center;
            background-color: #efeff4;
            border: 1px solid #ddd;
            border-radius: 25px;
            background-clip: padding-box;
            box-shadow: 0 4px 10px #bbb;
            overflow: hidden;
        }

        .mui-pull-top-tips.mui-transitioning {
            -webkit-transition-duration: 200ms;
            transition-duration: 200ms;
        }

        .mui-pull-top-tips .mui-pull-loading {
            /*-webkit-backface-visibility: hidden;
            -webkit-transition-duration: 400ms;
            transition-duration: 400ms;*/
            margin: 0;
        }

        .mui-pull-top-wrapper .mui-icon,
        .mui-pull-top-wrapper .mui-spinner {
            margin-top: 7px;
        }

        .mui-pull-top-wrapper .mui-icon.mui-reverse {
            /*-webkit-transform: rotate(180deg) translateZ(0);*/
        }

        .mui-pull-bottom-tips {
            text-align: center;
            background-color: #efeff4;
            font-size: 15px;
            line-height: 40px;
            color: #777;
        }

        .mui-pull-top-canvas {
            overflow: hidden;
            background-color: #fafafa;
            border-radius: 40px;
            box-shadow: 0 4px 10px #bbb;
            width: 40px;
            height: 40px;
            margin: 0 auto;
        }

        .mui-pull-top-canvas canvas {
            width: 40px;
        }

        .suggest-item {
            position: relative;
        }

        .suggest-item .suggest-title {
            display: block;
            padding-top: 2px;
            padding-bottom: 2px;
            padding-right: 65px;
        }

        .suggest-item .suggest-cnt {
            font-size: 13px;
            color: #acacb4;
        }

        .suggest-item .suggest-numb {
            font-size: 13px;
            color: #acacb4;
            position: absolute;
            right: 0px;
            top: 0px;
        }

        .suggest-numb:before {
            font-size: 16px;
        }

        #addimg {
            position: fixed;
            bottom: 30px;
            right: 20px;
            z-index: 1;
        }

        #msg {
            position: fixed;
            bottom: 100px;
            right: 22px;
            z-index: 1;
        }

        .mui-table-view {
            background-color: #efeff4;
        }

        .mui-table-view-cell {
            margin-top: 8px;
            margin-bottom: 8px;
            background-color: #fff;
        }

        .xlk-head {
            background: -webkit-gradient(linear, 0 0, 0 bottom, from(#08A8F4), to(#1088C0));
        }

        .xlk-head .mui-icon-left-nav {
            color: #fff;
        }

        .xlk-head .mui-title {
            color: #fff;
        }

        #segmentedControl {
            background-color: #fff;
            border-bottom: 1px solid #ddd;
        }

        #refreshContainer {
            top: 48px;
        }

        .mui-media-body > span {
            line-height: 40px;
        }

    </style>
</head>

<body>


<%--<header class="mui-bar mui-bar-nav xlk-head">--%>
    <%--<h1 class="mui-title">建言池</h1>--%>
    <%--<a id="btn-msg" href="${home}/wx/common/suggest/msg.html" class="mui-pull-right mui-icon mui-icon-email"--%>
       <%--style="color: white;"></a>--%>
<%--</header>--%>

<div class="mui-content">
    <div class="mui-slider mui-fullscreen">
        <div class="mui-scroll-wrapper white mui-slider-indicator mui-segmented-control mui-segmented-control-inverted">
            <div class="mui-scroll" id="cateIds">
                <c:forEach items="${cates}" var="cate" varStatus="i">
                    <c:choose>
                        <c:when test="${i.index eq 0}">
                            <a data-id="${cate.id}" class="mui-control-item mui-active" href="#refreshContainer${cate.id}">
                                    ${cate.name}
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a data-id="${cate.id}" class="mui-control-item" href="#refreshContainer${cate.id}">
                                    ${cate.name}
                            </a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </div>

        <div id="refreshContainer" class="mui-scroll-wrapper">
            <div class="mui-scroll">
                <ul class="mui-table-view"></ul>
            </div>
        </div>

        <div class="mui-slider-group">

            <%--
            <c:forEach items="${cates}" var="cate" varStatus="i">
                <c:choose>
                    <c:when test1="${i.index eq 0}">
                        <div id="refreshContainer${cate.id}" class="mui-slider-item mui-control-content mui-active">
                            <div class="mui-scroll-wrapper">
                                <div class="mui-scroll">
                                    <ul class="mui-table-view">
                                        <c:choose>
                                            <c:when test1="${empty cate.suggests}">
                                                <li class="mui-text-center">暂无动态哦</li>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${cate.suggests}" var="sug">
                                                    <li class="mui-table-view-cell">
                                                        <a href="${home}/wx/common/suggest/show/index.html?id=${sug.id}">
                                                            <div class="suggest-item">
                                                                <div class="suggest-title mui-ellipsis">${sug.title}</div>
                                                                <div class="suggest-cnt mui-ellipsis-2">${sug.content}</div>
                                                                <span class="suggest-numb mui-icon mui-icon-chatbubble"> ${empty sug.commNum?0:sug.commNum}人评论</span>
                                                            </div>
                                                        </a>
                                                    </li>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div id="refreshContainer${cate.id}" class="mui-slider-item mui-control-content">
                            <div class="mui-scroll-wrapper">
                                <div class="mui-scroll">
                                    <ul class="mui-table-view">
                                        <c:choose>
                                            <c:when test1="${empty cate.suggests}">
                                                <li class="mui-text-center">暂无动态哦</li>
                                            </c:when>
                                            <c:otherwise>
                                                <c:forEach items="${cate.suggests}" var="sug">
                                                    <li class="mui-table-view-cell">
                                                        <a href="${home}/wx/common/suggest/show/index.html?id=${sug.id}">
                                                            <div class="suggest-item">
                                                                <div class="suggest-title mui-ellipsis">${sug.title}</div>
                                                                <div class="suggest-cnt mui-ellipsis">${sug.content}</div>
                                                                <span class="suggest-numb mui-icon mui-icon-chatbubble"> ${empty sug.commNum?0:sug.commNum}人评论</span>
                                                            </div>
                                                        </a>
                                                    </li>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            --%>

        </div>

    </div>

    <div id="addimg">
        <a href="${home}/wx/common/suggest/add.html">
            <div><img src="${home}/weixin/image/common/bbs/addArt.png" width="50px" height="50px"/></div>
        </a>
    </div>

    <div id="msg" style="width: 50px;height: 50px;background: blue;border-radius: 50px;">
        <a id="btn-msg" href="${home}/wx/common/suggest/msg.html" class="mui-pull-right mui-icon mui-icon-email"
        style="color: white; line-height: 50px; margin-right: 13px;"></a>
     </div>

 </div>

 <script src="${home}/libs/weixin/weixin.min.js" type="text/javascript" ></script>
 <%--<script type="text/javascript" src="${home}/style/xlkai/mui-3.2.0/libs/mui.pullToRefresh.js"></script>--%>
<%--<script type="text/javascript" src="${home}/style/xlkai/mui-3.2.0/libs/mui.pullToRefresh.material.js"></script>--%>
<jsp:include page="/comm/mobile/scripts.jsp"/>

<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell">
        <a href="show/index.html?id={{=it.id}}">
            <div class="suggest-item">
                <div class="suggest-title mui-ellipsis">{{=it.title}}</div>
                <div class="suggest-cnt mui-ellipsis">{{=it.content}}</div>
                <span class="suggest-numb mui-icon mui-icon-chatbubble"> {{ if(it.commNum > 0){ }} {{=it.commNum}} {{ } else{ }} 0 {{ } }} 人评论</span>
            </div>
        </a>
    </li>
</script>
<script src="${home}/comm/mobile/js/refresh.js"></script>



<script type="text/javascript">
    mui.init()

    wxgh.wxInit('${weixin}');

    var deceleration = mui.os.ios ? 0.003 : 0.0009;
    mui('.mui-scroll-wrapper').scroll({
        bounce: false,
        indicators: false,
        deceleration: deceleration
    });
    var url = '${home}/wx/common/suggest/';
    var default_id = $('#cateIds').children("a:first-child").attr('data-id');

    var refresh = window.refresh('#refreshContainer', {
        url: url + 'list.json',
        data: {cid: default_id, pageIs: true},
        ispage: true,
        isBind: true,
        tplId: '#itemTpl',
        emptyText: '暂无动态哦',
        bindFn: bindfn,
        errorFn: function (type) {
            console.log(type)
            ui.alert('获取失败...')
        }
    })

    function bindfn(d) {
        var $item = refresh.getItem(d)
        console.log(d)
        $item.on('tap', function () {
            mui.openWindow(url + 'show/index.html?id=' + d.id)
        })
        return $item[0]
    }

//    initLoad(default_id)
//    $cateSelf = $('#cateIds');
//    $cateSelf.find('a').each(function () {
//        initLoad($(this).attr("data-id"));
//    });

    $('#cateIds').on('tap', '.mui-control-item', function () {
        var cid = $(this).attr('data-id');
        default_id = cid;
        refresh.refresh({cid: cid});
    })



</script>
</body>

</html>
