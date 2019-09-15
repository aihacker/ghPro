<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/4/20
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>四小审核中心</title>
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
        <a data-status="0" class="mui-control-item mui-active">待审核
            <small>（${counts[0]}）</small>
        </a>
        <a data-status="1" class="mui-control-item">已审核
            <small>（${counts[1]}）</small>
        </a>
        <a data-status="2" class="mui-control-item">未通过
            <small>（${counts[2]}）</small>
        </a>
    </div>

    <div id="refreshContainer" class="mui-content mui-scroll-wrapper">
        <div class="mui-scroll">
            <ul class="mui-table-view">
            </ul>
        </div>
    </div>
</div>

<jsp:include page="/comm/mobile/scripts.jsp"/>
<script src="${home}/comm/js/refresh.js"></script>
<script>
    mui.init({gestureConfig: {longtap: true}})
    var _self = homePath + '/wx/admin/union/four/'

    mui('.mui-scroll-wrapper').scroll({
        deceleration: 0.0005
    })

    $(function () {

        var $table = $('#refreshContainer ul.mui-table-view')

        var loading = new ui.loading('加载中...')

        loading.show()
        request(0)

        $('#segmentedControl').on('tap', 'a.mui-control-item', function () {
            if (!$(this).hasClass('mui-active')) {
                var status = $(this).attr('data-status')
                loading.show()
                request(status)
            }
        })

        var emptyHtml = '<li class="mui-table-view-cell"><div class="mui-text-center ui-text-info">暂无记录哦</div></li>'

        function request(status) {
            mui.getJSON(_self + 'list.json', {action: 'list', status: status}, function (res) {
                if (res.ok) {
                    var ps = res.data
                    $table.empty()
                    if (ps && ps.length > 0) {
                        for (var i in ps) {
                            var p = ps[i]
                            var $item = $('<li class="mui-table-view-cell"><div> ' + p.marketing +
                                '<span class="mui-badge mui-badge-' + (p.deviceStatus == 1 ? 'success' : 'primary') + ' mui-pull-right">' + (p.deviceStatus == 1 ? '新增' : '更换') + '</span> </div><p>' +
                                '<span>' + p.fpName + ' - ' + p.fpcName + ' x' + p.numb + '</span>' +
                                '<span class="mui-pull-right">' + p.suggest + '</span></p></li>')
                            $item.data('data', p)
                            $table.append($item)

                            $item.on('tap', function () {
                                var d = $(this).data('data')
                                mui.openWindow(homePath + '/wx/admin/union/four/show/index.html?id=' + d.id + '&deviceStatus=' + d.deviceStatus)
                            })

                            $item.on('longtap', function () {
                                var $self = $(this)
                                var cf = confirm('是否删除？')
                                if (cf) {
                                    var id = $self.data('data').id
                                    mui.getJSON(_self, {action: 'del', id: id}, function (res) {
                                        if (res.ok) {
                                            ui.showToast('删除成功', function () {
                                                var $stat = $('#segmentedControl a[data-status=' + status + '] small')
                                                var val = $stat.text().trim()
                                                val = val.substring(1, val.length - 1)
                                                $stat.text('（' + (Number(val) - 1) + '）')
                                                $self.remove()

                                                if ($table.find('li.mui-table-view-cell').length <= 0) {
                                                    $table.append(emptyHtml)
                                                }
                                            })
                                        } else {
                                            alert('删除失败')
                                        }
                                    })
                                }
                            })
                        }
                    } else {
                        $table.append(emptyHtml)
                    }

                    loading.hide()
                    mui('.mui-scroll-wrapper').scroll().scrollTo(0, 0, 200)
                }
            })
        }
    })
</script>
</body>
</html>
