<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/9
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    //获取登录时账号密码的状态，即口令是否正常
    String flag = (String)request.getSession().getAttribute("failMsg");
    System.out.println("flag="+flag);
    Integer changePwdDate = (Integer) request.getSession().getAttribute("changePwdDate");
    System.out.println("changePwdDate="+changePwdDate);
%>

<html>
<head>
    <title>微信企业后台管理系统</title>
    <jsp:include page="/comm/admin/styles.jsp"/>
    <style>

        .ui-admin-main .panel-group,
        .ui-left .panel,
        .ui-left .list-group-item {
            background-color: #2f4050;
            color: #a7b1c2;
        }

        .ui-left .panel-title {
            font-size: 14px;
        }

        .ui-left .list-group-item a {
            color: #a7b1c2;
            display: block;
            padding: 5px 5px 5px 30px;
        }

        .ui-left .list-group-item a:hover,
        .ui-left .list-group-item a:focus,
        .ui-left .panel-heading h4 a:hover,
        .ui-left .panel-heading h4 a:focus {
            text-decoration: none;
            color: #fff;
        }

        .ui-left .panel-group .panel + .panel {
            margin-top: 0;
        }

        .panel-group .panel {
            border-radius: 0;
        }

        .panel-group .panel-heading + .panel-collapse > .list-group,
        .ui-left .list-group-item {
            border: none;
        }

        .ui-left .panel-heading h4 a {
            display: block;
            padding: 10px 15px 5px 15px;
        }

        .ui-admin-main .panel-group,
        .ui-left .list-group-item,
        .ui-left .panel-heading {
            margin: 0;
            padding: 0;
        }

        .panel-collapse .list-group-item span.fa-caret-left {
            position: relative;
            right: -6px;
            font-size: 18px;
            color: #fff;
            display: none;
        }

        .ui-left.panel-group {
            width: 220px;
            padding-top: 62px;
            overflow-y: auto;
        }

        .ui-left {
            z-index: 2;
            position: absolute;
            height: 100%;
            top: 0;
        }

        .ui-right {
            position: absolute;
            width: 100%;
            height: 100%;
            left: 0;
            padding-left: 220px;
            top: 0;
            padding-top: 65px;
            background-color: #F5F5F5;
        }

        .ui-admin-main .navbar {
            margin-bottom: 0px;
            z-index: 999;
            -webkit-border-radius: 0px;
            -moz-border-radius: 0px;
            border-radius: 0px;
        }

        .ui-admin-main .navbar-brand a.btn {
            position: relative;
            top: -8px;
        }

        .ui-box {
            margin-bottom: 20px;
        }

        .ui-box-content p.ui-box-tip {
            margin: 0;
        }

        .ui-box-tip small {
            font-size: 11px;
        }

        .ui-box-content p.ui-box-tip span {
            font-size: 1.1em;
            position: relative;
            top: -2px;
        }

        .ui-box-content p.ui-box-tip span.fa {
            font-size: 1em;
        }

        .ui-box-content span {
            font-weight: 600;
            font-size: 1.6em;
        }

        .ui-iframe {
            border: none;
            overflow: hidden;
            width: 100%;
        }

        tbody > tr > td {
            text-align: center;
            font-size: 14px;
        }

        td > p {
            margin: 0;
        }

        td.ui-table-operate {
            min-width: 260px;
        }

        #uiMain {
            overflow: auto;
            position: relative;
        }

        .pagination {
            float: right;
            margin-right: 50px;
        }

        .ui-check-div {
            display: inline-block;
        }

        .ui-check-div label {
            font-size: 13px;
            font-weight: 500;
            margin: 0 6px;
        }

        .form-horizontal .control-label {
            font-size: 13px;
            font-weight: 500;
        }

        .ui-btn {
            padding: 4px 18px;
        }

        form .ui-btn {
            margin-left: 40px;
        }

        .row .center-form {
            float: none;
            margin-left: auto;
            margin-right: auto;
            margin-top: 30px;
        }

        .center-form input,
        .center-form select {
            height: 30px;
            padding: 0 10px;
            font-size: 13px;
        }

        form .ui-times .form-control {
            width: 40%;
            display: inline-block;
        }

        #uiMain .modal-backdrop {
            position: absolute;
        }

        #mainAlert .alert {
            margin: 10px 0;
        }
    </style>
