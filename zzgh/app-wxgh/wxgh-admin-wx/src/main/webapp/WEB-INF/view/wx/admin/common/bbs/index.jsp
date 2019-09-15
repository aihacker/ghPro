<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/25
  Time: 10:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>工会活动审核中心</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        #refreshContainer {
            top: 45px;
        }

        #refreshContainer img.mui-media-object {
            width: 55px;
            max-width: 55px;
            height: 55px;
        }
    </style>
</head>
<body>
<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-status="0" class="mui-control-item mui-active">待审核
            <small>（${counts[0]}）</small>
        </a>
        <a data-status="1" class="mui-control-item">已审核
            <small>（${counts[1]}）</small>
        </a>
        <a data-status="2" class="mui-control-item">未通过
            <small>（${counts[2]}）</small>
        </a>
        <a data-status="3" class="mui-control-item">未显示
            <small>（${counts[3]}）</small>
        </a>
    </div>

    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view">
            </ul>
        </div>
    </div>
</div>

<script type="text/html" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <div class="mui-slider-right mui-disabled">
            <a class="mui-btn mui-btn-red">删除</a>
        </div>
        <div class="mui-slider-handle">
            <img class="mui-media-object mui-pull-left" src="{{=it.image}}">
            <div class="mui-media-body">{{=it.name}}
                <small class="mui-pull-right ui-text-info">{{=it.createTime}}</small>
                <p class="mui-ellipsis">{{=it.content}}</p>
            </div>
        </div>
    </li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            data: {status: 0},
            url: 'api/list.json',
            ispage: true,
            isBind: true,
            tplId: '#itemTpl',
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
                ui.alert('获取失败...')
            }
        })

        function bindfn(d) {
            d['createTime'] = ui.timeAgo(d.createTime);
            d.image = homePath + d.image;
            var $item = refresh.getItem(d)

            $item.on('tap', function () {
                mui.openWindow('show.html?id=' + d.id);
            })

            return $item[0]
        }

        var $segment = $('#segmentedControl')
        $segment.data('status', 0)
        $segment.on('tap', 'a.mui-control-item', function () {
            var status = $(this).attr('data-status')
            if (status != $segment.data('status')) {
                refresh.refresh({status: status})
                $segment.data('status', status)
            }
        })
    })
</script>
</body>
</html>
