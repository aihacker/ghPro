<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/14
  Time: 15:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${m.name}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
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
        .mui-slider .mui-slider-group .mui-slider-item img.image{
            width:100%;
            height:100%;
        }

    </style>
</head>
<body>
<div class="mui-content">
    <div class="ui-fixed-bottom">
        <button id="yuyueBtn" type="button" class="mui-btn mui-btn-primary">立即预约</button>
    </div>

    <c:set value="${fn:length(m.fileList)}" var="fileLen"/>
    <div id="slider" class="mui-slider">
        <div class="mui-slider-group mui-slider-loop">
            <div class="mui-slider-item mui-slider-item-duplicate">
                <a href="javascript:;">
                    <img src="${home}${m.fileList[fileLen-1].thumb}">
                </a>
            </div>
            <c:forEach items="${m.fileList}" var="f">
                <div class="mui-slider-item div_image">
                    <a href="javascript:;" class="image_a">
                        <img data-preview-group="1" data-preview-src="${home}${f.path}" src="${home}${f.thumb}" class="image">
                    </a>
                </div>
            </c:forEach>
            <div class="mui-slider-item mui-slider-item-duplicate">
                <a href="javascript:;">
                    <img src="${home}${m.fileList[0].thumb}">
                </a>
            </div>
        </div>
        <div class="mui-slider-indicator">
            <div class="mui-indicator mui-active"></div>
            <div class="mui-indicator"></div>
        </div>
    </div>
    <div style="max-height: 80px;min-height: 50px;" class="ui-content mui-ellipsis-2">
        ${m.name}
    </div>

    <div class="ui-content ui-margin-top-10">
        <h5 class="ui-title">小屋介绍</h5>
        <p>
            ${m.info}
        </p>
    </div>

    <ul class="mui-table-view no ui-margin-top-10">
        <li class="mui-table-view-cell">
            <a data-url="time.html?id=${m.id}" class="ui-flex mui-navigate-right">
                <span class="fa fa-clock-o"></span>
                <span>&nbsp;开放时间</span>
                <small class="ui-right">${m.week}</small>
            </a>
        </li>

        <c:if test="${m.yuyueNum gt 0}">
            <li class="mui-table-view-cell">
                <a data-url="yuyue_list.html?id=${m.id}" class="ui-flex mui-navigate-right">
                    <span class="fa fa-eye"></span>
                    <span>&nbsp;查看预约</span>
                    <small class="ui-right">${m.yuyueNum}人预约</small>
                </a>
            </li>
        </c:if>
    </ul>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script>
    var momId = '${param.id}';
    $(function () {
        wxgh.previewImageInit();
        $('#yuyueBtn').on('tap', function () {
            mui.openWindow('yuyue.html?id=' + momId + '&time=${param.time}');
        });

        $('[data-url]').on('tap', function () {
            mui.openWindow($(this).data('url'));
        })
    })
</script>
</body>
</html>