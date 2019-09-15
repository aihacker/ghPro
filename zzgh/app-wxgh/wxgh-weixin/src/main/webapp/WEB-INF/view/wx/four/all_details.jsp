<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="date" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8"/>
    <title>${detailsList.fpName}</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css"/>
    <style>
        body {
            font-family: "微软雅黑";
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

        body {
            background-color: #fff;
        }

        .mui-content {
            background-color: #fff;
        }

        .item-div span:first-child {
        }

        .item-div span:last-child {
            color: #8f8f94;
            font-size: 14px;
        }

        .mui-table-view-cell {
            padding-left: 20px;
            padding-right: 20px;
        }

        .dept-name {
            padding: 20px 0px;
        }

        .dept-name img {
            width: 25px;
            height: 25px;
            margin-right: 10px;
        }

        .beizhu-li {
            padding: 12px 20px;
            color: #000;
            position: relative;
        }

        .beizhu-li a {
            color: #000;
        }

        .beizhu-li .item-div span:first-child {
            margin-top: 40px;
        }

        .beizhu-li .item-div span:last-child {
            left: 100px;
            position: absolute;
            top: 12px;
            display: block;
            padding-right: 20px;
        }

        .ui-img-div {
            display: -webkit-flex;
            display: flex;
            justify-content: center;
            overflow: hidden;
            align-items: center;
            max-height: 200px;
        }
    </style>
</head>
<body>

<header class="mui-bar mui-bar-nav xlk-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">${detailsList.fpName}</h1>
</header>

<div class="mui-content" style="margin-top: -15px">
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a>
                <label class="ui-li-label ui-text-info">容纳人数</label>
                <span class="mui-pull-right">
                    <c:choose>
                        <c:when test="${empty detailsList.contain}">
                            暂无
                        </c:when>
                        <c:otherwise>
                            ${detailsList.contain}
                        </c:otherwise>
                    </c:choose>
                </span>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a>
                <label class="ui-li-label ui-text-info">面积规格</label>
                <span class="mui-pull-right">
                    <c:choose>
                        <c:when test="${empty detailsList.scale}">
                            暂无
                        </c:when>
                        <c:otherwise>
                            ${detailsList.scale} m²
                        </c:otherwise>
                    </c:choose>
                </span>
            </a>
        </li>
        <li class="mui-table-view-cell">
            <a style="overflow: scroll;">
                <label class="ui-li-label ui-text-info">详细信息： </label>
                <span style="white-space: initial;">
                    <c:choose>
                        <c:when test="${empty detailsList.introduction}">
                            暂无
                        </c:when>
                        <c:otherwise>
                            ${fn:trim(detailsList.introduction)}
                        </c:otherwise>
                    </c:choose>
                </span>
            </a>
        </li>

        <li class="mui-table-view-cell">
            <a>
                <label class="ui-li-label ui-text-info">图片附件</label>
                <c:choose>
                    <c:when test="${empty detailsList.imgs}">
                        <span class="mui-pull-right">暂无</span>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${detailsList.imgs}" var="p">
                            <div class="ui-img-div">
                                <img data-preview-src="" data-preview-group="fujian" src="${p}"
                                     style="width: 100%;">
                            </div>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </a>
        </li>
    </ul>
    <div class="beizhu-li">
        <a>
            <span>备注说明</span>
            <span class="mui-pull-right">${empty detailsList.remark?'暂无备注信息':detailsList.remark}</span>
        </a>
    </div>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.zoom.js"></script>
<script type="text/javascript" src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script type="text/javascript">
    mui.init()

    wxgh.previewImageInit();
</script>
</body>
</html>
