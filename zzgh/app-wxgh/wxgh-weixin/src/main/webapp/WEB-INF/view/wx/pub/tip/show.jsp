<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/12
  Time: 9:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <title>系统提示</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
</head>

<body style="background-color: #fff;">

<div class="weui_msg">
    <div class="weui_icon_area">
        <c:if test="${tip.type eq 1}">
            <i class="weui_icon_success weui_icon_msg"></i>
        </c:if>
        <c:if test="${tip.type eq 2}">
            <i class="weui_icon_cancel weui_icon_msg"></i>
        </c:if>
        <c:if test="${tip.type eq 3}">
            <i class="weui_icon_msg weui_icon_info"></i>
        </c:if>
    </div>
    <div class="weui_text_area">
        <h2 class="weui_msg_title">${tip.title}</h2>

        <p class="weui_msg_desc">${tip.msg}</p>
    </div>
    <div class="weui_opr_area">
        <p class="weui_btn_area">
            <c:if test="${tip.type eq 1}">
                <a href="javascript:;" class="weui_btn weui_btn_primary">关闭页面</a>
            </c:if>
            <c:if test="${tip.type eq 2}">
                <a href="javascript:;" class="weui_btn weui_btn_warn">关闭页面</a>
            </c:if>
            <c:if test="${tip.type eq 3}">
                <a href="javascript:;" class="weui_btn weui_btn_primary">关闭页面</a>
            </c:if>
        </p>
    </div>
    <c:if test="${!empty tip.url}">
        <div class="weui_extra_area">
            <a href="${tip.url}">${empty tip.urlMsg?'查看详情':tip.urlMsg}</a>
        </div>
    </c:if>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/libs/weixin/weixin.min.js"></script>
<script type="text/javascript">
    $(function () {
        wxgh.wxInit('${weixin}')

        $('.weui_btn_area a').on('tap', function () {
            wx.closeWindow()
        })
    })
</script>
</body>

</html>
