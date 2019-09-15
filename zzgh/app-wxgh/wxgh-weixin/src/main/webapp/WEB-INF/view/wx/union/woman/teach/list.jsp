<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/14
  Time: 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>女子课堂</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        .mui-card .ui-img-div {
            padding: 15px;
            height: 180px;
        }

        .mui-media-body p {
            margin: 0;
        }

        .mui-card-header:after,
        .mui-card-footer:before {
            height: 0;
        }
    </style>
</head>
<body>
<div id="refreshContainer" class="mui-content mui-scroll-wrapper">
    <div class="mui-scroll">
        <div id="teachList">
        </div>
    </div>
</div>
<script type="text/html" id="itemTpl">
    <div class="mui-card">
        <div class="mui-card-header">
            <div class="mui-media-body">
                <span class="mui-ellipsis-2">{{=it.name}}</span>
                <p>课堂时间： {{=it.time}}</p>
            </div>
        </div>
        <div class="mui-card-content">
            <div class="ui-img-div">
                <img src="{{=it.path}}" id="imgss"/>
            </div>
        </div>
        <div class="mui-card-footer">
            <div class="mui-navigate-right">查看详情</div>
        </div>
    </div>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {


        var refresh = window.refresh('#refreshContainer', {
            url: 'api/list.json',
            ispage: true,
            isBind: true,
            dataEl: '#teachList',
            tplId: '#itemTpl',
            emptyHtml: '<div class="ui-empty">暂无课堂学习哦</div>',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                mui.toast('加载失败...');
            }
        })

        function bindfn(d) {
            d['path'] = wxgh.get_thumb(d, 'notice.png');
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow('show.html?id=' + d.id);
            })
            return $item[0];
        }
    })
</script>
</body>
</html>