<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/8
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>今年需要更换的设备</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body, .mui-content {
            /*background-color: #fff;*/
        }

        .mui-search {
            margin: 22px 12px -6px 12px;
        }

        .mui-content > .mui-table-view:first-child {
            margin-top: 0;
        }

        .ui-select-head {
            text-align: center;
        }

        .ui-select-head,
        .ui-select-head li,
        .ui-select-head select {
            background-color: #efeff4;
        }

        .ui-select-head select {
            padding: 0px 5px;
            margin-bottom: 0px;
            line-height: 18px;
        }

        .left-img {
            width: 40px;
            height: 40px;
            display: block;
            border-radius: 50%;
            color: #fff;
            margin-top: 36px;
            margin-right: 10px;
            text-align: center;
        }

        .buytime-span {
            font-size: 14px;
            color: #8f8f94;
        }

        .mui-media .mui-ellipsis {
            color: #000;
            padding-right: 20px;
            padding-top: 4px;
        }

        .left-color-zhise {
            background-color: #d0659b;
        }

        .left-color-huang {
            background-color: #ff972d;
        }

        .left-color-green {
            background-color: #5ed198;
        }

        .left-color-blue {
            background-color: #669aff;
        }
    </style>
</head>

<body>

<header class="mui-bar mui-bar-nav ui-head">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <h1 class="mui-title">今年需要更换的设备</h1>
</header>

<div class="mui-content" style="padding-top: 44px;">

    <ul class="mui-table-view">
        <c:forEach items="${list}" var="item" varStatus="status">
            <li class="mui-table-view-cell mui-media">
                <a class="mui-navigate-right" href="${home}/wx/four/show.html?id=${item.id}">
                    <div class="mui-media-object mui-pull-left"><span
                            class="left-img left-color-green">${status.index+1}</span></div>
                    <div class="mui-media-body">
                        <p class="mui-ellipsis">
                                ${item.fpcName}
                         <span class="mui-right mui-pull-right buytime-span">
                    品牌：${item.brand}
                </span>
                        </p>

                        <p class="mui-ellipsis"></p>

                        <p class="mui-ellipsis">
                        <span
                                class="buytime-span">采购时间：
            <c:choose>
                <c:when test="${empty item.buyTime}">
                    未知时间
                </c:when>
                <c:otherwise>${item.buyTime}</c:otherwise>
            </c:choose>
            </span>
                        </p>

                        <p class="mui-ellipsis">
                <span class="buytime-span">
                    型号：${item.modelName}
                </span>
                        </p>
                        <p class="mui-ellipsis">
                <span class="buytime-span">
                    营销中心：${item.marketName}
                </span>
                        </p>
                    </div>
                </a>
            </li>
        </c:forEach>
    </ul>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()

    var homePath = '${home}'

    //    window.onload = page.init()

</script>
</body>

</html>
