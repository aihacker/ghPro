<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/8/7
  Time: 15:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>协会管理中心</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
    </style>
</head>
<body>
<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-status="0" class="mui-control-item mui-active">待审核</a>
        <a data-status="1" class="mui-control-item">现有协会</a>
        <a data-status="2" class="mui-control-item">未通过</a>
    </div>
    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view">
            </ul>
        </div>
    </div>
</div>

<script type="text/template" id="itemTpl">
    <li class="mui-table-view-cell mui-media">
        <div class="mui-slider-right mui-disabled">
            <a class="mui-btn mui-btn-red ui-del">删除</a>
        </div>
        <div class="mui-slider-handle">
            <img class="mui-media-object mui-pull-left" src="${home}{{=it.avatar}}"/>
            <div class="mui-media-body">
                <div class="mui-ellipsis">{{=it.name}}</div>
                <p class="mui-ellipsis">{{=it.info}}</p>
            </div>
        </div>
    <li>
</script>
<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/mobile/js/refresh.js"></script>
<script>
    $(function () {
        var refresh = window.refresh('#refreshContainer', {
            url: 'api/list_group.json',
            data: {status: 0},
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
            d['info'] = d.info ? d.info : '暂无简介'
            d['avatar'] = d.avatar ? d.avatar : '/image/default/chat.png'
            var $item = refresh.getItem(d)
            $item.on('tap', function () {
                mui.openWindow('show.html?id=' + d.id)
            })
            $item.on('tap', '.mui-slider-right .ui-del', function (e) {
                e.stopPropagation()
                ui.confirm('是否删除？', function () {
                    wxgh.request.getURL('api/delete.json?id=' + d.id, function () {
                        ui.showToast('删除成功', function () {
                            refresh.refresh({status: d.status})
                        })
                    })
                })
            })
            return $item[0];
        }

        $('#segmentedControl').on('tap', '.mui-control-item', function () {
            var status = $(this).data('status')
            refresh.refresh({status: status})
        })
    })
</script>
</body>
</html>