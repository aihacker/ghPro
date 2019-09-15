<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/11
  Time: 20:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${chat.name}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
    <link rel="stylesheet" href="${wx}/style/chat/chat.css">
</head>
<body>

<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <div class="ui-msg-list">
        </div>
    </div>
</div>

<nav id="chatBottom" class="mui-bar mui-bar-tab">
    <div class="mui-row">
        <div class="ui-flex">
            <%--<div class="mui-col-sm-2 mui-col-xs-2 mui-text-center">--%>
                <%--<span id="voiceBtn" class="mui-icon mui-icon-mic-filled ui-text-primary"></span>--%>
            <%--</div>--%>
            <div class="ui-flex-1" style="padding-left: 15px">
                <div id="voiceDiv" ont class="animated fadeIn mui-hidden">按住说话</div>
                <%--<button id="voiceDiv" type="button" class="mui-btn mui-btn-block animated fadeIn">按住说话</button>--%>
                <textarea wrap="soft" rows="1" id="chatInput" autofocus="autofocus" class="animated fadeIn"></textarea>
            </div>
            <div class="mui-text-center" style="width: 60px;">
                <button id="sendBtn" type="button" class="mui-btn mui-hidden">发送</button>
                <span id="plusBtn" class="mui-icon mui-icon-plus"></span>
            </div>
        </div>
    </div>
    <div id="otherDiv" class="mui-row mui-hidden animated fadeInLeft">
        <div class="mui-slider">
            <div class="mui-slider-group">
                <div class="mui-slider-item">
                    <ul class="mui-table-view mui-grid-view mui-grid-9">
                        <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                            <a href="#" data-type="image">
                                <img src="${wx}/image/chat/msg/chat_image.png"/>

                                <div class="mui-media-body">图片</div>
                            </a>
                        </li>
                        <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                            <a href="#" data-type="location">
                                <img src="${wx}/image/chat/msg/chat_location.png"/>

                                <div class="mui-media-body">位置</div>
                            </a>
                        </li>
                        <c:forEach items="${chat.models}" var="m">
                            <li class="mui-table-view-cell mui-media mui-col-xs-3 mui-col-sm-3">
                                <a data-href="${m.url}" href="javascript:;">
                                    <img src="${home}${m.path}"/>

                                    <div class="mui-media-body">${m.name}</div>
                                </a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</nav>

<div style="display: flex;align-items: center;width: 100%;height: 100%;height: 400px">
    <div id="locationPopover" class="mui-popover" style="height: 300px;">
        <div class="mui-scroll-wrapper">
            <div class="mui-scroll">
                <ul class="mui-table-view mui-table-view-radio">
                </ul>
            </div>
        </div>
        <div class="ui-popover-btn">
            <button id="sendLocationBtn" type="button" class="weui_btn weui_btn_plain_primary">确定</button>
        </div>
    </div>
</div>

<div id="recordDialog" class="weui_toast animated fadeInDown mui-hidden">
    <div style="display: flex;justify-content: center;align-items: center;-webkit-box-align: center;margin-top: 10px;flex-flow: column;">
        <img id="recordImage" style="width: 60px;" src="${wx}/image/chat/record/record_animate_01.png"/>

        <p style="margin-top: 5px;" class="weui_toast_content">上滑取消发送</p>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script src="${home}/libs/weixin/weixin.min.js"></script>
<script>
    var id = '${chat.id}';
    var groupId = '${chat.groupId}';
    var type = '${chat.type}';
    var userId = '${wxgh_user.userid}';
    var userAvatar = '${wxgh_user.avatar}';
    var userName = '${wxgh_user.name}';
    wxgh.init('${weixin}')

    $(function () {
        $('a[data-href]').on('tap', function () {
            var href = $(this).data('href')
            if (href.indexOf('?') > 0) {
                href += '&'
            } else {
                href += '?'
            }
            href += 'id=' + id
            mui.openWindow(homePath + href)
        })
    })
</script>
<script src="${wx}/script/chat/chat.js"></script>
</body>
</html>
