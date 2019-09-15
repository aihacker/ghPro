<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/14
  Time: 18:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>女工园地</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        #slider {
            height: 220px;
        }

        #slider .mui-slider-indicator {
            text-align: right;
            padding-right: 10px;
            bottom: 2px;
        }

        #slider .mui-slider-indicator .mui-indicator {
            margin: 1px 4px;
        }

        .menu {
            height: 80px;
            background-color: white;
            line-height: 80px;
            text-align: center;
            font-size: 17px;
            width: 100%;
        }

        .menu img {
            width: 50px;
            margin-top: 15px;
            display: inline-block;
        }

        .menu .menu-name {
            line-height: 80px;
            display: inline-block;
            position: relative;
            top: -15px;
            left: 5px;
            color: #000;
        }
        .list.mui-table-view .mui-media-object {
            width: 100px;
            max-width: 100px;
            height: 80px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div id="slider" class="mui-slider">
        <div class="mui-slider-group mui-slider-loop">
            <div class="mui-slider-item mui-slider-item-duplicate">
                <a href="javascript:;">
                    <img src="${home}${imgs[fileLen-1].thumb}" style="height:100%">
                </a>
            </div>
            <c:set value="${fn:length(imgs)}" var="fileLen"/>
            <c:forEach items="${imgs}" var="f">
                <div class="mui-slider-item">
                    <a href="javascript:;">
                        <img data-preview-group="1" data-preview-src="${home}${f.path}" src="${home}${f.path}" style="height:100%">
                    </a>
                </div>
            </c:forEach>
            <div class="mui-slider-item mui-slider-item-duplicate">
                <a href="javascript:;">
                    <img src="${home}${imgs[0].path}" style="height:100%">
                </a>
            </div>
        </div>
        <div class="mui-slider-indicator">
            <div class="mui-indicator mui-active"></div>
            <div class="mui-indicator"></div>
        </div>
    </div>

    <div class="mui-row menu">
        <div class="mui-col-xs-6 mui-col-sm-6">
            <a href="teach/list.html">
                <img src="${home}/weixin/image/common/female/nvgongketang.png">
                <span class="menu-name">女子课堂</span>
            </a>
        </div>

        <div class="mui-col-xs-6 mui-col-sm-6">
            <a href="mom/list.html">
                <img src="${home}/weixin/image/common/female/mamaxiaowu.png">
                <span class="menu-name">妈妈小屋</span>
            </a>
        </div>
    </div>

    <div style="background-color: white;height: 40px;margin-top: 10px;padding-top: 20px;">
        <hr style="border-top: 1px solid red;margin:0px;">
        <p style="margin-bottom: 0px;width: 100px;margin: 0 auto;text-align: center;font-size: 17px;color: black;background-color: white;margin-top: -14px;z-index: 10;">
            最新课堂</p>
    </div>

    <div id="refreshContainer" class="mui-content mui-scroll-wrapper" style="top: 352px;">
        <div class="mui-scroll">
            <ul class="mui-table-view list">
            </ul>
        </div>
    </div>
</div>

<script type="text/html" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <a href="javascript:;">
            <img class="mui-media-object mui-pull-left" src="{{=it.path}}">
            <div class="mui-media-body">
                {{=it.name}}
                <p class="mui-ellipsis-2">{{=it.content}}</p>
            </div>
        </a>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: 'teach/api/list.json',
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            emptyText: '暂无课堂哦',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                mui.toast('加载失败...');
            }
        });

        function bindfn(d) {
            d['path'] = wxgh.get_thumb(d);
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow('teach/show.html?id=' + d.id);
            })
            return $item[0];
        }
    })
</script>
</body>
</html>