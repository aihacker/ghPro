<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>登陆页面</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
    <!-- load css -->
    <link rel="stylesheet" type="text/css" href="${home}/script/layui/css/layui.css" media="all">
    <link rel="stylesheet" type="text/css" href="${home}/css/login.css" media="all">

    <script type="text/javascript">
        function check(form) {
        if(form.account.value=='') {
        alert("请输入用户帐号!");
        form.account.focus();
        return false;
    }
        if(form.password.value==''){
        alert("请输入登录密码!");
        form.password.focus();
        return false;
    }
        return true;
    }
</script>
    <script>
        window.onload = function()
        {
            document.getElementById("account").focus();
        }

    </script>
</head>

<body>
<div id="" class="login-top">
    <img src="${home}/image/login.png" class="logo-img">
</div>
<div id="" class="login-content">
    <div id="" class="content-left">
        <img src="${home}/image/login-logo.png" alt="电信logo" class="content-logo">
    </div>
    <div id="" class="content-center"></div>
    <div id="" class="content-right">
        <form class="layui-form" action="" method="post">
            <input type="hidden" name="action" value="login"/>
            <div class="layui-form-item">
                <div class="layui-input-block admin">
							<span id="" class="icon-1">
								<img src="${home}/image/people.png" />
							</span>
                    <input type="text" name="account" id="account" required lay-verify="required" placeholder="请输入您的用户名" autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-input-block admin">
							<span id="" class="icon-2">
								<img src="${home}/image/pass.png" />
							</span>
                    <input type="password" name="password" required lay-verify="required" placeholder="请输入密码" autocomplete="off" class="layui-input">
                </div>
            </div>

            <div class="layui-form-item">
                <div class="layui-input-block">
                   <button class="login-btn layui-btn submit" id="submit" lay-submit lay-filter="formDemo" onclick="return check(this.form)">登录</button>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript" src="${home}/script/layui/layui.js"></script>
<script>
    function demo() {
        window.location.href="login.html";
    }


    /*ayui.use('form', function() {

        var form = layui.form;

        //监听提交
        form.on('submit(formDemo)', function(data) {
            layer.msg(JSON.stringify(data.field));
            return false;
        });
        $(function() {
            $('.btn-reset').click(function() {
                $('form')[0].reset();
                return false;
            });
        });

    });*/
</script>
</body>

</html>


