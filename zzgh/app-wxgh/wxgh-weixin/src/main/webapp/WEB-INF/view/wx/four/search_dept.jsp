<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>搜索部门</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <link rel="stylesheet" href="${home}/libs/mui-v3.7.0/css/mui.previewimage.css"/>

    <style>
        * {
            list-style-type: none;
        }

        .search_box {
            flex: 1;
            height: 32px;
            line-height: 32px;
            display: flex;
            background-color: #ffffff;
            margin: 6px 12px;
            border-radius: 5px;
            position: relative;
        }

        .search_box img {
            width: 18px;
            height: 18px;
            vertical-align: middle;
            margin-top: 6px;
            margin-left: 6px;
        }

        #searchInput {
            height: 20px;
            line-height: 20px;
            flex: 1;
            font-size: .8em;
            background-color: transparent;
            border: 0;
            padding: 0 6px;
            margin-top: 6px;
            margin-right: 32px;
        }

        #noData {
            color: #515c68;
            text-align: center;
            margin: 20% auto;
            font-size: .8em;
            background-color: #efeff4;
            border-color: #efeff4;
        }

        #cleanCtn {
            display: block;
            width: 18px;
            height: 18px;
            position: absolute;
            background: url("${home}/weixin/image/four/x.png") no-repeat center;
            background-size: 100% 100%;
            opacity: .7;
            border-radius: 50%;
            right: 12px;
            top: 50%;
            margin-top: -9px;
        }

        #list {
            margin-top: 16px;
        }

        #list .list_item {
            height: 45px;
            line-height: 45px;
            padding-left: 12px;
            background-color: #ffffff;
            border-bottom: 1px solid #ebebeb;
        }

        .list_item a {
            color: #222;
            display: block;
            height: 100%;
        }
    </style>
</head>

<body>

<header class="mui-bar mui-bar-nav ui-head" style="display: flex;">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <div class="search_box">
        <img src="${home}/weixin/image/four/list_search.png">
        <input type="text" id="searchInput" placeholder="搜索部门">
        <span id="cleanCtn"></span>
    </div>
</header>
<input type="hidden" id="groupId" value="${groupId}">

<div class="mui-content" style="padding-top: 34px;">
    <ul id="list">
        <%--<c:if test1="${empty list}">--%>
        <%--<li id="noData">--%>
        <%--无结果--%>
        <%--</li>--%>
        <%--</c:if>--%>
        <%--<c:forEach items="${list}" var="item">--%>
        <%--<li class="list_item">--%>
        <%--<a href="${home}/four/details_item.html?deptStr=${item.deptStr}">--%>
        <%--${item.deptStr}--%>
        <%--</a>--%>
        <%--</li>--%>
        <%--</c:forEach>--%>
    </ul>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script type="text/javascript" src="${home}/weixin/script/area/request.js"></script>
<script type="text/javascript">
    var homePath = '${home}';
    $(function () {

        initSearchData($('#searchInput').val());

        $('#searchInput').focus().bind('input propertychange', function (e) {
            var value = $(this).val();
            initSearchData(value);
        });

        //
        $('#cleanCtn').click(function () {
            $('#searchInput').val('').focus();
        });
    })

    function initSearchData(value) {
        if (value == '') {
            return;
        }
        var info = {
            deptName: value
        };
        var $list = $('#list');
        requestServer(info, homePath + '/wx/four/search_d.json', function (result) {
//                alert(JSON.stringify(result));
            var data = result.data;
            $list.find('li').remove();
            if (data == null || data.length <= 0) {
                var $noData = $('<li id="noData" style="background-color: #efeff4;">无结果</li>');
                $list.append($noData);
            } else {
                for (var n = 0; n < data.length; n++) {
                    var item = data[n];
                    var $li = $('<li class="list_item">' +
                    '<a href="${home}/wx/four/details_item.html?deptStr=' + item + '">' + item +
                    '</a>' +
                    '</li>');
                    $list.append($li);
                }
            }
        });
    }

</script>
</body>

</html>
