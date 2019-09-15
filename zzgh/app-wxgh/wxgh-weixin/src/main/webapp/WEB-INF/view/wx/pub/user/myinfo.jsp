<%--
  Created by IntelliJ IDEA.
  User: hhl
  Date: 2017-07-27
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的资料</title>
    <meta name="charset" content="utf-8">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <jsp:include page="/comm/mobile/styles.jsp"></jsp:include>
    <style>
        .mui-bar a,
        .mui-bar .mui-title {
            color: #fff;
        }

        #personBg {
            height: 180px;
            position: relative;
        }

        #personBg img {
            width: 100%;
        }

        #personBg img.user-bg {
            width: 100%;
            height: 100%;
        }

        .user-bg {
            position: absolute;
        }

        #personBg .ui-img-div {
            width: 70px;
            height: 70px;
            -webkit-border-radius: 50%;
            border-radius: 50%;
        }

        .mui-table-view .mui-table-view-cell {
            padding: 10px 15px;
        }

        .user-info {
            margin-top: 20px;
            z-index: 99;
            color: #fff;
            text-align: center;
        }

        .user-head-img {
            z-index: 99;
        }
    </style>
</head>
<body>
<header id="header" class="mui-bar mui-bar-transparent">
    <h1 class="mui-title">我的资料</h1>
</header>

<div class="mui-content">
    <!--<div id="personBg">
        <img class="user-bg" src="/wxgh/image/default/person_bg.png"/>

        <div style="width:100%;height: 100%;display: flex;justify-content: center;align-items: center;flex-flow: column;">
            <div class="user-info">
                <div class="ui-img-div">
                    <img class="user-head-img"
                         src="${empty userList.avatar?'/wxgh/image/default/user.png':userList.avatar}"/>
                </div>
                <small>${wxgh_user.name}&nbsp;<span
                        class="fa ${wxgh_user.gender eq 2? 'fa-venus ui-text-orig': 'fa-mars ui-text-primary'}"></span>
                </small>
            </div>
        </div>
    </div>-->

    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <%--<a class="mui-navigate-right" href="${home}/pub/user/myinfo.html">--%>
            <%--<span class="fa fa-user-o"></span> 我的资料--%>
            <%--</a>--%>
            <label>手机：</label>
            <span>${wxgh_user.mobile}</span>
        </li>
        <li class="mui-table-view-cell">
            <%--<a class="mui-navigate-right" href="${home}/archive/list.wx?userid=${wxgh_user.userid}">--%>
            <%--<span class="fa fa-heartbeat"></span> 健康档案--%>
            <%--</a>--%>
            <label>部门：</label>
            <span>${deptName}</span>
        </li>
    </ul>
    <ul class="mui-table-view ui-margin-top-15">
        <li class="mui-table-view-cell">
            <%--<a class="mui-navigate-right" href="${home}/user/score/info.wx">--%>
            <%--<span class="fa fa-database"></span> 积分管理--%>
            <%--</a>--%>
            <label>职位：</label>
            <span>${userList.position}</span>
        </li>
        <%--<li class="mui-table-view-cell">--%>
        <%--<a class="mui-navigate-right" href="${home}/place/list.html">--%>
        <%--<span class="fa fa-clock-o"></span> 我的场地--%>
        <%--</a>--%>
        <%--</li>--%>
        <%--<li class="mui-table-view-cell">--%>
        <%--<a class="mui-navigate-right" href="${home}/apply/status.html">--%>
        <%--<span class="fa fa-volume-up"></span> 审核通知--%>
        <%--</a>--%>
        <%--</li>--%>
    </ul>

</div>

<jsp:include page="/comm/mobile/styles.jsp"></jsp:include>
</body>
</html>
