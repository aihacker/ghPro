<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/11
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String msg =(String)request.getSession().getAttribute("loginMsg");
    request.getSession().removeAttribute("loginMsg");
%>
<html>
<head>
    <title>“微信企业”管理后台登录</title>
    <jsp:include page="/comm/admin/styles.jsp"/>
    <style>
        .ui-banner {
            width: 100%;
            height: 100%;
            position: absolute;
            overflow: hidden;
        }

        .ui-banner .carousel-inner > .item > img {
            width: 100%;
            height: 100%;
        }

        .ui-banner .ui-mask {
            position: absolute;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, .2);
            z-index: 2;
        }

        .login-main {
            position: absolute;
            background-color: #fff;
            z-index: 5;
            width: 300px;
            padding: 20px;
            right: 100px;
            top: 50%;
            margin-top: -150px;
            border-radius: 5px;
        }

        .ui-login-title {
            font-size: 20px;
            text-align: center;
            margin-bottom: 10px;
        }

        .ui-footer {
            position: fixed;
            width: 100%;
            left: 0;
            bottom: 0;
            background-color: #fff;
            text-align: center;
            padding: 10px 10px 5px 10px;
            z-index: 3;
        }
    </style>
</head>
<body>
<div class="ui-banner">
    <div class="ui-mask"></div>
    <div class="carousel" data-ride="carousel">
        <div class="carousel-inner" role="listbox">
            <div class="item active animated fadeIn">
                <img src="${home}/image/baner/banner_6.jpg">
                <div class="carousel-caption">
                </div>
            </div>
            <%--<div class="item animated fadeIn">--%>
            <%--<img src="${home}/image/baner/banner_2.jpg">--%>
            <%--<div class="carousel-caption">--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="item animated fadeIn">--%>
            <%--<img src="${home}/image/baner/banner_3.jpg">--%>
            <%--<div class="carousel-caption">--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="item animated fadeIn">--%>
            <%--<img src="${home}/image/baner/banner_4.jpg">--%>
            <%--<div class="carousel-caption">--%>
            <%--</div>--%>
            <%--</div>--%>
            <%--<div class="item animated fadeIn">--%>
            <%--<img src="${home}/image/baner/banner_5.jpg">--%>
            <%--<div class="carousel-caption">--%>
            <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>
</div>

<div class="login-main">
    <div class="ui-login-title">微信企业</div>
    <form id="loginForm" method="post" action="api/login.html">
        <input type="hidden" name="url">
        <div class="form-group">
            <label>用户名</label>
            <input type="tel" name="name" placeholder="管理员用户名" class="form-control" required>
        </div>
        <div class="form-group">
            <label>密码</label>
            <input type="password" name="password" placeholder="密码" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-success btn-block">立即登录</button>
    </form>
</div>
<div class="ui-footer">
    <p>© 2017
        <a href="javascript:;">fsdx.fsecity.com</a> All Rights Reserved</p>
</div>
<jsp:include page="/comm/admin/scripts.jsp"/>
<script src="${home}/libs/encrypt/base64.js"></script>
<script>
    var msg = "<%=msg%>";
    if(msg != null && msg != '' && msg != 'null'){
        ui.alert( msg ,function(){
        });
        console.log("msg="+msg)
    }
    $(function () {
        var url = window.location.href;
        if (url.indexOf('url=') > 0) {
            url = url.substring(url.indexOf('url=') + 4, url.length);
            url = new Base64().encode(url);
        } else {
            url = '';
        }
        $('input[name=url]').val(url);


    })
</script>
</body>
</html>