<%--
  Created by IntelliJ IDEA.
  User: XDLK
  Date: 2016/8/30
  Time: 15:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>团队招募</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .result-div span {
            padding: 4px 15px;
            font-size: 16px;
        }

        .mui-slider .mui-slider-group .mui-slider-item img {
            height: 230px;
        }

        .ui-icon {
            width: 18px;
            height: 18px;
            display: inline-block;
            background-repeat: no-repeat;
            background-position: center;
            background-size: 100% 100%;
            vertical-align: top;
            margin-top: 2px;
        }

        .ui-icon-notice {
            background-image: url(${home}/weixin/image/union/innovation/icon/notice.png);
        }

        .ui-icon-team {
            background-image: url(${home}/weixin/image/union/innovation/icon/team.png);
        }

        .ui-icon-team-add {
            background-image: url(${home}/weixin/image/union/innovation/icon/team-add.png);
        }
    </style>
</head>

<body>


<div class="mui-content">
    <div id="slider" class="mui-slider" style="height: 200px;">
        <div class="mui-slider-group mui-slider-loop">
            <!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
            <div class="mui-slider-item mui-slider-item-duplicate">
                <a href="#">
                    <img src="${home}/weixin/image/union/innovation/icon/banner-1.png">
                </a>
            </div>
            <div class="mui-slider-item">
                <a href="#">
                    <img src="${home}/weixin/image/union/innovation/icon/banner-2.png">
                </a>
            </div>
            <!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
            <div class="mui-slider-item mui-slider-item-duplicate">
                <a href="#">
                    <img src="${home}/weixin/image/union/innovation/icon/banner-1.png">
                </a>
            </div>
        </div>
        <div class="mui-slider-indicator mui-text-right">
            <div class="mui-indicator mui-active"></div>
            <div class="mui-indicator"></div>
        </div>
    </div>

    <div class="ui-margin-top-10">
        <ul class="mui-table-view">
            <li class="mui-table-view-cell">
                <a href="${home}/wx/union/innovation/team/create.html" class="mui-navigate-right">
                    <span class="ui-icon ui-icon-team-add"></span>&nbsp;创建团队
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a href="${home}/wx/union/innovation/team/all.html" class="mui-navigate-right">
                    <span class="ui-icon ui-icon-team"></span>&nbsp;所有团队
                </a>
            </li>
            <li class="mui-table-view-cell">
                <a href="${home}/wx/union/innovation/team/my.html" class="mui-navigate-right">
                    <span class="ui-icon ui-icon-team"></span>&nbsp;我的团队
                </a>
            </li>
        </ul>
    </div>

</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript">
    mui.init()

    mui("#slider").slider({
        interval: 5000
    });
</script>

</body>

</html>
