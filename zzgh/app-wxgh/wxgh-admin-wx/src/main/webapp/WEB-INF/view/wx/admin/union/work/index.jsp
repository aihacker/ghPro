<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/6/7
  Time: 18:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>工作坊管理中心</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        #refreshContainer {
            top: 45px;
        }
    </style>
</head>
<body>

<div class="mui-content">
    <div id="segmentedControl"
         class="mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <a data-type="0" class="mui-control-item mui-active">待审核
        </a>
        <a data-type="1" class="mui-control-item">已通过
        </a>
        <a data-type="2" class="mui-control-item">未通过
        </a>
    </div>

    <div id="refreshContainer" class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view">
            </ul>
        </div>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/js/refresh.js"></script>
<script>
    $(function () {
        var info = {action: 'list', status: 0}
        var refresh = window.refresh('#refreshContainer', {
            url: homePath + '/wx/admin/union/work/list.json',
            data: info,
            ispage: true,
            bindFn: bindfn,
            errorFn: function (type) {
                console.error(type)
            }
        })

        function bindfn(d) {
            var ads = d.shops
            if (ads && ads.length > 0) {
                for (var i in ads) {
                    var a = ads[i]
                    if(a.deptname)
                        var dname = '-' + a.deptname;
                    else
                        var dname = ''
                    var $item = $('<li class="mui-table-view-cell">' +
                        '<a class="mui-navigate-right"> ' + a.itemName +
                        '<p>申请人：' + a.username +  dname + '</p>' +
                        '<p>类型：' + getType(a.shopType) + '</p>' +
                        '<p>申请时间：' + (new Date(a.addTime).format('yyyy-MM-dd hh:mm')) + '</p> </a> </li>')
                    $item.data('id', a.shopId)
                    refresh.addItem($item[0])
                    $item.on('tap', function () {
                        var id = $(this).data('id')
                        mui.openWindow(homePath + '/wx/admin/union/work/show/index.html?id=' + id)
                    })
                }
            } else {
                refresh.addItem('<li class="mui-table-view-cell"><div class="mui-text-center ui-text-info">暂无工作坊哦</div></li>')
            }
        }

        $('#segmentedControl').on('tap', 'a.mui-control-item', function () {
            if (!$(this).hasClass('mui-active')) {
                var type = $(this).data('type')
                refresh.refresh({status: type})
            }
        })

        function getType(type) {
            var str
            if (type == 1) {
                str = '创新工作坊'
            } else if (type == 2) {
                str = '劳模工作室'
            }
            return str
        }
    })
</script>
</body>
</html>
