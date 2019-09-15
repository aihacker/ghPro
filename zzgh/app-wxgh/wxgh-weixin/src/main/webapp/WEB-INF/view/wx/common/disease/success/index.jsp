<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/9/2
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>申请提交成功</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-bar .mui-icon {
            font-size: 35px;
            line-height: 20px;
        }
    </style>
</head>

<body>
<%--<header class="mui-bar mui-bar-nav ui-head">--%>
    <%--<a id="closeWin" class="mui-icon mui-icon-closeempty mui-pull-left"></a>--%>

    <%--<h1 class="mui-title">申请已提交</h1>--%>
<%--</header>--%>

<div class="mui-content">
    <div class="mui-content-padded">
        你的申请已经提交，静候管理员的审核吧，审核结果我们将第一时间通知你...
        <a href="${home}/wx/pub/apply/index.html">查看审核进度</a>
    </div>
</div>


<jsp:include page="/comm/mobile/scripts.jsp"/>

<script type="text/javascript">
    mui.init()

    wxgh.wxInit('${weixin}')

    var closeBtn = wxgh.getElement("closeWin");
    closeBtn.addEventListener("tap", function () {
        wx.closeWindow(); //关闭当前网页
    });
</script>
</body>

</html>
