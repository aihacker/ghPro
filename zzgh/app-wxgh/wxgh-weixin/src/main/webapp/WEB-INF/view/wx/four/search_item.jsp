<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>搜索四小台账</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
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

        #list li {
            border-bottom: 1px solid #ebebeb;
            background-color: #ffffff;
            padding: 10px 16px;
        }

        #list a {
            display: flex;
            color: #222;
        }

        #list div {
            flex: 1;
            padding: 6px 14px 0 14px;
            position: relative;
        }

        #list span {
            display: block;
            height: 20px;
            line-height: 20px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        #list small {
            color: #8f8f94;
        }

        #list em {
            background: url("${home}/image/common/right.gif") no-repeat center;
            display: block;
            height: 12px;
            width: 5px;
            background-size: 100% 100%;
            position: absolute;
            top: 50%;
            margin-top: -6px;
            right: 5px;
        }

        #list .index {
            border-radius: 50%;
            display: block;
            height: 40px;
            line-height: 40px;
            width: 40px;
            text-align: center;
            background-color: #5ed198;
            color: #ffffff;
            margin: 10px auto;
        }
    </style>
</head>

<body>
<header class="mui-bar mui-bar-nav ui-head" style="display: flex;">
    <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>

    <div class="search_box">
        <img src="${home}/weixin/image/four/list_search.png">
        <input type="text" id="searchInput" placeholder="输入四小需求类型或台账名称">
        <span id="cleanCtn"></span>
    </div>
</header>
<input type="hidden" id="deptStr" value="${deptStr}">

<div class="mui-content" style="padding-top: 34px;">
    <ul id="list">
        <%--<li>--%>
        <%--<a href="${home}/four/show.html?id=3257&type=2">--%>
        <%--<span class="index">1</span>--%>

        <%--<div>--%>
        <%--<span>乒乓球拍</span>--%>
        <%--<small>采购时间：2017年12月09日</small>--%>
        <%--<em></em>--%>
        <%--</div>--%>
        <%--</a>--%>
        <%--</li>--%>
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
    });

    function initSearchData(value) {
        if (value == '') {
            return;
        }

        var info = {
            name: value,
            deptStr: $('#deptStr').val()
        };

        var $list = $('#list');
        requestServer(info, homePath + '/wx/four/search_i.json', function (result) {
//                alert(JSON.stringify(result));
            var data = result.data;
            $list.find('li').remove();
            if (data == null || data.length <= 0) {
                var $noData = $('<li id="noData" style="background-color: #efeff4;">无结果</li>');
                $list.append($noData);
            } else {
                for (var n = 0; n < data.length; n++) {
                    var item = data[n];
                    var href = homePath + "/wx/four/show.html?id=" + item.id + "&type=2";
                    var $li = $('<li>' +
                    '<a href="' + href + '">' +
                    '<span class="index">' + (n * 1 + 1) + '</span>' +
                    '<div>' +
                    '<span>' + item.fpcName + '</span>' +
                    '<small>采购时间：' + item.time + '</small>' +
                    '<em></em>' +
                    '</div>' +
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
