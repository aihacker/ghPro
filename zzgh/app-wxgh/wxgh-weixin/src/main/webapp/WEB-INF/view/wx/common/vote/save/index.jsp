<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 2016/7/8
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport"
          content="user-scalable=no, initial-scale=1, maximum-scale=1, minimum-scale=1, width=device-width">
    <title>发布投票</title>

    <jsp:include page="/comm/mobile/styles.jsp"/>

    <link type="text/css" href="${home}/style/common/headerStyle.css" rel="stylesheet">
    <link type="text/css" href="${home}/weixin/style/common/vote/save.css" rel="stylesheet">
    <link rel="stylesheet" href="${home}/weixin/style/lib/common.css">

    <script type="text/javascript" src="${home}/weixin/script/lib/common.js"></script>
    <script type="text/javascript" src="${home}/weixin/script/lib/pub.js"></script>
    <script type="text/javascript" src="${home}/weixin/script/common/vote/save.js"></script>


    <script type="text/javascript">
        var homeHttpPath = '${home}';
    </script>
</head>
<body>
<input type="hidden" value="${wxgh_user.userid}" id="loginUserId"/>
<input type="hidden" value="${wxgh_user.name}" id="loginUserName"/>

<div id="page">
    <%--<header>--%>
        <%--<div class="header_tool_bar">--%>
            <%--<a id="backBtn" href="javascript:history.go(-1);">返回</a>--%>

            <%--<div id="htb_title">发布投票</div>--%>
            <%--<a id="adminBtn" href="javascript:void(0);">发布</a>--%>
        <%--</div>--%>
    <%--</header>--%>
    <div class="ui-fixed-bottom">
        <a id="adminBtn" href="javascript:void(0);" class="mui-btn mui-btn-primary">发布</a>
    </div>
    <div id="vote">
        <div id="voteinfo">
            <textarea class="textarea_content" placeholder="输入投票主题，2-80字"></textarea>
        </div>

        <ul>
            <li class="votechoice">
                <span>选项</span><input type="text" name="choice" placeholder="1"/>
            </li>
            <li class="votechoice">
                <span>选项</span><input type="text" name="choice" placeholder="2"/>
            </li>
            <li class="addchoice">
                <a id="addchoice" href="javascript:void(0);" title="增加一个选项"></a>
            </li>
        </ul>
    </div>

</div>
</body>
</html>
