<%@ page import="java.net.URLDecoder" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/9
  Time: 11:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>工作坊</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-table-view-cell span:last-child {
            display: inline-block;
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

<div class="mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view" style="margin-top: 0px;">
            <li class="mui-table-view-cell mui-text-center">
                基本信息
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">类型</label>
                <span class="mui-right mui-pull-right">
                    <c:choose>
                        <c:when test="${data.shopType == 1}">创新工作坊</c:when>
                        <c:otherwise>劳模工作坊</c:otherwise>
                    </c:choose>
                </span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">团队名称</label>
                <span class="mui-right mui-pull-right">${data.teamName}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">项目名称</label>
                <span class="mui-right mui-pull-right">${data.itemName}</span>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">项目场地</label>
                <span class="mui-right mui-pull-right">${data.address}</span>
            </li>
            <li onclick="window.location.href='${home}/wx/union/innovation/result/member.html?id=${data.id}&workType=2'"
                class="mui-table-view-cell">

                <label class="ui-li-label ui-text-info">成员</label>
                <span class="mui-right mui-pull-right">
                <c:forEach items="${users}" var="item" varStatus="status">
                    <c:if test="${status.index < 2}">
                        <img src="${item.userImg}" style="height: 25px;width: 25px;border-radius: 50%;">
                    </c:if>
                </c:forEach>
                <c:if test="${fn:length(users) > 1}">
                    ...<span class="mui-icon mui-icon-forward"></span>
                </c:if>
                    </span>
            </li>
        </ul>

        <ul class="mui-table-view ui-margin-top-10">
            <%--<li class="mui-table-view-cell">--%>
                <%--成果描述--%>
            <%--</li>--%>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info"> 成果描述</label>
                <div id="resultInfo">${txt}</div>
            </li>
            <li class="mui-table-view-cell">
                <label class="ui-li-label ui-text-info">附件</label>
                <c:choose>
                    <c:when test="${imgList[0] == null || imgList[0] == ''}">
                        <span class="mui-pull-right">暂无</span>
                    </c:when>
                    <c:otherwise>
                        <br>

                        <ul class="mui-table-view mui-grid-view ul-img" style="background: whitesmoke;">
                            <c:forEach items="${imgList}" var="img">
                                <li class="mui-table-view-cell mui-media mui-col-xs-6 li-img"
                                    style="margin-bottom: 10px;">
                                    <a href="#" style="height:200px;padding:0px 10px;" class="show-a">
                                        <img class="mui-media-object show-img"
                                             style="height: 100% ;width: 100%;"
                                             src="${img}"
                                             data-preview-src="" data-preview-group="0">
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:otherwise>
                </c:choose>
            </li>
        </ul>

        <c:choose>
            <c:when test="${showType == 1}">

            </c:when>
            <c:when test="${showType == 2}">
                <ul class="mui-table-view ui-margin-top-15">
                    <li class="mui-table-view-cell mui-text-center">
                        <label class="ui-li-label ui-text-info">审核状态</label>
                    </li>
                    <li class="mui-table-view-cell mui-text-center">

                <span>
                    <c:choose>
                        <c:when test="${data.applyStatus == 0}">审核中</c:when>
                        <c:when test="${data.applyStatus == 1}">通过</c:when>
                        <c:when test="${data.applyStatus == 2}">不通过</c:when>
                        <c:otherwise>未知状态</c:otherwise>
                    </c:choose>
                </span>
                    </li>
                    <c:if test="${data.auditIdea != null}">
                        <li class="mui-table-view-cell mui-text-center">
                            <label class="ui-li-label ui-text-info">审核意见</label><br>
                            <div>${data.auditIdea}</div>
                        </li>
                    </c:if>
                    <c:if test="${data.auditIdea != null}">
                        <li class="mui-table-view-cell">
                            <label class="ui-li-label ui-text-info">审核时间</label>
                            <span>
                        <fmt:formatDate value="${data.auditTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
                    </span>
                        </li>
                    </c:if>
                </ul>
            </c:when>
            <c:otherwise></c:otherwise>
        </c:choose>
    </div>
</div>


<jsp:include page="/comm/mobile/scripts.jsp"/>
<link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css">
<script src="${home}/libs/mui-v3.7.0/js/mui.previewimage.js"></script>
<script type="text/javascript">
    mui.init()
    wxgh.previewImageInit();
    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
    });

    $(document).ready(function () {

        var $info = $('#resultInfo');
        var infoTxt = $info.text();
        if (infoTxt) {
            $info.text(decodeURIComponent(infoTxt))
        }

        adjustImg();

        function adjustImg() {
            $(".show-a").height($(".show-a").width());
            var $imgs = $(".show-img");
            if ($imgs.length > 1) {
                /* for (var i = 0; i < $imgs.length; i++) {
                 var $img = $($imgs[i]);
                 var width = $img.width();
                 var height = $img.height();
                 if (height > width) {
                 $($img).css({"height": "initial", "width": "100%"/!*, "margin-top": -height / 2*!/});
                 }else {
                 $($img).css({"height": "100%", "width": "auto"/!*, "margin-top": -height / 2*!/});
                 }
                 }*/
            } else {
                //$($imgs).parents(".li-img").removeClass("mui-col-xs-6").addClass("mui-col-xs-12");
                $("ul.ul-img").css("text-align", "center");
                $("ul.ul-img li").css("margin-bottom", "0px");
            }

        }
    });
</script>
<script>

</script>
</body>

</html>
