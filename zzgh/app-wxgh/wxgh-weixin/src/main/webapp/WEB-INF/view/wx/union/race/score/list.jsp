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
    <title>选择比赛</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        body,
        .ui-race-list {
            background-color: #efeff4;
        }

        .ui-race-list.mui-table-view:after,
        .ui-race-list.mui-table-view:before {
            height: 0;
        }

        .ui-race-list .mui-table-view-cell:first-child {
            margin: 0;
        }

        .ui-race-list .mui-table-view-cell {
            margin: 8px 0;
            background-color: #fff;
        }

        .ui-race-list .mui-table-view-cell:after {
            height: 0;
        }

        .ui-race-list .ui-img-div {
            max-width: 100px;
            width: 90px;
            height: 70px;
            border: 1px solid #ddd;
            padding: 1px;
        }

        .ui-race-list .mui-media-body {
            min-height: 80px;
        }

        .ui-race-list .ui-bottom {
            border-top: 1px solid #e2e2e2;
        }

        .ui-race-list .ui-bottom .fa-clock-o {
            color: #0099e9;
        }
    </style>
</head>

<body>

<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <ul class="mui-table-view ui-race-list">
        </ul>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/libs/weixin/weixin.min.js" type="text/javascript"></script>
<script src="${home}/comm/js/refresh.js"></script>
<script type="text/javascript">

    var type = '${param.type}'

    var info = {
        data: {action: 'list'},
        ispage: true,
        bindFn: bindfn,
        errorFn: function (type) {
            console.log(type)
        }
    }
    if (type) {
        info['url'] = homePath + '/wx/union/race/score/api/list.json?bianpaiIs=0'
    } else {
        info['url'] = homePath + '/wx/union/race/score/api/list.json'
    }

    var refresh = window.refresh('#refreshContainer', info)

    function bindfn(d) {
        var races = d.races;
        if (races && races.length > 0) {
            for (var i = 0; i < races.length; i++) {
                var d = races[i]
                var li = document.createElement('li')
                li.className = 'mui-table-view-cell mui-media'

                var img = d.img ? wxgh.get_image(d.img) : homePath + '/image/common/nopic.gif';

                li.innerHTML = '<a href="javascript:;">' +
                    '<div class="ui-img-div mui-pull-left mui-media-object"><img class="" src="' + img + '"></div>' +
                    '<div class="mui-media-body">' +
                    '<span class="mui-ellipsis">' + d.name + '</span>' +
                    '<span class="mui-badge mui-badge-' + (d.bianpaiIs == 1 ? 'success' : 'default') + ' mui-pull-right">' + (d.bianpaiIs == 1 ? '已编排' : '未编排') + '</span>' +
                    '<p class="mui-ellipsis-2">' + d.info + '</p>' +
                    '</div><div class="ui-bottom">' +
                    '<span class="fa fa-clock-o"></span> ' +
                    '<small class="ui-time">' + new Date(d.startTime).format('yyyy-MM-dd hh:mm') + ' 至 ' + new Date(d.endTime).format('yyyy-MM-dd hh:mm') + '</small>' +
                    '</div></a>'
                $(li).data('id', d.id).data('bianpai', d.bianpaiIs)
                $(li).on('tap', function () {
                    var id = $(this).data('id')
                    if (!type) {
                        mui.openWindow(homePath + '/wx/union/race/score/match.html?id=' + id)
                    } else {
                        mui.openWindow(homePath + '/wx/union/race/score/match2.html?id=' + id)
                    }
                })
                refresh.addItem(li)
            }
        }else{
            refresh.addItem('<li class="mui-table-view-cell"><div class="mui-text-center ui-text-info">暂无比赛哦</div><li>')
        }
    }
</script>
</body>

</html>