</head>
<body>
<!--[if lte IE 9 ]>
<nav id="mainAlert" class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
        <div class="alert alert-danger alert-dismissible text-center" role="alert">
            <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                    aria-hidden="true">&times;</span></button>
            <strong>系统提醒：</strong> 你的浏览器太古董了，为了保证你能稳定使用系统
            <a class="alert-link" href="https://browsehappy.com/" target="_blank">速速点击换一个</a>！
        </div>
    </div>
</nav>
<![endif]-->

<div class="ui-admin-main">
    <nav id="navbar" class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#adminCollapse" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">微信企业后台系统</a>
            </div>
            <div class="collapse navbar-collapse" id="adminCollapse">
                <ul class="nav navbar-nav" id="navbarList">
                    <c:forEach items="${cates}" var="c" varStatus="i">
                        <li data-id="${c.id}" class="${i.first?'active':''}"><a href="javascript:;">${c.name}</a></li>
                    </c:forEach>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li class="dropdown">
                        <a href="javascript:;" class="dropdown-toggle" data-toggle="dropdown" role="button"><span
                                class="fa fa-user-circle-o"></span> ${wxgh_admin.name} <span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#editPassModal" data-toggle="modal">修改密码</a></li>
                            <li><a href="logout.html">退出登录</a></li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <div class="panel-group ui-left" id="mainPanel">
    </div>
    <div class="ui-right">
        <div class="container-fluid" id="uiMain">
        </div>
    </div>
</div>
<div class="modal fade" id="editPassModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">管理员密码修改</h4>
            </div>
            <div class="modal-body">
                <form>
                    <div class="form-group">
                        <label>原密码</label>
                        <input class="form-control" type="password" name="oldpassword">
                    </div>
                    <div class="form-group">
                        <label>新密码</label>
                        <input class="form-control" type="password" name="password">
                    </div>
                    <div class="form-group">
                        <label>再次密码</label>
                        <input class="form-control" type="password" name="newpassword">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-theme" id="editPassBtn">确认修改</button>
            </div>
        </div>
    </div>
</div>
<script type="text/template" id="panelItem">
    <div class="panel">
        <div class="panel-heading">
            <h4 class="panel-title">
                <a data-toggle="collapse" data-parent="#mainPanel" href="#collapse_{{=it.index}}">
                    <span style="width: 14px;text-align: center;" class="fa fa-{{=it.icon}}"></span>
                    <span>&nbsp;&nbsp;{{=it.name}}</span>
                    <span class="fa fa-angle-right fa-pull-right"></span>
                </a>
            </h4>
        </div>
        <div data-info="{{=it.info}}" id="collapse_{{=it.index}}" class="panel-collapse collapse">
            <ul class="list-group">
                {{for(var i in it.navs){}}
                <li class="list-group-item">
                    <a data-url="{{=it.navs[i].url}}" href="javascript:;">{{=it.navs[i].name}}</a>
                </li>
                {{}}}
            </ul>
        </div>
    </div>
</script>
<jsp:include page="/comm/admin/scripts.jsp"/>
<script>
    ui.history = {
        go: function (url, data, func) {
            if (!url)return;
            var loading = ui.loading('wave', {backdrop: true, selector: '#uiMain'})
            loading.show()
            var tourl = url
            if (url.indexOf('.html') < 0) {
                tourl += ".html"
            }
            $('#uiMain').load(tourl, data, function () {
                loading.hide()
                var hash = url
                if (data) {
                    hash += '?'
                    var i = 0;
                    for (var k in data) {
                        if (i > 0) hash += '&'
                        hash += (k + '=' + data[k])
                        i++
                    }
                }
                var mhash = window.location.hash;
                if (mhash) mhash = mhash.substring(1, mhash.length);
                if (mhash.indexOf('#') < 0) {
                    window.location.hash += '#' + hash
                } else {
                    window.location.hash = mhash.split('#')[0] + '#' + hash;
                }
                if (func) func()
            })
        },
        map: function (func) {
            $.getScript('http://api.map.baidu.com/getscript?v=2.0&ak=uOy28xEGgK5vrqIG4gGTw7PgAmu667Ua&services=&t=20170823191629', function () {
                if (func) func();
            })
        }
    }
