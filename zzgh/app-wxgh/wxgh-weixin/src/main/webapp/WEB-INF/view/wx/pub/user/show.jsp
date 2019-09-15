<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/8
  Time: 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户信息</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
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

        .user-info {
            margin-top: 20px;
            z-index: 99;
            color: #fff;
            text-align: center;
        }

        .user-head-img {
            z-index: 99;
        }

        .ui-flex .fa {
            font-size: 20px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div id="personBg">
        <img class="user-bg" src="${home}/image/default/person_bg.png"/>

        <div style="width:100%;height: 100%;display: flex;justify-content: center;align-items: center;flex-flow: column;">
            <div class="user-info">
                <div class="ui-img-div">
                    <img class="user-head-img"
                         src="${empty u.avatar?'/wxgh/image/default/user.png':u.avatar}"/>
                </div>
                <small>${u.name}&nbsp;<span
                        class="fa ${u.gender eq 2? 'fa-venus ui-text-orig': 'fa-mars ui-text-primary'}"></span>
                </small>
            </div>
        </div>
    </div>

    <ul class="mui-table-view no">
        <c:if test="${!empty u.mobile}">
            <li class="mui-table-view-cell">
                <a class="ui-flex">
                    <span><span class="fa fa-mobile"></span> 手机</span>
                    <small class="ui-right">${u.mobile}</small>
                </a>
            </li>
        </c:if>
        <c:if test="${!empty u.email}">
            <li class="mui-table-view-cell">
                <a class="ui-flex">
                    <span><span class="fa fa-envelope-o"></span> 邮箱</span>
                    <small class="ui-right">${u.email}</small>
                </a>
            </li>
        </c:if>
        <c:if test="${!empty u.weixinid}">
            <li class="mui-table-view-cell">
                <a class="ui-flex">
                    <span><span class="fa fa-weixin"></span> 微信号</span>
                    <small class="ui-right">${u.weixinid}</small>
                </a>
            </li>
        </c:if>
    </ul>

    <div class="ui-content ui-margin-top-10">
        <h5 class="ui-title"><span style="width: 30px;text-align: center" class="fa fa-sitemap"></span> 部门</h5>
        <p style="padding-left: 10px;">
            ${u.deptname}
        </p>
    </div>
    <ul class="mui-table-view">
        <li class="mui-table-view-cell">
            <a class="ui-flex">
                <span><span class="fa fa-star-o"></span> 职位</span>
                <small class="ui-right">${empty u.position?'未知职位':u.position}</small>
            </a>
        </li>
    </ul>

    <ul class="mui-table-view no ui-margin-top-10">
        <li class="mui-table-view-cell">
            <a class="ui-flex">
                <span>关注状态</span>
                <small class="ui-right">${u.status eq 1?'已关注':'未关注'}</small>
            </a>
        </li>
    </ul>
</div>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script>
    $(function () {

    })
</script>
</body>
</html>