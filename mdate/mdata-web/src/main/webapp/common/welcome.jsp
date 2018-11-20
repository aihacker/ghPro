<%@ page import="com.gpdi.mdata.web.app.data.SessionData" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>欢迎页面-X-admin2.0</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <%--<meta name="viewport" content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi" />--%>
    <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="${home}/css/title/font.css">
    <link rel="stylesheet" href="${home}/css/title/xadmin.css">
    <style type="text/css">
        body{
            background: -webkit-linear-gradient(#def2ff, #ffffff);
            background: -o-linear-gradient(#def2ff, #ffffff);
            background: -moz-linear-gradient(#def2ff, #ffffff);
            background: linear-gradient(#def2ff, #ffffff)
        }
    </style>
</head>
<body>
<div class="x-body layui-anim layui-anim-up">
    <div id="top" style="height: 130px;position: relative;padding-left: 20px; margin-top: -45px;">
        <img src="${home}/image/icon1.png" style="position: absolute;width:30%;bottom: 36px;" />
    </div>
    <div id="" class="mid">
        <img src="${home}/image/banner.png" style="width: 100%; height: 300px;" />
    </div>
    <div id="" class="bottom" style="position: relative;">
        <img src="${home}/image/icon2.png" style="position: absolute;width:10%;right: 160px;top: 40px;" />
    </div>
</div>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        //hm.src = "https://hm.baidu.com/hm.js?b393d153aeb26b46e9431fabaf0f6190";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
</body>
</html>