</script>
<script>

    var flag = "<%=flag%>";
    var changePwdDate = "<%=changePwdDate%>";
    var overday = 90-changePwdDate;

    if(flag == 'false'){
        ui.alert("你的密码不符合要求，请先修改密码",function(){
        })
    }
    if(changePwdDate > 80 && changePwdDate < 90){
        ui.alert("你的密码还有" + overday + "天就过期了，请及时修改",function(){
        })
    }
    if(changePwdDate > 90){
        ui.alert("你的密码已过期，请先修改密码",function(){
        })
    }

    $(function () {
        var $navMain = $('#mainPanel'),
            $navTpl = $('#panelItem'),
            $editpassForm = $('#editPassModal form');
        var navTpl = doT.template($navTpl.html());
        $navTpl.remove()

        var $defaultNav = $('#navbarList li.active');

        var url = window.location.hash;
        if (url) {
            url = url.substring(1, url.length);
            url = url.split('#');
            var tourl, navId;
            if (url.length > 1) {
                tourl = url[1];
                navId = url[0];
            } else {
                if (!isNaN(url[0])) {
                    tourl = '';
                    navId = url[0];
                } else {
                    tourl = url[0];
                    navId = $defaultNav.data('id');
                }
            }
            list_navs($('#navbarList li[data-id="' + navId + '"]'), function () {
                var $url = $('.panel-collapse .list-group-item>a[data-url="' + tourl + '"]');
                $url.parent().parent().parent().addClass('in');
            })
            ui.history.go(tourl);
        } else {
            list_navs($defaultNav)
        }

        init()

        function init() {
            $('#navbarList').on('click', 'li', function () {
                var $self = $(this)
                if ($self.hasClass('active')) return;
                list_navs($self);
            });

            $('#editPassBtn').click(function () {
                var info = $editpassForm.serializeJson();
                if (!info.oldpassword || !info.password || !info.newpassword) {
                    ui.alert('请补全密码信息！');
                    return;
                }
                if (info.password != info.newpassword) {
                    ui.alert('两次密码不相同！');
                    return;
                }

                $.ajax({
                    type:'post',
                    url:'api/edit_password.json',
                    data:info,
                    dataType:'text',
                    success:function(data){
                        console.log("data="+data);
                        if(data == "修改成功"){
                            ui.alert(data,function(){
                                $editpassForm.clearForm()
                                list_navs($('#navbarList li.active'))
                                /*$('#navbarList li[data-id="1"]')*/
                            });

                        }else{
                            ui.alert(data,function(){

                            });
                        }
                    }
                });

            });

            resize()
            $(window).on('resize', resize());
        }

        function list_navs(self, func) {
            var menus = self.data('menus');
            if (!menus) {
                ui.get('api/nav_list.json', {cateId: self.data('id')}, function (navs) {
                    init_navs(navs);
                    self.data('menus', navs);

                    $('#navbarList li.active').removeClass('active');
                    self.addClass('active');
                    window.location.hash = self.data('id');
                    if (func) func();
                })
            } else {
                init_navs(menus);
                $('#navbarList li.active').removeClass('active');
                self.addClass('active');
                if (func) func();
            }
        }

        function init_navs(navs) {
            $navMain.empty()
            for (var i in navs) {
                var d = navs[i]
                d['index'] = i
                var $item = $(navTpl(d))
                initEvent($item)
                $navMain.append($item)
            }
        }

        function initEvent($item) {
            $item.find('.panel-collapse.collapse').on('show.bs.collapse', function () {
                $(this).prev().find('.fa.fa-pull-right')
                    .removeClass('fa-angle-right')
                    .addClass('fa-angle-down')
            })

            $item.find('.panel-collapse.collapse').on('hide.bs.collapse', function () {
                $(this).prev().find('.fa.fa-pull-right')
                    .removeClass('fa-angle-down')
                    .addClass('fa-angle-right')
            })

            $item.find('.list-group').on('click', 'a[data-url]', function () {
                htmlUrl = $(this).data('url')
                ui.history.go(htmlUrl)
            })
        }

        function resize() {
            var h = $('.ui-left').outerHeight() - $('#navbar').outerHeight()
            $('#uiMain').css('height', h - 25)
        }
    })
</script>
</body >
< / html >