<%--
  Created by IntelliJ IDEA.
  User: hhl
  Date: 2017-07-27
  Time: 8:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>个人中心</title>
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
    <h1 class="mui-title">个人中心</h1>
</header>

<div class="mui-content">
    <div id="personBg">
        <img class="user-bg" src="${home}/image/default/person_bg.png"/>

        <div style="width:100%;height: 100%;display: flex;justify-content: center;align-items: center;flex-flow: column;">
            <div class="user-info">
                <div class="ui-img-div">
                    <img class="user-head-img"
                         src="${empty wxgh_user.avatar?'/wxgh/image/default/user.png':wxgh_user.avatar}"/>
                </div>
                <small>${wxgh_user.name}&nbsp;<span
                        class="fa ${wxgh_user.gender eq 2? 'fa-venus ui-text-orig': 'fa-mars ui-text-primary'}"></span>
                </small>
            </div>
        </div>
    </div>

    <ul class="mui-table-view no">
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right" href="show.html?userid=${wxgh_user.userid}">
                <span class="fa fa-user-o"></span> 我的资料
            </a>
        </li>
        <%--<li class="mui-table-view-cell">--%>
            <%--<a class="mui-navigate-right" href="notif.html">--%>
                <%--<span class="fa fa-bell-o"></span> 通知--%>
            <%--</a>--%>
        <%--</li>--%>
        <li class="mui-table-view-cell">
            <a class="mui-navigate-right" href="${home}/wx/pub/archive/list.html?userid=${wxgh_user.userid}">
                <span class="fa fa-heartbeat"></span> 健康档案
            </a>
        </li>
    </ul>
    <%--<ul class="mui-table-view ui-margin-top-15 no">--%>
        <%--<li class="mui-table-view-cell">--%>
            <%--<a class="mui-navigate-right" href="score/index.html">--%>
                <%--<span class="fa fa-database"></span> 积分管理--%>
            <%--</a>--%>
        <%--</li>--%>
        <%--&lt;%&ndash;<li class="mui-table-view-cell">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<a class="mui-navigate-right" href="${home}/act/myact.wx?userid=${wxgh_user.userid}">&ndash;%&gt;--%>
        <%--&lt;%&ndash;<span class="fa fa-flag-o"></span> 我的活动&ndash;%&gt;--%>
        <%--&lt;%&ndash;</a>&ndash;%&gt;--%>
        <%--&lt;%&ndash;</li>&ndash;%&gt;--%>
    <%--</ul>--%>

    <%--<ul class="mui-table-view ui-margin-top-15 no">--%>
        <%--<li class="mui-table-view-cell">--%>
            <%--<a class="mui-navigate-right" href="score/transfer.html">--%>
                <%--<span class="fa fa-share-alt"> 积分转移</span>--%>
            <%--</a>--%>
        <%--</li>--%>
    <%--</ul>--%>
</div>

<jsp:include page="/comm/mobile/styles.jsp"></jsp:include>
</body>
</html>



