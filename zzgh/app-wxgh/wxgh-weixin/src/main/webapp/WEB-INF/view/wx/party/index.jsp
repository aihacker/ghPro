<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/27
  Time: 15:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>党建在线</title>
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .result-div span {
            padding: 4px 15px;
            font-size: 16px;
        }

        .mui-slider .mui-slider-group .mui-slider-item img {
            height: 200px;
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
    </style>
    <style type="text/css">
        body {
            overflow-x: hidden;
            overflow-y: hidden;
        }

        #scroll .mui-media img {
            width: 45px;
            height: 45px;
        }

        .mui-grid-view.mui-grid-9 .mui-table-view-cell {
            border-right: 0px solid #eee;
            border-bottom: 0px solid #eee;
        }

        .mui-slider-indicator {
            top: 0px;
            bottom: auto;
        }

        .mui-slider-indicator .mui-indicator {
            margin: 1px 2px;
        }
    </style>
</head>

<body class="mui-fullscreen">
<div id="slider" class="mui-slider">
    <div class="mui-slider-group mui-slider-loop">
        <!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
        <div class="mui-slider-item mui-slider-item-duplicate">
            <a href="#">
                <img src="${home}/weixin/image/lyq/d1.jpg">

                <p class="mui-slider-title">增强党的执政能力，永葆党的青春活力。</p>
            </a>
        </div>
        <div class="mui-slider-item">
            <a href="#">
                <img src="${home}/weixin/image/lyq/d1.jpg">

                <p class="mui-slider-title">勇于创新，敢挑重担，冲锋在前，无私奉献。</p>
            </a>
        </div>
        <div class="mui-slider-item">
            <a href="#">
                <img src="${home}/weixin/image/lyq/d2.jpg">

                <p class="mui-slider-title">把党的基层组织建设成为实践“三个代表”重要思想的实践者、组织者和推动者。</p>
            </a>
        </div>
        <div class="mui-slider-item">
            <a href="#">
                <img src="${home}/weixin/image/lyq/d3.jpg">

                <p class="mui-slider-title">人人为组织建设作贡献，个个以我镇发展添光彩。</p>
            </a>
        </div>
        <div class="mui-slider-item">
            <a href="#">
                <img src="${home}/weixin/image/lyq/d4.jpg">

                <p class="mui-slider-title">增强党的执政能力，永葆党的青春活力。</p>
            </a>
        </div>
        <!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
        <div class="mui-slider-item mui-slider-item-duplicate">
            <a href="#">
                <img src="${home}/weixin/image/lyq/d1.jpg">

                <p class="mui-slider-title">勇于创新，敢挑重担，冲锋在前，无私奉献。</p>
            </a>
        </div>
    </div>
    <div class="mui-slider-indicator mui-text-center">
        <div class="mui-indicator mui-active"></div>
        <div class="mui-indicator"></div>
        <div class="mui-indicator"></div>
        <div class="mui-indicator"></div>
    </div>
</div>
<div id="scroll" class="mui-scroll-wrapper" style="top:200px;">

    <div class="mui-scroll">
        <ul class="mui-table-view mui-grid-view mui-grid-9">
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
                <a href="${home}/wx/party/list.html?type=1" data-type="jingshen">
                    <img src="${home}/weixin/image/lyq/jingshen.png"/>

                    <div class="mui-media-body">中央精神</div>
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
                <a href="${home}/wx/party/list.html?type=2" data-type="jiaoyu">
                    <img src="${home}/weixin/image/lyq/jiaoyu.png"/>

                    <div class="mui-media-body">教育学习</div>
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
                <a href="${home}/wx/party/list.html?type=3" data-type="jianxun">
                    <img src="${home}/weixin/image/lyq/jianxun.png"/>

                    <div class="mui-media-body">党内规章</div>
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
                <a href="${home}/wx/party/list.html?type=4" data-type="dangjian">
                    <img src="${home}/weixin/image/lyq/dangjian.png"/>

                    <div class="mui-media-body">企业党建</div>
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
                <a href="${home}/wx/party/list.html?type=5" data-type="learn">
                    <img src="${home}/weixin/image/lyq/learn.png"/>

                    <div class="mui-media-body">文件制度</div>
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
                <a href="${home}/wx/party/list.html?type=6" data-type="lilun">
                    <img src="${home}/weixin/image/lyq/lilun.png"/>

                    <div class="mui-media-body">他山之石</div>
                </a>
            </li>
            <li class="mui-table-view-cell mui-media mui-col-xs-4 mui-col-sm-3">
                <a href="branch/chat/list.html" data-type="score">
                    <img src="${home}/weixin/image/lyq/branch.png"/>
                    <div class="mui-media-body">我的支部</div>
                </a>
            </li>
        </ul>
    </div>
    <%--</div>--%>
    <%--</div>--%>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init({
        swipeBack: true //启用右滑关闭功能
    });
    mui('.mui-scroll-wrapper').scroll({
//    indicators: true,
        deceleration: 0.0005
    });

    var slider = mui("#slider");
    slider.slider({
        interval: 5000         //每隔5秒调用一次
    });

    mui('.mui-table-view').on('tap', '.mui-table-view-cell a', function () {
        var href = this.getAttribute('href')
        if (href != 'javascript:;') {
            mui.openWindow(href);
        } else {
            var dataType = this.getAttribute('data-type')

            mui.alert('暂未开放哦', '系统提示', ['确定'], function () {
            })
        }
    })

</script>
</body>

</html>
