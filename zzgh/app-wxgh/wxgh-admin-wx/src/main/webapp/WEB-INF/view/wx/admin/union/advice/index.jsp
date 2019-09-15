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
    <title>创新建议管理中心</title>
    <jsp:include page="/comm/mobile/styles.jsp"/>
    <style>
        #refreshContainer {
            top: 46px;
        }
    </style>
</head>
<body>

<div class="mui-content">
    <div id="segmentedControl"
         class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted mui-segmented-control-primary">
        <div class="mui-scroll">
            <a data-type="0" class="mui-control-item mui-active">待审核
            </a>
            <a data-type="1" class="mui-control-item">已通过
            </a>
            <a data-type="2" class="mui-control-item">未通过
            </a>
            <a data-type="3" class="mui-control-item">资金申请
                <small>（待审核）</small>
            </a>
            <a data-type="4" class="mui-control-item">资金申请
                <small>（已通过）</small>
            </a>
            <a data-type="5" class="mui-control-item">资金申请
                <small>（未通过）</small>
            </a>
        </div>
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
            url: homePath + '/wx/admin/union/advice/list.json',
            data: info,
            ispage: true,
            bindFn: bindfn,
            errorFn: function (type) {
                console.log(type)
            }
        })

        function bindfn(d) {
            var ads = d.advices
            if (ads && ads.length > 0) {
                for (var i in ads) {
                    var a = ads[i]
                    if(a.deptname)
                        a.deptname = '-' + a.deptname;
                    else
                        a.deptname = '';
                    var $item = $('<li class="mui-table-view-cell">' +
                        '<a class="mui-navigate-right"> ' + a.title +
                        '<p>申请人：' + a.username + a.deptname + '</p>' +
                        '<p>类型：' + getType(a.adviceType) + '</p>' +
                        '<p>申请时间：' + (new Date(a.addTime).format('yyyy-MM-dd hh:mm')) + '</p> </a> </li>')
                    $item.data('id', a.adviceId)
                    refresh.addItem($item[0])
                    $item.on('tap', function () {
                        var id = $(this).data('id')
                        if (refresh.getParam().status >= 3) {
                            mui.openWindow(homePath + '/wx/admin/union/advice/micro/index.html?adviceId=' + id)
                        } else {
                            mui.openWindow(homePath + '/wx/admin/union/advice/show/index.html?id=' + id)
                        }
                    })
                }
            } else {
                refresh.addItem('<li class="mui-table-view-cell"><div class="mui-text-center ui-text-info">暂无创新建议哦</div></li>')
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
                str = '技能'
            } else if (type == 2) {
                str = '营销'
            } else if (type == 3) {
                str = '服务'
            } else if (type == 4) {
                str = '管理'
            } else if (type == 5) {
                str = '其他'
            } else {
                str = '未知类型'
            }
            return str
        }
    })
</script>
</body>
</html>
