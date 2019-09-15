<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

<head>
    <title>往期回顾</title>
    <jsp:include page="/comm/mobile/styles.jsp"></jsp:include>
    <style>
        .mui-content > .mui-table-view:first-child {
            margin-top: 0;
        }

        .mui-table-view .mui-media-object {
            height: 70px;
            width: 68px;
            max-width: 70px;
        }
    </style>
</head>

<body>


<div class="mui-content">
    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view">
            </ul>
        </div>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"></jsp:include>
<script type="text/javascript" src="${home}/comm/js/refresh.js"></script>

<script type="text/javascript">

    function formatTime(time) {
        //   格式：yyyy-MM-dd hh:mm:ss
        var date = new Date(time);
        Y = date.getFullYear() + '-';
        M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
        D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
        h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
        m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':';
        s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();

        return Y + M + D;
    }

    var refresh = refresh('#refreshContainer', {
        url: 'resultlist.json',
        ispage: false,
        bindFn: bindfn,
        errorFn: function (type) {
            console.log(type)
        }
    })

    function bindfn(data) {
        var list = data.list;
        for (var i = 0; i < list.length; i++) {
            var li = document.createElement('li')
            li.className = 'mui-table-view-cell mui-media'
            li.innerHTML = '<a href="info.html?id=' + list[i].id + '"><img class="mui-media-object mui-pull-left" src="' + wxgh.get_image(list[i].coverImg) + '"/><div class="mui-media-body"><p class="mui-ellipsis-2">' + list[i].title + '</p><p>' + new Date(list[i].addDate).format('yyyy-MM-dd hh:mm') + '</p></div></a>'
            refresh.addItem(li)
        }
    }
    $(function () {
        $("#refreshContainer").on("tap", "li a", function () {
            window.location.href = $(this).attr("href");
        });
    })
</script>
</body>

</html>
