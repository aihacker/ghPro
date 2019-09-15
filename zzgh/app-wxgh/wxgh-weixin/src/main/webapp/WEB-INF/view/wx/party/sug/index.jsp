<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/2
  Time: 14:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>

<head>
    <meta charset="UTF-8">
    <title>我的建议</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        #refreshContainer img.mui-media-object {
            width: 55px;
            max-width: 55px;
            height: 55px;
        }
    </style>
</head>

<body>

<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view">
        </ul>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script type="text/javascript">

    var status = '${param.status}'

    var info = {action: 'list'}
    var refresh = refresh('#refreshContainer', {
        url: homePath + '/wx/party/sug/list.json',
        data: info,
        ispage: true,
        bindFn: bindfn,
        errorFn: function (type) {
            console.log(type)
        }
    })

    function bindfn(d) {
        var sugs = d.sugs;
        if (sugs && sugs.length > 0) {
            for (var i = 0; i < sugs.length; i++) {
                var sug = sugs[i]
                var $item = $('<li class="mui-table-view-cell mui-media">' +
                    '<a href="javascript:;">' +
                    //'<img class="mui-media-object mui-pull-left" src="' + wxgh.get_image(sug.image) + '">' +
                    '<div class="mui-media-body">@ ' + sug.username +
                    '<p class="mui-ellipsis">' + (new Date(sug.addTime).format('yyyy-MM-dd')) + '</p>' +
                    '</div></a></li>')
                $item.data('id', sug.id)

                $item.on('tap', function () {
                    var id = $(this).data('id')
                    mui.openWindow(homePath + '/wx/party/sug/show/index.html?id=' + id)
                })
                refresh.addItem($item[0])
            }
        } else {
            refresh.addItem('<li class="mui-table-view-cell mui-text-center">您未提出任何建议哦</li>')
        }
    }
</script>
</body>

</html>
